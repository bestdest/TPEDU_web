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
	private int grade;
	
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
