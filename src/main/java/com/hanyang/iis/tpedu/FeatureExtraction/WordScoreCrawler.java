package com.hanyang.iis.tpedu.FeatureExtraction;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mysql.jdbc.Connection;
import com.org.watsonwrite.lawrence.Lawrence;

public class WordScoreCrawler {
	public static double MAX = 50;
	
	public static HashMap<String,Double> vocaScore(String sentence) throws IOException{
		StringTokenizer st = new StringTokenizer(sentence," ,.?!-()/;:~\"");
		String[] words = new String[st.countTokens()];
		Double score = 0.0;
		
		int i=0;
		while(st.hasMoreTokens()){
			words[i++]=st.nextToken().toLowerCase();
		}
		double sentence_Score = 0.0;
		double AWL_score = 0.0;
		HashMap<String,Double> lexical = new HashMap<String,Double>();
		
		int words_count=0;
		int sum_of_wordlen=0;
		int sum_of_wordsyl=0;
		Lawrence lawrence = new Lawrence();
		
		for(i=0; i< words.length;i++){
			
			/*
			 * AWL score Adding
			 */
			for(int j=1; j<11;j++){
				String file = "src/main/resources/AWL/AWL"+j+".txt";
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				String awl_word = null;
				while((awl_word = reader.readLine())!=null){
					if(words[i].equals(awl_word)){
//						System.out.println(j+ " : " + words[i]);
						AWL_score += 0.1*j;
					}	
				}
			}
			
			sum_of_wordlen += words[i].length();
			sum_of_wordsyl += lawrence.getSyllable(words[i]);
			
			words[i].replace("\"", "");
			if(words[i].length()==1)
				continue;
			if(words[i].contains("hansel") ||words[i].contains("mongolians") ||words[i].contains("spiderman") ||words[i].contains("pipi") ||words[i].contains("mattie") ||words[i].contains("bella") || words[i].contains("mira") || words[i].contains("dancers") || words[i].contains("?rst"))
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
			
			if(score == 0.0)
				continue;
			
			words_count++;
			sentence_Score += Math.log10(MAX/score); // 문장 어휘 점수 총합
		}
		
		double avg_of_wordlen = (double)sum_of_wordlen/(double)words.length;
		double avg_of_wordsyl = (double)sum_of_wordsyl/(double)words.length;
//		System.out.println("NumChar : " + avg_of_wordlen);
//		System.out.println("NumSyll : " + avg_of_wordsyl);
		lexical.put("voca_score", sentence_Score);
		lexical.put("NumChar", avg_of_wordlen);
		lexical.put("NumSyll", avg_of_wordsyl);
		lexical.put("AWL_score", AWL_score);
		
		return lexical; // Sum
		
//		if(sentence_Score == 0.0) //Avg
//			return sentence_Score;
//		else
//			return sentence_Score/(double)words_count; 
	}
	
	public static int countSyllabes2(String word){
		  // getNumSyllables method in BasicDocument (module 1) and 
	    // EfficientDocument (module 2).
	    int syllables = 0;
	    word = word.toLowerCase();
	    if(word.contains("the ")){
	        syllables ++;
	    }
	    String[] split = word.split("e!$|e[?]$|e,|e |e[),]|e$");

	    ArrayList<String> tokens = new ArrayList<String>();
	    Pattern tokSplitter = Pattern.compile("[aeiouy]+");

	    for (int i = 0; i < split.length; i++) {
	        String s = split[i];
	        Matcher m = tokSplitter.matcher(s);

	        while (m.find()) {
	            tokens.add(m.group());
	        }
	    }

	    syllables += tokens.size();
	    return syllables;
	}
	
	
	public static int countSyllabes(String word){
		int count =0;
		word = word.toLowerCase();
	    for (int i = 0; i < word.length(); i++) {
	        if (word.charAt(i) == '\"' || word.charAt(i) == '\'' || word.charAt(i) == '-' || word.charAt(i) == ',' || word.charAt(i) == ')' || word.charAt(i) == '(') {
	            word = word.substring(0,i)+word.substring(i+1, word.length());
	        }
	    }
	    boolean isPrevVowel = false;
	    for (int j = 0; j < word.length(); j++) {
	        if (word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u")) {
	            if (isVowel(word.charAt(j)) && !((word.charAt(j) == 'e') && (j == word.length()-1))) {
	                if (isPrevVowel == false) {
	                    count++;
	                    isPrevVowel = true;
	                }
	            } else {
	                isPrevVowel = false;
	            }
	        } else {
	            count++;
	            break;
	        }
	    }
		
		return count;
	}
	
	public static boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return true;
        } else {
            return false;
        }
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
//		System.out.println(document);
		Elements div = document.select("script");
		// script.text/javascript
//		System.out.println(div.toString());
		Double word_score = 0.0;
		
		for(Element e: div){
			if(e.data().contains("2008")){
				String[] script = e.data().split("2008:");
				for(int i=0; i< script.length;i++){
					String[] script2 = script[i].split("\";");
					if(i==1){
						if(script2[0].contains("null"))
							word_score = 100.0;
						else{
							word_score = Double.parseDouble("0."+script2[0]);
						}
					}
				}
				break;
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
