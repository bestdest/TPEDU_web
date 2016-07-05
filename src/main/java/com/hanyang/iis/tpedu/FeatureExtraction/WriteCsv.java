package com.hanyang.iis.tpedu.FeatureExtraction;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class WriteCsv {
	public static void writeCSV_train(String str, int set){
		try{
			String csvFilename = "src/main/resources/classification/TPEDU_train_set"+set+".csv";
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilename),"MS949"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilename, true));
			writer.write(str);
			writer.close();
			
		}catch(Exception e){
			System.err.println("writeCSV err : " + e.getMessage());
		}
	}
	
	public static void writeCSV_eval(String str, int set){
		try{
			String csvFilename = "src/main/resources/classification/TPEDU_eval_set"+set+".csv";
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFilename),"MS949"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilename, true));
			writer.write(str);
			writer.close();
			
		}catch(Exception e){
			System.err.println("writeCSV err : " + e.getMessage());
		}
	}
	
	
}
