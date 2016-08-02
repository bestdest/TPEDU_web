package com.hanyang.iis;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanyang.iis.tpedu.TPEMatch.main_Pattern_Matcher;
import com.hanyang.iis.tpedu.dao.NaiveBayesianDAO;
import com.hanyang.iis.tpedu.dao.TPEDAO;
import com.hanyang.iis.tpedu.dto.Sentence;
import com.hanyang.iis.tpedu.mlp.MLPController;
import com.hanyang.iis.utils.WordScoreCrawler;
import com.hanyang.iis.tpedu.svm.svm_predict_sentence;

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
		model.addAttribute("message", "Search");
		//System.out.println(tpedao.getSentence());
		return "/search";
	}
	
	@RequestMapping(value="/search_sub.do")
	public String search_sub(HttpServletRequest request, Locale locale, Model model){
		model.addAttribute("message", "Search_SUB");
		return "/search";
	}
	
	
	@RequestMapping(value="/result.do")
	public String result(HttpServletRequest request, Locale locale,
			@RequestParam (value="search_txt" ,required=false, defaultValue = "") String search_txt, 
			Model model) throws IOException, InterruptedException{

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		System.out.println("검색시작시간 : " + formattedDate);
		
		/*
		 * Sentence Score 지정해주는 함수 호출
		 * */
		
		search_txt = search_txt.trim();
		Sentence Sentence = getFeatureScore(search_txt);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _MLP
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		MLPController mlpdao = new MLPController();
		int grade_mlp = mlpdao.getMLPResult(Sentence);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _SVM
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		svm_predict_sentence svmdao = new svm_predict_sentence();
		int grade_svm = svmdao.getSVMResult(Sentence);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _ Naive 
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		Sentence = naivedao.getResult(search_txt, Sentence);
		int grade_naive = Sentence.getGrade();
		
		/*MLP*/
		String grade_txt_mlp = "초등";
		
		switch(grade_mlp){
		case 0: grade_txt_mlp = "초등"; break;
		case 1: grade_txt_mlp = "중등"; break;
		case 2: grade_txt_mlp = "고등"; break;
		default: grade_txt_mlp = "초등"; break;
		}
		
		/*SVM*/
		String grade_txt_svm = "초등";
		
		switch(grade_svm){
		case 0: grade_txt_svm = "초등"; break;
		case 1: grade_txt_svm = "중등"; break;
		case 2: grade_txt_svm = "고등"; break;
		default: grade_txt_svm = "초등"; break;
		}
		
		/*Naive*/
		String grade_txt_naive = "초등";
		
		switch(grade_naive){
		case 0: grade_txt_naive = "초등"; break;
		case 1: grade_txt_naive = "중등"; break;
		case 2: grade_txt_naive = "고등"; break;
		default: grade_txt_naive = "초등"; break;
		}
		
		/*Total*/
		String grade_txt = "초등";

		switch(grade_mlp+grade_svm+grade_naive){
		case 0 : 
		case 1 : 
			grade_txt = "초등"; break;
		case 2: 
		case 3: 
		case 4: 
			grade_txt = "중등"; break;
		case 5: 
		case 6: 
			grade_txt = "고등"; break;
		default: grade_txt = "초등"; break;
		}
		
		date = new Date();
		dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		formattedDate = dateFormat.format(date);
		
		System.out.println("결과나온시간 : " + formattedDate);
		System.out.println("result : " + grade_txt_svm);
		
		model.addAttribute("input_txt", search_txt);
		model.addAttribute("grade", grade_txt);
		model.addAttribute("mlp_grade", grade_txt_mlp);
		model.addAttribute("svm_grade", grade_txt_svm);
		model.addAttribute("naive_grade", grade_txt_naive);
		model.addAttribute("sentence_word", Sentence.getGrade_word()+1);
		model.addAttribute("sentence_advp", Sentence.getGrade_cnt_advp()+1);
		model.addAttribute("sentence_adjp", Sentence.getGrade_cnt_adjp()+1);
		model.addAttribute("sentence_pattern", Sentence.getGrade_pattern_score()+1);
		model.addAttribute("sentence_length", Sentence.getGrade_length()+1);
		model.addAttribute("sentence_struct_type", Sentence.getGrade_struct_type()+1);
		model.addAttribute("sentence_voca", Sentence.getGrade_voca_score()+1);
		model.addAttribute("sentence", Sentence);
		/*model.addAttribute("sentence_word", Sentence.getWord());
		model.addAttribute("sentence_advp", Sentence.getCnt_advp());
		model.addAttribute("sentence_adjp", Sentence.getCnt_adjp());
		model.addAttribute("sentence_pattern", Sentence.getPattern_score());
		model.addAttribute("sentence_length", Sentence.getLength());
		model.addAttribute("sentence_struct_type", Sentence.getStruct_type());
		model.addAttribute("sentence_voca", Sentence.getVoca_score());*/
		return "/result";
	}
	
	public Sentence getFeatureScore(String sentence){
		int sentence_count = 3;
		Sentence re = new Sentence();
		ArrayList<Sentence> datas = new ArrayList<Sentence>(); 
		
		String tblName = "tbl_sentence_lists"; // TPE Matching 시킬 테이블 이름.
		WordScoreCrawler wsc = new WordScoreCrawler();
		main_Pattern_Matcher pm = new main_Pattern_Matcher();
		StringTokenizer stkn = null;
		
		/* 여러 문장인 경우 */
		//String[] split_sentence = sentence.split(".");
		stkn = new StringTokenizer(sentence.toString(), ".");
		//token 구분된 문장 loop
		while (stkn.hasMoreTokens()) {
			String split_sentence = stkn.nextToken();
			Sentence st = new Sentence();
			/*
			 * Pattern Score 추출하는 부분
			 */
			st = pm.Pattern_Matcher(split_sentence, numOfGrade, tblName);
			/*
			 * Word Score 추출하는 부분
			 */
			Double vocaScore = wsc.vocaScore(split_sentence);
			st.setVoca_score(vocaScore/40.80);
			
			datas.add(st);
		}
		
		double leng_var = 0.0;
		double adjp_var = 0.0;
		double advp_var = 0.0;
		double pattern_var = 0.0;
		double struct_var = 0.0;
		double word_var = 0.0;
		double voca_var = 0.0;
		for(Sentence data : datas){
			leng_var =+ data.getLength();
			adjp_var =+ data.getCnt_adjp();
			advp_var =+ data.getCnt_advp();
			pattern_var =+ data.getPattern_score();
			struct_var =+ data.getStruct_type();
			word_var =+ data.getWord();
			voca_var =+ data.getVoca_score();
		}
		sentence_count = datas.size();
		re.setLength(leng_var/sentence_count);
		re.setCnt_advp(advp_var/sentence_count);
		re.setCnt_adjp(adjp_var/sentence_count);
		re.setPattern_score(pattern_var/sentence_count);
		re.setStruct_type(struct_var/sentence_count);
		re.setWord(word_var/sentence_count);
		re.setVoca_score(voca_var/sentence_count);
		
		return re; 
	}
}
