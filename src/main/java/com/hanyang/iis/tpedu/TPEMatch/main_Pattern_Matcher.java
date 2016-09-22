package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.Prun.LeafChanger;
import com.hanyang.iis.tpedu.Prun.Prunner;
import com.hanyang.iis.tpedu.SentenceTBL.TypeClassifier;
import com.hanyang.iis.tpedu.dao.TargetDAO;
import com.hanyang.iis.tpedu.dto.Sentence;
import com.hanyang.iis.tpedu.dto.TargetDTO;

import edu.stanford.nlp.trees.TypedDependency;

public class main_Pattern_Matcher {
	// public static int grade = 7;
	public static int SEN_COUNT = 3050; // 수정 필요
	public static int Input_type = 0;

	public static int getInput_type() {
		return Input_type;
	}

	public static void setInput_type(int input_type) {
		Input_type = input_type;
	}

	public static int getInput_cnt_advp() {
		return Input_cnt_advp;
	}

	public static void setInput_cnt_advp(int input_cnt_advp) {
		Input_cnt_advp = input_cnt_advp;
	}

	public static int getInput_cnt_adjp() {
		return Input_cnt_adjp;
	}

	public static void setInput_cnt_adjp(int input_cnt_adjp) {
		Input_cnt_adjp = input_cnt_adjp;
	}

	public static int getInput_length() {
		return Input_length;
	}

	public static void setInput_length(int input_length) {
		Input_length = input_length;
	}

	public static int getInput_word() {
		return Input_word;
	}

	public static void setInput_word(int input_word) {
		Input_word = input_word;
	}

	public static int Input_cnt_advp = 0;
	public static int Input_cnt_adjp = 0;
	public static int Input_cnt_modifier = 0;
	public static int Input_length = 0;
	public static int Input_word = 0;
	public static String Input_pattern = "";

	public static String getInput_pattern() {
		return Input_pattern;
	}

	public static void setInput_pattern(String input_pattern) {
		Input_pattern = input_pattern;
	}

	public static HashMap<String, String> Pattern_Matcher_weebit(String Pattern, int numOfGrade, String tableName) {
		Sentence sentence = new Sentence();
		HashMap<String, String> syntactic = new HashMap<String, String>();
		double pattern_Score = 0.0;
		int total_Matched_size = 0;
		int[] matched_count_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		double[] score_byGrade = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		TargetDAO Targetdao = new TargetDAO();
		SentenParser SentenP = new SentenParser();
		LeafChanger LC = new LeafChanger();
		// get original input

		String Pattern_ParseTree = Pattern;

		Pattern_ParseTree = LC.ChangeLeaf(Pattern_ParseTree);

		Prunner Prun = new Prunner();

		Pattern_ParseTree = Prun.Prun_Leafs(Pattern_ParseTree);

		Pattern_ParseTree = Prun.Prunning(Pattern_ParseTree);

		List<PatternNode> Pattern_PostTree = Build_Pattern_PostTree(Pattern_ParseTree);

		List<TargetDTO> TargetList = Targetdao.GetTargetList(tableName);

		List<TargetDTO> MatchedTargetList = new ArrayList<TargetDTO>();

		for (int i = 0; i < TargetList.size(); i++) {

			List<TargetNode> curTargetPostTree = null;
			curTargetPostTree = SentenP.GetParseTreePostTree(TargetList.get(i).getSentence(),
					TargetList.get(i).getPattern());

			if (curTargetPostTree == null) {
				System.out.println("cur target fail!!");
				return syntactic;
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
			}
			// System.out.println(MatchedTargetList.get(i).getGrage());
		}

		if (total_Matched_size != 0) { // 매칭된 결과가 하나라도 있으면 패턴 스코어 산정
			for (int i = 0; i < numOfGrade; i++) {
				score_byGrade[i] = (i + 1) * ((double) matched_count_byGrade[i] / (double) total_Matched_size);

				pattern_Score += score_byGrade[i];
				// System.out.println((i+1)+" : " + matched_count_byGrade[i]);
				// System.out.println((i+1)+" : " + score_byGrade[i]);
			}
			// System.out.println(pattern_Score);
			double standardDeviation = getStandardDeviation(matched);
			// pattern_Score =
			// pattern_Score*(((double)SEN_COUNT-(double)total_Matched_size)/(double)SEN_COUNT);
			pattern_Score = pattern_Score * (1.0 / (standardDeviation + 1.0));
			System.out.println(MatchedTargetList.size() + "개의 표준편차 : " + standardDeviation);

		} else { // 없으면 가장 어려운 문장 구조로 판단해서 MAX 값 입력
			pattern_Score = (double) numOfGrade;
		}

		syntactic.put("pattern_score", String.valueOf(pattern_Score));

		return syntactic;
	}

