package com.hanyang.iis;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.hanyang.iis.tpedu.dto.Score;
import com.hanyang.iis.tpedu.dto.Sentence;
import com.opencsv.CSVReader;


public class TrainingDataSelect {

	public HashMap<Integer, Score> selectSentenceScore() {
		Connection conn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;

		HashMap<Integer, Score> list = new HashMap<Integer, Score>();
		String sql = "select AVG(`length`) as avg_length, VARIANCE(`length`) as var_length, AVG(`cnt_advp`) as avg_advp, VARIANCE(`cnt_advp`) as var_advp,"
				+ " AVG(`cnt_adjp`) as avg_adjp, VARIANCE(`cnt_adjp`) as var_adjp, AVG(`voca_score`) as avg_voca, VARIANCE(`voca_score`) as var_voca, "
				+ " AVG(`voca_score_sum`) as avg_voca_sum, VARIANCE(`voca_score_sum`) as var_voca_sum, AVG(`pattern_score`) as avg_pattern, VARIANCE(`pattern_score`) as var_pattern, `grade` "
				+ "from("
				+ "select case when grade=10 then 10 when grade=11 then 10 when grade=12 then 10 when grade=13 then 10 when grade=14 then 10  when grade=15 then 10  when grade=16 then 10 "
				+ "    when grade=20 then 20 when grade=21 then 20 when grade=22 then 20 when grade=23 then 20 "
				+ "    when grade=30 then 30 when grade=31 then 30 when grade=32 then 30 when grade=33 then 30 "
				+ "    end as grade, `lang`,  `type`,  `cnt_advp`,  `cnt_adjp`,  `length`,  `word`,  `voca_score`,  `voca_score_sum`,  `voca_score_50`,  `pattern_score`,  `etc` "
				+ "from tbl_sentence_lists ) x group by grade";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TPE_EDU", "root", "1234");
			psmt = conn.prepareStatement(sql.toString());
			rs = psmt.executeQuery();

			while (rs.next()) {
				Score score = new Score();
				
				score.setAvg_length(rs.getDouble("avg_length"));
				score.setVar_length(rs.getDouble("var_length"));
				score.setAvg_adjp(rs.getDouble("avg_adjp"));
				score.setVar_adjp(rs.getDouble("var_adjp"));
				score.setAvg_advp(rs.getDouble("avg_advp"));
				score.setVar_advp(rs.getDouble("var_advp"));
				score.setAvg_voca(rs.getDouble("avg_voca"));
				score.setVar_voca(rs.getDouble("var_voca"));
				score.setAvg_pattern(rs.getDouble("avg_pattern"));
				score.setVar_pattern(rs.getDouble("var_pattern"));
				
				list.put(rs.getInt("grade"), score);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (Exception e) {}
			if (psmt != null)
				try {psmt.close();} catch (Exception e) {}
			if (psmt2 != null)
				try {psmt2.close();} catch (Exception e) {}
			if (conn != null)
				try {conn.close();} catch (Exception e) {}
		}

		return list;
	}
	

	public ArrayList<Sentence> selectRandomSentence(String grade, int limit) {
		Connection conn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;

		ArrayList<Sentence> list = new ArrayList<Sentence>();
		String sql = "select * from ( "
				+ "select case when grade=10 then 0 when grade=11 then 0 when grade=12 then 0 when grade=13 then 0 when grade=14 then 0 when grade=15 then 0 when grade=16 then 0"
				+ " when grade=20 then 1 when grade=21 then 1 when grade=22 then 1 when grade=23 then 1 "
				+ " when grade=30 then 2 when grade=31 then 2 when grade=32 then 2 when grade=33 then 2  end as grade, "
				+ " `id`, `lang`,  `type`,  `cnt_advp`,  `cnt_adjp`,  `length`,  `word`,  `voca_score`,  `voca_score_sum`,  `voca_score_50`,  `pattern_score`,  `etc` "
				+ " from tbl_sentence_lists )x where grade in(?) order by rand() limit ?;";

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TPE_EDU", "root", "1234");
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, grade);
			psmt.setLong(2, limit);
			rs = psmt.executeQuery();

