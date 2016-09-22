package com.hanyang.iis.tpedu.FeatureExtraction;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.hanyang.iis.tpedu.PretoPost.SentenParser;
import com.hanyang.iis.tpedu.SentenceTBL.TypeClassifier;
import com.hanyang.iis.tpedu.dao.FeatureDAO;
import com.hanyang.iis.tpedu.dto.FeatureDTO;

import edu.stanford.nlp.trees.TypedDependency;

public class FeatureExtractor {

	public FeatureExtractor() {
		SentenParser Parse = new SentenParser();
		
		FeatureDAO FDAO = new FeatureDAO();
		List<FeatureDTO> FeatureList = FDAO.getSentenceOnly();
		List<FeatureDTO> FeatureUpdateList = new ArrayList<FeatureDTO>();

		
		//for each sentences
		
		
		for (int s =18000;s<FeatureList.size();s++) {
			
			FeatureDTO Feature  = FeatureList.get(s);
			
			int id = Feature.getId();
			String str = Feature.getSentence();
			String pattern;
			
			System.out.println(id +  " " + s);
			
			int type = 0;
			int cnt_advp = 0;
			int cnt_adjp = 0;
			int cnt_modifier = 0;
			float modifierVar = 0;
			float advpVar = 0;
			float adjpVar = 0;
			float numCC = 0;
			float numSBAR = 0;
			float numCompound = 0;
			float numGR = 0;
			
			SPResult ParserResult = new SPResult(str);
			
			pattern = ParserResult.getParseTree();
			
			TypeClassifier TC = new TypeClassifier();
			type = TC.GetType(pattern);
			
			
			StringTokenizer stkn = new StringTokenizer(pattern);

			while (stkn.hasMoreTokens()) {
				String curtkn = stkn.nextToken();
				
				if(curtkn.contains("ADVP")){
					cnt_advp++;
				}
				else if(curtkn.contains("ADJP")){
					cnt_adjp++;
				}
				else if(curtkn.contains("RBS") || curtkn.contains("RBR")|| curtkn.contains("RB")|| curtkn.contains("JJS")|| curtkn.contains("JJR")|| curtkn.contains("JJ")|| curtkn.contains("WP")|| curtkn.contains("WP$")|| curtkn.contains("PRP$")|| curtkn.contains("VBN")|| curtkn.contains("VBG")|| curtkn.contains("TO")|| curtkn.contains("IN")|| curtkn.contains("DT")|| curtkn.contains("CD")){
					cnt_modifier++;
				}
				else if (curtkn.contains("SBAR")) {
					numSBAR++;
				} else if (curtkn.contains("CC")) {
					numCC++;
				} 
			}

			List<TypedDependency> GR = ParserResult.getTdl();

			numGR = GR.size();


			int sumGRdist = 0;
			int maxGRdist = 0;
			
			for (int i = 0; i < GR.size(); i++) {
				int gov = GR.get(i).gov().hashCode();
				int dep = GR.get(i).dep().hashCode();

				int dist = 0;
				
				if (gov < 1) {
					dist = dep;
				} else if (gov > dep) {
					dist = gov - dep;
				} else {
					dist = dep = gov;
				}

				sumGRdist  += dist;
				if (dist > maxGRdist ) {
					maxGRdist = dist;
				}

				if (GR.get(i).reln().toString().equals("compound")) {
					
					numCompound++;
				}
			}
			float avgGRdist = sumGRdist / numGR;
			StringTokenizer stkn2 = new StringTokenizer(str);
			float word = (float)stkn2.countTokens();
			
			modifierVar = cnt_modifier/word;
			advpVar = cnt_advp/word;
			adjpVar = cnt_adjp/word;
			
			FeatureDTO tmp = new FeatureDTO(id, str, pattern, type, cnt_advp, cnt_adjp, cnt_modifier, modifierVar, advpVar, adjpVar, numCC, numSBAR, numCompound, numGR, avgGRdist, maxGRdist);
			
			//FeatureDTO tmp = new FeatureDTO(id, numCC, numSBAR, numCompond, numGR, avgGRdist, maxGRdist);
			FeatureUpdateList.add(tmp);
		}
		
		FDAO.UpdateAllFeature(FeatureUpdateList);
	}
}

