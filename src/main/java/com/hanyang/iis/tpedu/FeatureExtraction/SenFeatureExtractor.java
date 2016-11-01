package com.hanyang.iis.tpedu.FeatureExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.PretoPost.TargetNode;
import com.hanyang.iis.tpedu.SentenceTBL.TypeClassifier;
import com.hanyang.iis.tpedu.dao.SenFeatureDAO;
import com.hanyang.iis.tpedu.dto.ModifierDTO;
import com.hanyang.iis.tpedu.dto.SenFeatureDTO;

import edu.stanford.nlp.trees.TypedDependency;

public class SenFeatureExtractor {
	public void SenFeatureExtractorExceptPattern() {
		/*
		 * Pattern Score 제외하고 문장에서 모든 Feature 추출해서 DB에 넣는 작업
		 */
		try {
			SentenParser Parser = new SentenParser();
			SenFeatureDAO SDAO = new SenFeatureDAO();
			List<SenFeatureDTO> SDTO = SDAO.getSentenceOnly();
			List<SenFeatureDTO> FeatureUpdateList = new ArrayList<SenFeatureDTO>();

			for (int i = 0; i < SDTO.size(); i++) {
				SenFeatureDTO Feature = SDTO.get(i);
				int id = Feature.getId();
				String sen = Feature.getSentence();
				String ParseTree = Parser.GetParseTree(sen);
				StringTokenizer stkn = new StringTokenizer(sen);

				WordScoreCrawler wsc = new WordScoreCrawler();
				HashMap<String, String> LEX = new HashMap<String, String>();
				LEX.putAll(wsc.vocaScore(sen)); // voca_score, numChar, numSyll, AWL_score
				float voca_score = Float.parseFloat(LEX.get("voca_score"));
				float numSyll = Float.parseFloat(LEX.get("numSyll"));
				float numChar = Float.parseFloat(LEX.get("numChar"));
				float AWL_score = Float.parseFloat(LEX.get("AWL_score"));

				TypeClassifier TC = new TypeClassifier();
				int type = TC.GetType(ParseTree);
				int length = sen.length();
				int word = stkn.countTokens();
				
				HashMap<String, Integer> Counter = new HashMap<String, Integer>();
				Counter = Counter(ParseTree);
				int cnt_advp = Counter.get("cnt_advp");
				int cnt_adjp = Counter.get("cnt_adjp");
				int cnt_modifier = Counter.get("cnt_modifier");
				int numCC = Counter.get("numCC");
				int numSBAR = Counter.get("numSBAR");

				List<TypedDependency> GR = Parser.getTdl();
				HashMap<String, Float> GR_Finder = new HashMap<String, Float>();
				GR_Finder = GR_Finder(GR);
				float numCompound = GR_Finder.get("numCompound");
				float numGR = GR_Finder.get("numGR");
				float avg_dist_GR = GR_Finder.get("avg_dist_GR");
				float max_dist_GR = GR_Finder.get("max_dist_GR");
				float advpVar = (float) cnt_advp / (float) word;
				float adjpVar = (float) cnt_adjp / (float) word;
				float modifierVar = (float) cnt_modifier / (float) word;

				SenFeatureDTO tmp = new SenFeatureDTO(id, sen, ParseTree, type, cnt_advp, cnt_adjp, cnt_modifier,
						length, word, numChar, numSyll, voca_score, AWL_score, modifierVar, advpVar, adjpVar, numCC,
						numSBAR, numCompound, numGR, avg_dist_GR, max_dist_GR);
				System.out.println(id);
				FeatureUpdateList.add(tmp);
			}
			SDAO.UpdateFeatureExceptPattern(FeatureUpdateList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 그냥 일괄적? 
	 */
	public void PatternScoreExtractor(int numOfGrade) {
		try {
			SentenParser Parser = new SentenParser();
			SenFeatureDAO SDAO = new SenFeatureDAO();
			List<SenFeatureDTO> SDTO = SDAO.getSentenceOnly();
			List<SenFeatureDTO> FeatureUpdateList = new ArrayList<SenFeatureDTO>();
			System.out.println(SDTO.size());
			for (int i = 0; i < SDTO.size(); i++) {
				SenFeatureDTO Feature = SDTO.get(i);
				int id = Feature.getId();
				String sen = Feature.getSentence();

				/*
				 * Pattern_Score 구하는 함수 만들자. 
				 */
				float pattern_score =0;
				PatternScoreExtractor PSE = new PatternScoreExtractor();
				pattern_score = PSE.PatternScoreExtractor(numOfGrade, sen, "tbl_weebit_sentences");
				
				SenFeatureDTO tmp = new SenFeatureDTO(id, pattern_score);
				System.out.println(id+" : " +pattern_score);
				FeatureUpdateList.add(tmp);
				SDAO.UpdatePatternScoreOnly(FeatureUpdateList);
				FeatureUpdateList.clear();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 문장 입력시
	 */
	public SenFeatureDTO SenFeatureExtractor(int numOfGrade, String sen){

		
		try{
			SentenParser Parser = new SentenParser();
			String ParseTree = Parser.GetParseTree(sen);
			StringTokenizer stkn = new StringTokenizer(sen);

			WordScoreCrawler wsc = new WordScoreCrawler();
			HashMap<String, String> LEX = new HashMap<String, String>();
			LEX.putAll(wsc.vocaScore(sen)); // voca_score, numChar, numSyll, AWL_score
			float voca_score = Float.parseFloat(LEX.get("voca_score"));
			float numSyll = Float.parseFloat(LEX.get("numSyll"));
			float numChar = Float.parseFloat(LEX.get("numChar"));
			float AWL_score = Float.parseFloat(LEX.get("AWL_score"));

			TypeClassifier TC = new TypeClassifier();
			int type = TC.GetType(ParseTree);
			int length = sen.length();
			int word = stkn.countTokens();
			
			PatternScoreExtractor PSE = new PatternScoreExtractor();
			float pattern_score = PSE.PatternScoreExtractor_forService(numOfGrade, ParseTree, "tbl_weebit_sentences");
			
			HashMap<String, Integer> Counter = new HashMap<String, Integer>();
			Counter = Counter(ParseTree);
			
			ModifierDTO Counters = Counters(ParseTree);
			
			int cnt_advp = Counter.get("cnt_advp");
			int cnt_adjp = Counter.get("cnt_adjp");
			int cnt_modifier = Counter.get("cnt_modifier");
			int numCC = Counter.get("numCC");
			int numSBAR = Counter.get("numSBAR");

			List<TypedDependency> GR = Parser.getTdl();
			HashMap<String, Float> GR_Finder = new HashMap<String, Float>();
			GR_Finder = GR_Finder(GR);
			float numCompound = GR_Finder.get("numCompound");
			float numGR = GR_Finder.get("numGR");
			float avg_dist_GR = GR_Finder.get("avg_dist_GR");
			float max_dist_GR = GR_Finder.get("max_dist_GR");
			float advpVar = (float) cnt_advp / (float) word;
			float adjpVar = (float) cnt_adjp / (float) word;
			float modifierVar = (float) cnt_modifier / (float) word;

			SenFeatureDTO senFeature = new SenFeatureDTO(sen, ParseTree, type, cnt_advp, cnt_adjp, cnt_modifier,
					length, word, numChar, numSyll, voca_score, pattern_score, AWL_score, modifierVar, advpVar, adjpVar, numCC,
					numSBAR, numCompound, numGR, avg_dist_GR, max_dist_GR);
			
			senFeature.setModifier(Counters);
			
			return senFeature;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	public static HashMap<String, Integer> Counter(String ParseTree) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		StringTokenizer stkn = new StringTokenizer(ParseTree);
		int num_SBAR = 0;
		int num_CC = 0;
		int num_Modifier = 0;
		int num_ADJP = 0;
		int num_ADVP = 0;
		

		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();

			if (curtkn.contains("SBAR")) {
				num_SBAR++;
			} else if (curtkn.contains("CC")) {
				num_CC++;
			} else if (curtkn.contains("RBS") || curtkn.contains("RBR") || curtkn.contains("RB")
					|| curtkn.contains("JJS") || curtkn.contains("JJR") || curtkn.contains("JJ")
					|| curtkn.contains("WP") || curtkn.contains("WP$") || curtkn.contains("PRP$")
					|| curtkn.contains("VBN") || curtkn.contains("VBG") || curtkn.contains("TO")
					|| curtkn.contains("IN") || curtkn.contains("DT") || curtkn.contains("CD")) {
				num_Modifier++;
			} else if (curtkn.contains("ADJP")) {
				num_ADJP++;
			} else if (curtkn.contains("ADVP")) {
				num_ADVP++;
			}
		}
		
		counter.put("numSBAR", num_SBAR);
		counter.put("numCC", num_CC);
		counter.put("cnt_modifier", num_Modifier);
		counter.put("cnt_adjp", num_ADJP);
		counter.put("cnt_advp", num_ADVP);
		return counter;
	}
	
	/*수식어 개수 세기*/
	public static ModifierDTO Counters(String ParseTree) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		StringTokenizer stkn = new StringTokenizer(ParseTree.replace("(", "").replace(")", ""));
		
		ModifierDTO mod = new ModifierDTO();
		
		while (stkn.hasMoreTokens()) {
			String curtkn = stkn.nextToken();
			
			switch(curtkn.replace("(", "").replace(")", "")){
			case "SBAR":
				mod.setSbar_count(mod.getSbar_count() + 1);
				break;
			case "CC":
				mod.setCc_count(mod.getCc_count() + 1);
				break;
			case "RBS":
				mod.setRbs_count(mod.getRbs_count() + 1);
				break;
			case "RBR":
				mod.setRbr_count(mod.getRbr_count() + 1);
				break;
			case "RB":
				mod.setRb_count(mod.getRb_count() + 1);
				break;
			case "JJS":
				mod.setJjs_count(mod.getJjs_count() + 1);
				break;
			case "JJ":
				mod.setJj_count(mod.getJj_count() + 1);
				break;
			case "WP":
				mod.setWp_count(mod.getWp_count() + 1);
				break;
			case "WP$":
				mod.setWp$_count(mod.getWp$_count() + 1);
				break;
			case "PRP$":
				mod.setPrp$_count(mod.getPrp$_count() + 1);
				break;
			case "VBN":
				mod.setVbn_count(mod.getVbn_count() + 1);
				break;
			case "VBG":
				mod.setVbg_count(mod.getVbg_count() + 1);
				break;
			case "TO":
				mod.setTo_count(mod.getTo_count() + 1);
				break;
			case "IN":
				mod.setIn_count(mod.getIn_count() + 1);
				break;
			case "DT":
				mod.setDt_count(mod.getDt_count() + 1);
				break;
			case "WDT":
				mod.setWdt_count(mod.getWdt_count() + 1);
				break;
			case "CD":
				mod.setCd_count(mod.getCd_count() + 1);
				break;
			case "ADJP":
				mod.setAdjp_count(mod.getAdjp_count() + 1);
				break;
			case "ADVP":
				mod.setAdvp_count(mod.getAdvp_count() + 1);
				break;
			}
		}
		
		return mod;
	}

	public static HashMap<String, Float> GR_Finder(List<TypedDependency> GR) {
		HashMap<String, Float> GR_Finder = new HashMap<String, Float>();
		int numCompound = 0;
		int numGR = GR.size();
		int sumGRdist = 0;
		int maxGRdist = 0;

		for (int j = 0; j < GR.size(); j++) {
			int gov = GR.get(j).gov().hashCode();
			int dep = GR.get(j).dep().hashCode();

			int dist = 0;

			if (gov < 1) {
				dist = dep;
			} else if (gov > dep) {
				dist = gov - dep;
			} else {
				dist = dep = gov;
			}

			sumGRdist += dist;
			if (dist > maxGRdist) {
				maxGRdist = dist;
			}

			if (GR.get(j).reln().toString().equals("compound")) {
				numCompound++;
			}
		}
		float avgGRdist = (float) sumGRdist / (float) numGR;
		GR_Finder.put("numCompound", (float) numCompound);
		GR_Finder.put("numGR", (float) numGR);
		GR_Finder.put("avg_dist_GR", avgGRdist);
		GR_Finder.put("max_dist_GR", (float) maxGRdist);
		return GR_Finder;
	}

}
