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
			@RequestParam (value="is_sentence" ,required=false, defaultValue = "1") String is_sentence, 		//1: 문장 2: 문단
			Model model) throws IOException, InterruptedException{

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		System.out.println("검색시작시간 : " + formattedDate);
		
		date = new Date();
		dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		formattedDate = dateFormat.format(date);
		
		if(is_sentence.equals("1")){
			searchSentence(search_txt, model);
		}else{
			searchParagraph(search_txt, model);
		}
		
		System.out.println("결과나온시간 : " + formattedDate);
		
		return "/result";
	}
	
	/*문장 검색시 */
	public void searchSentence(String search_txt, Model model) throws IOException, InterruptedException{
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
		
		model.addAttribute("input_txt", search_txt);
		model.addAttribute("grade", grade_txt);
		model.addAttribute("mlp_grade", grade_txt_mlp);
		model.addAttribute("svm_grade", grade_txt_svm);
		model.addAttribute("naive_grade", grade_txt_naive);
		/*model.addAttribute("sentence_word", Sentence.getGrade_word()+1);
		model.addAttribute("sentence_advp", Sentence.getGrade_cnt_advp()+1);
		model.addAttribute("sentence_adjp", Sentence.getGrade_cnt_adjp()+1);
		model.addAttribute("sentence_pattern", Sentence.getGrade_pattern_score()+1);
		model.addAttribute("sentence_length", Sentence.getGrade_length()+1);
		model.addAttribute("sentence_struct_type", Sentence.getGrade_struct_type()+1);
		model.addAttribute("sentence_voca", Sentence.getGrade_voca_score()+1);*/
		model.addAttribute("sentence_feature1", Sentence.getGrade_voca_score()+1);
		model.addAttribute("sentence_feature2", Sentence.getGrade_pattern_score()+1);
		model.addAttribute("sentence_feature3", Sentence.getGrade_cnt_modifier()+1);
		model.addAttribute("sentence_feature4", Sentence.getGrade_struct_type()+1);
		model.addAttribute("sentence_feature5", Sentence.getGrade_avg_syllables()+1);
		model.addAttribute("sentence_feature6", Sentence.getGrade_avg_char()+1);
		
		model.addAttribute("sentence_feature1_str", "단어점수");
		model.addAttribute("sentence_feature2_str", "패턴복잡도");
		model.addAttribute("sentence_feature3_str", "수식어수");
		model.addAttribute("sentence_feature4_str", "구조점수");
		model.addAttribute("sentence_feature5_str", "평균음절수");	//단어당 평균음절수
		model.addAttribute("sentence_feature6_str", "평균단어수");	//단어당 평균단어수
		
		model.addAttribute("sentence", Sentence);
	}
	
	/*문단 검색시 */
	public void searchParagraph(String search_txt, Model model) throws IOException, InterruptedException{
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
		
		model.addAttribute("input_txt", search_txt);
		model.addAttribute("grade", grade_txt);
		model.addAttribute("mlp_grade", grade_txt_mlp);
		model.addAttribute("svm_grade", grade_txt_svm);
		model.addAttribute("naive_grade", grade_txt_naive);
		model.addAttribute("sentence_feature1", Sentence.getGrade_voca_score()+1);
		model.addAttribute("sentence_feature2", Sentence.getGrade_pattern_score()+1);
		model.addAttribute("sentence_feature3", Sentence.getGrade_var_modifier()+1);
		model.addAttribute("sentence_feature4", Sentence.getGrade_struct_type()+1);
		model.addAttribute("sentence_feature5", Sentence.getGrade_ttr()+1);
		model.addAttribute("sentence_feature6", Sentence.getGrade_cli()+1);
		
		model.addAttribute("sentence_feature1_str", "단어점수");
		model.addAttribute("sentence_feature2_str", "패턴복잡도");
		model.addAttribute("sentence_feature3_str", "수식어수");
		model.addAttribute("sentence_feature4_str", "구조점수");
		model.addAttribute("sentence_feature5_str", "유사단어비율");
		model.addAttribute("sentence_feature6_str", "가독성점수");
		
		model.addAttribute("sentence", Sentence);
	}
	
	
	/*Feature Score 구하기*/
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
		
		double avg_char = 0.0;
    	double syllable = 0.0;
    	double cnt_modifier = 0.0;
    	double awl = 0.0;
    	double variation_modifier = 0.0; 
    	double variation_adv = 0.0;
    	double variation_adj = 0.0;
    	double cc = 0.0;
    	double sbar = 0.0;
    	double compound = 0.0;
    	double cnt_gr = 0.0;
    	double avg_gr = 0.0;
    	double max_gr = 0.0;

    	double num_sen = 0.0;
    	double ttr = 0.0;
    	double cli = 0.0;
    	double lix = 0.0;
		
		
		for(Sentence data : datas){
			leng_var =+ data.getLength();
			adjp_var =+ data.getCnt_adjp();
			advp_var =+ data.getCnt_advp();
			pattern_var =+ data.getPattern_score();
			struct_var =+ data.getStruct_type();
			word_var =+ data.getWord();
			voca_var =+ data.getVoca_score();
			
			avg_char = data.getAvg_char();
	    	syllable = data.getAvg_syllables();
	    	cnt_modifier = data.getCnt_modifier();
	    	awl = data.getRatio_awl();
	    	variation_modifier = data.getVar_modifier(); 
	    	variation_adv = data.getVar_adv();
	    	variation_adj = data.getVar_adj();
	    	cc = data.getCnt_cc();
	    	sbar = data.getCnt_sbar();
	    	compound = data.getCnt_compound();
	    	cnt_gr = data.getCnt_gr();
	    	avg_gr = data.getAvg_dis_gr();
	    	max_gr = data.getMax_dis_gr();

	    	num_sen = data.getNum_sen();
	    	ttr = data.getTtr();
	    	cli = data.getCli();
	    	lix = data.getLix();
		}
		
		/*문단의 문장 갯수를 각 변수의 합에 나눠서 평균 구하기*/
		sentence_count = datas.size();
		re.setLength(leng_var/sentence_count);
		re.setCnt_advp(advp_var/sentence_count);
		re.setCnt_adjp(adjp_var/sentence_count);
		re.setPattern_score(pattern_var/sentence_count);
		re.setStruct_type(struct_var/sentence_count);
		re.setWord(word_var/sentence_count);
		re.setVoca_score(voca_var/sentence_count);

		re.setAvg_char(avg_char/sentence_count);
		re.setAvg_syllables(syllable/sentence_count);
		re.setCnt_modifier(cnt_modifier/sentence_count);
		re.setRatio_awl(awl/sentence_count);
		re.setVar_modifier(variation_modifier/sentence_count);
		re.setVar_adv(variation_adv/sentence_count);
		re.setVar_adj(variation_adj/sentence_count);
		
		re.setCnt_cc(cc/sentence_count);
		re.setCnt_sbar(sbar/sentence_count);
		re.setCnt_compound(compound/sentence_count);
		re.setCnt_gr(cnt_gr/sentence_count);
		re.setAvg_dis_gr(avg_gr/sentence_count);
		re.setMax_dis_gr(max_gr/sentence_count);

		re.setNum_sen(num_sen/sentence_count);
		re.setTtr(ttr/sentence_count);
		re.setCli(cli/sentence_count);
		re.setLix(lix/sentence_count);
		
		return re; 
	}
}
