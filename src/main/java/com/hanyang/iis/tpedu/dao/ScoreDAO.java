package com.hanyang.iis.tpedu.dao;

import java.util.ArrayList;

import com.hanyang.iis.tpedu.dto.Score;
import com.hanyang.iis.tpedu.dto.Sentence;

public class ScoreDAO {
	
	/*평균 구하기*/
    public Double getAvg(ArrayList<Double> dataSet){
    	Double result = null;
    	Double sumData = 0.0;
    	for(int i = 0; i < dataSet.size(); i++){
    		if(dataSet.get(i) == null){
    			break;
    		}
    		sumData += dataSet.get(i);
    	}
    	result = sumData / dataSet.size();
    	
    	return result;
    }
    
    /*분산 구하기*/
    public Double getStdv(ArrayList<Double> dataSet){
	   	Double result = null;
	   	Double sumData = 0.0;
	   	int dataSize = dataSet.size();
	   	Double avgData = getAvg(dataSet);
	   
	   	if(dataSize > 2){
		   	int i = 0; 
		   	for(i = 0; i < dataSize; i++){
			   	if(dataSet.get(i) == null){
			   		break;
			   	}
			   	Double diff = dataSet.get(i) - avgData;
			   	sumData += Math.pow(diff, 2);
		   	}
	   	}
	   	result = sumData / dataSize;
	   
	   	return Math.sqrt(result);
   	}
   
    
	 /*문장 길이 평균, 분산 점수 */
    public Score getLengthScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getLength());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_length(avgData);    	
    	scoreGrade.setVar_length(varData);    	
    	
    	return scoreGrade;
    }
    
    /*어휘 점수 평균, 분산 점수 */
    public Score getVocaScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
   			dataSet.add(dataList.get(i).getVoca_score());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_voca(avgData);    	
    	scoreGrade.setVar_voca(varData);    	    	
    	return scoreGrade;
    }
    
    /*패턴 점수 평균, 분산 점수 */
    public Score getPatternScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
   			dataSet.add(dataList.get(i).getPattern_score());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_pattern(avgData);    	
    	scoreGrade.setVar_pattern(varData); 
    	
    	return scoreGrade;
    }
    
    /*단어 수 평균, 분산 점수 */
    public Score getWordScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getWord());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_word_count(avgData);    	
    	scoreGrade.setVar_word_count(varData); 
    	
    	return scoreGrade;
    }
    
    /*형용사구 평균, 분산 점수 */
    public Score getAdjpScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getCnt_adjp());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_adjp(avgData);    	
    	scoreGrade.setVar_adjp(varData); 
    	
    	return scoreGrade;
    }
    
    /*부사구 평균, 분산 점수 */
    public Score getAdvpScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getCnt_advp());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	
    	scoreGrade.setAvg_advp(avgData);    	
    	scoreGrade.setVar_advp(varData); 
    	
    	return scoreGrade;
    }
    
    /*구조 타입 평균, 분산 점수 */
    public Score getStructType(ArrayList<Sentence> dataList, Score scoreGrade){
    	ArrayList<Double> dataSet = new ArrayList<Double>();
    	
    	for(int i = 0; i < dataList.size(); i++){
    		dataSet.add(dataList.get(i).getStruct_type());
    	}
    	
    	Double avgData = getAvg(dataSet);
    	Double varData = getStdv(dataSet);
    	//System.out.println("Avg : " + avgData + "  Var : " + varData);
    	
    	scoreGrade.setAvg_struct_type(avgData);    	
    	scoreGrade.setVar_struct_type(varData); 
    	
    	return scoreGrade;
    }
    
    
  	 /*단어당 평균 음절 수 평균, 분산 점수 */
      public Score getCharScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getAvg_char());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_cnt_char(avgData);    	
      	scoreGrade.setVar_cnt_char(varData);    	
      	
      	return scoreGrade;
      }
      
      /*단어당 평균 글자 수 평균, 분산 점수 */
      public Score getSyllableScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
     			dataSet.add(dataList.get(i).getAvg_syllables());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_cnt_syllable(avgData);    	
      	scoreGrade.setVar_cnt_syllable(varData);    	    	
      	return scoreGrade;
      }
      
      /*수식어 개수 평균, 분산 점수 */
      public Score getCntModifierScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
     			dataSet.add(dataList.get(i).getCnt_modifier());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_cnt_modifier(avgData);    	
      	scoreGrade.setVar_cnt_modifier(varData); 
      	
      	return scoreGrade;
      }
      
      /*AWL에 등록된 단어의 비율 평균, 분산 점수 */
      public Score getAWLScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getRatio_awl());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_awl(avgData);    	
      	scoreGrade.setVar_awl(varData); 
      	
      	return scoreGrade;
      }
      
      /*문장의 수식어 수 / 총 단어 수 평균, 분산 점수 */
      public Score getVariationModifierScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getVar_modifier());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_variation_modifier(avgData);    	
    	  scoreGrade.setVar_variation_modifier(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*문장의 부사어 수 / 총 단어 수 평균, 분산 점수 */
      public Score getVariationAdvScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getVar_adv());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_variation_adv(avgData);    	
      	scoreGrade.setVar_variation_adv(varData); 
      	
      	return scoreGrade;
      }
      
      /*문장의 형용사 수 / 총 단어 수 평균, 분산 점수 */
      public Score getVariationAdjScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getVar_adj());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_variation_adj(avgData);    	
      	scoreGrade.setVar_variation_adj(varData); 
      	
      	return scoreGrade;
      }
      
      /*등위 접속사 수 평균, 분산 점수 */
      public Score getCCScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getCnt_cc());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	//System.out.println("Avg : " + avgData + "  Var : " + varData);
      	
      	scoreGrade.setAvg_cnt_cc(avgData);    	
      	scoreGrade.setVar_cnt_cc(varData); 
      	
      	return scoreGrade;
      }
      

      /*종속절의 수 평균, 분산 점수 */
      public Score getSBARScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getCnt_sbar());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_cnt_sbar(avgData);    	
      	scoreGrade.setVar_cnt_sbar(varData); 
      	
      	return scoreGrade;
      }
      
      /*합성명사 수 평균, 분산 점수 */
      public Score getCompoundScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getCnt_compound());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_cnt_compound(avgData);    	
      	scoreGrade.setVar_cnt_compound(varData); 
      	
      	return scoreGrade;
      }
      
      /*구성 성분간의 문법적 관계 평균, 분산 점수 */
      public Score getCntGRScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getCnt_gr());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_gr_cnt(avgData);    	
      	scoreGrade.setVar_gr_cnt(varData); 
      	
      	return scoreGrade;
      }
      
      /*GR 간의 평균 거리 평균, 분산 점수 */
      public Score getAvgGRScore(ArrayList<Sentence> dataList, Score scoreGrade){
      	ArrayList<Double> dataSet = new ArrayList<Double>();
      	
      	for(int i = 0; i < dataList.size(); i++){
      		dataSet.add(dataList.get(i).getAvg_dis_gr());
      	}
      	
      	Double avgData = getAvg(dataSet);
      	Double varData = getStdv(dataSet);
      	
      	scoreGrade.setAvg_gr_avg(avgData);    	
      	scoreGrade.setVar_gr_avg(varData); 
      	
      	return scoreGrade;
      }
      
      /*GR 간의 최대 거리 평균, 분산 점수 */
      public Score getMaxGRScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getMax_dis_gr());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_gr_max(avgData);    	
    	  scoreGrade.setVar_gr_max(varData); 
    	  
    	  return scoreGrade;
      }
      
      
      /*문장 개수 점수 */
      public Score getNumSenScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getNum_sen());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_num_sen(avgData);
    	  scoreGrade.setVar_num_sen(varData); 
    	  
    	  return scoreGrade;
      }

      /*문단 단위 TTR 구사한 어휘의 다양성 점수 */
      public Score getTTRScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getTtr());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_ttr(avgData);    	
    	  scoreGrade.setVar_ttr(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*문단 수준에서 얼마나 잘 이해할 수 있는지 측정 점수 */
      public Score getCLIScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getCli());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_cli(avgData);    	
    	  scoreGrade.setVar_cli(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*문단 수준에서 얼마나 이해하기 어려운지 측정 점수 */
      public Score getLIXScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getLix());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_lix(avgData);    	
    	  scoreGrade.setVar_lix(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*PP 개수 */
      public Score getCntPPScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getCnt_pp());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_cnt_pp(avgData);
    	  scoreGrade.setVar_cnt_pp(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*CDEP 점수 */
      public Score getCdepScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getCdep());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_cdep(avgData);    	
    	  scoreGrade.setVar_cdep(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*왼쪽에서 수식하는 경우의 간격 */
      public Score getDepLeftScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getDep_left());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_dep_left(avgData);    	
    	  scoreGrade.setVar_dep_left(varData); 
    	  
    	  return scoreGrade;
      }
      
      /*오른쪽에서 수식하는 경우의 간격 */
      public Score getDepRightScore(ArrayList<Sentence> dataList, Score scoreGrade){
    	  ArrayList<Double> dataSet = new ArrayList<Double>();
    	  
    	  for(int i = 0; i < dataList.size(); i++){
    		  dataSet.add(dataList.get(i).getDep_right());
    	  }
    	  
    	  Double avgData = getAvg(dataSet);
    	  Double varData = getStdv(dataSet);
    	  
    	  scoreGrade.setAvg_dep_right(avgData);    	
    	  scoreGrade.setVar_dep_right(varData); 
    	  
    	  return scoreGrade;
      }
}
