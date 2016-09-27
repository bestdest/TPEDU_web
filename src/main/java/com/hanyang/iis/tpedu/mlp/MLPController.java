package com.hanyang.iis.tpedu.mlp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.hanyang.iis.tpedu.FeatureExtraction.ParaFeatureExtractor;
import com.hanyang.iis.tpedu.FeatureExtraction.SenFeatureExtractor;
import com.hanyang.iis.tpedu.dto.ParaFeatureDTO;
import com.hanyang.iis.tpedu.dto.SenFeatureDTO;
import com.hanyang.iis.tpedu.dto.Sentence;

public class MLPController {
	/*
	 * numOfGrade = 7 : 7Grade
	 * numOfGrade = 3 : 3Grade
	 */
	public static int numOfGrade = 5; 
	public static int numInputs = 24; // 피쳐 개수
	public static int seed = 123;
	public static int batchSize = 50;
	public static int nEpochs = 30; // FF 회전 수 원래 30
	public static int numHiddenNodes = 37;
	public static float learningRate = (float) 0.06;
	public static boolean isNormalized = true;
	
	public static int numPara_GCSE=0;
	public static int totalNum =0;

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
	
	public int getMLPResult(Sentence sentence, int is_paragraph) throws IOException, InterruptedException {
		// 그리고 Feature들 Normalized 해야함!
		/*double type = sentence.getStruct_type(); // MAX = 4
		double cnt_advp = sentence.getCnt_advp(); // MAX = 5
		double cnt_adjp = sentence.getCnt_adjp(); // MAX = 5
		double length = sentence.getLength(); // MAX = 380
		double word = sentence.getWord(); // MAX = 55
		double voca_score_sum = sentence.getVoca_score(); // MAX = 40.80
		double pattern_score = sentence.getPattern_score(); // MAX = 7
		
		int testGrade = 0;*/
		
		/*MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer Perceptron
		//double[][] features = {{0.25,0.0,0.0,0.197,0.254,0.183,0.857}}; // feature value 입력
		double[][] features = {{type,cnt_advp,cnt_adjp,length,word,voca_score_sum,pattern_score}}; // feature value 입력
        double[][] grade = {{0,0,1}}; // label 입력
        testGrade = ff.feedForward(features, grade,numOfGrade,numInputs,seed,batchSize,nEpochs,numHiddenNodes,learningRate);*/
		
		/*String testStr = grade + "," + type + "," + cnt_advp + "," + cnt_adjp + "," + length + "," + word + "," + voca_score_sum + "," + pattern_score + "\r\n";
		writeCSV_test(testStr);

		// MLP Testing 돌리는 부분.
		MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer
															// Perceptron
		// 시간 많이 걸리면 nEpochs 변수 값 10->5로 줄여도 됨. 성능 약간 저하
		int testGrade = ff.feedForward(numOfGrade, numInputs, seed, batchSize, nEpochs, numHiddenNodes, learningRate, 4, isNormalized);*/

		int label = 0;
		
		if(is_paragraph == 0){
			/*
			 * 문장 수준의 MLP
			 */
			
			/*float type = (float)sentence.getStruct_type()/4;
			float cnt_advp = (float)sentence.getCnt_advp()/15;
			float cnt_adjp = (float)sentence.getCnt_adjp()/34;
			float cnt_modifier = (float)sentence.getCnt_modifier()/103;
			float length = (float)sentence.getLength()/1306;
			float word = (float)sentence.getWord()/207;
			float numChar = (float)sentence.getAvg_char()/15;
			float numSyll = (float)sentence.getAvg_syllables()/6;
			float voca_score = (float)sentence.getVoca_score()/(float)226.4;
			float pattern_score = (float)sentence.getPattern_score()/5;
			float AWL_score = (float)sentence.getRatio_awl()/(float)9.1;
			float modifierVar = (float)sentence.getVar_modifier();
			float advpVar = (float)sentence.getVar_adv();
			float adjpVar = (float)sentence.getVar_adj();
			float numCC = (float)sentence.getCnt_cc()/14;
			float numSBAR = (float)sentence.getCnt_sbar()/20;
			float numCompound = (float)sentence.getCnt_compound()/41;
			float numGR = (float)sentence.getCnt_gr()/207;
			float avg_dist_GR = (float)sentence.getAvg_dis_gr()/207;
			float max_dist_GR = (float)sentence.getMax_dis_gr()/207;*/
			
			float type = (float)sentence.getStruct_type();
			float cnt_advp = (float)sentence.getCnt_advp();
			float cnt_adjp = (float)sentence.getCnt_adjp();
			float cnt_modifier = (float)sentence.getCnt_modifier();
			float length = (float)sentence.getLength();
			float word = (float)sentence.getWord();
			float numChar = (float)sentence.getAvg_char();
			float numSyll = (float)sentence.getAvg_syllables();
			float voca_score = (float)sentence.getVoca_score();
			float pattern_score = (float)sentence.getPattern_score();
			float AWL_score = (float)sentence.getRatio_awl();
			float modifierVar = (float)sentence.getVar_modifier();
			float advpVar = (float)sentence.getVar_adv();
			float adjpVar = (float)sentence.getVar_adj();
			float numCC = (float)sentence.getCnt_cc();
			float numSBAR = (float)sentence.getCnt_sbar();
			float numCompound = (float)sentence.getCnt_compound();
			float numGR = (float)sentence.getCnt_gr();
			float avg_dist_GR = (float)sentence.getAvg_dis_gr();
			float max_dist_GR = (float)sentence.getMax_dis_gr();
			
			
			MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer Perceptron
			float[][] features = {{type,cnt_advp,cnt_adjp,cnt_modifier,length,word,numChar,numSyll,
				voca_score,pattern_score,AWL_score,modifierVar,advpVar,adjpVar,numCC,numSBAR,numCompound,
				numGR,avg_dist_GR,max_dist_GR}}; // feature value 입력
			float[][] grade = {{0,0,1,0,0}}; // label 입력
			label = ff.feedForward(features,grade,numOfGrade, false);
			System.out.println("Grade : " + label);
			
		}
		
		else{
			
			/*
			 * 문단 수준
			 */
			
			/*float type = (float)sentence.getStruct_type()/4;
			float cnt_advp = (float)sentence.getCnt_advp()/135;
			float cnt_adjp = (float)sentence.getCnt_adjp()/125;
			float cnt_modifier = (float)sentence.getCnt_modifier()/2209;
			float length = (float)sentence.getLength()/24606;
			float word = (float)sentence.getWord()/4443;
			float numChar = (float)sentence.getAvg_char()/7;
			float numSyll = (float)sentence.getAvg_syllables()/(float)2.3;
			float voca_score = (float)sentence.getVoca_score()/(float)31.3;
			float pattern_score = (float)sentence.getPattern_score()/5;
			float AWL_score = (float)sentence.getRatio_awl()/(float)50;
			float modifierVar = (float)sentence.getVar_modifier()/(float)0.8;
			float advpVar = (float)sentence.getVar_adv()/(float)0.27;
			float adjpVar = (float)sentence.getVar_adj()/(float)0.23;
			float numCC = (float)sentence.getCnt_cc()/205;
			float numSBAR = (float)sentence.getCnt_sbar()/236;
			float numCompound = (float)sentence.getCnt_compound()/146;
			float numGR = (float)sentence.getCnt_gr()/4740;
			float avg_dist_GR = (float)sentence.getAvg_dis_gr()/(float)39.5;
			float max_dist_GR = (float)sentence.getMax_dis_gr()/87;

			float TTR = (float)sentence.getTtr();
			float CLI = (float)sentence.getCli()/(float)32.1;
			float LIX = (float)sentence.getLix()/(float)106.3;
			float numSen = (float)sentence.getNum_sen()/230;*/
			float type = (float)sentence.getStruct_type();
			float cnt_advp = (float)sentence.getCnt_advp();
			float cnt_adjp = (float)sentence.getCnt_adjp();
			float cnt_modifier = (float)sentence.getCnt_modifier();
			float length = (float)sentence.getLength();
			float word = (float)sentence.getWord();
			float numChar = (float)sentence.getAvg_char();
			float numSyll = (float)sentence.getAvg_syllables();
			float voca_score = (float)sentence.getVoca_score();
			float pattern_score = (float)sentence.getPattern_score();
			float AWL_score = (float)sentence.getRatio_awl();
			float modifierVar = (float)sentence.getVar_modifier();
			float advpVar = (float)sentence.getVar_adv();
			float adjpVar = (float)sentence.getVar_adj();
			float numCC = (float)sentence.getCnt_cc();
			float numSBAR = (float)sentence.getCnt_sbar();
			float numCompound = (float)sentence.getCnt_compound();
			float numGR = (float)sentence.getCnt_gr();
			float avg_dist_GR = (float)sentence.getAvg_dis_gr();
			float max_dist_GR = (float)sentence.getMax_dis_gr();
			
			float TTR = (float)sentence.getTtr();
			float CLI = (float)sentence.getCli();
			float LIX = (float)sentence.getLix();
			float numSen = (float)sentence.getNum_sen();
			
			
			/*
			 * 문단 수준의 MLP
			 */
			MLPClassifierLinear ff = new MLPClassifierLinear(); // Multilayer Perceptron
			float[][] features = {{numSen,type,cnt_advp,cnt_adjp,cnt_modifier,length,word,numChar,numSyll,
				voca_score,pattern_score,AWL_score,modifierVar,advpVar,adjpVar,numCC,numSBAR,numCompound,
				numGR,avg_dist_GR,max_dist_GR,TTR,CLI,LIX}}; // feature value 입력
			float[][] grade = {{0,0,1,0,0}}; // label 입력
			label = ff.feedForward(features,grade,numOfGrade, true);
			System.out.println("Grade : " + label);
		}
		
		return label;
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
