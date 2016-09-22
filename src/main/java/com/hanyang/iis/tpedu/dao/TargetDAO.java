package com.hanyang.iis.tpedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanyang.iis.tpedu.dto.TargetDTO;

public class TargetDAO {
	public List<TargetDTO> GetTargetList(String tblName){
		List<TargetDTO> TargetList = new ArrayList<TargetDTO>();
		
		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
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
		
		ResultSet rs = null;
		
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			
			String SQLQuery = "select id,sentence,pattern,grade from "+tblName;

			rs = stmt.executeQuery(SQLQuery);
			
			while(rs.next()){
				int id = rs.getInt("id");
				String Sentence = rs.getString("sentence").replace(".", "");
				String Pattern = rs.getString("pattern");
				int grade = rs.getInt("grade");
				float pattern_score = rs.getFloat("pattern_score");
				
				
				TargetDTO tmp = new TargetDTO(id,Sentence, Pattern, grade, pattern_score);
				
				TargetList.add(tmp);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return TargetList;
	}
	

	   public List<TargetDTO> GetTargetList_PS(String tblName){
	      List<TargetDTO> TargetList = new ArrayList<TargetDTO>();
	      
	      String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
	      String userID = "root";
	      String userPass = "iislabkey";
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
	      
	      ResultSet rs = null;
	      
	      Statement stmt;
	      
	      try {
	         stmt = conn.createStatement();
	         
	         String SQLQuery = "select id,sentence,pattern,grade,pattern_score from "+tblName;

	         rs = stmt.executeQuery(SQLQuery);
	         
	         while(rs.next()){
	            int id = rs.getInt("id");
	            String Sentence = rs.getString("sentence").replace(".", "");
	            String Pattern = rs.getString("pattern");
	            int grade = rs.getInt("grade");
	            float pattern_score = rs.getFloat("pattern_score");
	            
	            
	            TargetDTO tmp = new TargetDTO(id,Sentence, Pattern, grade,pattern_score);
	            
	            TargetList.add(tmp);
	         }
	         
	         rs.close();
	         stmt.close();
	         conn.close();
	         
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
	      
	      return TargetList;
	   }
	
}
