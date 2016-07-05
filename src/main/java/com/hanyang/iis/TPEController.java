package com.hanyang.iis;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanyang.iis.tpedu.TPEMatch.main_Pattern_Matcher;
import com.hanyang.iis.tpedu.dao.NaiveBayesianDAO;
import com.hanyang.iis.tpedu.dao.TPEDAO;
import com.hanyang.iis.tpedu.dto.Score;
import com.hanyang.iis.tpedu.dto.Sentence;
import com.hanyang.iis.utils.WordScoreCrawler;

@RequestMapping(value="/tpe")
@Controller

public class TPEController {

	@Autowired
	private TPEDAO tpedao;
	
	@Autowired
	private NaiveBayesianDAO naivedao;
	
	public static int numOfGrade = 3; 				//등급 개수
	
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

		Sentence Sentence = getFeatureScore(search_txt);
		int grade = naivedao.getResult(search_txt, Sentence);
		String grade_txt = "초등";
		switch(grade){
		case 0: grade_txt = "초등"; break;
		case 1: grade_txt = "중등"; break;
		case 2: grade_txt = "고등"; break;
		default: grade_txt = "초등"; break;
		}
		System.out.println("result : " + grade_txt);
		//naivedao.
		
		model.addAttribute("grade", grade_txt);
		return "/result";
	}
	
	public Sentence getFeatureScore(String sentence){
		Sentence st = new Sentence();
		
		/*
		 * Pattern Score 추출하는 부분
		 */
		String tblName = "tbl_essay_sen_set3"; // TPE Matching 시킬 테이블 이름.
		main_Pattern_Matcher pm = new main_Pattern_Matcher();
		st = pm.Pattern_Matcher(sentence,numOfGrade,tblName);
		//System.out.println(patternScore);
		
		/*
		 * Word Score 추출하는 부분
		 */
		String inputSen = "the dirigibles would have to hang sandbags by ropes";
		WordScoreCrawler wsc = new WordScoreCrawler();
		Double vocaScore = wsc.vocaScore(sentence);
		//System.out.println(vocaScore);
		st.setVoca_score(vocaScore);
		
		return st; 
	}
}
