package com.hanyang.iis.tpedu.mlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hanyang.iis.tpedu.dto.Sentence;

public class MLPController {
	/*
	 * numOfGrade = 7 : 7Grade numOfGrade = 3 : 3Grade
	 */
	public static int numOfGrade = 3; // Grade 수 
	public static int numInputs = 7; // 피쳐 개수
	public static int seed = 123;
	public static int batchSize = 50;
	public static int nEpochs = 30; // FF 회전 수
	public static int numHiddenNodes = 16;
	public static double learningRate = 0.05;
	public static boolean isNormalized = true;

	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { // 그리고 Feature들 Normalized 해야함! int grade=0; //
	 * 아무값 상관 없음 double type=0.25; // MAX = 4 double cnt_advp=0.5; // MAX = 5
	 * double cnt_adjp=0.25; // MAX = 5 double length=0.34; // MAX = 380 double
	 * word=0.45; // MAX = 55 double voca_score_sum=0.3; // MAX = 40.80 double
	 * pattern_score=0.4; // MAX = 7 String testStr =
	 * grade+","+type+","+cnt_advp+","+cnt_adjp+","+length+","+word+","+
	 * voca_score_sum+","+pattern_score+"\r\n"; writeCSV_test(testStr);
	 * 
	 * // MLP Testing 돌리는 부분. MLPClassifierLinear ff = new
	 * MLPClassifierLinear(); // Multilayer Perceptron // 시간 많이 걸리면 nEpochs 변수 값
	 * 10->5로 줄여도 됨. 성능 약간 저하 int testGrade =
	 * ff.feedForward(numOfGrade,numInputs,seed,batchSize,nEpochs,numHiddenNodes
	 * ,learningRate,4,isNormalized);
	 * 
	 * }
	 */
	
	public int getMLPResult(Sentence sentence) throws IOException, InterruptedException {
		// 그리고 Feature들 Normalized 해야함!
		double type = sentence.getStruct_type(); // MAX = 4
		double cnt_advp = sentence.getCnt_advp(); // MAX = 5
		double cnt_adjp = sentence.getCnt_adjp(); // MAX = 5
		double length = sentence.getLength(); // MAX = 380
		double word = sentence.getWord(); // MAX = 55
		double voca_score_sum = sentence.getVoca_score(); // MAX = 40.80
		double pattern_score = sentence.getPattern_score(); // MAX = 7
		
		int testGrade = 0;
		
		MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer Perceptron
		//double[][] features = {{0.25,0.0,0.0,0.197,0.254,0.183,0.857}}; // feature value 입력
		double[][] features = {{type,cnt_advp,cnt_adjp,length,word,voca_score_sum,pattern_score}}; // feature value 입력
        double[][] grade = {{0,0,1}}; // label 입력
        testGrade = ff.feedForward(features, grade,numOfGrade,numInputs,seed,batchSize,nEpochs,numHiddenNodes,learningRate);
		
		/*String testStr = grade + "," + type + "," + cnt_advp + "," + cnt_adjp + "," + length + "," + word + "," + voca_score_sum + "," + pattern_score + "\r\n";
		writeCSV_test(testStr);

		// MLP Testing 돌리는 부분.
		MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer
															// Perceptron
		// 시간 많이 걸리면 nEpochs 변수 값 10->5로 줄여도 됨. 성능 약간 저하
		int testGrade = ff.feedForward(numOfGrade, numInputs, seed, batchSize, nEpochs, numHiddenNodes, learningRate, 4, isNormalized);*/

		return testGrade;
	}

	/*public static void writeCSV_test(String str) {
		try {
			String csvFilename = "D:/Temp/TPEDU_test.csv";
			File file = new File(csvFilename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilename, true));
			
			writer.write(str);
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.println("writeCSV err : " + e.getMessage());
		}
	}*/

}
