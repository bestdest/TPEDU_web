package com.hanyang.iis.tpedu.TPEMatch;

import java.util.ArrayList;
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

public class main_Pattern_Matcher {
//	public static int grade = 7;
	public static int SEN_COUNT = 3050; // 수정 필요
	public static int Input_type=0;
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

	public static int Input_cnt_advp=0;
	public static int Input_cnt_adjp=0;
	public static int Input_length=0;
	public static int Input_word=0;
	public static String Input_pattern="";
	
	public static String getInput_pattern() {
		return Input_pattern;
	}

	public static void setInput_pattern(String input_pattern) {
		Input_pattern = input_pattern;
	}

	public static Sentence Pattern_Matcher (String Pattern_Sentence, int numOfGrade, String tableName) {
		Sentence sentence = new Sentence();
		Double pattern_Score = 0.0;
		int total_Matched_size = 0;
		int[] matched_count_byGrade = {0,0,0,0,0,0,0};
		double[] score_byGrade = {0.0,0.0,0.0,0.0,0.0,0.0,0.0};
			
		
		TargetDAO Targetdao = new TargetDAO();
	      SentenParser SentenP = new SentenParser();
	      
	      String Pattern_ParseTree = SentenP.GetParseTree(Pattern_Sentence);

	      LeafChanger LC = new LeafChanger();
	      
	      //get original input
	      String Input_sentence = Pattern_Sentence;
	      Input_pattern = Pattern_ParseTree;
	      
	      Pattern_ParseTree = LC.ChangeLeaf(Pattern_ParseTree);
	      
	      Prunner Prun = new Prunner();
	      
	      Pattern_ParseTree = Prun.Prun_Leafs(Pattern_ParseTree);
	      
	      Pattern_ParseTree = Prun.Prunning(Pattern_ParseTree);
	      
	      
	      //get input's state
	      StringTokenizer stkn = new StringTokenizer(Input_sentence);
	      String Input_pruned_pattern = Pattern_ParseTree;
	      int Input_grade = 0;
	      int Input_lang = 2;
	      
	      TypeClassifier TC = new TypeClassifier();
	      Input_type = TC.GetType(Input_pattern);
	      Input_cnt_advp = LC.Count_ADVP(Input_pattern);
	      Input_cnt_adjp = LC.Count_ADJP(Input_pattern);
	      Input_length = Input_sentence.length();
	      Input_word = stkn.countTokens();

	      
	      System.out.println("Sentence : "+Input_sentence);
	      System.out.println("Pattern : "+Input_pattern);
	      System.out.println("Pruned Pattern : "+Input_pruned_pattern);
	      System.out.println("Type : "+Input_type);
	      System.out.println("Advp : "+Input_cnt_advp);
	      System.out.println("Adjp : "+Input_cnt_adjp);
	      System.out.println("Length : "+Input_length);
	      System.out.println("Word : "+Input_word);
	      
		  sentence.setCnt_adjp(Input_cnt_adjp/5.0);
		  sentence.setCnt_advp(Input_cnt_advp/5.0);
		  sentence.setLength(Input_length/380.0);
	 	  sentence.setWord(Input_word/55.0);
		  sentence.setStruct_type(Input_type/4.0);
			
	      
	      List<PatternNode> Pattern_PostTree = Build_Pattern_PostTree(Pattern_ParseTree);
	      
	      List<TargetDTO> TargetList = Targetdao.GetTargetList(tableName);
	      
	      List<TargetDTO> MatchedTargetList = new ArrayList<TargetDTO>();
	      
	      
	      for(int i = 0;i<TargetList.size();i++){
	    	  
	         List<TargetNode> curTargetPostTree = null;
	         curTargetPostTree = SentenP.GetParseTreePostTree(TargetList.get(i).getSentence(), TargetList.get(i).getPattern());

	         if(curTargetPostTree == null){
	            System.out.println("cur target fail!!");
	            sentence.setPattern_score(0);
				return sentence;
	         }
	         
	         if(matching(curTargetPostTree,Pattern_PostTree)){
	            MatchedTargetList.add(TargetList.get(i));
	         }
	      }
		
		total_Matched_size = MatchedTargetList.size();
		ArrayList<Integer> matched = new ArrayList<Integer>();
		
		// 수준 별로 매칭된 패턴의 결과를 세는 부분
		for(int i = 0;i<MatchedTargetList.size();i++){
			if(numOfGrade==7){
				if(MatchedTargetList.get(i).getGrage()/10 == 1){ // 10 11 12 13 14 15 16
					matched_count_byGrade[0]++;
					matched.add(0);
				}else if(MatchedTargetList.get(i).getGrage() == 21){
					matched_count_byGrade[1]++;
					matched.add(1);
				}else if(MatchedTargetList.get(i).getGrage() == 22){
					matched_count_byGrade[2]++;
					matched.add(2);
				}else if(MatchedTargetList.get(i).getGrage() == 23){
					matched_count_byGrade[3]++;
					matched.add(3);
				}else if(MatchedTargetList.get(i).getGrage() == 31){
					matched_count_byGrade[4]++;
					matched.add(4);
				}else if(MatchedTargetList.get(i).getGrage() == 32){
					matched_count_byGrade[5]++;
					matched.add(5);
				}else if(MatchedTargetList.get(i).getGrage() == 33){
					matched_count_byGrade[6]++;
					matched.add(6);
				}else{
					
				}
			}else if(numOfGrade==4){
				if(MatchedTargetList.get(i).getGrage() == 0){
					matched_count_byGrade[0]++;
					matched.add(0);
				}else if(MatchedTargetList.get(i).getGrage() == 1){
					matched_count_byGrade[1]++;
					matched.add(1);
				}else if(MatchedTargetList.get(i).getGrage() == 2){
					matched_count_byGrade[2]++;
					matched.add(2);
				}else if(MatchedTargetList.get(i).getGrage() == 3){
					matched_count_byGrade[3]++;
					matched.add(3);
				}else if(MatchedTargetList.get(i).getGrage() == 4){
					matched_count_byGrade[4]++;
					matched.add(4);
				}else{
					
				}
			}else if(numOfGrade==5){
				if(MatchedTargetList.get(i).getGrage() == 0){
					matched_count_byGrade[0]++;
					matched.add(0);
				}else if(MatchedTargetList.get(i).getGrage() == 1){
					matched_count_byGrade[1]++;
					matched.add(1);
				}else if(MatchedTargetList.get(i).getGrage() == 2){
					matched_count_byGrade[2]++;
					matched.add(2);
				}else if(MatchedTargetList.get(i).getGrage() == 3){
					matched_count_byGrade[3]++;
					matched.add(3);
				}else if(MatchedTargetList.get(i).getGrage() == 4){
					matched_count_byGrade[4]++;
					matched.add(4);
				}else if(MatchedTargetList.get(i).getGrage() == 5){
					matched_count_byGrade[5]++;
					matched.add(5);
				}else{
					
				}
			}else if(numOfGrade==3){
				if(MatchedTargetList.get(i).getGrage()/10 == 1){ // 10 11 12 13 14 15 16
					matched_count_byGrade[0]++;
					matched.add(0);
				}else if(MatchedTargetList.get(i).getGrage()/10 == 2){
					matched_count_byGrade[1]++;
					matched.add(1);
				}else if(MatchedTargetList.get(i).getGrage()/10 == 3){
					matched_count_byGrade[2]++;
					matched.add(2);
				}else{
					
				}
			}
			
			
//			System.out.println(MatchedTargetList.get(i).getGrage());
		}
		
		if(total_Matched_size != 0){ // 매칭된 결과가 하나라도 있으면 패턴 스코어 산정
			for(int i=0; i<numOfGrade;i++){
				score_byGrade[i] = (i+1)*((double)matched_count_byGrade[i]/(double)total_Matched_size);
				pattern_Score += score_byGrade[i];
//				System.out.println((i+1)+" : " + matched_count_byGrade[i]);
//				System.out.println((i+1)+" : " + score_byGrade[i]);
			}
//			System.out.println(pattern_Score);
			double standardDeviation = getStandardDeviation(matched);
			System.out.println("pattern_Score : " + pattern_Score);
//			pattern_Score = pattern_Score*(((double)SEN_COUNT-(double)total_Matched_size)/(double)SEN_COUNT);
			pattern_Score = pattern_Score*(1.0/(standardDeviation+1.0));
			System.out.println(MatchedTargetList.size()+"개의 표준편차 : "+standardDeviation);
			
		}else{ // 없으면 가장 어려운 문장 구조로 판단해서 MAX 값 입력
			pattern_Score = (double)numOfGrade;
		}
		
//		System.out.println("num : " + MatchedTargetList.size());
		sentence.setPattern_score(pattern_Score/3.0);
		return sentence;
	}
	
	public static double getStandardDeviation(ArrayList<Integer> lists){
		double StandardDeviation = 0.0;
		StandardDeviation = Math.sqrt(getVariance(lists));
		return StandardDeviation;
	}
	public static double getVariance(ArrayList<Integer> lists){
		double Variance = 0.0;
		double avg=0;
		int sum=0;
		for(int i=0; i<lists.size();i++){
			sum += lists.get(i);
		}
		avg = (double)sum/(double)lists.size();
		
		double SumOfDeviation =0.0;
		for(int i=0; i<lists.size();i++){
			SumOfDeviation += Math.pow(lists.get(i).doubleValue()-avg, 2);
		}
		Variance = SumOfDeviation/lists.size();
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
			PatternTree_Post.get(PatternTree_Post.get(i).getPID()).Addchildren(
					i);
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




