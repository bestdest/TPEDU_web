package com.hanyang.iis.tpedu.dto;

public class SenFeatureDTO {
	private int id;
	private String sentence;
	private String pattern;
	private int para_id;
	private int txt_id;
	private int type;
	private int cnt_advp;
	private int cnt_adjp;
	private int cnt_modifier;
	private int length;
	private int word;
	private float numChar;
	private float numSyll;
	private float voca_score;
	private float pattern_score;
	private float AWL_score;
	private float modifierVar;
	private float advpVar;
	private float adjpVar;
	private int numCC;
	private int numSBAR;
	private float numCompound;
	private float numGR;
	private float avg_dist_GR;
	private float max_dist_GR;

	public SenFeatureDTO(int id, String sentence) {
		this.id = id;
		this.sentence = sentence;
	}
	
	public SenFeatureDTO(int id, float pattern_score) {
		this.id = id;
		this.pattern_score = pattern_score;
	}

	public SenFeatureDTO(int id, String sentence, String pattern, int type, int cnt_advp, int cnt_adjp,
			int cnt_modifier, int length, int word, float numChar, float numSyll, float voca_score, float AWL_score,
			float modifierVar, float advpVar, float adjpVar, int numCC, int numSBAR, float numCompound,
			float numGR, float avg_dist_GR, float max_dist_GR) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.pattern = pattern;
		this.type = type;
		this.cnt_advp = cnt_advp;
		this.cnt_adjp = cnt_adjp;
		this.cnt_modifier = cnt_modifier;
		this.length = length;
		this.word = word;
		this.numChar = numChar;
		this.numSyll = numSyll;
		this.voca_score = voca_score;
		this.AWL_score = AWL_score;
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
	
	
	public SenFeatureDTO(String sentence, String pattern, int type, int cnt_advp, int cnt_adjp,
			int cnt_modifier, int length, int word, float numChar, float numSyll, float voca_score, 
			float pattern_score, float AWL_score, float modifierVar, float advpVar, float adjpVar, int numCC, int numSBAR, float numCompound,
			float numGR, float avg_dist_GR, float max_dist_GR) {
		super();
		this.sentence = sentence;
		this.pattern = pattern;
		this.type = type;
		this.cnt_advp = cnt_advp;
		this.cnt_adjp = cnt_adjp;
		this.cnt_modifier = cnt_modifier;
		this.length = length;
		this.word = word;
		this.numChar = numChar;
		this.numSyll = numSyll;
		this.voca_score = voca_score;
		this.pattern_score = pattern_score;
		this.AWL_score = AWL_score;
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
	
	public SenFeatureDTO(int id, String sentence, String pattern, int para_id, int type, int cnt_advp, int cnt_adjp,
			int cnt_modifier, int length, int word, float numChar, float numSyll, float voca_score, float pattern_score, float AWL_score,
			float modifierVar, float advpVar, float adjpVar, int numCC, int numSBAR, int numCompound,
			int numGR, float avg_dist_GR, float max_dist_GR) {
		super();
		this.id = id;
		this.sentence = sentence;
		this.pattern = pattern;
		this.para_id = para_id;
		this.type = type;
		this.cnt_advp = cnt_advp;
		this.cnt_adjp = cnt_adjp;
		this.cnt_modifier = cnt_modifier;
		this.length = length;
		this.word = word;
		this.numChar = numChar;
		this.numSyll = numSyll;
		this.voca_score = voca_score;
		this.pattern_score = pattern_score;
		this.AWL_score = AWL_score;
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

	public int getPara_id() {
		return para_id;
	}

	public void setPara_id(int para_id) {
		this.para_id = para_id;
	}

	public int getTxt_id() {
		return txt_id;
	}

	public void setTxt_id(int txt_id) {
		this.txt_id = txt_id;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWord() {
		return word;
	}

	public void setWord(int word) {
		this.word = word;
	}

	public float getNumChar() {
		return numChar;
	}

	public void setNumChar(float numChar) {
		this.numChar = numChar;
	}

	public float getNumSyll() {
		return numSyll;
	}

	public void setNumSyll(float numSyll) {
		this.numSyll = numSyll;
	}

	public float getVoca_score() {
		return voca_score;
	}

	public void setVoca_score(float voca_score) {
		this.voca_score = voca_score;
	}

	public float getPattern_score() {
		return pattern_score;
	}

	public void setPattern_score(float pattern_score) {
		this.pattern_score = pattern_score;
	}

	public float getAWL_score() {
		return AWL_score;
	}

	public void setAWL_score(float aWL_score) {
		AWL_score = aWL_score;
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

	public int getNumCC() {
		return numCC;
	}

	public void setNumCC(int numCC) {
		this.numCC = numCC;
	}

	public int getNumSBAR() {
		return numSBAR;
	}

	public void setNumSBAR(int numSBAR) {
		this.numSBAR = numSBAR;
	}

	public float getNumCompound() {
		return numCompound;
	}

	public void setNumCompound(int numCompound) {
		this.numCompound = numCompound;
	}

	public float getNumGR() {
		return numGR;
	}

	public void setNumGR(int numGR) {
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
