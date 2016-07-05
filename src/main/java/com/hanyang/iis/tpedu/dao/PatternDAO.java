package com.hanyang.iis.tpedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanyang.iis.tpedu.dto.PatternDTO;

public class PatternDAO {

	public List<PatternDTO> GetPatternList() {
		List<PatternDTO> PatternList = new ArrayList<PatternDTO>();

		String jdbcUrl = "jdbc:mysql://166.104.140.75:60000";
		String userID = "root";
		String userPass = "iislabkey";
		String tblName = "tbl_pattern_count_depth_prun";
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

			String SQLQuery = "select id,sentence,pattern,grade,lang,count,type,cnt_advp,cnt_adjp from " + tblName;

			rs = stmt.executeQuery(SQLQuery);

			while (rs.next()) {
				int id = rs.getInt("id");
				String Sentence = rs.getString("sentence");
				String Pattern = rs.getString("pattern");
				int grade = rs.getInt("grade");
				int lang = rs.getInt("lang");
				int count = rs.getInt("count");
				int type = rs.getInt("type");
				int cnt_advp = rs.getInt("cnt_advp");
				int cnt_adjp = rs.getInt("cnt_adjp");

				PatternDTO tmp = new PatternDTO(id, Sentence, Pattern, grade, lang, count, type, cnt_advp, cnt_adjp);

				PatternList.add(tmp);
			}

			rs.close();
			stmt.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return PatternList;
	}

}
