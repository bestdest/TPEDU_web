package com.hanyang.iis;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import com.hanyang.iis.tpedu.dto.ModifierDTO;
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
		
		if(is_sentence.equals("1")){	//문장
			searchSentence(search_txt.toLowerCase(), model);
			System.out.println("결과나온시간 : " + formattedDate);
			return "/result_sen";
		}else{							//문단
			searchParagraph(search_txt.toLowerCase(), model);
			System.out.println("결과나온시간 : " + formattedDate);
			
			return "/result";
		}
		
	}
	
	/*문장 검색시 */
	public void searchSentence(String search_txt, Model model) throws IOException, InterruptedException{
		/*
		 * Sentence Score 지정해주는 함수 호출
		 * */
		int is_paragraph = 0;		//0: 문장 , 1: 문단
		search_txt = search_txt.trim();
		Sentence Sentence = getFeatureScoreSentence(search_txt);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _MLP
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		MLPController mlpdao = new MLPController();
		int grade_mlp = mlpdao.getMLPResult(Sentence, is_paragraph);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _SVM
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		svm_predict_sentence svmdao = new svm_predict_sentence();
		int grade_svm = svmdao.getSVMResult(Sentence, is_paragraph);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _ Naive 
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		Sentence = naivedao.getResult(search_txt, Sentence, is_paragraph);
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
		model.addAttribute("grade", Math.round((grade_mlp+grade_svm+grade_naive+3)/3));
		model.addAttribute("grade_txt", grade_txt);
		model.addAttribute("mlp_grade", grade_mlp+1);
		model.addAttribute("svm_grade", grade_svm+1);
		model.addAttribute("naive_grade", grade_naive+1);
		model.addAttribute("naive_grade", grade_naive);
		model.addAttribute("mlp_grade_txt", grade_mlp);
		model.addAttribute("svm_grade_txt", grade_txt_svm);
		model.addAttribute("naive_grade_txt", grade_txt_naive);
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
		
		HashMap<String, String> mod_map = new HashMap<String, String>();
		mod_map = getTopModifier(Sentence.getOriginScore().getModifier());
		model.addAttribute("sentence_modifier", mod_map);
		
		model.addAttribute("sentence_feature1_str", "단어점수");
		model.addAttribute("sentence_feature2_str", "패턴복잡도");
		model.addAttribute("sentence_feature3_str", "수식어수");
		model.addAttribute("sentence_feature4_str", "구조점수");
		model.addAttribute("sentence_feature5_str", "평균음절수");	//단어당 평균음절수
		model.addAttribute("sentence_feature6_str", "평균단어수");	//단어당 평균단어수
		
		model.addAttribute("sentence", Sentence);
		model.addAttribute("sentence_origin", Sentence.getOriginScore());
	}
	
	/*문단 검색시 */
	public void searchParagraph(String search_txt, Model model) throws IOException, InterruptedException{
		/*
		 * Sentence Score 지정해주는 함수 호출
		 * */
		int is_paragraph = 1;		//0: 문장 , 1: 문단
		search_txt = search_txt.trim();
		Sentence Sentence = getFeatureScoreParagraph(search_txt);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _MLP
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		MLPController mlpdao = new MLPController();
		int grade_mlp = mlpdao.getMLPResult(Sentence, is_paragraph);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _SVM
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		svm_predict_sentence svmdao = new svm_predict_sentence();
		int grade_svm = svmdao.getSVMResult(Sentence, is_paragraph);
		
		/*
		 * Sentence Score 값을 등급별로 계산해주는 함수 호출 _ Naive 
		 * return 0 : 초등 1 : 중등 2 : 고등
		 * */
		Sentence = naivedao.getResult(search_txt, Sentence, is_paragraph);
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
		model.addAttribute("grade", Math.round((grade_mlp+grade_svm+grade_naive+3)/3));
		model.addAttribute("grade_txt", grade_txt);
		model.addAttribute("mlp_grade", grade_mlp+1);
		model.addAttribute("svm_grade", grade_svm+1);
		model.addAttribute("naive_grade", grade_naive+1);
		model.addAttribute("mlp_grade_txt", grade_mlp);
		model.addAttribute("svm_grade_txt", grade_txt_svm);
		model.addAttribute("naive_grade_txt", grade_txt_naive);
		model.addAttribute("sentence_feature1", Sentence.getGrade_voca_score()+1);
		model.addAttribute("sentence_feature2", Sentence.getGrade_pattern_score()+1);
		model.addAttribute("sentence_feature3", Sentence.getGrade_var_modifier()+1);
		model.addAttribute("sentence_feature4", Sentence.getGrade_struct_type()+1);
		model.addAttribute("sentence_feature5", Sentence.getGrade_ttr()+1);
		model.addAttribute("sentence_feature6", Sentence.getGrade_cli()+1);

		HashMap<String, String> mod_map = new HashMap<String, String>();
		mod_map = getTopModifier(Sentence.getOriginScorePara().getModifier());
		model.addAttribute("sentence_modifier", mod_map);
		
		model.addAttribute("sentence_feature1_str", "단어점수");
		model.addAttribute("sentence_feature2_str", "패턴복잡도");
		model.addAttribute("sentence_feature3_str", "수식어수");
		model.addAttribute("sentence_feature4_str", "구조점수");
		model.addAttribute("sentence_feature5_str", "유사단어비율");
		model.addAttribute("sentence_feature6_str", "가독성점수");
		
		model.addAttribute("sentence", Sentence);
		model.addAttribute("sentence_origin", Sentence.getOriginScorePara());
	}
	
	
	/*Feature Score 구하기 문장*/
	public Sentence getFeatureScoreSentence(String sentence){
		FeatureController feature = new FeatureController();
		Sentence re = feature.getFeatureSentence(sentence);
		return re;
	}

	/*Feature Score 구하기 문단*/
	public Sentence getFeatureScoreParagraph(String paragraph){
		FeatureController feature = new FeatureController();
		Sentence re = feature.getFeaturePara(paragraph);
		return re;
		
	}
	
	/*TOP 5의 수식어 추출하기*/
	public HashMap<String, String> getTopModifier(ModifierDTO mod){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, String> result_map = new HashMap<String, String>();
		//map.put("adjp", mod.getAdjp_count());
		//map.put("advp", mod.getAdvp_count());
		//map.put("cc", mod.getCc_count());
		map.put("기수", mod.getCd_count());					//cd
		map.put("한정사", mod.getDt_count());				//dt
		map.put("전치사 또는 종속접속사", mod.getIn_count());	//in
		map.put("형용사", mod.getJj_count());				//jj
		map.put("형용사(비교급)", mod.getJjr_count());		//jjr
		map.put("형용사(최상급)", mod.getJjs_count());		//jjs
		map.put("소유대명사", mod.getPrp$_count());			//prp$
		map.put("부사", mod.getRb_count());					//rb
		map.put("부사(비교급)", mod.getRbr_count());		//rbr
		map.put("부사(최상급)", mod.getRbs_count());		//rbs
		//map.put("sbar", mod.getSbar_count());
		map.put("전치사TO", mod.getTo_count());				//to
		map.put("동명사(현재분사)", mod.getVbg_count());	//vbg
		map.put("과거분사", mod.getVbn_count());			//vbn
		map.put("소유격대명사(wh-)", mod.getWp$_count());		//wp$
		map.put("관계대명사(wh-)", mod.getWp_count());			//wp
		map.put("한정사(wh-)", mod.getWp_count());			//wdt
		
		
		Iterator it = TPEController.sortByValue(map).iterator();
		int i = 0;
		while(it.hasNext() && i < 5){
            String temp = (String) it.next();
            System.out.println(temp + " = " + map.get(temp));
            if(map.get(temp) != 0){
            	result_map.put("modifier_sen"+i, temp);
            	result_map.put("modifier"+i, map.get(temp)+"");
            }else{
            	result_map.put("modifier"+i, "0");
            }
            i++;
        }
		
		return result_map;
		
	}
	
	public static List sortByValue(final Map map){
        List<String> list = new ArrayList();
        list.addAll(map.keySet());
         
        Collections.sort(list,new Comparator(){
             
            public int compare(Object o1,Object o2){
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                 
                return ((Comparable) v1).compareTo(v2);
            }
             
        });
        Collections.reverse(list); // 주석시 오름차순
        return list;
    }
	
}