	public static Sentence Pattern_Matcher(String Pattern_Sentence, int numOfGrade, String tableName) {
		Sentence sentence = new Sentence();
		HashMap<String, String> syntactic = new HashMap<String, String>();
		double pattern_score = 0.0;
		int total_Matched_size = 0;
		int[] matched_count_byGrade = { 0, 0, 0, 0, 0, 0, 0 };
		double[] score_byGrade = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		TargetDAO Targetdao = new TargetDAO();
		SentenParser SentenP = new SentenParser();
		String Pattern_ParseTree = SentenP.GetParseTree(Pattern_Sentence);

		// SPResult ParserResult = new SPResult(Pattern_Sentence);
		List<TypedDependency> GR = SentenP.getTdl();

		LeafChanger LC = new LeafChanger();
		// get original input
		String Input_sentence = Pattern_Sentence;
		Input_pattern = Pattern_ParseTree;

		Pattern_ParseTree = LC.ChangeLeaf(Pattern_ParseTree);

		Prunner Prun = new Prunner();

		Pattern_ParseTree = Prun.Prun_Leafs(Pattern_ParseTree);

		Pattern_ParseTree = Prun.Prunning(Pattern_ParseTree);

		// get input's state
		StringTokenizer stkn = new StringTokenizer(Input_sentence);
		String Input_pruned_pattern = Pattern_ParseTree;

		int numCompound = 0;
		int numGR = GR.size();
		int sumGRdist = 0;
		int maxGRdist = 0;

		for (int i = 0; i < GR.size(); i++) {
			int gov = GR.get(i).gov().hashCode();
			int dep = GR.get(i).dep().hashCode();

			int dist = 0;

			if (gov < 1) {
				dist = dep;
			} else if (gov > dep) {
				dist = gov - dep;
			} else {
				dist = dep = gov;
			}

			sumGRdist += dist;
			if (dist > maxGRdist) {
				maxGRdist = dist;
			}

			if (GR.get(i).reln().toString().equals("compound")) {
				numCompound++;
			}
		}
		double avgGRdist = (double) sumGRdist / (double) numGR;

		syntactic.put("numSBAR", String.valueOf(LC.Count_SBAR(Input_pattern)));
		syntactic.put("numCC", String.valueOf(LC.Count_CC(Input_pattern)));
		syntactic.put("numCompound", String.valueOf(numCompound));
		syntactic.put("numGR", String.valueOf(numGR));
		syntactic.put("avg_dist_GR", String.valueOf(avgGRdist));
		syntactic.put("max_dist_GR", String.valueOf(maxGRdist));

		sentence.setCnt_sbar(LC.Count_SBAR(Input_pattern)/4.0);
		sentence.setCnt_cc(LC.Count_CC(Input_pattern)/5.0);
		sentence.setCnt_compound(numCompound/5.0);
		sentence.setCnt_gr(numGR);
		sentence.setAvg_dis_gr(avgGRdist);
	 	sentence.setMax_dis_gr(maxGRdist/55.0);

		TypeClassifier TC = new TypeClassifier();
		Input_type = TC.GetType(Input_pattern);
		Input_cnt_advp = LC.Count_ADVP(Input_pattern);
		Input_cnt_adjp = LC.Count_ADJP(Input_pattern);
		Input_cnt_modifier = LC.Count_Modifier(Input_pattern);
		Input_length = Input_sentence.length();
		Input_word = stkn.countTokens();

		syntactic.put("sentence", Input_sentence);
		syntactic.put("pattern", Input_pattern);
		syntactic.put("pruned_pattern", Input_pruned_pattern);
		syntactic.put("type", String.valueOf(Input_type));
		syntactic.put("cnt_advp", String.valueOf(Input_cnt_advp));
		syntactic.put("cnt_adjp", String.valueOf(Input_cnt_adjp));
		syntactic.put("cnt_modifier", String.valueOf(Input_cnt_modifier));
		syntactic.put("length", String.valueOf(Input_length));
		syntactic.put("word", String.valueOf(Input_word));
		
		sentence.setStruct_type(Input_type/4.0);
		sentence.setCnt_adjp(Input_cnt_adjp/5.0);
		sentence.setCnt_advp(Input_cnt_advp/5.0);
		sentence.setLength(Input_length/380.0);
		sentence.setCnt_modifier(Input_cnt_modifier);
	 	sentence.setWord(Input_word/55.0);

		System.out.println("Sentence : " + Input_sentence);
		System.out.println("Pattern : " + Input_pattern);
		System.out.println("Pruned Pattern : " + Input_pruned_pattern);
		System.out.println("Type : " + Input_type);
		System.out.println("Advp : " + Input_cnt_advp);
		System.out.println("Adjp : " + Input_cnt_adjp);
		System.out.println("Modifer : " + Input_cnt_modifier);
		System.out.println("Length : " + Input_length);
		System.out.println("Word : " + Input_word);

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

		// 기존의 동일 패턴이 점수로 존재하면 그냥 가져와서 쓰자.
		if (isExistPattern) {
			System.out.println("Pattern Exist : " + pattern_score);
			
		} else { // 아니면 TPE Matching을 통해 Pattern Score 계산
			pattern_score =0.0;
			for (int i = 0; i < TargetList.size(); i++) {

				List<TargetNode> curTargetPostTree = null;
				curTargetPostTree = SentenP.GetParseTreePostTree(TargetList.get(i).getSentence(),TargetList.get(i).getPattern());
				
				if (curTargetPostTree == null) {
					System.out.println("cur target fail!!");
					//return sentence;		여기 그냥 return 을 해버리면 안될듯?
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
//				 System.out.println(MatchedTargetList.get(i).getGrage());
			}

			if (total_Matched_size != 0) { // 매칭된 결과가 하나라도 있으면 패턴 스코어 산정
				for (int i = 0; i < numOfGrade; i++) {
					score_byGrade[i] = (i + 1) * ((double) matched_count_byGrade[i] / (double) total_Matched_size);

					pattern_score += score_byGrade[i];
					// System.out.println((i+1)+" : " +
					// matched_count_byGrade[i]);
					// System.out.println((i+1)+" : " + score_byGrade[i]);
				}
				// System.out.println(pattern_Score);
				double standardDeviation = getStandardDeviation(matched);
				// pattern_Score = pattern_Score*(((double)SEN_COUNT-(double)total_Matched_size)/(double)SEN_COUNT);
				pattern_score = pattern_score * (1.0 / (standardDeviation + 1.0));
				System.out.println(MatchedTargetList.size() + "개의 표준편차 : " + standardDeviation);

			} else { // 없으면 가장 어려운 문장 구조로 판단해서 MAX 값 입력
				pattern_score = (double) numOfGrade;
			}

		}
		
//		System.out.println("Pattern Score : "+ pattern_score);
		syntactic.put("pattern_score", String.valueOf((double)pattern_score));
		syntactic.put("advpVar", String.valueOf((double) Input_cnt_advp / (double) Input_word));
		syntactic.put("adjpVar", String.valueOf((double) Input_cnt_adjp / (double) Input_word));
		syntactic.put("modifierVar", String.valueOf((double) Input_cnt_modifier / (double) Input_word));

		sentence.setPattern_score((double)pattern_score);
		sentence.setVar_adv((double) Input_cnt_advp / (double) Input_word);
		sentence.setVar_adj((double) Input_cnt_adjp / (double) Input_word);
		sentence.setVar_modifier((double) Input_cnt_modifier / (double) Input_word);
		
		
		
		return sentence;
	}

	public static double getStandardDeviation(ArrayList<Integer> lists) {
		double StandardDeviation = 0.0;
		StandardDeviation = Math.sqrt(getVariance(lists));
		return StandardDeviation;
	}

	public static double getVariance(ArrayList<Integer> lists) {
		double Variance = 0.0;
		double avg = 0;
		int sum = 0;
		for (int i = 0; i < lists.size(); i++) {
			sum += lists.get(i);
		}

		avg = (double) sum / (double) lists.size();
		double SumOfDeviation = 0.0;
		for (int i = 0; i < lists.size(); i++) {
			SumOfDeviation += Math.pow(lists.get(i).doubleValue() - avg, 2);
		}
		Variance = SumOfDeviation / lists.size();
		return Variance;
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
}
