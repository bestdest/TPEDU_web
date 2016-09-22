package com.hanyang.iis.tpedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanyang.iis.tpedu.dto.ParaFeatureDTO;


public class ParaFeatureDAO {

	public List<ParaFeatureDTO> getParagraphOnly(){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String tblName = "tbl_weebit_paragraph";
		Connection conn = null;
		
		List<ParaFeatureDTO> ParaFeatureDTOList = new ArrayList<ParaFeatureDTO>();
		
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
			
			String SQLQuery = "select id,paragraph from "+tblName+";";

			rs = stmt.executeQuery(SQLQuery);
			
			while(rs.next()){
				int id = rs.getInt("id");
				String paragraph = rs.getString("paragraph");
				
				
				ParaFeatureDTO tmp = new ParaFeatureDTO(id, paragraph);
				
				ParaFeatureDTOList.add(tmp);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ParaFeatureDTOList;
	}
	
	public void UpdateAllFeature(List<ParaFeatureDTO> ParaFeatureList){
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String DBname = "tbl_weebit_paragraph";
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
					+ "max_dist_GR =?, "
					+ "numSen = ?,"
					+ "TTR = ?,"
					+ "CLI = ?,"
					+ "LIX = ?"
					+ " where id = ?";

			PreparedStatement ps = conn.prepareStatement(SQLQuery);
			
			for(int i = 0;i<ParaFeatureList.size();i++){
				ParaFeatureDTO cur = ParaFeatureList.get(i);
				
				ps.setFloat(1, cur.getType());
				ps.setInt(2, cur.getCnt_advp());
				ps.setInt(3, cur.getCnt_adjp());
				ps.setInt(4, cur.getCnt_modifier());
				ps.setInt(5, cur.getLength());
				ps.setInt(6, cur.getWord());
				ps.setFloat(7, cur.getNumChar());
				ps.setFloat(8, cur.getNumSyll());
				ps.setFloat(9, cur.getVoca_score());
				ps.setFloat(10, cur.getPattern_score());
				ps.setFloat(11, cur.getAWL_score());
				ps.setFloat(12, cur.getModifierVar());
				ps.setFloat(13, cur.getAdvpVar());
				ps.setFloat(14, cur.getAdjpVar());
				ps.setInt(15, cur.getNumCC());
				ps.setInt(16, cur.getNumSBAR());
				ps.setInt(17, cur.getNumCompound());
				ps.setInt(18, cur.getNumGR());
				ps.setFloat(19, cur.getAvg_dist_GR());
				ps.setFloat(20, cur.getMax_dist_GR());
				ps.setInt(21, cur.getNumSen());
				ps.setFloat(22, cur.getTTR());
				ps.setFloat(23, cur.getCLI());
				ps.setFloat(24, cur.getLIX());
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
}
