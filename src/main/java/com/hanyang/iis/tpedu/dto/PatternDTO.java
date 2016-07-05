package com.hanyang.iis.tpedu.dto;

public class PatternDTO {
	private int Id;
	private String Sentence;
	private String Pattern;
	private int grade;
	private int lang;
	private int count;
	private int type;
	private int cnt_advp;
	private int cnt_ajvp;
	
	
	
	public PatternDTO(int id, String sentence, String pattern, int grade,
			int lang, int count, int type, int cnt_advp, int cnt_ajvp) {
		super();
		Id = id;
		Sentence = sentence;
		Pattern = pattern;
		this.grade = grade;
		this.lang = lang;
		this.count = count;
		this.type = type;
		this.cnt_advp = cnt_advp;
		this.cnt_ajvp = cnt_ajvp;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getSentence() {
		return Sentence;
	}
	public void setSentence(String sentence) {
		Sentence = sentence;
	}
	public String getPattern() {
		return Pattern;
	}
	public void setPattern(String pattern) {
		Pattern = pattern;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getLang() {
		return lang;
	}
	public void setLang(int lang) {
		this.lang = lang;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
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
	public int getCnt_ajvp() {
		return cnt_ajvp;
	}
	public void setCnt_ajvp(int cnt_ajvp) {
		this.cnt_ajvp = cnt_ajvp;
	}
}
