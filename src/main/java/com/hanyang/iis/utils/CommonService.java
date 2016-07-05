package com.hanyang.iis.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.hanyang.iis.utils.CommonDAO;


public class CommonService {
	
	@Autowired
	private CommonDAO commonDAO;
	
	public ArrayList select(String map, HashMap data) {
		return commonDAO.select(map, data);
	}

	public Object insert(String map, HashMap data) {
		return commonDAO.insert(map, data);
	}

	public Object update(String map, HashMap data) {
		return commonDAO.update(map, data);
	}

	public Object delete(String map, HashMap data) {
		return commonDAO.delete(map, data);
	}

	public ArrayList count(String map, HashMap data) {
		return commonDAO.count(map, data);
	}
}
