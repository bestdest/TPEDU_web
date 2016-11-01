package com.hanyang.iis.tpedu.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.hanyang.iis.TrainingDataSelect;
import com.hanyang.iis.tpedu.dto.Score;
import com.hanyang.iis.tpedu.dto.Sentence;
import com.opencsv.CSVReader;

public class NaiveBayesianDAO {
	
	private String[] dataSet;
    private Map<String, Long> classifies = new HashMap<>();		//등급 몇개 있는지
    private Map<String, Map<String, Long>> counter = new HashMap<>();		//등립안에 개수 몇개

   

    /*가우시안 등급 계산*/
    /*public void gaussianCal(ArrayList<Sentence> testList, ArrayList<Score> gradeScore, int grade_count, int is_para){
		int isFail = 0;
		int isSuccess = 0;
		int isTotalGrade[] = new int[grade_count];
    	int isTruegrade[] = new int[grade_count];
    	int isEachGradeCount[][] = new int[grade_count][grade_count];
    	
    	 testList 값 꺼내서 체크 
    	for(int i = 0; i < testList.size(); i++){
    		
    		 Grade 값 계산 
    		Double[] grade = new Double[grade_count];
    		for(int j = 0; j < gradeScore.size(); j++){
    			grade[j] = getCal(gradeScore.get(j), testList.get(i), is_para);
    		}
    		int orgin_grade = testList.get(i).getGrade();
    		int classification = 0;
    		
    		 Grade 최대값 구하기 
    		Double temp = 0.0;
    		for(int j = 0; j < grade.length; j++) {
    			if(grade[j] > temp ){
    				temp = grade[j];
    				classification = j;
    			}
    		}
    		
    		//전체 분류된값 개수 추가
    		isTotalGrade[classification] = isTotalGrade[classification] + 1;
    		isEachGradeCount[orgin_grade][classification] = isEachGradeCount[orgin_grade][classification] + 1;
    		
    		//분류 된 값 성공여부 비교
    		if(classification == orgin_grade){
    			isSuccess++;
    			isTruegrade[classification] = isTruegrade[classification] + 1;		//성공된 분류값들 추가
    		}else{
    			isFail++;
    		}
    	}

    	 각 등급별 분류 개수 
    	for(int k = 0; k < isEachGradeCount.length; k++){
    		System.out.print(" " + (k+1) + "등급 grade1" + " :  " + isEachGradeCount[k][0]);
    		for(int kk = 1; kk < grade_count; kk++){
    			System.out.print(" grade" + (kk+1) + " :  " + isEachGradeCount[k][kk]);
    		}
    		System.out.println("");
    	}
    	
    	int index = 0;
    	Double re = 0.0;
    	float tempre = 0;
    	 전체 성공 실패 계산 값 
    	for (int total : isTotalGrade) {
    		int succ = isTruegrade[index];
    		tempre = (float) (succ*1.0 / total*1.0);
    		re += tempre;
    		System.out.println("  " + index++ + " total : " + total + " success :  " + succ + "  calculate : " + tempre);
    	}
    	
    	System.out.println("성공횟수 : " + isSuccess + "  실패횟수 : " + isFail + "  Accurancy : " + isSuccess*1.0/(isFail + isSuccess)*1.0 + "  Precision : " + re/grade_count);
    }*/
    
