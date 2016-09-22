package com.hanyang.iis.tpedu.dto;

public class FeatureDTO {
	
	private int id;
	private String sentence;
	private String pattern;
	private int type;
	private int cnt_advp;
	private int cnt_adjp;
	private int cnt_modifier;
	private float modifierVar;
	private float advpVar;
	private float adjpVar;
	private float numCC;
	private float numSBAR;
	private float numCompound;
	private float numGR;
	private float avg_dist_GR;
	private float max_dist_GR;
	
	public FeatureDTO(int id,String sentence){
		this.id = id;
		this.sentence = sentence;
	}
	

	public FeatureDTO(int id, String sentence, String pattern, int type,
			int cnt_advp, int cnt_adjp, int cnt_modifier, float modifierVar,
			float advpVar, float adjpVar, float numCC, float numSBAR,
			float numCompound, float numGR, float avg_dist_GR, float max_dist_GR) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.pattern = pattern;
		this.type = type;
		this.cnt_advp = cnt_advp;
		this.cnt_adjp = cnt_adjp;
		this.cnt_modifier = cnt_modifier;
		this.modifierVar = modifierVar;
		this.advpVar = advpVar;
		this.adjpVar = adjpVar;
		this.numCC = numCC;
		this.numSBAR = numSBAR;
		this.numCompound = numCompound;
		this.numGR = numGR;
		this.avg_dist_GR = avg_dist_GR;
		this.max_dist_GR = max_dist_GR;
	}




	public FeatureDTO(int id, float numCC2, float numSBAR2, int numCompound,
			float numGR2, float avg_dist_GR, float max_dist_GR) {
		super();
		this.id = id;
		this.numCC = numCC2;
		this.numSBAR = numSBAR2;
		this.numCompound = numCompound;
		this.numGR = numGR2;
		this.avg_dist_GR = avg_dist_GR;
		this.max_dist_GR = max_dist_GR;
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getSentence() {
		return sentence;
	}




	public void setSentence(String sentence) {
		this.sentence = sentence;
	}




	public String getPattern() {
		return pattern;
	}




	public void setPattern(String pattern) {
		this.pattern = pattern;
	}




	public int getType() {
		return type;
	}




	public void setType(int type) {
		this.type = type;
	}




	public int getCnt_advp() {
		return cnt_advp;
	}




	public void setCnt_advp(int cnt_advp) {
		this.cnt_advp = cnt_advp;
	}




	public int getCnt_adjp() {
		return cnt_adjp;
	}




	public void setCnt_adjp(int cnt_adjp) {
		this.cnt_adjp = cnt_adjp;
	}




	public int getCnt_modifier() {
		return cnt_modifier;
	}




	public void setCnt_modifier(int cnt_modifier) {
		this.cnt_modifier = cnt_modifier;
	}




	public float getModifierVar() {
		return modifierVar;
	}




	public void setModifierVar(float modifierVar) {
		this.modifierVar = modifierVar;
	}




	public float getAdvpVar() {
		return advpVar;
	}




	public void setAdvpVar(float advpVar) {
		this.advpVar = advpVar;
	}




	public float getAdjpVar() {
		return adjpVar;
	}




	public void setAdjpVar(float adjpVar) {
		this.adjpVar = adjpVar;
	}




	public float getNumCC() {
		return numCC;
	}




	public void setNumCC(float numCC) {
		this.numCC = numCC;
	}




	public float getNumSBAR() {
		return numSBAR;
	}




	public void setNumSBAR(float numSBAR) {
		this.numSBAR = numSBAR;
	}




	public float getNumCompound() {
		return numCompound;
	}




	public void setNumCompound(float numCompound) {
		this.numCompound = numCompound;
	}




	public float getNumGR() {
		return numGR;
	}




	public void setNumGR(float numGR) {
		this.numGR = numGR;
	}




	public float getAvg_dist_GR() {
		return avg_dist_GR;
	}




	public void setAvg_dist_GR(float avg_dist_GR) {
		this.avg_dist_GR = avg_dist_GR;
	}




	public float getMax_dist_GR() {
		return max_dist_GR;
	}




	public void setMax_dist_GR(float max_dist_GR) {
		this.max_dist_GR = max_dist_GR;
	}
	
	
	
	
}
