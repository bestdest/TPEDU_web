package com.hanyang.iis.tpedu.dto;

public class Sentence {
	private double length;				//길이
	private double struct_type;			//구조 (단중복문)
	private double voca_score;			//단어 점수
	private double pattern_score;		//패턴 점수
	private double voca_score_sum;		
	private double cnt_advp;			//부사구 수
	private double cnt_adjp;			//형용사구 수
	private double word;
	
	private double avg_char;			//단어당 평균글자수
	private double avg_syllables;		//단어당 평균 음절수
	private double cnt_modifier;		//수식어 개수
	private double ratio_awl;			//AWL(Acadimic Word List) 등록 단어의 비율
	private double var_modifier;		//문장 수식어 수/총 단어 수
	private double var_adv;				//문장 부사어 수/총 단어 수
	private double var_adj;				//문장 형용사 수/총 단어 수
	
	private double cnt_cc;				//등위접속사 수
	private double cnt_sbar;			//종속절의 수
	private double cnt_compound;		//합성명사 수
	private double cnt_gr;				//구성 성분간의 문법적 관계
	private double avg_dis_gr;			//GR 간의 평균 거리
	private double max_dis_gr;			//GR 간의 최대 거리
	
	private double num_sen;			//문단의 문장 개수
	private double ttr;				//구성 성분간의 문법적 관계
	private double cli;			//GR 간의 평균 거리
	private double lix;			//GR 간의 최대 거리

	private double cnt_pp;
	private double cdep;
	private double dep_left;
	private double dep_right;

	private byte grade_length;
	private byte grade_struct_type;
	private byte grade_voca_score;
	private byte grade_pattern_score;
	private byte grade_cnt_advp;
	private byte grade_cnt_adjp;
	private byte grade_word;
	private int grade;
	
	private byte grade_avg_char;
	private byte grade_avg_syllables;
	private byte grade_cnt_modifier;
	private byte grade_ratio_awl;
	private byte grade_var_modifier;
	private byte grade_var_adv;
	private byte grade_var_adj;
	private byte grade_cnt_cc;				//등위접속사 수
	private byte grade_cnt_sbar;			//종속절의 수
	private byte grade_cnt_compound;		//합성명사 수
	private byte grade_cnt_gr;				//구성 성분간의 문법적 관계
	private byte grade_avg_dis_gr;			//GR 간의 평균 거리
	private byte grade_max_dis_gr;			//GR 간의 최대 거리

	private byte grade_num_sen;				//문단의 문장 개수
	private byte grade_ttr;				//구성 성분간의 문법적 관계
	private byte grade_cli;			//GR 간의 평균 거리
	private byte grade_lix;			//GR 간의 최대 거리
	
	private double grade_cnt_pp;
	private double grade_cdep;
	private double grade_dep_left;
	private double grade_dep_right;
	
	private SenFeatureDTO originScore;
	private ParaFeatureDTO originScorePara;