			while (rs.next()) {
				Sentence sentence = new Sentence();
				
				sentence.setCnt_adjp(rs.getDouble("cnt_adjp"));
				sentence.setCnt_advp(rs.getDouble("cnt_advp"));
				sentence.setLength(rs.getDouble("length"));
				sentence.setVoca_score(rs.getDouble("voca_score"));
				sentence.setVoca_score_sum(rs.getDouble("voca_score_sum"));
				sentence.setPattern_score(rs.getDouble("pattern_score"));
				sentence.setWord(rs.getDouble("word"));
				sentence.setStruct_type(rs.getDouble("type"));
				sentence.setGrade(rs.getInt("grade"));
				
				list.add(sentence);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (Exception e) {}
			if (psmt != null)
				try {psmt.close();} catch (Exception e) {}
			if (psmt2 != null)
				try {psmt2.close();} catch (Exception e) {}
			if (conn != null)
				try {conn.close();} catch (Exception e) {}
		}

		return list;
	}
	
	public ArrayList<Sentence> selectRandomSentence_essay(String grade, int limit) {
		Connection conn = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt2 = null;
		ResultSet rs = null;
		
		ArrayList<Sentence> list = new ArrayList<Sentence>();
		String sql = "select * from (  select case when grade=0 then 0 when grade=1 then 0"
				+ "		  when grade=2 then 1  when grade=3 then 2 end as grade, "
				+ "		  `id`, `lang`,  `type`,  `cnt_advp`,  `cnt_adjp`,  `length`,  `word`,  `voca_score`,  `pattern_score`,  `etc` "
				+ "    from tbl_essay_sen_set3 )x where grade in(?) order by rand() limit ?;";
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://166.104.140.75:60000/TPE_EDU", "root", "iislabkey");
			psmt = conn.prepareStatement(sql.toString());
			psmt.setString(1, grade);
			psmt.setLong(2, limit);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				Sentence sentence = new Sentence();
				
				sentence.setCnt_adjp(rs.getDouble("cnt_adjp"));
				sentence.setCnt_advp(rs.getDouble("cnt_advp"));
				sentence.setLength(rs.getDouble("length"));
				sentence.setVoca_score(rs.getDouble("voca_score"));
				//sentence.setVoca_score_sum(rs.getDouble("voca_score_sum"));
				sentence.setPattern_score(rs.getDouble("pattern_score"));
				sentence.setWord(rs.getDouble("word"));
				sentence.setStruct_type(rs.getDouble("type"));
				sentence.setGrade(rs.getInt("grade"));
				
				list.add(sentence);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {rs.close();} catch (Exception e) {}
			if (psmt != null)
				try {psmt.close();} catch (Exception e) {}
			if (psmt2 != null)
				try {psmt2.close();} catch (Exception e) {}
			if (conn != null)
				try {conn.close();} catch (Exception e) {}
		}
		
		return list;
	}
	

    public Double calculation(double avg, double stdev, double e2){
    	Double d = 0.0;
    	//d =  Math.pow(1 / Math.sqrt(2 * Math.PI * f), Math.E(- Math.pow((value - e),2) / 2 * f));
    	
    	//System.out.println(d1 + "   :   " + 1 / Math.sqrt(2 * Math.PI * f) );
    	Double d1 = Math.pow(Math.E, (- Math.pow((e2 - avg),2) / 2 * stdev));
    	d1 = Math.exp(-(Math.pow((e2 - avg), 2) / (2 * Math.pow(stdev, 2))));		
    	Double d2 = (1 / (Math.sqrt(2 * Math.PI * Math.pow(stdev, 2)))) * d1;
    	//System.out.println(d2);
    	//d =  1 / Math.sqrt(2 * Math.PI * stdev) * Math.exp((- Math.pow((e2 - avg),2) / 2 * stdev));
    	//d = (1 / (Math.sqrt(2 * Math.PI) * stdev)) * Math.exp(-(Math.pow(e2 - avg, 2) / 2 * Math.pow(stdev, 2)));
    	d = (1 / (Math.sqrt(2 * Math.PI) * stdev)) * d1;
    	if (Double.isNaN(d)){d = 1.0;}
    	return d;
    }
    /*public Double calculation2(double avg, double stdev, double e2){
    	Double d = 0.0;
    	//d =  Math.pow(1 / Math.sqrt(2 * Math.PI * f), Math.E(- Math.pow((value - e),2) / 2 * f));
    	
    	//System.out.println(d1 + "   :   " + 1 / Math.sqrt(2 * Math.PI * f) );
    	Double d1 = Math.pow(Math.E, (- Math.pow((e2 - avg),2) / 2 * stdev));
    	d1 = Math.exp(-(Math.pow((e2 - avg), 2) / (2 * Math.pow(stdev, 2))));		
    	
    	
    	//d =  1 / Math.sqrt(2 * Math.PI * stdev) * Math.exp((- Math.pow((e2 - avg),2) / 2 * stdev));
    	//d = (1 / (Math.sqrt(2 * Math.PI) * stdev)) * Math.exp(-(Math.pow(e2 - avg, 2) / 2 * Math.pow(stdev, 2)));
    	d = (1 / (Math.sqrt(2 * Math.PI) * Math.pow(stdev,2))) * d1;
    	
    	return d;
    }*/
    


    public ArrayList<Sentence> readCsv(String filename, int grade) {

    	ArrayList<Sentence> data = new ArrayList<Sentence>();
		try {
			// CSVReader reader = new CSVReader(new FileReader(filename), '\t');
			// UTF-8
			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));
			String[] s;

			while ((s = reader.readNext()) != null) {
				int i = 0;
				if(grade == -1 || s[0].equals(grade+"")){
					Sentence sentence = new Sentence();
					sentence.setGrade(Integer.parseInt(s[i]));
					sentence.setStruct_type(Double.parseDouble(s[++i]));
					sentence.setCnt_advp(Double.parseDouble(s[++i]));
					sentence.setCnt_adjp(Double.parseDouble(s[++i]));
					sentence.setCnt_modifier(Double.parseDouble(s[++i]));
					sentence.setLength(Double.parseDouble(s[++i]));
					sentence.setWord(Double.parseDouble(s[++i]));
					sentence.setAvg_char(Double.parseDouble(s[++i]));
					sentence.setAvg_syllables(Double.parseDouble(s[++i]));
					sentence.setVoca_score(Double.parseDouble(s[++i]));
					sentence.setPattern_score(Double.parseDouble(s[++i]));

					sentence.setRatio_awl(Double.parseDouble(s[++i]));
					sentence.setVar_modifier(Double.parseDouble(s[++i]));
					sentence.setVar_adv(Double.parseDouble(s[++i]));
					sentence.setVar_adj(Double.parseDouble(s[++i]));
					sentence.setCnt_cc(Double.parseDouble(s[++i]));
					sentence.setCnt_sbar(Double.parseDouble(s[++i]));
					sentence.setCnt_compound(Double.parseDouble(s[++i]));
					sentence.setCnt_gr(Double.parseDouble(s[++i]));
					sentence.setAvg_dis_gr(Double.parseDouble(s[++i]));
					sentence.setMax_dis_gr(Double.parseDouble(s[++i]));

					sentence.setNum_sen(Double.parseDouble(s[++i]));
					sentence.setTtr(Double.parseDouble(s[++i]));
					sentence.setCli(Double.parseDouble(s[++i]));
					sentence.setLix(Double.parseDouble(s[++i]));
					data.add(sentence);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

}
