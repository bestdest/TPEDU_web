package com.hanyang.iis;

import com.hanyang.iis.tpedu.FeatureExtraction.ParaFeatureExtractor;
import com.hanyang.iis.tpedu.FeatureExtraction.SenFeatureExtractor;
import com.hanyang.iis.tpedu.dto.ParaFeatureDTO;
import com.hanyang.iis.tpedu.dto.SenFeatureDTO;
import com.hanyang.iis.tpedu.dto.Sentence;

public class FeatureController {
	public static int numOfGrade = 5; 
	
	public Sentence getFeaturePara(String paragraph){
		Sentence sentence = new Sentence();
		ParaFeatureExtractor PFE = new ParaFeatureExtractor();
		ParaFeatureDTO PDTO = PFE.ParaFeatureExtractor_forService(numOfGrade, paragraph);
		
		sentence.setStruct_type((double)PDTO.getType()/4);
	    sentence.setCnt_advp((double)PDTO.getCnt_advp()/135);
	    sentence.setCnt_adjp((double)PDTO.getCnt_adjp()/125);
	    sentence.setCnt_modifier((double)PDTO.getModifierVar()/2209);
	    sentence.setLength((double)PDTO.getLength()/24606);
	    sentence.setWord((double)PDTO.getWord()/4443);
	    sentence.setAvg_char((double)PDTO.getNumChar()/7);
	    sentence.setAvg_syllables((double)PDTO.getNumSyll()/(float)2.3);
	    sentence.setVoca_score((double)PDTO.getVoca_score()/(float)31.3);
	    sentence.setPattern_score((double)PDTO.getPattern_score()/5);
	    sentence.setRatio_awl((double)PDTO.getAWL_score()/50);
	    sentence.setVar_modifier((double)PDTO.getModifierVar()/(float)0.8);
	    sentence.setVar_adv((double)PDTO.getAdvpVar()/(float)0.27);
	    sentence.setVar_adj((double)PDTO.getAdjpVar()/(float)0.23);
	    sentence.setCnt_cc((double)PDTO.getNumCC()/205);
	    sentence.setCnt_sbar((double)PDTO.getNumSBAR()/236);
	    sentence.setCnt_compound((double)PDTO.getNumCompound()/146);
	    sentence.setCnt_gr((double)PDTO.getNumGR()/4740);
	    sentence.setAvg_dis_gr((double)PDTO.getAvg_dist_GR()/(float)39.5);
	    sentence.setMax_dis_gr((double)PDTO.getMax_dist_GR()/87);
		
	    sentence.setTtr((double)PDTO.getTTR());
	    sentence.setCli((double)PDTO.getCLI()/(float)32.1);
		sentence.setLix((double)PDTO.getLIX()/(float)106.3);
		sentence.setNum_sen(PDTO.getNumSen()/230);
		sentence.setOriginScorePara(PDTO);
		
		return sentence;
	}
	
	public Sentence getFeatureSentence(String sen){
		Sentence sentence = new Sentence();
	    SenFeatureExtractor SFE = new SenFeatureExtractor();
	    SenFeatureDTO SenFeature = SFE.SenFeatureExtractor(numOfGrade, sen);
	      
	    sentence.setStruct_type((double)SenFeature.getType()/4);
	    sentence.setCnt_advp((double)SenFeature.getCnt_advp()/15);
	    sentence.setCnt_adjp((double)SenFeature.getCnt_adjp()/34);
	    sentence.setCnt_modifier((double)SenFeature.getCnt_modifier()/103);
	    sentence.setLength((double)SenFeature.getLength()/1306);
	    sentence.setWord((double)SenFeature.getWord()/207);
	    sentence.setAvg_char((double)SenFeature.getNumChar()/15);
	    sentence.setAvg_syllables((double)SenFeature.getNumSyll()/6);
	    sentence.setVoca_score((double)SenFeature.getVoca_score()/(float)226.4);
	    sentence.setPattern_score((double)SenFeature.getPattern_score()/5);
	    sentence.setRatio_awl((double)SenFeature.getAWL_score()/(float)9.1);
	    sentence.setVar_modifier((double)SenFeature.getModifierVar());
	    sentence.setVar_adv((double)SenFeature.getAdvpVar());
	    sentence.setVar_adj((double)SenFeature.getAdjpVar());
	    sentence.setCnt_cc((double)SenFeature.getNumCC()/14);
	    sentence.setCnt_sbar((double)SenFeature.getNumSBAR()/20);
	    sentence.setCnt_compound((double)SenFeature.getNumCompound()/41);
	    sentence.setCnt_gr((double)SenFeature.getNumGR()/207);
	    sentence.setAvg_dis_gr((double)SenFeature.getAvg_dist_GR()/207);
	    sentence.setMax_dis_gr((double)SenFeature.getMax_dist_GR()/207);
	    
	    sentence.setOriginScore(SenFeature);
	    return sentence;
	}
	
	
}
