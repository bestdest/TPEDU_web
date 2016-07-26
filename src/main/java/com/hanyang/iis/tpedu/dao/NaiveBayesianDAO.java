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

    /*문장 길이 평균, 분산 점수 */
    public Score getLengthScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getLength());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_length(avgData);    	
    	scoreGrade.setVar_length(varData);    	
    	
    	return scoreGrade;
    }
    
    /*어휘 점수 평균, 분산 점수 */
    public Score getVocaScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
   			dataSet.add(dataList.get(i).getVoca_score());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_voca(avgData);    	
    	scoreGrade.setVar_voca(varData);    	    	
    	return scoreGrade;
    }
    
    /*패턴 점수 평균, 분산 점수 */
    public Score getPatternScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	ArrayList<Double> testSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
   			dataSet.add(dataList.get(i).getPattern_score());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_pattern(avgData);    	
    	scoreGrade.setVar_pattern(varData); 
    	
    	return scoreGrade;
    }
    
    /*단어 수 평균, 분산 점수 */
    public Score getWordScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getWord());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_word_count(avgData);    	
    	scoreGrade.setVar_word_count(varData); 
    	
    	return scoreGrade;
    }
    
    /*형용사구 평균, 분산 점수 */
    public Score getAdjpScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getCnt_adjp());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_adjp(avgData);    	
    	scoreGrade.setVar_adjp(varData); 
    	
    	return scoreGrade;
    }
    
    /*부사구 평균, 분산 점수 */
    public Score getAdvpScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getCnt_advp());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_advp(avgData);    	
    	scoreGrade.setVar_advp(varData); 
    	
    	return scoreGrade;
    }
    
    /*구조 타입 평균, 분산 점수 */
    public Score getStructType(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getStruct_type());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	//System.out.println("Avg : " + avgData + "  Var : " + varData);
    	
    	scoreGrade.setAvg_struct_type(avgData);    	
    	scoreGrade.setVar_struct_type(varData); 
    	
    	return scoreGrade;
    }

    /*가우시안 등급 계산*/
    public void gaussianCal(ArrayList<Sentence> testList, ArrayList<Score> gradeScore, int grade_count){
		int isFail = 0;
		int isSuccess = 0;
		int isTotalGrade[] = new int[grade_count];
    	int isTruegrade[] = new int[grade_count];
    	int isEachGradeCount[][] = new int[grade_count][grade_count];
    	
    	/* testList 값 꺼내서 체크 */
    	for(int i = 0; i < testList.size(); i++){
    		
    		/* Grade 값 계산 */
    		Double[] grade = new Double[grade_count];
    		for(int j = 0; j < gradeScore.size(); j++){
    			grade[j] = getCal(gradeScore.get(j), testList.get(i));
    		}
    		int orgin_grade = testList.get(i).getGrade();
    		int classification = 0;
    		
    		/* Grade 최대값 구하기 */
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

    	/* 각 등급별 분류 개수 */
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
    	/* 전체 성공 실패 계산 값 */
    	for (int total : isTotalGrade) {
    		int succ = isTruegrade[index];
    		tempre = (float) (succ*1.0 / total*1.0);
    		re += tempre;
    		System.out.println("  " + index++ + " total : " + total + " success :  " + succ + "  calculate : " + tempre);
    	}
    	
    	System.out.println("성공횟수 : " + isSuccess + "  실패횟수 : " + isFail + "  Accurancy : " + isSuccess*1.0/(isFail + isSuccess)*1.0 + "  Precision : " + re/grade_count);
    }
    
    /*가우시안 문장 별 등급 분류 (가장 큰 값 구하기)*/
    public int gaussianCal(Sentence sentence, ArrayList<Score> gradeScore, int grade_count){
    	
    	/* Grade 값 계산 */
		Double[] grade = new Double[grade_count];
		for(int j = 0; j < gradeScore.size(); j++){
			grade[j] = getCal(gradeScore.get(j), sentence);
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
    
    
    /*각 항목별 등급 분류 */
    public Sentence gaussianEachFeatureCal(Sentence sentence, ArrayList<Score> gradeScore, int grade_count){

    	ArrayList<Double[]> length = new ArrayList<Double[]>();
    	ArrayList<Double[]> vaca = new ArrayList<Double[]>();
    	ArrayList<Double[]> word = new ArrayList<Double[]>();
    	ArrayList<Double[]> advp = new ArrayList<Double[]>();
    	ArrayList<Double[]> adjp = new ArrayList<Double[]>();
    	ArrayList<Double[]> pattern = new ArrayList<Double[]>();
    	ArrayList<Double[]> struct = new ArrayList<Double[]>();
    	
    	/* Grade 값 계산 */
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
    		
    		temp = new Double[]{gradeScore.get(j).getAvg_struct_type(), gradeScore.get(j).getAvg_struct_type()};
    		struct.add(temp);
    		
    	}
    	sentence.setGrade_length(gradeClassification(length, sentence.getLength()));
    	sentence.setGrade_voca_score(gradeClassification(vaca, sentence.getVoca_score()));
    	sentence.setGrade_word(gradeClassification(word, sentence.getWord()));
    	sentence.setGrade_cnt_advp(gradeClassification(advp, sentence.getCnt_advp()));
    	sentence.setGrade_cnt_adjp(gradeClassification(adjp, sentence.getCnt_adjp()));
    	sentence.setGrade_pattern_score(gradeClassification(pattern, sentence.getPattern_score()));
    	sentence.setGrade_struct_type(gradeClassification(struct, sentence.getStruct_type()));

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
    public Double getCal(Score gradeScore, Sentence sentence){
    	TrainingDataSelect td = new TrainingDataSelect();
    	
    	Double length_var = td.calculation(gradeScore.getAvg_length(), gradeScore.getVar_length(), sentence.getLength());
    	Double voca_var = td.calculation(gradeScore.getAvg_voca(), gradeScore.getVar_voca(), sentence.getVoca_score());
    	Double word_var = td.calculation(gradeScore.getAvg_word_count(), gradeScore.getVar_word_count(), sentence.getWord());
    	Double pattern_var = td.calculation(gradeScore.getAvg_pattern(), gradeScore.getVar_pattern(), sentence.getPattern_score());
    	Double adjp_var = td.calculation(gradeScore.getAvg_adjp(), gradeScore.getVar_adjp(), sentence.getCnt_adjp());
    	Double advp_var = td.calculation(gradeScore.getAvg_advp(), gradeScore.getVar_advp(), sentence.getCnt_advp());
    	Double struct_var = td.calculation(gradeScore.getAvg_struct_type(), gradeScore.getVar_struct_type(), sentence.getStruct_type());
    	
    	Double result = length_var * voca_var * pattern_var * word_var * adjp_var * advp_var * struct_var;
    	
    	return result;
    }
    
    /*Grade 별 평균, 분산값 집어넣기 */
    public Score setScore(ArrayList<Sentence> list_grade, Score scoreGrade){
    	NaiveBayesianDAO nb = new NaiveBayesianDAO();
    	
    	scoreGrade = nb.getLengthScore(list_grade, scoreGrade);
    	scoreGrade = nb.getVocaScore(list_grade, scoreGrade);
    	scoreGrade = nb.getPatternScore(list_grade, scoreGrade);
    	scoreGrade = nb.getWordScore(list_grade, scoreGrade);
    	scoreGrade = nb.getAdjpScore(list_grade, scoreGrade);
    	scoreGrade = nb.getAdvpScore(list_grade, scoreGrade);
    	scoreGrade = nb.getStructType(list_grade, scoreGrade);
    	
    	return scoreGrade;
    }

    
    public static void main(String[] args) throws Exception {
        //-- 학습 데이터
    	TrainingDataSelect td = new TrainingDataSelect();

    	/* DB 에서 데이터 가져오기 */
    	//HashMap<Integer, Score> map = td.selectSentenceScore();
    	ArrayList<Sentence> list_grade1 = td.selectRandomSentence("0", 6000);
    	ArrayList<Sentence> list_grade2 = td.selectRandomSentence("1", 6000);
    	ArrayList<Sentence> list_grade3 = td.selectRandomSentence("2", 6000);
    	ArrayList<Sentence> test_list = td.selectRandomSentence("0,1,2", 2000);
    	
    	/*String filename = "D:\\Temp\\0615_essay_Normalized\\TPEDU_essay3_train.csv";
    	//String filename = "D:\\Temp\\0529_grade7\\TPEDU_eval_set1.csv";
    	ArrayList<Sentence> list_grade1 = td.readCsv(filename, 0);
    	ArrayList<Sentence> list_grade2 = td.readCsv(filename, 1);
    	ArrayList<Sentence> list_grade3 = td.readCsv(filename, 2);
    	ArrayList<Sentence> list_grade4 = td.readCsv(filename, 3);
    	ArrayList<Sentence> list_grade5 = td.readCsv(filename, 4);
    	ArrayList<Sentence> list_grade6 = td.readCsv(filename, 5);
    	ArrayList<Sentence> list_grade7 = td.readCsv(filename, 6);
    	ArrayList<Sentence> test_list = td.readCsv("D:\\Temp\\0615_essay_Normalized\\TPEDU_essay3_train.csv", -1);*/

    	/* 가져온 데이터 보기 */
    	/*Set<Integer> keySet = map.keySet();
    	Iterator<Integer> iterator = keySet.iterator();
    	while (iterator.hasNext()) {
    		Integer key = iterator.next();
    		Sentence sentence = map.get(key);
    		System.out.printf("key : %s , leng_avg : %s , leng_var : %s %n", key, score.getAvg_length(), score.getVar_length());
    	}*/
    	
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
    	/*scoreGrade4 = nb.setScore(list_grade4, scoreGrade4);
    	scoreGrade5 = nb.setScore(list_grade5, scoreGrade5);
    	scoreGrade6 = nb.setScore(list_grade6, scoreGrade6);
    	scoreGrade7 = nb.setScore(list_grade7, scoreGrade7);*/

    	ArrayList<Score> scoreGrade = new ArrayList<Score>();
    	if(list_grade1.isEmpty() == false) 	scoreGrade.add(scoreGrade1);
    	if(list_grade2.isEmpty() == false)	scoreGrade.add(scoreGrade2);
    	if(list_grade3.isEmpty() == false)	scoreGrade.add(scoreGrade3);
    	/*if(list_grade4.isEmpty() == false)	scoreGrade.add(scoreGrade4);
    	if(list_grade5.isEmpty() == false)	scoreGrade.add(scoreGrade5);
    	if(list_grade6.isEmpty() == false)	scoreGrade.add(scoreGrade6);
    	if(list_grade7.isEmpty() == false)	scoreGrade.add(scoreGrade7);*/
    	
    	nb.gaussianCal(test_list, scoreGrade, 3);
    	
    }

    public Double getAvg(ArrayList<Double> dataSet){
    	Double result = null;
    	Double sumData = 0.0;
    	for(int i = 0; i < dataSet.size(); i++){
    		if(dataSet.get(i) == null){
    			break;
    		}
    		sumData += dataSet.get(i);
    	}
    	result = sumData / dataSet.size();
    	
    	return result;
    }
    
    public Double getStdv(ArrayList<Double> dataSet){
	   	Double result = null;
	   	Double sumData = 0.0;
	   	int dataSize = dataSet.size();
	   	Double avgData = getAvg(dataSet);
	   
	   	if(dataSize > 2){
		   	int i = 0; 
		   	for(i = 0; i < dataSize; i++){
			   	if(dataSet.get(i) == null){
			   		break;
			   	}
			   	Double diff = dataSet.get(i) - avgData;
			   	sumData += Math.pow(diff, 2);
		   	}
	   	}
	   	result = sumData / dataSize;
	   
	   	return Math.sqrt(result);
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
   	public Sentence getResult(String search_txt, Sentence sentence){
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
   		/*String filename = "D:\\Temp\\TPEDU_train.csv";
    	ArrayList<Sentence> list_grade1 = td.readCsv(filename, 0);
    	ArrayList<Sentence> list_grade2 = td.readCsv(filename, 1);
    	ArrayList<Sentence> list_grade3 = td.readCsv(filename, 2);*/
    	/*ArrayList<Sentence> list_grade4 = td.readCsv(filename, 3);
    	ArrayList<Sentence> list_grade5 = td.readCsv(filename, 4);
    	ArrayList<Sentence> list_grade6 = td.readCsv(filename, 5);
    	ArrayList<Sentence> list_grade7 = td.readCsv(filename, 6);
    	ArrayList<Sentence> test_list = td.readCsv("D:\\Temp\\0615_essay_Normalized\\TPEDU_essay5_train.csv", -1);
    	*/

	    
   		/*
   		Score scoreGrade1 = new Score();
   		Score scoreGrade2 = new Score();
   		Score scoreGrade3 = new Score();
	   
   		//Grade 별 평균, 분산값 집어넣기 
   		scoreGrade1 = nb.setScore(list_grade1, scoreGrade1);
   		scoreGrade2 = nb.setScore(list_grade2, scoreGrade2);
   		scoreGrade3 = nb.setScore(list_grade3, scoreGrade3);*/

   		//등급값들을 하나의 ArrayList 에 집어넣기
   		ArrayList<Score> scoreGrade = readData("D:/Temp/Naive/conf.bin");
   		/*if(list_grade1.isEmpty() == false) 	scoreGrade.add(scoreGrade1);
   		if(list_grade2.isEmpty() == false)	scoreGrade.add(scoreGrade2);
   		if(list_grade3.isEmpty() == false)	scoreGrade.add(scoreGrade3);*/
   		
	   
   		sentence = gaussianEachFeatureCal(sentence, scoreGrade, 3);
   		
   		sentence.setGrade(nb.gaussianCal(sentence, scoreGrade, 3));
   		return sentence;				    /*가우시안 문장 별 등급 분류 (가장 큰 값 구하기)*/
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
				data.setVar_adjp(Double.parseDouble(split[i]));
				data.setVar_advp(Double.parseDouble(split[++i]));
				data.setVar_length(Double.parseDouble(split[++i]));
				data.setVar_pattern(Double.parseDouble(split[++i]));
				data.setVar_struct_type(Double.parseDouble(split[++i]));
				data.setVar_voca(Double.parseDouble(split[++i]));
				data.setVar_word_count(Double.parseDouble(split[++i]));
				
				scoreGrade.add(data);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scoreGrade;
	}

}
