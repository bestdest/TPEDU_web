package com.hanyang.iis;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanyang.iis.dao.NaiveBayesianDAO;
import com.hanyang.iis.dao.TPEDAO;

@RequestMapping(value="/tpe")
@Controller

public class TPEController {

	@Autowired
	private TPEDAO tpedao;
	
	@Autowired
	private NaiveBayesianDAO naivedao;
	
	@RequestMapping(value="/search_main.do")
	public String search(HttpServletRequest request, Locale locale, Model model){
		System.out.println("search");
		model.addAttribute("message", "Search");
		
		System.out.println(tpedao.getSentence());
		
		return "/search";
	}
	
	@RequestMapping(value="/search_sub.do")
	public String search_sub(HttpServletRequest request, Locale locale, Model model){
		System.out.println("search_sub");
		model.addAttribute("message", "Search_SUB");
		return "/search";
	}
	
	
	@RequestMapping(value="/result.do")
	public String result(HttpServletRequest request, Locale locale,
			@RequestParam (value="search_txt" ,required=false, defaultValue = "") String search_txt, 
			Model model){
		
		int grade = naivedao.getResult(search_txt);
		String grade_txt = "초등";
		switch(grade){
		case 0: grade_txt = "초등"; break;
		case 1: grade_txt = "중등"; break;
		case 2: grade_txt = "중등"; break;
		default: grade_txt = "초등"; break;
		}
		System.out.println("result : " + grade_txt);
		//naivedao.
		
		model.addAttribute("grade", grade_txt);
		return "/result";
	}
	
}
