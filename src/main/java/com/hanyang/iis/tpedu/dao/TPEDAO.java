package com.hanyang.iis.tpedu.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hanyang.iis.tpedu.dto.Score;
import com.hanyang.iis.utils.*;

public class TPEDAO {
	private Logger logger = LoggerFactory.getLogger(TPEDAO.class);
	
	@Autowired
	private CommonService CommonService;
	 
	public ArrayList<Integer> getSentence(){
		HashMap hm = new HashMap();

		ArrayList ar = CommonService.select("tpe.select_test", hm);
		ArrayList<Integer> datas = new ArrayList<Integer>();
		
		if(((HashMap)ar.get(0)).get("grade") != null){
			datas.add(Integer.parseInt(((HashMap)ar.get(0)).get("grade").toString()));
		}else{
			datas.add(0);
	    }
		
		return datas;
	}
	
}
