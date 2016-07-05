package com.hanyang.iis.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.support.SqlSessionDaoSupport;


public class CommonDAO extends SqlSessionDaoSupport   {
		
	
	public ArrayList select(String map, HashMap data) {		
		return (ArrayList) getSqlSession().selectList(map, data);
	}

	public Object insert(String map, HashMap data) {
		return (ArrayList)getSqlSession().selectList(map, data);
	}
	
	public Object update(String map, HashMap data) {
		return (ArrayList)getSqlSession().selectList(map, data);
	}
		
	public Object delete(String map, HashMap data) {
		return (ArrayList)getSqlSession().selectList(map, data);
	}

	public ArrayList count(String map, HashMap data) {
		return (ArrayList)getSqlSession().selectList(map, data);
	}
}
