package com.hanyang.iis.tpedu.FeatureExtraction;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.dao.ParaFeatureDAO;
import com.hanyang.iis.tpedu.dto.ParaFeatureDTO;
import com.mysql.jdbc.Connection;
import com.hanyang.iis.tpedu.dao.SenFeatureDAO;
import com.hanyang.iis.tpedu.dto.SenFeatureDTO;

public class ParaFeatureExtractor {
	
	public ParaFeatureDTO ParaFeatureExtractor_forService(int numOfGrade, String para){
		
		try{
			StringTokenizer st = new StringTokenizer(para);
			int sen_count =0;
			
			float type_para = 0;
			int cnt_advp_para = 0, cnt_adjp_para = 0, cnt_modifier_para = 0, length_para = 0, word_para = 0;
			float numChar_para = 0, numSyll_para = 0;
			int numCC_para = 0, numSBAR_para = 0, numCompound_para = 0, numGR_para = 0;
			float avg_dist_GR_para = 0, max_dist_GR_para = 0;
			float voca_score_para = 0, pattern_score_para = 0, AWL_score_para = 0;
			float modifierVar_para = 0, advpVar_para = 0, adjpVar_para = 0;
			float voca_score_avg = 0, pattern_score_avg = 0, AWL_score_avg = 0;
			
			while(st.hasMoreElements()){
				String sen = st.nextToken(".?!");
				if(sen.startsWith(" ")){
					sen = sen.substring(1);
				}
				if(sen.length() <2)
					continue;
				
				SenFeatureExtractor SFE = new SenFeatureExtractor();
				SenFeatureDTO SenFeature = SFE.SenFeatureExtractor(numOfGrade, sen);
				
				para += SenFeature.getSentence();
				cnt_advp_para += SenFeature.getCnt_advp();
				cnt_adjp_para += SenFeature.getCnt_adjp();
				cnt_modifier_para += SenFeature.getCnt_modifier();
				length_para += SenFeature.getLength();
				word_para += SenFeature.getWord();
				numChar_para += SenFeature.getWord() * SenFeature.getNumChar();
				numSyll_para += SenFeature.getWord() * SenFeature.getNumSyll();
				numCC_para += SenFeature.getNumCC();
				numSBAR_para += SenFeature.getNumSBAR();
				numCompound_para += SenFeature.getNumCompound();
				numGR_para += SenFeature.getNumGR();

				type_para += (float) SenFeature.getType();
				modifierVar_para += SenFeature.getWord() * SenFeature.getModifierVar();
				advpVar_para += SenFeature.getWord() * SenFeature.getAdvpVar();
				adjpVar_para += SenFeature.getWord() * SenFeature.getAdjpVar();
				avg_dist_GR_para += SenFeature.getAvg_dist_GR();
				max_dist_GR_para += SenFeature.getMax_dist_GR();
				voca_score_para += SenFeature.getVoca_score();
				pattern_score_para += SenFeature.getPattern_score();
				AWL_score_para += SenFeature.getAWL_score();
				sen_count++;
			}
			
			float TTR = getTTR(para, word_para);
			type_para = type_para / (float) sen_count;
			numChar_para = numChar_para / word_para;
			numSyll_para = numSyll_para / word_para;
			modifierVar_para = modifierVar_para / word_para;
			advpVar_para = advpVar_para / word_para;
			adjpVar_para = adjpVar_para / word_para;
			avg_dist_GR_para = avg_dist_GR_para / (float) sen_count;
			max_dist_GR_para = max_dist_GR_para / (float) sen_count;
			float L = ((float) length_para / (float) word_para) * 100;
			float S = ((float) sen_count / (float) word_para) * 100;
			float CLI = (float) (0.0588 * L - 0.296 * S - 15.8);
			float LIX = getLIX(para, word_para, (int) sen_count);
			voca_score_avg = voca_score_para / (float) sen_count;
			pattern_score_avg = pattern_score_para / (float) sen_count;

			ParaFeatureDTO PDTO = new ParaFeatureDTO( para, type_para, cnt_advp_para, cnt_adjp_para,
					cnt_modifier_para, length_para, word_para, numChar_para, numSyll_para, voca_score_avg,
					pattern_score_avg, AWL_score_para, modifierVar_para, advpVar_para, adjpVar_para, numCC_para,
					numSBAR_para, numCompound_para, numGR_para, avg_dist_GR_para, max_dist_GR_para, sen_count, TTR,
					CLI, LIX);
			
			return PDTO;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void ParaFeatureExtractor_forTesting() {
		try {
			SenFeatureDAO SDAO = new SenFeatureDAO();
			List<SenFeatureDTO> SDTO = SDAO.getSenAllFeature();

			ParaFeatureDAO PDAO = new ParaFeatureDAO();
			List<ParaFeatureDTO> PDTO = PDAO.getParagraphOnly();
			List<ParaFeatureDTO> FeatureUpdateList = new ArrayList<ParaFeatureDTO>();
			for (int i = 0; i < PDTO.size(); i++) {
				ParaFeatureDTO ParaFeature = PDTO.get(i);
				int id = ParaFeature.getId();

				float type_para = 0;
				int cnt_advp_para = 0, cnt_adjp_para = 0, cnt_modifier_para = 0, length_para = 0, word_para = 0;
				float numChar_para = 0, numSyll_para = 0;
				int numCC_para = 0, numSBAR_para = 0, numCompound_para = 0, numGR_para = 0;
				float avg_dist_GR_para = 0, max_dist_GR_para = 0;
				float voca_score_para = 0, pattern_score_para = 0, AWL_score_para = 0;
				float modifierVar_para = 0, advpVar_para = 0, adjpVar_para = 0;
				float voca_score_avg = 0, pattern_score_avg = 0, AWL_score_avg = 0;
				int sen_count = 0;
				String para = "";

				for (int j = 0; j < SDTO.size(); j++) {
					SenFeatureDTO SenFeature = SDTO.get(j);
					int para_id = SenFeature.getPara_id();

					if (id == para_id) {
						para += SenFeature.getSentence();
						cnt_advp_para += SenFeature.getCnt_advp();
						cnt_adjp_para += SenFeature.getCnt_adjp();
						cnt_modifier_para += SenFeature.getCnt_modifier();
						length_para += SenFeature.getLength();
						word_para += SenFeature.getWord();
						numChar_para += SenFeature.getWord() * SenFeature.getNumChar();
						numSyll_para += SenFeature.getWord() * SenFeature.getNumSyll();
						numCC_para += SenFeature.getNumCC();
						numSBAR_para += SenFeature.getNumSBAR();
						numCompound_para += SenFeature.getNumCompound();
						numGR_para += SenFeature.getNumGR();

						type_para += (float) SenFeature.getType();
						modifierVar_para += SenFeature.getWord() * SenFeature.getModifierVar();
						advpVar_para += SenFeature.getWord() * SenFeature.getAdvpVar();
						adjpVar_para += SenFeature.getWord() * SenFeature.getAdjpVar();
						avg_dist_GR_para += SenFeature.getAvg_dist_GR();
						max_dist_GR_para += SenFeature.getMax_dist_GR();
						voca_score_para += SenFeature.getVoca_score();
						pattern_score_para += SenFeature.getPattern_score();
						AWL_score_para += SenFeature.getAWL_score();

						sen_count++;
					}
				}
				
				float TTR = getTTR(para, word_para);
				type_para = type_para / (float) sen_count;
				numChar_para = numChar_para / word_para;
				numSyll_para = numSyll_para / word_para;
				modifierVar_para = modifierVar_para / word_para;
				advpVar_para = advpVar_para / word_para;
				adjpVar_para = adjpVar_para / word_para;
				avg_dist_GR_para = avg_dist_GR_para / (float) sen_count;
				max_dist_GR_para = max_dist_GR_para / (float) sen_count;
				float L = ((float) length_para / (float) word_para) * 100;
				float S = ((float) sen_count / (float) word_para) * 100;
				float CLI = (float) (0.0588 * L - 0.296 * S - 15.8);
				float LIX = getLIX(para, word_para, (int) sen_count);
				voca_score_avg = voca_score_para / (float) sen_count;
				pattern_score_avg = pattern_score_para / (float) sen_count;

				
				System.out.println(id + " : " + pattern_score_avg);
				ParaFeatureDTO tmp = new ParaFeatureDTO(id, para, type_para, cnt_advp_para, cnt_adjp_para,
						cnt_modifier_para, length_para, word_para, numChar_para, numSyll_para, voca_score_avg,
						pattern_score_avg, AWL_score_para, modifierVar_para, advpVar_para, adjpVar_para, numCC_para,
						numSBAR_para, numCompound_para, numGR_para, avg_dist_GR_para, max_dist_GR_para, sen_count, TTR,
						CLI, LIX);
				FeatureUpdateList.add(tmp);
			}
			PDAO.UpdateAllFeature(FeatureUpdateList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static float getLIX(String str, int words, int sen_count) {
		float LIX = 0;
		StringTokenizer st = new StringTokenizer(str);
		int C = 0;
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (word.length() > 6) {
				C++;
			}
		}
		LIX = (float) words / (float) sen_count + (C * 100) / (float) words;
		// System.out.println(LIX);
		return LIX;
	}

	public static float getTTR(String str, int words) {
		float TTR = 0;
		StringTokenizer st = new StringTokenizer(str);
		ArrayList<String> type = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String word = st.nextToken();
			if (!type.contains(word)) {
				type.add(word);
			} else {
				continue;
			}

		}
		TTR = (float) type.size() / (float) words;
		// System.out.println("TTR : "+TTR);

		return TTR;
	}

}
