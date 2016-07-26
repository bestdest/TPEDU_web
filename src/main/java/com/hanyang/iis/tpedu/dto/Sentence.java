package com.hanyang.iis.tpedu.dto;

public class Sentence {
	private double length;
	private double struct_type;
	private double voca_score;
	private double pattern_score;
	private double voca_score_sum;
	private double cnt_advp;
	private double cnt_adjp;
	private double word;
	
	private byte grade_length;
	private byte grade_struct_type;
	private byte grade_voca_score;
	private byte grade_pattern_score;
	private byte grade_cnt_advp;
	private byte grade_cnt_adjp;
	private byte grade_word;
	private int grade;

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
	
}
