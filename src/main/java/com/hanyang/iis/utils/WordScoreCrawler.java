package com.hanyang.iis.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;

public class WordScoreCrawler {
	public static double MAX = 50;
	
	public static double vocaScore(String sentence){
		StringTokenizer st = new StringTokenizer(sentence," ,.?!-()/;:~\"");
		String[] words = new String[st.countTokens()];
		Double score = 0.0;
		
		int i=0;
		while(st.hasMoreTokens()){
			words[i++]=st.nextToken().toLowerCase();
		}
		Double sentence_Score = 0.0;
		int words_count=0;
		for(i=0; i< words.length;i++){
//			System.out.println(words[i]);
			words[i].replace("\"", "");
			if(words[i].length()==1)
				continue;
			if(words[i].contains("hansel") ||words[i].contains("mongolians") ||words[i].contains("spiderman") ||words[i].contains("pipi") ||words[i].contains("mattie") ||words[i].contains("bella") || words[i].contains("mira") || words[i].contains("dancers") || words[i].contains("ﬁrst"))
				continue;
			if(words[i].contains("'") || words[i].contains("$") || words[i].contains("&")|| words[i].contains("%"))
				continue;
			
			if(GetWordScore_DB(words[i])>0.0 && GetWordScore_DB(words[i]) < MAX){
				score = GetWordScore_DB(words[i]);
			}else if(GetWordScore_DB(words[i]) >=MAX){
				continue;
			}else{
				score = GetWordScore_Crawler(words[i]);
				System.out.println(words[i] + " :  "+ score);
				if(score != 0.0){
					InsertWordScore_DB(words[i], score);
				}
			}
			
			if(score ==0.0)
				continue;
			
//			System.out.println(words[i]+"="+score);
			words_count++;
//			System.out.println(MAX/score+" : " + Math.log(MAX/score));
			sentence_Score += Math.log10(MAX/score); // 문장 어휘 점수 총합
//			System.out.println(words[i] + " : " + Math.log10(MAX/score));
		}
		
		return sentence_Score; // Sum
		
//		if(sentence_Score == 0.0) //Avg
//			return sentence_Score;
//		else
//			return sentence_Score/(double)words_count; 
	}
	
	
	public static void vocaInsertToDB(){
		// 여기는 Training Dataset 어휘 DB화 하는 부분.
				try{
					String url = "jdbc:mysql://166.104.140.75:60000/TPE_EDU";
					Class.forName("com.mysql.jdbc.Driver");
					Connection conn = ((Connection)DriverManager.getConnection(url, "root","iislabkey"));
					Statement stmt = conn.createStatement();
					String sql = "select * from tbl_pattern_lists2";
					
					
					ResultSet rs = stmt.executeQuery(sql);
					double sumByGrade=0.0;
					int sen_count = 0; 
					
							
					while(rs.next()){
//						System.out.println(rs.getString("sentence"));
						String sentence = rs.getString("sentence");
						StringTokenizer st = new StringTokenizer(sentence," ,.?!-()/:\"");
						String[] words = new String[st.countTokens()];
						Double score = 0.0;
						
						int i=0;
						while(st.hasMoreTokens()){
							words[i++]=st.nextToken().toLowerCase();
						}
						Double sentence_Score = 0.0;
						for(i=0; i< words.length;i++){
							if(words[i].contains("dancers"))
								continue;
							if(GetWordScore_DB(words[i])>0.0){
								score = GetWordScore_DB(words[i]);
							}else{
								score = GetWordScore_Crawler(words[i]);
								if(score != 0.0)
									InsertWordScore_DB(words[i], score);
							}
							
							sentence_Score += (MAX-score)/MAX; // 문장 어휘 점수 총합
						}
						System.out.println("문장 어휘 점수 : " + sentence_Score);
						sumByGrade += sentence_Score; // Grade X의 문장 어휘 총합
						sen_count++;
					}
					
					double avgByGrade = sumByGrade/sen_count;
					System.out.println(sen_count+ "개 문장 어휘 평균 점수 : " + avgByGrade);
					
					rs.close();
					stmt.close();
					conn.close();
					
				}catch(Exception e){
					System.err.println("Main Err : " + e.getMessage());
				}
	}
	
	public static void InsertWordScore_DB(String word, Double score){
		try{
			
			String url = "jdbc:mysql://166.104.140.75:60000/TPE_EDU";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = ((Connection)DriverManager.getConnection(url, "root","iislabkey"));
			Statement stmt = conn.createStatement();
			String sql = "insert into tbl_word_score_eng (Word, Score) values ('"+word+"','"+score+"');";
			
			
			stmt.executeUpdate(sql);
			System.out.println(word+":"+score +" inserted!!");
			
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			System.err.println("InsertWordScore_DB Err : " + e.getMessage());
		}
		
	}
	
	
	public static Double GetWordScore_DB(String word){
		Double word_score=0.0;
		try{
			
			String url = "jdbc:mysql://166.104.140.75:60000/TPE_EDU";
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = ((Connection)DriverManager.getConnection(url, "root","iislabkey"));
			Statement stmt = conn.createStatement();
			String sql = "select Score from tbl_word_score_eng where Word='"+word+"';";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				word_score = rs.getDouble("score");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			System.err.println("GetWordScore_DB Err : " + e.getMessage());
		}
		
		return word_score;	
	}

	public static Double GetWordScore_Crawler(String word){
		// Loop
		String url = "http://www.collinsdictionary.com/dictionary/english/"+word;
		String html = GetHtmlThroughURL(url);
		Document document = Jsoup.parse(html);
		
		Elements div = document.select("script");
		
		Double word_score = 0.0;
		
		for(Element e: div){
			for(DataNode node : e.dataNodes()){
				String[] script = node.getWholeData().split("\"2008\",");
				for(int i=0; i<script.length;i++){
					String[] script2 = script[i].split("]];");
					
					
					if(i==1 ){
						if(script2[0].contains("null"))
							word_score = 100.0;
						else
							word_score = Double.parseDouble(script2[0]);
//						System.out.println(word_score);
					}
				}
			}
		}
		
		return word_score;
	}
	
	
	
	public static String GetHtmlThroughURL(String strUrl) {
		BufferedInputStream in = null;
		StringBuffer sb = new StringBuffer();

		try {
			URL url = new URL(strUrl);
			URLConnection urlConnection = url.openConnection();
			in = new BufferedInputStream(urlConnection.getInputStream());

			byte[] bufRead = new byte[4096];
			int lenRead = 0;
			while ((lenRead = in.read(bufRead)) > 0)
				sb.append(new String(bufRead, 0, lenRead));

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return sb.toString();
	}
}