    /*가우시안 문장 별 등급 분류 (가장 큰 값 구하기)*/
    public int gaussianCal(Sentence sentence, ArrayList<Score> gradeScore, int grade_count, int is_para){
    	
    	/* Grade 값 계산 */
		Double[] grade = new Double[grade_count];
		for(int j = 0; j < gradeScore.size(); j++){
			grade[j] = getCal(gradeScore.get(j), sentence, is_para);
		}
		int classification = 0;
		
		/* Grade 최대값 구하기 */
		Double temp = 0.0;
		for(int j = 0; j < grade.length; j++) {
			System.out.println((j+1) + "등급 : " + grade[j]);
			if(grade[j] > temp ){
				temp = grade[j];
				classification = j;
			}
		}
		
    	
    	return classification;
    }
    
    
    /*각 항목별 등급 분류 (근데 밖에 나오는것만  등급내면되서 다할 필요 없음.) */
    public Sentence gaussianEachFeatureCal(Sentence sentence, ArrayList<Score> gradeScore, int grade_count){

    	ArrayList<Double[]> length = new ArrayList<Double[]>();
    	ArrayList<Double[]> vaca = new ArrayList<Double[]>();
    	ArrayList<Double[]> word = new ArrayList<Double[]>();
    	ArrayList<Double[]> advp = new ArrayList<Double[]>();
    	ArrayList<Double[]> adjp = new ArrayList<Double[]>();
    	ArrayList<Double[]> pattern = new ArrayList<Double[]>();
    	ArrayList<Double[]> struct = new ArrayList<Double[]>();

    	ArrayList<Double[]> avg_char = new ArrayList<Double[]>();
    	ArrayList<Double[]> syllable = new ArrayList<Double[]>();
    	ArrayList<Double[]> cnt_modifier = new ArrayList<Double[]>();
    	ArrayList<Double[]> awl = new ArrayList<Double[]>();
    	ArrayList<Double[]> variation_modifier = new ArrayList<Double[]>();
    	ArrayList<Double[]> variation_adv = new ArrayList<Double[]>();
    	ArrayList<Double[]> variation_adj = new ArrayList<Double[]>();
    	ArrayList<Double[]> cc = new ArrayList<Double[]>();
    	ArrayList<Double[]> sbar = new ArrayList<Double[]>();
    	ArrayList<Double[]> compound = new ArrayList<Double[]>();
    	ArrayList<Double[]> cnt_gr = new ArrayList<Double[]>();
    	ArrayList<Double[]> avg_gr = new ArrayList<Double[]>();
    	ArrayList<Double[]> max_gr = new ArrayList<Double[]>();

    	ArrayList<Double[]> num_sen = new ArrayList<Double[]>();
    	ArrayList<Double[]> ttr = new ArrayList<Double[]>();
    	ArrayList<Double[]> cli = new ArrayList<Double[]>();
    	ArrayList<Double[]> lix = new ArrayList<Double[]>();
    	
    	/* 각 리스트에 해당 값 담기 */
    	for(int j = 0; j < gradeScore.size(); j++){
    		Double[] temp = new Double[2];
    		temp = new Double[]{gradeScore.get(j).getAvg_length(), gradeScore.get(j).getVar_length()};
    		length.add(temp);

    		temp = new Double[]{gradeScore.get(j).getAvg_voca(), gradeScore.get(j).getVar_voca()};
    		vaca.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_word_count(), gradeScore.get(j).getVar_word_count()};
    		word.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_advp(), gradeScore.get(j).getVar_advp()};
    		advp.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_adjp(), gradeScore.get(j).getVar_adjp()};
    		adjp.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_pattern(), gradeScore.get(j).getVar_pattern()};
    		pattern.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_struct_type(), gradeScore.get(j).getVar_struct_type()};
    		struct.add(temp);

    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_char(), gradeScore.get(j).getVar_cnt_char()};
    		avg_char.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_syllable(), gradeScore.get(j).getVar_cnt_syllable()};
    		syllable.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_modifier(), gradeScore.get(j).getVar_cnt_modifier()};
    		cnt_modifier.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_awl(), gradeScore.get(j).getVar_awl()};
    		awl.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_variation_modifier(), gradeScore.get(j).getVar_variation_modifier()};
    		variation_modifier.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_variation_adv(), gradeScore.get(j).getVar_variation_adv()};
    		variation_adv.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_variation_adj(), gradeScore.get(j).getVar_variation_adj()};
    		variation_adj.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_cc(), gradeScore.get(j).getVar_cnt_cc()};
    		cc.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_sbar(), gradeScore.get(j).getVar_cnt_sbar()};
    		sbar.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cnt_compound(), gradeScore.get(j).getVar_cnt_compound()};
    		compound.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_gr_cnt(), gradeScore.get(j).getVar_gr_cnt()};
    		cnt_gr.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_gr_avg(), gradeScore.get(j).getVar_gr_avg()};
    		avg_gr.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_gr_max(), gradeScore.get(j).getVar_gr_max()};
    		max_gr.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_num_sen(), gradeScore.get(j).getVar_num_sen()};
    		num_sen.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_ttr(), gradeScore.get(j).getVar_ttr()};
    		ttr.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_cli(), gradeScore.get(j).getVar_cli()};
    		cli.add(temp);
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_lix(), gradeScore.get(j).getVar_lix()};
    		lix.add(temp);
    		
    	}
    	sentence.setGrade_length(gradeClassification(length, sentence.getLength()));
    	sentence.setGrade_voca_score(gradeClassification(vaca, sentence.getVoca_score()));
    	sentence.setGrade_word(gradeClassification(word, sentence.getWord()));
    	sentence.setGrade_cnt_advp(gradeClassification(advp, sentence.getCnt_advp()));
    	sentence.setGrade_cnt_adjp(gradeClassification(adjp, sentence.getCnt_adjp()));
    	sentence.setGrade_pattern_score(gradeClassification(pattern, sentence.getPattern_score()));
    	sentence.setGrade_struct_type(gradeClassification(struct, sentence.getStruct_type()));

    	sentence.setGrade_avg_char(gradeClassification(avg_char, sentence.getAvg_char()));
    	sentence.setGrade_avg_syllables(gradeClassification(syllable, sentence.getAvg_syllables()));
    	sentence.setGrade_cnt_modifier(gradeClassification(cnt_modifier, sentence.getCnt_modifier()));
    	sentence.setGrade_ratio_awl(gradeClassification(awl, sentence.getRatio_awl()));
    	sentence.setGrade_var_modifier(gradeClassification(variation_modifier, sentence.getVar_modifier()));
    	sentence.setGrade_var_adv(gradeClassification(variation_adv, sentence.getVar_adv()));
    	sentence.setGrade_var_adj(gradeClassification(variation_adj, sentence.getVar_adj()));
    	sentence.setGrade_cnt_cc(gradeClassification(cc, sentence.getCnt_cc()));
    	sentence.setGrade_cnt_sbar(gradeClassification(sbar, sentence.getCnt_sbar()));
    	sentence.setGrade_cnt_compound(gradeClassification(compound, sentence.getCnt_compound()));
    	sentence.setGrade_cnt_gr(gradeClassification(cnt_gr, sentence.getCnt_gr()));
    	sentence.setGrade_avg_dis_gr(gradeClassification(avg_gr, sentence.getAvg_dis_gr()));
    	sentence.setGrade_max_dis_gr(gradeClassification(max_gr, sentence.getMax_dis_gr()));

    	sentence.setGrade_num_sen(gradeClassification(num_sen, sentence.getNum_sen()));
    	sentence.setGrade_ttr(gradeClassification(ttr, sentence.getTtr()));
    	sentence.setGrade_cli(gradeClassification(cli, sentence.getCli()));
    	sentence.setGrade_lix(gradeClassification(lix, sentence.getLix()));

    	return sentence;
    }
    
    public byte gradeClassification(ArrayList<Double[]> gradeScore, Double sentence_score){
    	TrainingDataSelect td = new TrainingDataSelect();
    	Double score = 0.0;
    	byte grade = 0;
    	
    	for(int j = 0; j < gradeScore.size(); j++){
    		Double temp = 0.0;
    		temp = td.calculation(gradeScore.get(j)[0], gradeScore.get(j)[1], sentence_score);
    		//이전 등급들중 최대값 구하기
    		if(temp > score){
    			score = temp;
    			grade = (byte) j;		//해당 grade 값 최대이면 등급
    		}
    	}
    	
    	return grade;
    }
    
    
    /*가우시안 분산 값 grade 별 계산*/
    public Double getCal(Score gradeScore, Sentence sentence, int is_paragraph){
    	TrainingDataSelect td = new TrainingDataSelect();
    	
    	Double length_var = td.calculation(gradeScore.getAvg_length(), gradeScore.getVar_length(), sentence.getLength());
    	Double voca_var = td.calculation(gradeScore.getAvg_voca(), gradeScore.getVar_voca(), sentence.getVoca_score());
    	Double word_var = td.calculation(gradeScore.getAvg_word_count(), gradeScore.getVar_word_count(), sentence.getWord());
    	Double pattern_var = td.calculation(gradeScore.getAvg_pattern(), gradeScore.getVar_pattern(), sentence.getPattern_score());
    	Double adjp_var = td.calculation(gradeScore.getAvg_adjp(), gradeScore.getVar_adjp(), sentence.getCnt_adjp());
    	Double advp_var = td.calculation(gradeScore.getAvg_advp(), gradeScore.getVar_advp(), sentence.getCnt_advp());
    	Double struct_var = td.calculation(gradeScore.getAvg_struct_type(), gradeScore.getVar_struct_type(), sentence.getStruct_type());
    	
    	Double avg_char = td.calculation(gradeScore.getAvg_cnt_char(), gradeScore.getVar_cnt_char(), sentence.getAvg_char());
    	Double syllable = td.calculation(gradeScore.getAvg_cnt_syllable(), gradeScore.getVar_cnt_syllable(), sentence.getAvg_syllables());
    	Double cnt_modifier = td.calculation(gradeScore.getAvg_cnt_modifier(), gradeScore.getVar_cnt_modifier(), sentence.getCnt_modifier());
    	Double awl = td.calculation(gradeScore.getAvg_awl(), gradeScore.getVar_awl(), sentence.getRatio_awl());
    	Double variation_modifier = td.calculation(gradeScore.getAvg_variation_modifier(), gradeScore.getVar_variation_modifier(), sentence.getCnt_modifier());
    	Double variation_adv = td.calculation(gradeScore.getAvg_variation_adv(), gradeScore.getVar_variation_adv(), sentence.getVar_adv());
    	Double variation_adj = td.calculation(gradeScore.getAvg_variation_adj(), gradeScore.getVar_variation_adj(), sentence.getVar_adj());
    	Double cc = td.calculation(gradeScore.getAvg_cnt_cc(), gradeScore.getVar_cnt_cc(), sentence.getCnt_cc());
    	Double sbar = td.calculation(gradeScore.getAvg_cnt_sbar(), gradeScore.getVar_cnt_sbar(), sentence.getCnt_sbar());
    	Double compound = td.calculation(gradeScore.getAvg_cnt_compound(), gradeScore.getVar_cnt_compound(), sentence.getCnt_compound());
    	Double cnt_gr = td.calculation(gradeScore.getAvg_gr_cnt(), gradeScore.getVar_gr_cnt(), sentence.getCnt_gr());
    	Double avg_gr = td.calculation(gradeScore.getAvg_gr_avg(), gradeScore.getVar_gr_avg(), sentence.getAvg_dis_gr());
    	Double max_gr = td.calculation(gradeScore.getAvg_gr_max(), gradeScore.getVar_gr_max(), sentence.getMax_dis_gr());

    	Double num_sen = td.calculation(gradeScore.getAvg_num_sen(), gradeScore.getVar_num_sen(), sentence.getNum_sen());
    	Double ttr = td.calculation(gradeScore.getAvg_ttr(), gradeScore.getVar_ttr(), sentence.getTtr());
    	Double cli = td.calculation(gradeScore.getAvg_cli(), gradeScore.getVar_cli(), sentence.getCli());
    	Double lix = td.calculation(gradeScore.getAvg_lix(), gradeScore.getVar_lix(), sentence.getLix());

    	Double cnt_pp = td.calculation(gradeScore.getAvg_cnt_pp(), gradeScore.getVar_cnt_pp(), sentence.getCnt_pp());
    	Double cdep = td.calculation(gradeScore.getAvg_cdep(), gradeScore.getVar_cdep(), sentence.getCdep());
    	Double dep_left = td.calculation(gradeScore.getAvg_dep_left(), gradeScore.getVar_dep_left(), sentence.getDep_left());
    	Double dep_right = td.calculation(gradeScore.getAvg_dep_right(), gradeScore.getVar_dep_right(), sentence.getDep_right());
    	
    	Double result = length_var * voca_var * pattern_var * word_var * adjp_var * advp_var * struct_var *
    	avg_char * syllable * cnt_modifier * awl * variation_modifier * variation_adv * variation_adj * cc * sbar * compound * cnt_gr * avg_gr * max_gr;
    	
    	if(is_paragraph == 1){
    		result = length_var * voca_var * pattern_var * word_var * adjp_var * advp_var * struct_var *
    				avg_char * syllable * cnt_modifier * awl * variation_modifier * variation_adv * variation_adj * cc * sbar * compound * cnt_gr * avg_gr * max_gr * num_sen * ttr * cli * lix * cnt_pp * cdep * dep_left * dep_right;
    		
    	}
    	return result;
    }
    
    /*Grade 별 평균, 분산값 집어넣기 */
    public Score setScore(ArrayList<Sentence> list_grade, Score scoreGrade){
    	ScoreDAO sc = new ScoreDAO();
    	
    	scoreGrade = sc.getLengthScore(list_grade, scoreGrade);
    	scoreGrade = sc.getVocaScore(list_grade, scoreGrade);
    	scoreGrade = sc.getPatternScore(list_grade, scoreGrade);
    	scoreGrade = sc.getWordScore(list_grade, scoreGrade);
    	scoreGrade = sc.getAdjpScore(list_grade, scoreGrade);
    	scoreGrade = sc.getAdvpScore(list_grade, scoreGrade);
    	scoreGrade = sc.getStructType(list_grade, scoreGrade);

    	scoreGrade = sc.getCharScore(list_grade, scoreGrade);
    	scoreGrade = sc.getSyllableScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCntModifierScore(list_grade, scoreGrade);
    	scoreGrade = sc.getAWLScore(list_grade, scoreGrade);
    	scoreGrade = sc.getVariationModifierScore(list_grade, scoreGrade);
    	scoreGrade = sc.getVariationAdvScore(list_grade, scoreGrade);
    	scoreGrade = sc.getVariationAdjScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCCScore(list_grade, scoreGrade);
    	scoreGrade = sc.getSBARScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCompoundScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCntGRScore(list_grade, scoreGrade);
    	scoreGrade = sc.getAvgGRScore(list_grade, scoreGrade);
    	scoreGrade = sc.getMaxGRScore(list_grade, scoreGrade);

    	scoreGrade = sc.getNumSenScore(list_grade, scoreGrade);
    	scoreGrade = sc.getTTRScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCLIScore(list_grade, scoreGrade);
    	scoreGrade = sc.getLIXScore(list_grade, scoreGrade);
    	
    	scoreGrade = sc.getCntPPScore(list_grade, scoreGrade);
    	scoreGrade = sc.getCdepScore(list_grade, scoreGrade);
    	scoreGrade = sc.getDepLeftScore(list_grade, scoreGrade);
    	scoreGrade = sc.getDepRightScore(list_grade, scoreGrade);
    	
    	return scoreGrade;
    }

    
    public static void main(String[] args) throws Exception {
        //-- 학습 데이터
    	/*TrainingDataSelect td = new TrainingDataSelect();

    	 DB 에서 데이터 가져오기 
    	//HashMap<Integer, Score> map = td.selectSentenceScore();
    	ArrayList<Sentence> list_grade1 = td.selectRandomSentence("0", 6000);
    	ArrayList<Sentence> list_grade2 = td.selectRandomSentence("1", 6000);
    	ArrayList<Sentence> list_grade3 = td.selectRandomSentence("2", 6000);
    	ArrayList<Sentence> test_list = td.selectRandomSentence("0,1,2", 2000);
    	
    	String filename = "D:\\Temp\\0615_essay_Normalized\\TPEDU_essay3_train.csv";
    	//String filename = "D:\\Temp\\0529_grade7\\TPEDU_eval_set1.csv";
    	ArrayList<Sentence> list_grade1 = td.readCsv(filename, 0);
    	ArrayList<Sentence> list_grade2 = td.readCsv(filename, 1);
    	ArrayList<Sentence> list_grade3 = td.readCsv(filename, 2);
    	ArrayList<Sentence> list_grade4 = td.readCsv(filename, 3);
    	ArrayList<Sentence> list_grade5 = td.readCsv(filename, 4);
    	ArrayList<Sentence> list_grade6 = td.readCsv(filename, 5);
    	ArrayList<Sentence> list_grade7 = td.readCsv(filename, 6);
    	ArrayList<Sentence> test_list = td.readCsv("D:\\Temp\\0615_essay_Normalized\\TPEDU_essay3_train.csv", -1);

    	 가져온 데이터 보기 
    	Set<Integer> keySet = map.keySet();
    	Iterator<Integer> iterator = keySet.iterator();
    	while (iterator.hasNext()) {
    		Integer key = iterator.next();
    		Sentence sentence = map.get(key);
    		System.out.printf("key : %s , leng_avg : %s , leng_var : %s %n", key, score.getAvg_length(), score.getVar_length());
    	}
    	
    	NaiveBayesianDAO nb = new NaiveBayesianDAO();
    	
    	Score scoreGrade1 = new Score();
    	Score scoreGrade2 = new Score();
    	Score scoreGrade3 = new Score();
    	Score scoreGrade4 = new Score();
    	Score scoreGrade5 = new Score();
    	Score scoreGrade6 = new Score();
    	Score scoreGrade7 = new Score();
    	
    	//Grade 별 평균, 분산값 집어넣기
    	scoreGrade1 = nb.setScore(list_grade1, scoreGrade1);
    	scoreGrade2 = nb.setScore(list_grade2, scoreGrade2);
    	scoreGrade3 = nb.setScore(list_grade3, scoreGrade3);
    	scoreGrade4 = nb.setScore(list_grade4, scoreGrade4);
    	scoreGrade5 = nb.setScore(list_grade5, scoreGrade5);
    	scoreGrade6 = nb.setScore(list_grade6, scoreGrade6);
    	scoreGrade7 = nb.setScore(list_grade7, scoreGrade7);

    	ArrayList<Score> scoreGrade = new ArrayList<Score>();
    	if(list_grade1.isEmpty() == false) 	scoreGrade.add(scoreGrade1);
    	if(list_grade2.isEmpty() == false)	scoreGrade.add(scoreGrade2);
    	if(list_grade3.isEmpty() == false)	scoreGrade.add(scoreGrade3);
    	if(list_grade4.isEmpty() == false)	scoreGrade.add(scoreGrade4);
    	if(list_grade5.isEmpty() == false)	scoreGrade.add(scoreGrade5);
    	if(list_grade6.isEmpty() == false)	scoreGrade.add(scoreGrade6);
    	if(list_grade7.isEmpty() == false)	scoreGrade.add(scoreGrade7);
    	int is_para = 0;
    	nb.gaussianCal(test_list, scoreGrade, 3, is_para);*/
    	
    }


   	/* Sentence Score 
   	public Sentence getSentenceScore(String sentence){
   		Sentence str = new Sentence();
   		str.setLength(sentence.length());
   		str.setWord(sentence.split(" ").length);
   		str.setCnt_adjp(0);
   		str.setCnt_advp(0);
   		str.setPattern_score(0.0);
   		str.setStruct_type(0);
   		str.setVoca_score(0);
   		
   		return str;
   	}*/
   
   
   	/* input : Sentence
   	 * output : grade 
   	 * */ 
   	public Sentence getResult(String search_txt, Sentence sentence, int is_para){
   		//-- 학습 데이터
   		TrainingDataSelect td = new TrainingDataSelect();
   		NaiveBayesianDAO nb = new NaiveBayesianDAO();
   		/* DB 에서 데이터 가져오기 */
   		//HashMap<Integer, Score> map = td.selectSentenceScore();
   		/*ArrayList<Sentence> list_grade1 = td.selectRandomSentence("0", 6000);
   		ArrayList<Sentence> list_grade2 = td.selectRandomSentence("1", 6000);
   		ArrayList<Sentence> list_grade3 = td.selectRandomSentence("2", 6000);*/
   		/*ArrayList<Sentence> list_grade1 = td.selectRandomSentence_essay("0", 6000);
   		ArrayList<Sentence> list_grade2 = td.selectRandomSentence_essay("1", 6000);
   		ArrayList<Sentence> list_grade3 = td.selectRandomSentence_essay("2", 6000);*/
   		
   		//String filename = "D:\\Temp\\0529_grade7\\TPEDU_eval_set1.csv";
   		String filename = "";
   		ArrayList<Sentence> list_grade1 = new ArrayList<Sentence>();
   		ArrayList<Sentence> list_grade2 = new ArrayList<Sentence>();
   		ArrayList<Sentence> list_grade3 = new ArrayList<Sentence>();
   		ArrayList<Sentence> list_grade4 = new ArrayList<Sentence>();
   		ArrayList<Sentence> list_grade5 = new ArrayList<Sentence>();
   		
   		if(is_para == 0){		//문장
   			filename = "D:\\Temp\\TPEDU_train_sen.csv";
   			list_grade1 = td.readCsvSentence(filename, 0);
   			list_grade2 = td.readCsvSentence(filename, 1);
   			list_grade3 = td.readCsvSentence(filename, 2);
   			list_grade4 = td.readCsvSentence(filename, 3);
   			list_grade5 = td.readCsvSentence(filename, 4);
   		}else{					//문단
   			filename = "D:\\Temp\\1021\\TPEDU_train_set3.csv";
   			list_grade1 = td.readCsv(filename, 0);
   			list_grade2 = td.readCsv(filename, 1);
   			list_grade3 = td.readCsv(filename, 2);
   			list_grade4 = td.readCsv(filename, 3);
   			list_grade5 = td.readCsv(filename, 4);
   		}
    	/*ArrayList<Sentence> list_grade6 = td.readCsv(filename, 5);
    	ArrayList<Sentence> list_grade7 = td.readCsv(filename, 6);
    	ArrayList<Sentence> test_list = td.readCsv("D:\\Temp\\0615_essay_Normalized\\TPEDU_essay5_train.csv", -1);
    	*/

   		Score scoreGrade1 = new Score();
   		Score scoreGrade2 = new Score();
   		Score scoreGrade3 = new Score();
   		Score scoreGrade4 = new Score();
   		Score scoreGrade5 = new Score();
	   
   		//Grade 별 평균, 분산값 집어넣기 
   		scoreGrade1 = nb.setScore(list_grade1, scoreGrade1);
   		scoreGrade2 = nb.setScore(list_grade2, scoreGrade2);
   		scoreGrade3 = nb.setScore(list_grade3, scoreGrade3);
   		scoreGrade4 = nb.setScore(list_grade4, scoreGrade4);
   		scoreGrade5 = nb.setScore(list_grade5, scoreGrade5);

   		//등급값들을 하나의 ArrayList 에 집어넣기
   		//ArrayList<Score> scoreGrade = readData("D:/Temp/Naive/conf.bin");
   		ArrayList<Score> scoreGrade = new ArrayList<Score>();
   		if(list_grade1.isEmpty() == false) 	scoreGrade.add(scoreGrade1);
   		if(list_grade2.isEmpty() == false)	scoreGrade.add(scoreGrade2);
   		if(list_grade3.isEmpty() == false)	scoreGrade.add(scoreGrade3);
   		if(list_grade4.isEmpty() == false)	scoreGrade.add(scoreGrade4);
   		if(list_grade5.isEmpty() == false)	scoreGrade.add(scoreGrade5);
   		
   		sentence = gaussianEachFeatureCal(sentence, scoreGrade, 5);
   		
   		sentence.setGrade(nb.gaussianCal(sentence, scoreGrade, 5, is_para));
   		return sentence;				    /*가우시안 문장 별 등급 분류 (가장 큰 값 구하기)*/
   	}
   	

    /*public ArrayList<Score> paragraphReadData(String filename) {
    	ArrayList<Score> scoreGrade = new ArrayList<Score>();
		try {
			// CSVReader reader = new CSVReader(new FileReader(filename), '\t');
			// UTF-8
			BufferedReader in = new BufferedReader(new FileReader(filename));
			String s;
			while ((s = in.readLine()) != null) {
				Score data = new Score();
				String[] split = s.split("\t");
				int i = 0;
				
				data.setAvg_adjp(Double.parseDouble(split[i]));
				data.setAvg_advp(Double.parseDouble(split[++i]));
				data.setAvg_length(Double.parseDouble(split[++i]));
				data.setAvg_pattern(Double.parseDouble(split[++i]));
				data.setAvg_struct_type(Double.parseDouble(split[++i]));
				data.setAvg_voca(Double.parseDouble(split[++i]));
				data.setAvg_word_count(Double.parseDouble(split[++i]));
				
				data.setAvg_cnt_char(Double.parseDouble(split[++i]));
				data.setAvg_cnt_syllable(Double.parseDouble(split[++i]));
				data.setAvg_cnt_modifier(Double.parseDouble(split[++i]));
				data.setAvg_awl(Double.parseDouble(split[++i]));
				data.setAvg_variation_modifier(Double.parseDouble(split[++i]));
				data.setAvg_variation_adv(Double.parseDouble(split[++i]));
				data.setAvg_variation_adj(Double.parseDouble(split[++i]));
				data.setAvg_cnt_cc(Double.parseDouble(split[++i]));
				data.setAvg_cnt_sbar(Double.parseDouble(split[++i]));
				data.setAvg_cnt_compound(Double.parseDouble(split[++i]));
				data.setAvg_gr_cnt(Double.parseDouble(split[++i]));
				data.setAvg_gr_avg(Double.parseDouble(split[++i]));
				data.setAvg_gr_max(Double.parseDouble(split[++i]));
				data.setAvg_num_sen(Double.parseDouble(split[++i]));
				data.setAvg_ttr(Double.parseDouble(split[++i]));
				data.setAvg_cli(Double.parseDouble(split[++i]));
				data.setAvg_lix(Double.parseDouble(split[++i]));

				data.setVar_adjp(Double.parseDouble(split[i]));
				data.setVar_advp(Double.parseDouble(split[++i]));
				data.setVar_length(Double.parseDouble(split[++i]));
				data.setVar_pattern(Double.parseDouble(split[++i]));
				data.setVar_struct_type(Double.parseDouble(split[++i]));
				data.setVar_voca(Double.parseDouble(split[++i]));
				data.setVar_word_count(Double.parseDouble(split[++i]));
				
				data.setVar_cnt_char(Double.parseDouble(split[++i]));
				data.setVar_cnt_syllable(Double.parseDouble(split[++i]));
				data.setVar_cnt_modifier(Double.parseDouble(split[++i]));
				data.setVar_awl(Double.parseDouble(split[i]));
				data.setVar_variation_modifier(Double.parseDouble(split[++i]));
				data.setVar_variation_adv(Double.parseDouble(split[++i]));
				data.setVar_variation_adj(Double.parseDouble(split[++i]));
				data.setVar_cnt_cc(Double.parseDouble(split[++i]));
				data.setVar_cnt_sbar(Double.parseDouble(split[++i]));
				data.setVar_cnt_compound(Double.parseDouble(split[++i]));
				data.setVar_gr_cnt(Double.parseDouble(split[++i]));
				data.setVar_gr_avg(Double.parseDouble(split[++i]));
				data.setVar_gr_max(Double.parseDouble(split[++i]));
				data.setVar_num_sen(Double.parseDouble(split[++i]));
				data.setVar_ttr(Double.parseDouble(split[++i]));
				data.setVar_cli(Double.parseDouble(split[++i]));
				data.setVar_lix(Double.parseDouble(split[++i]));

				data.setAvg_cnt_pp(Double.parseDouble(split[++i]));
				data.setVar_cnt_pp(Double.parseDouble(split[++i]));
				data.setAvg_cdep(Double.parseDouble(split[++i]));
				data.setVar_cdep(Double.parseDouble(split[++i]));
				data.setAvg_dep_left(Double.parseDouble(split[++i]));
				data.setVar_dep_left(Double.parseDouble(split[++i]));
				data.setAvg_dep_right(Double.parseDouble(split[++i]));
				data.setVar_dep_right(Double.parseDouble(split[++i]));
				scoreGrade.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scoreGrade;
	}
    public ArrayList<Score> readData(String filename) {
    	ArrayList<Score> scoreGrade = new ArrayList<Score>();
    	try {
    		// CSVReader reader = new CSVReader(new FileReader(filename), '\t');
    		// UTF-8
    		BufferedReader in = new BufferedReader(new FileReader(filename));
    		String s;
    		while ((s = in.readLine()) != null) {
    			Score data = new Score();
    			String[] split = s.split("\t");
    			int i = 0;
    			
    			data.setAvg_adjp(Double.parseDouble(split[i]));
    			data.setAvg_advp(Double.parseDouble(split[++i]));
    			data.setAvg_length(Double.parseDouble(split[++i]));
    			data.setAvg_pattern(Double.parseDouble(split[++i]));
    			data.setAvg_struct_type(Double.parseDouble(split[++i]));
    			data.setAvg_voca(Double.parseDouble(split[++i]));
    			data.setAvg_word_count(Double.parseDouble(split[++i]));
    			
    			data.setAvg_cnt_char(Double.parseDouble(split[++i]));
    			data.setAvg_cnt_syllable(Double.parseDouble(split[++i]));
    			data.setAvg_cnt_modifier(Double.parseDouble(split[++i]));
    			data.setAvg_awl(Double.parseDouble(split[++i]));
    			data.setAvg_variation_modifier(Double.parseDouble(split[++i]));
    			data.setAvg_variation_adv(Double.parseDouble(split[++i]));
    			data.setAvg_variation_adj(Double.parseDouble(split[++i]));
    			data.setAvg_cnt_cc(Double.parseDouble(split[++i]));
    			data.setAvg_cnt_sbar(Double.parseDouble(split[++i]));
    			data.setAvg_cnt_compound(Double.parseDouble(split[++i]));
    			data.setAvg_gr_cnt(Double.parseDouble(split[++i]));
    			data.setAvg_gr_avg(Double.parseDouble(split[++i]));
    			data.setAvg_gr_max(Double.parseDouble(split[++i]));
    			data.setAvg_num_sen(Double.parseDouble(split[++i]));
    			data.setAvg_ttr(Double.parseDouble(split[++i]));
    			data.setAvg_cli(Double.parseDouble(split[++i]));
    			data.setAvg_lix(Double.parseDouble(split[++i]));
    			
    			data.setVar_adjp(Double.parseDouble(split[i]));
    			data.setVar_advp(Double.parseDouble(split[++i]));
    			data.setVar_length(Double.parseDouble(split[++i]));
    			data.setVar_pattern(Double.parseDouble(split[++i]));
    			data.setVar_struct_type(Double.parseDouble(split[++i]));
    			data.setVar_voca(Double.parseDouble(split[++i]));
    			data.setVar_word_count(Double.parseDouble(split[++i]));
    			
    			data.setVar_cnt_char(Double.parseDouble(split[++i]));
    			data.setVar_cnt_syllable(Double.parseDouble(split[++i]));
    			data.setVar_cnt_modifier(Double.parseDouble(split[++i]));
    			data.setVar_awl(Double.parseDouble(split[i]));
    			data.setVar_variation_modifier(Double.parseDouble(split[++i]));
    			data.setVar_variation_adv(Double.parseDouble(split[++i]));
    			data.setVar_variation_adj(Double.parseDouble(split[++i]));
    			data.setVar_cnt_cc(Double.parseDouble(split[++i]));
    			data.setVar_cnt_sbar(Double.parseDouble(split[++i]));
    			data.setVar_cnt_compound(Double.parseDouble(split[++i]));
    			data.setVar_gr_cnt(Double.parseDouble(split[++i]));
    			data.setVar_gr_avg(Double.parseDouble(split[++i]));
    			data.setVar_gr_max(Double.parseDouble(split[++i]));
    			data.setVar_num_sen(Double.parseDouble(split[++i]));
    			data.setVar_ttr(Double.parseDouble(split[++i]));
    			data.setVar_cli(Double.parseDouble(split[++i]));
    			data.setVar_lix(Double.parseDouble(split[++i]));
    			
    			scoreGrade.add(data);
    		}
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return scoreGrade;
    }*/

}
