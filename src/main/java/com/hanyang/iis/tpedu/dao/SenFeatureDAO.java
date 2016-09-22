package com.hanyang.iis.tpedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanyang.iis.tpedu.dto.SenFeatureDTO;

public class SenFeatureDAO {
	
	public List<SenFeatureDTO> getSentenceOnly(){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String tblName = "tbl_weebit_sentences";
		Connection conn = null;
		
		List<SenFeatureDTO> SenFeatureDTOList = new ArrayList<SenFeatureDTO>();
		
		try {
			 conn = DriverManager.getConnection(jdbcUrl, userID, userPass);

		} catch (SQLException e) {
			System.out.println("Connection fail!");
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("USE TPE_EDU");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			
			String SQLQuery = "select id,sentence from "+tblName+" where pattern_score=0 && id<40000;";

			rs = stmt.executeQuery(SQLQuery);
			while(rs.next()){
				int id = rs.getInt("id");
				String Sentence = rs.getString("sentence");
				SenFeatureDTO tmp = new SenFeatureDTO(id, Sentence);
				SenFeatureDTOList.add(tmp);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SenFeatureDTOList;
	}
	
	public List<SenFeatureDTO> getSenAllFeature(){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String tblName = "tbl_weebit_sentences";
		Connection conn = null;
		
		List<SenFeatureDTO> SenFeatureDTOList = new ArrayList<SenFeatureDTO>();
		
		try {
			 conn = DriverManager.getConnection(jdbcUrl, userID, userPass);

		} catch (SQLException e) {
			System.out.println("Connection fail!");
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("USE TPE_EDU");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			
			String SQLQuery = "select id,sentence,pattern,para_id,type,cnt_advp,cnt_adjp,cnt_modifier,"
					+ "length,word,numChar,numSyll,voca_score,pattern_score,AWL_score,"
					+ "modifierVar,advpVar,adjpVar,numCC,numSBAR,numCompound,numGR,"
					+ "avg_dist_GR,max_dist_GR from "+tblName+";";

			rs = stmt.executeQuery(SQLQuery);
			while(rs.next()){
				int id = rs.getInt("id");
				String sen = rs.getString("sentence");
				String pattern = rs.getString("pattern");
				int para_id = rs.getInt("para_id");
				int type = rs.getInt("type");
				int cnt_advp = rs.getInt("cnt_advp");
				int cnt_adjp = rs.getInt("cnt_adjp");
				int cnt_modifier = rs.getInt("cnt_modifier");
				int length = rs.getInt("length");
				int word = rs.getInt("word");
				float numChar = rs.getFloat("numChar");
				float numSyll = rs.getFloat("numSyll");
				float voca_score = rs.getFloat("voca_score");
				float pattern_score = rs.getFloat("pattern_score");
				float AWL_score = rs.getFloat("AWL_score");
				float modifierVar = rs.getFloat("modifierVar");
				float advpVar = rs.getFloat("advpVar");
				float adjpVar = rs.getFloat("adjpVar");
				int numCC = rs.getInt("numCC");
				int numSBAR = rs.getInt("numSBAR");
				int numCompound = rs.getInt("numCompound");
				int numGR = rs.getInt("numGR");
				float avg_dist_GR = rs.getFloat("avg_dist_GR");
				float max_dist_GR = rs.getFloat("max_dist_GR");
				
				SenFeatureDTO tmp = new SenFeatureDTO(id, sen,pattern,para_id,type,cnt_advp,cnt_adjp,cnt_modifier,
						length,word,numChar,numSyll,voca_score,pattern_score,AWL_score,modifierVar,advpVar,
						adjpVar,numCC,numSBAR,numCompound,numGR,avg_dist_GR,max_dist_GR);
				SenFeatureDTOList.add(tmp);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SenFeatureDTOList;
	}
	
	
	
	
	
	public void UpdateAllFeature(List<SenFeatureDTO> SenFeatureList){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String DBname = "tbl_weebit_sentences";
		Connection conn = null;
		
		
		
		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPass);

		} catch (SQLException e) {
			System.out.println("Connection fail!");
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("USE TPE_EDU");

			// System.out.println("connect success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			String SQLQuery = "update "+DBname+" set "
					+ "sentence =?,"
					+ "pattern = ?,"
					+ "para_id = ?,"
					+ "txt_id = ?,"
					+ "type = ?,"
					+ "cnt_advp = ?,"
					+ "cnt_adjp = ?,"
					+ "cnt_modifier = ?,"
					+ "length = ?,"
					+ "word = ?,"
					+ "numChar = ?,"
					+ "numSyll = ?,"
					+ "voca_score = ?,"
					+ "pattern_score = ?,"
					+ "AWL_score = ?,"
					+ "modifierVar = ?,"
					+ "advpVar = ?,"
					+ "adjpVar = ?,"
					+ "numCC = ?,"
					+ "numSBAR = ?,"
					+ "numCompound = ?,"
					+ "numGR = ?,"
					+ "avg_dist_GR =?,"
					+ "max_dist_GR =? "
					+ "where id = ?";

			PreparedStatement ps = conn.prepareStatement(SQLQuery);
			
			for(int i = 0;i<SenFeatureList.size();i++){
				SenFeatureDTO cur = SenFeatureList.get(i);
				ps.setString(1, cur.getSentence());
				ps.setString(2, cur.getPattern());
				ps.setInt(3, cur.getPara_id());
				ps.setInt(4, cur.getTxt_id());
				ps.setInt(5, cur.getType());
				ps.setInt(6, cur.getCnt_advp());
				ps.setInt(7, cur.getCnt_adjp());
				ps.setInt(8, cur.getCnt_modifier());
				ps.setInt(9, cur.getLength());
				ps.setInt(10, cur.getWord());
				ps.setFloat(11, cur.getNumChar());
				ps.setFloat(12, cur.getNumSyll());
				ps.setFloat(13, cur.getVoca_score());
				ps.setFloat(14, cur.getPattern_score());
				ps.setFloat(15, cur.getAWL_score());
				ps.setFloat(16, cur.getModifierVar());
				ps.setFloat(17, cur.getAdvpVar());
				ps.setFloat(18, cur.getAdjpVar());
				ps.setInt(19, cur.getNumCC());
				ps.setInt(20, cur.getNumSBAR());
				ps.setFloat(21, cur.getNumCompound());
				ps.setFloat(22, cur.getNumGR());
				ps.setFloat(23, cur.getAvg_dist_GR());
				ps.setFloat(24, cur.getMax_dist_GR());
				ps.setFloat(25, cur.getId());
				
				ps.addBatch();
			}
			
			
			ps.executeBatch();
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateFeatureExceptPattern(List<SenFeatureDTO> SenFeatureList){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String DBname = "tbl_weebit_sentences";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPass);

		} catch (SQLException e) {
			System.out.println("Connection fail!");
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("USE TPE_EDU");

			// System.out.println("connect success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			String SQLQuery = "update "+DBname+" set "
					+ "sentence =?,"
					+ "pattern = ?,"
					+ "type = ?,"
					+ "cnt_advp = ?,"
					+ "cnt_adjp = ?,"
					+ "cnt_modifier = ?,"
					+ "length = ?,"
					+ "word = ?,"
					+ "numChar = ?,"
					+ "numSyll = ?,"
					+ "voca_score = ?,"
					+ "AWL_score = ?,"
					+ "modifierVar = ?,"
					+ "advpVar = ?,"
					+ "adjpVar = ?,"
					+ "numCC = ?,"
					+ "numSBAR = ?,"
					+ "numCompound = ?,"
					+ "numGR = ?,"
					+ "avg_dist_GR =?,"
					+ "max_dist_GR =? "
					+ "where id = ?";

			PreparedStatement ps = conn.prepareStatement(SQLQuery);
			
			for(int i = 0;i<SenFeatureList.size();i++){
				SenFeatureDTO cur = SenFeatureList.get(i);
				ps.setString(1, cur.getSentence());
				ps.setString(2, cur.getPattern());
				ps.setInt(3, cur.getType());
				ps.setInt(4, cur.getCnt_advp());
				ps.setInt(5, cur.getCnt_adjp());
				ps.setInt(6, cur.getCnt_modifier());
				ps.setInt(7, cur.getLength());
				ps.setInt(8, cur.getWord());
				ps.setFloat(9, cur.getNumChar());
				ps.setFloat(10, cur.getNumSyll());
				ps.setFloat(11, cur.getVoca_score());
				ps.setFloat(12, cur.getAWL_score());
				ps.setFloat(13, cur.getModifierVar());
				ps.setFloat(14, cur.getAdvpVar());
				ps.setFloat(15, cur.getAdjpVar());
				ps.setFloat(16, cur.getNumCC());
				ps.setFloat(17, cur.getNumSBAR());
				ps.setFloat(18, cur.getNumCompound());
				ps.setFloat(19, cur.getNumGR());
				ps.setFloat(20, cur.getAvg_dist_GR());
				ps.setFloat(21, cur.getMax_dist_GR());
				ps.setFloat(22, cur.getId());
				
				ps.addBatch();
			}
			
			
			ps.executeBatch();
			
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdatePatternScoreOnly(List<SenFeatureDTO> SenFeatureList){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String DBname = "tbl_weebit_sentences";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbcUrl, userID, userPass);

		} catch (SQLException e) {
			System.out.println("Connection fail!");
			e.printStackTrace();
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("USE TPE_EDU");

			// System.out.println("connect success!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Statement stmt = null;
		try {
			stmt = conn.createStatement();

			String SQLQuery = "update "+DBname+" set "
					+ "pattern_score = ?"
					+ "where id = ?";

			PreparedStatement ps = conn.prepareStatement(SQLQuery);
			for(int i = 0;i<SenFeatureList.size();i++){
				SenFeatureDTO cur = SenFeatureList.get(i);
				ps.setFloat(1, cur.getPattern_score());
				ps.setFloat(2, cur.getId());
				ps.addBatch();
			}
			
			
			ps.executeBatch();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
