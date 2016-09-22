package com.hanyang.iis.tpedu.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.dto.FeatureDTO;

public class FeatureDAO {
	
	public List<FeatureDTO> getSentenceOnly(){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String tblName = "tbl_weebit_sentences";
		Connection conn = null;
		
		List<FeatureDTO> FeatureDTOList = new ArrayList<FeatureDTO>();
		
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
		
		ResultSet rs = null;
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			
			String SQLQuery = "select id,sentence from "+tblName;

			rs = stmt.executeQuery(SQLQuery);
			
			while(rs.next()){
				int id = rs.getInt("id");
				String Sentence = rs.getString("sentence");
				
				
				FeatureDTO tmp = new FeatureDTO(id, Sentence);
				
				FeatureDTOList.add(tmp);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return FeatureDTOList;
	}
	
	public void UpdateAllFeature(List<FeatureDTO> FeatureList){
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
					+ "pattern = ?,"
					+ "type = ?,"
					+ "cnt_advp = ?,"
					+ "cnt_adjp = ?,"
					+ "cnt_modifier = ?,"
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
			
			for(int i = 0;i<FeatureList.size();i++){
				FeatureDTO cur = FeatureList.get(i);
				
				ps.setString(1, cur.getPattern());
				ps.setInt(2, cur.getType());
				ps.setInt(3, cur.getCnt_advp());
				ps.setInt(4, cur.getCnt_adjp());
				ps.setInt(5, cur.getCnt_modifier());
				ps.setFloat(6, cur.getModifierVar());
				ps.setFloat(7, cur.getAdvpVar());
				ps.setFloat(8, cur.getAdjpVar());
				ps.setFloat(9, cur.getNumCC());
				ps.setFloat(10, cur.getNumSBAR());
				ps.setFloat(11, cur.getNumCompound());
				ps.setFloat(12, cur.getNumGR());
				ps.setFloat(13, cur.getAvg_dist_GR());
				ps.setFloat(14, cur.getMax_dist_GR());
				ps.setFloat(15, cur.getId());
				
				
				
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
	
	
	public void UpdateFeature(List<FeatureDTO> FeatureList){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String DBname = "tbl_sentence_lists";
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
					+ "numCC = ?,"
					+ "numSBAR = ?,"
					+ "numCompound = ?,"
					+ "numGR = ?,"
					+ "avg_dist_GR =?,"
					+ "max_dist_GR =?"
					+ "where id = ?";

			PreparedStatement ps = conn.prepareStatement(SQLQuery);
			
			for(int i = 0;i<FeatureList.size();i++){
				FeatureDTO cur = FeatureList.get(i);
				ps.setFloat(1, cur.getNumCC());
				ps.setFloat(2, cur.getNumSBAR());
				ps.setFloat(3, cur.getNumCompound());
				ps.setFloat(4, cur.getNumGR());
				ps.setFloat(5, cur.getAvg_dist_GR());
				ps.setFloat(6, cur.getMax_dist_GR());
				ps.setInt(7, cur.getId());
				
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
