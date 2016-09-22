package com.hanyang.iis.tpedu.dto;

public class TargetDTO {

	private int Id;
	private String Sentence;
	private String Pattern;
	private int grage;
	private int lang;
	public float pattern_score;
	
	public float getPattern_score() {
		return pattern_score;
	}
	public void setPattern_score(float pattern_score) {
		this.pattern_score = pattern_score;
	}
	public TargetDTO(int id, String sentence, String pattern, int grage, float pattern_score) {
		super();
		Id = id;
		Sentence = sentence;
		Pattern = pattern;
		this.grage = grage;
		this.pattern_score = pattern_score;
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
	public int getGrage() {
		return grage;
	}
	public void setGrage(int grage) {
		this.grage = grage;
	}
	
}
