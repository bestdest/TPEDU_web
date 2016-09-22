package com.hanyang.iis.tpedu.FeatureExtraction;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.Prun.LeafChanger;
import com.hanyang.iis.tpedu.Prun.Prunner;
import com.hanyang.iis.tpedu.TPEMatch.Matcher;
import com.hanyang.iis.tpedu.TPEMatch.Pair;
import com.hanyang.iis.tpedu.TPEMatch.PatternNode;
import com.hanyang.iis.tpedu.TPEMatch.PatternQuery;
import com.hanyang.iis.tpedu.TPEMatch.QueryChk;
import com.hanyang.iis.tpedu.dao.TargetDAO;
import com.hanyang.iis.tpedu.dto.TargetDTO;


public class PatternScoreExtractor {
	
	public float PatternScoreExtractor_forService(int numOfGrade, String Pattern_ParseTree,String tableName){
		float pattern_score=0;
		int total_Matched_size = 0;
		int[] matched_count_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		float[] score_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		
		try{
			SentenParser SentenP = new SentenParser();
			LeafChanger LC = new LeafChanger();
			Prunner Prun = new Prunner();
			Pattern_ParseTree = LC.ChangeLeaf(Pattern_ParseTree);
			Pattern_ParseTree = Prun.Prun_Leafs(Pattern_ParseTree);
			Pattern_ParseTree = Prun.Prunning(Pattern_ParseTree);
			
			TargetDAO Targetdao = new TargetDAO();
			List<PatternNode> Pattern_PostTree = Build_Pattern_PostTree(Pattern_ParseTree);
			List<TargetDTO> TargetList = Targetdao.GetTargetList_PS(tableName);
			List<TargetDTO> MatchedTargetList = new ArrayList<TargetDTO>();
			
			boolean isExistPattern = false;
			
			for (int i = 0; i < TargetList.size(); i++) {
				TargetDTO curTarget = TargetList.get(i);
				String curTargetPattern = curTarget.getPattern();
				String prunedCTP = LC.ChangeLeaf(curTargetPattern);
				prunedCTP = Prun.Prun_Leafs(prunedCTP);
				prunedCTP = Prun.Prunning(prunedCTP);
				if (Pattern_ParseTree.equals(prunedCTP)) {
					
					pattern_score = curTarget.getPattern_score();
					if(pattern_score!=0.0){
						isExistPattern = true;
						break;
					}else
						continue;
				}
			}
			
			if (isExistPattern) {
				System.out.println("Pattern Exist : " + pattern_score);
				
			} else { // 아니면 TPE Matching을 통해 Pattern Score 계산
				pattern_score=0;
				for (int i = 0; i < TargetList.size(); i++) {

					List<TargetNode> curTargetPostTree = null;
					curTargetPostTree = SentenP.GetParseTreePostTree(TargetList.get(i).getSentence(),TargetList.get(i).getPattern());
					
					if (curTargetPostTree == null) {
						System.out.println("cur target fail!!");
						return pattern_score;
					}

					if (matching(curTargetPostTree, Pattern_PostTree)) {
						
						MatchedTargetList.add(TargetList.get(i));
					}
				}
				
				total_Matched_size = MatchedTargetList.size();
				ArrayList<Integer> matched = new ArrayList<Integer>();
				
				// 수준 별로 매칭된 패턴의 결과를 세는 부분
				for (int i = 0; i < MatchedTargetList.size(); i++) {
					if (numOfGrade == 3) {
						if (MatchedTargetList.get(i).getGrage() == 0) {
							matched_count_byGrade[0]++;
							matched.add(0);
						} else if (MatchedTargetList.get(i).getGrage() == 1) {
							matched_count_byGrade[1]++;
							matched.add(1);
						} else if (MatchedTargetList.get(i).getGrage() == 2) {
							matched_count_byGrade[2]++;
							matched.add(2);
						}
					}else if(numOfGrade ==5){
						if (MatchedTargetList.get(i).getGrage() == 0) {
							matched_count_byGrade[0]++;
							matched.add(0);
						} else if (MatchedTargetList.get(i).getGrage() == 1) {
							matched_count_byGrade[1]++;
							matched.add(1);
						} else if (MatchedTargetList.get(i).getGrage() == 2) {
							matched_count_byGrade[2]++;
							matched.add(2);
						}else if (MatchedTargetList.get(i).getGrage() == 3) {
							matched_count_byGrade[3]++;
							matched.add(3);
						} else if (MatchedTargetList.get(i).getGrage() == 4) {
							matched_count_byGrade[4]++;
							matched.add(4);
						}
					}
				}

				if (total_Matched_size != 0) { // 매칭된 결과가 하나라도 있으면 패턴 스코어 산정
					for (int i = 0; i < numOfGrade; i++) {
						score_byGrade[i] = (i + 1) * ((float) matched_count_byGrade[i] / (float) total_Matched_size);
						pattern_score += score_byGrade[i];
					}
					// System.out.println(pattern_Score);
					float standardDeviation = getStandardDeviation(matched);
					// pattern_Score = pattern_Score*(((float)SEN_COUNT-(float)total_Matched_size)/(float)SEN_COUNT);
					pattern_score = (pattern_score * (1 / (standardDeviation + 1)));
					System.out.println(MatchedTargetList.size() + "개의 표준편차 : " + standardDeviation);

				} else { // 없으면 가장 어려운 문장 구조로 판단해서 MAX 값 입력
					pattern_score = (float) numOfGrade;
				}
			}
			
			return pattern_score;	
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public float PatternScoreExtractor(int numOfGrade, String sen,String tableName){
		float pattern_score=0;
		int total_Matched_size = 0;
		int[] matched_count_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		float[] score_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		
		try{
			SentenParser SentenP = new SentenParser();
			String Pattern_ParseTree = SentenP.GetParseTree(sen);
			LeafChanger LC = new LeafChanger();
			Prunner Prun = new Prunner();
			Pattern_ParseTree = LC.ChangeLeaf(Pattern_ParseTree);
			Pattern_ParseTree = Prun.Prun_Leafs(Pattern_ParseTree);
			Pattern_ParseTree = Prun.Prunning(Pattern_ParseTree);
			
			TargetDAO Targetdao = new TargetDAO();
			List<PatternNode> Pattern_PostTree = Build_Pattern_PostTree(Pattern_ParseTree);
			List<TargetDTO> TargetList = Targetdao.GetTargetList_PS(tableName);
			List<TargetDTO> MatchedTargetList = new ArrayList<TargetDTO>();
			
			boolean isExistPattern = false;
			
			for (int i = 0; i < TargetList.size(); i++) {
				TargetDTO curTarget = TargetList.get(i);
				String curTargetPattern = curTarget.getPattern();
				String prunedCTP = LC.ChangeLeaf(curTargetPattern);
				prunedCTP = Prun.Prun_Leafs(prunedCTP);
				prunedCTP = Prun.Prunning(prunedCTP);
				if (Pattern_ParseTree.equals(prunedCTP)) {
					
					pattern_score = curTarget.getPattern_score();
					if(pattern_score!=0.0){
						isExistPattern = true;
						break;
					}else
						continue;
				}
			}
			
			if (isExistPattern) {
				System.out.println("Pattern Exist : " + pattern_score);
				
			} else { // 아니면 TPE Matching을 통해 Pattern Score 계산
				pattern_score=0;
				for (int i = 0; i < TargetList.size(); i++) {

					List<TargetNode> curTargetPostTree = null;
					curTargetPostTree = SentenP.GetParseTreePostTree(TargetList.get(i).getSentence(),TargetList.get(i).getPattern());
					
					if (curTargetPostTree == null) {
						System.out.println("cur target fail!!");
						return pattern_score;
					}

					if (matching(curTargetPostTree, Pattern_PostTree)) {
						
						MatchedTargetList.add(TargetList.get(i));
					}
				}
				
				total_Matched_size = MatchedTargetList.size();
				ArrayList<Integer> matched = new ArrayList<Integer>();
				
				// 수준 별로 매칭된 패턴의 결과를 세는 부분
				for (int i = 0; i < MatchedTargetList.size(); i++) {
					if (numOfGrade == 3) {
						if (MatchedTargetList.get(i).getGrage() == 0) {
							matched_count_byGrade[0]++;
							matched.add(0);
						} else if (MatchedTargetList.get(i).getGrage() == 1) {
							matched_count_byGrade[1]++;
							matched.add(1);
						} else if (MatchedTargetList.get(i).getGrage() == 2) {
							matched_count_byGrade[2]++;
							matched.add(2);
						}
					}else if(numOfGrade ==5){
						if (MatchedTargetList.get(i).getGrage() == 0) {
							matched_count_byGrade[0]++;
							matched.add(0);
						} else if (MatchedTargetList.get(i).getGrage() == 1) {
							matched_count_byGrade[1]++;
							matched.add(1);
						} else if (MatchedTargetList.get(i).getGrage() == 2) {
							matched_count_byGrade[2]++;
							matched.add(2);
						}else if (MatchedTargetList.get(i).getGrage() == 3) {
							matched_count_byGrade[3]++;
							matched.add(3);
						} else if (MatchedTargetList.get(i).getGrage() == 4) {
							matched_count_byGrade[4]++;
							matched.add(4);
						}
					}
				}

				if (total_Matched_size != 0) { // 매칭된 결과가 하나라도 있으면 패턴 스코어 산정
					for (int i = 0; i < numOfGrade; i++) {
						score_byGrade[i] = (i + 1) * ((float) matched_count_byGrade[i] / (float) total_Matched_size);
						pattern_score += score_byGrade[i];
					}
					// System.out.println(pattern_Score);
					float standardDeviation = getStandardDeviation(matched);
					// pattern_Score = pattern_Score*(((float)SEN_COUNT-(float)total_Matched_size)/(float)SEN_COUNT);
					pattern_score = (pattern_score * (1 / (standardDeviation + 1)));
					System.out.println(MatchedTargetList.size() + "개의 표준편차 : " + standardDeviation);

				} else { // 없으면 가장 어려운 문장 구조로 판단해서 MAX 값 입력
					pattern_score = (float) numOfGrade;
				}
			}
			
			return pattern_score;	
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	private static List<PatternNode> Build_Pattern_PostTree(String Pattern) {
		String Query = "";

		int SID = 0;
		QueryChk QC = new QueryChk(Query);

		Query = QC.RemoveRoot(Pattern);

		Query = QC.ChangeBrakey(Query);

		SID++;

		// Pattern Tree making
		// from here
		List<PatternNode> tree = new ArrayList<PatternNode>();

		PatternNode junk = new PatternNode(-1, "", -1, -1, "", -1);

		tree.add(junk);

		StringTokenizer st = new StringTokenizer(Query, " ");

		PatternQuery PQuery = new PatternQuery(st);

		PQuery.SepPattern(tree, 0);

		tree.get(1).PostOrderTrvs(SID, tree, 0);

		List<PatternNode> MatchList = PQuery.getMatchlist();

		for (int i = 1; i < tree.size(); i++) {
			for (int j = 1; j < MatchList.size(); j++) {
				if (tree.get(i).getUID() == MatchList.get(j).getUID()) {
					MatchList.set(j, tree.get(i));
					break;
				}
			}
		}

		List<PatternNode> PatternTree_Post = new ArrayList<PatternNode>();

		PatternTree_Post.add(junk);

		tree.get(1).PostOrderInsert(SID, PatternTree_Post, tree);

		// find parent
		for (int i = 1; i < PatternTree_Post.size(); i++) {
			PatternTree_Post.get(PatternTree_Post.get(i).getPID()).Addchildren(i);
		}

		// PatternTree making end

		return PatternTree_Post;
	}

	private static boolean matching(List<TargetNode> TargetPostTree, List<PatternNode> PatternTree_Post) {

		List<TargetNode> TargetTree = TargetPostTree;

		Matcher matcher = new Matcher();

		List<Pair> Result = new ArrayList<Pair>();

		Result = matcher.treePatternMatch(PatternTree_Post, TargetTree);

		// ResultPrint(Result, TargetTree);
		
		if (Result.size() != 0) {
			return true;
		}

		else {
			return false;
		}
	}
	
	public static float getStandardDeviation(ArrayList<Integer> lists) {
		float StandardDeviation = 0;
		StandardDeviation = (float) Math.sqrt(getVariance(lists));
		return StandardDeviation;
	}

	public static float getVariance(ArrayList<Integer> lists) {
		float Variance = 0;
		float avg = 0;
		int sum = 0;
		for (int i = 0; i < lists.size(); i++) {
			sum += lists.get(i);
		}

		avg = (float) sum / (float) lists.size();
		float SumOfDeviation = 0;
		for (int i = 0; i < lists.size(); i++) {
			SumOfDeviation += Math.pow(lists.get(i).floatValue() - avg, 2);
		}
		Variance = SumOfDeviation / lists.size();
		return Variance;
	}
	
}