	public byte getGrade_length() {
		return grade_length;
	}
	public void setGrade_length(byte grade_length) {
		this.grade_length = grade_length;
	}
	public byte getGrade_struct_type() {
		return grade_struct_type;
	}
	public void setGrade_struct_type(byte grade_struct_type) {
		this.grade_struct_type = grade_struct_type;
	}
	public byte getGrade_voca_score() {
		return grade_voca_score;
	}
	public void setGrade_voca_score(byte grade_voca_score) {
		this.grade_voca_score = grade_voca_score;
	}
	public byte getGrade_pattern_score() {
		return grade_pattern_score;
	}
	public void setGrade_pattern_score(byte grade_pattern_score) {
		this.grade_pattern_score = grade_pattern_score;
	}
	public byte getGrade_cnt_advp() {
		return grade_cnt_advp;
	}
	public void setGrade_cnt_advp(byte grade_cnt_advp) {
		this.grade_cnt_advp = grade_cnt_advp;
	}
	public byte getGrade_cnt_adjp() {
		return grade_cnt_adjp;
	}
	public void setGrade_cnt_adjp(byte grade_cnt_adjp) {
		this.grade_cnt_adjp = grade_cnt_adjp;
	}
	public byte getGrade_word() {
		return grade_word;
	}
	public void setGrade_word(byte grade_word) {
		this.grade_word = grade_word;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getStruct_type() {
		return struct_type;
	}
	public void setStruct_type(double struct_type) {
		this.struct_type = struct_type;
	}
	public double getVoca_score() {
		return voca_score;
	}
	public void setVoca_score(double voca_score) {
		this.voca_score = voca_score;
	}
	public double getPattern_score() {
		return pattern_score;
	}
	public void setPattern_score(double pattern_score) {
		this.pattern_score = pattern_score;
	}
	public double getVoca_score_sum() {
		return voca_score_sum;
	}
	public void setVoca_score_sum(double voca_score_sum) {
		this.voca_score_sum = voca_score_sum;
	}
	public double getCnt_advp() {
		return cnt_advp;
	}
	public void setCnt_advp(double cnt_advp) {
		this.cnt_advp = cnt_advp;
	}
	public double getCnt_adjp() {
		return cnt_adjp;
	}
	public void setCnt_adjp(double cnt_adjp) {
		this.cnt_adjp = cnt_adjp;
	}
	public double getWord() {
		return word;
	}
	public void setWord(double word) {
		this.word = word;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public double getAvg_char() {
		return avg_char;
	}
	public void setAvg_char(double avg_char) {
		this.avg_char = avg_char;
	}
	public double getAvg_syllables() {
		return avg_syllables;
	}
	public void setAvg_syllables(double avg_syllables) {
		this.avg_syllables = avg_syllables;
	}
	public double getCnt_modifier() {
		return cnt_modifier;
	}
	public void setCnt_modifier(double cnt_modifier) {
		this.cnt_modifier = cnt_modifier;
	}
	public double getRatio_awl() {
		return ratio_awl;
	}
	public void setRatio_awl(double ratio_awl) {
		this.ratio_awl = ratio_awl;
	}
	public double getVar_modifier() {
		return var_modifier;
	}
	public void setVar_modifier(double var_modifier) {
		this.var_modifier = var_modifier;
	}
	public double getVar_adv() {
		return var_adv;
	}
	public void setVar_adv(double var_adv) {
		this.var_adv = var_adv;
	}
	public double getVar_adj() {
		return var_adj;
	}
	public void setVar_adj(double var_adj) {
		this.var_adj = var_adj;
	}
	public double getCnt_cc() {
		return cnt_cc;
	}
	public void setCnt_cc(double cnt_cc) {
		this.cnt_cc = cnt_cc;
	}
	public double getCnt_sbar() {
		return cnt_sbar;
	}
	public void setCnt_sbar(double cnt_sbar) {
		this.cnt_sbar = cnt_sbar;
	}
	public double getCnt_compound() {
		return cnt_compound;
	}
	public void setCnt_compound(double cnt_compound) {
		this.cnt_compound = cnt_compound;
	}
	public double getCnt_gr() {
		return cnt_gr;
	}
	public void setCnt_gr(double cnt_gr) {
		this.cnt_gr = cnt_gr;
	}
	public double getAvg_dis_gr() {
		return avg_dis_gr;
	}
	public void setAvg_dis_gr(double avg_dis_gr) {
		this.avg_dis_gr = avg_dis_gr;
	}
	public double getMax_dis_gr() {
		return max_dis_gr;
	}
	public void setMax_dis_gr(double max_dis_gr) {
		this.max_dis_gr = max_dis_gr;
	}
	public byte getGrade_avg_char() {
		return grade_avg_char;
	}
	public void setGrade_avg_char(byte grade_avg_char) {
		this.grade_avg_char = grade_avg_char;
	}
	public byte getGrade_avg_syllables() {
		return grade_avg_syllables;
	}
	public void setGrade_avg_syllables(byte grade_avg_syllables) {
		this.grade_avg_syllables = grade_avg_syllables;
	}
	public byte getGrade_cnt_modifier() {
		return grade_cnt_modifier;
	}
	public void setGrade_cnt_modifier(byte grade_cnt_modifier) {
		this.grade_cnt_modifier = grade_cnt_modifier;
	}
	public byte getGrade_ratio_awl() {
		return grade_ratio_awl;
	}
	public void setGrade_ratio_awl(byte grade_ratio_awl) {
		this.grade_ratio_awl = grade_ratio_awl;
	}
	public byte getGrade_var_modifier() {
		return grade_var_modifier;
	}
	public void setGrade_var_modifier(byte grade_var_modifier) {
		this.grade_var_modifier = grade_var_modifier;
	}
	public byte getGrade_var_adv() {
		return grade_var_adv;
	}
	public void setGrade_var_adv(byte grade_var_adv) {
		this.grade_var_adv = grade_var_adv;
	}
	public byte getGrade_var_adj() {
		return grade_var_adj;
	}
	public void setGrade_var_adj(byte grade_var_adj) {
		this.grade_var_adj = grade_var_adj;
	}
	public byte getGrade_cnt_cc() {
		return grade_cnt_cc;
	}
	public void setGrade_cnt_cc(byte grade_cnt_cc) {
		this.grade_cnt_cc = grade_cnt_cc;
	}
	public byte getGrade_cnt_sbar() {
		return grade_cnt_sbar;
	}
	public void setGrade_cnt_sbar(byte grade_cnt_sbar) {
		this.grade_cnt_sbar = grade_cnt_sbar;
	}
	public byte getGrade_cnt_compound() {
		return grade_cnt_compound;
	}
	public void setGrade_cnt_compound(byte grade_cnt_compound) {
		this.grade_cnt_compound = grade_cnt_compound;
	}
	public byte getGrade_cnt_gr() {
		return grade_cnt_gr;
	}
	public void setGrade_cnt_gr(byte grade_cnt_gr) {
		this.grade_cnt_gr = grade_cnt_gr;
	}
	public byte getGrade_avg_dis_gr() {
		return grade_avg_dis_gr;
	}
	public void setGrade_avg_dis_gr(byte grade_avg_dis_gr) {
		this.grade_avg_dis_gr = grade_avg_dis_gr;
	}
	public byte getGrade_max_dis_gr() {
		return grade_max_dis_gr;
	}
	public void setGrade_max_dis_gr(byte grade_max_dis_gr) {
		this.grade_max_dis_gr = grade_max_dis_gr;
	}
	public double getNum_sen() {
		return num_sen;
	}
	public void setNum_sen(double num_sen) {
		this.num_sen = num_sen;
	}
	
	public double getTtr() {
		return ttr;
	}
	public void setTtr(double ttr) {
		this.ttr = ttr;
	}
	public double getCli() {
		return cli;
	}
	public void setCli(double cli) {
		this.cli = cli;
	}
	public double getLix() {
		return lix;
	}
	public void setLix(double lix) {
		this.lix = lix;
	}
	
	public byte getGrade_num_sen() {
		return grade_num_sen;
	}
	public void setGrade_num_sen(byte grade_num_sen) {
		this.grade_num_sen = grade_num_sen;
	}
	public byte getGrade_ttr() {
		return grade_ttr;
	}
	public void setGrade_ttr(byte grade_ttr) {
		this.grade_ttr = grade_ttr;
	}
	public byte getGrade_cli() {
		return grade_cli;
	}
	public void setGrade_cli(byte grade_cli) {
		this.grade_cli = grade_cli;
	}
	public byte getGrade_lix() {
		return grade_lix;
	}
	public void setGrade_lix(byte grade_lix) {
		this.grade_lix = grade_lix;
	}
	public SenFeatureDTO getOriginScore() {
		return originScore;
	}
	public void setOriginScore(SenFeatureDTO originScore) {
		this.originScore = originScore;
	}
	public ParaFeatureDTO getOriginScorePara() {
		return originScorePara;
	}
	public void setOriginScorePara(ParaFeatureDTO originScorePara) {
		this.originScorePara = originScorePara;
	}
	public double getCnt_pp() {
		return cnt_pp;
	}
	public void setCnt_pp(double cnt_pp) {
		this.cnt_pp = cnt_pp;
	}
	public double getCdep() {
		return cdep;
	}
	public void setCdep(double cdep) {
		this.cdep = cdep;
	}
	public double getDep_left() {
		return dep_left;
	}
	public void setDep_left(double dep_left) {
		this.dep_left = dep_left;
	}
	public double getDep_right() {
		return dep_right;
	}
	public void setDep_right(double dep_right) {
		this.dep_right = dep_right;
	}
	public double getGrade_cnt_pp() {
		return grade_cnt_pp;
	}
	public void setGrade_cnt_pp(double grade_cnt_pp) {
		this.grade_cnt_pp = grade_cnt_pp;
	}
	public double getGrade_cdep() {
		return grade_cdep;
	}
	public void setGrade_cdep(double grade_cdep) {
		this.grade_cdep = grade_cdep;
	}
	public double getGrade_dep_left() {
		return grade_dep_left;
	}
	public void setGrade_dep_left(double grade_dep_left) {
		this.grade_dep_left = grade_dep_left;
	}
	public double getGrade_dep_right() {
		return grade_dep_right;
	}
	public void setGrade_dep_right(double grade_dep_right) {
		this.grade_dep_right = grade_dep_right;
	}
	
	
}
