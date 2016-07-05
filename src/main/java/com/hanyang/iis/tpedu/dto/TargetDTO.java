package com.hanyang.iis.tpedu.dto;

public class TargetDTO {

	private int Id;
	private String Sentence;
	private String Pattern;
	private int grage;
	private int lang;
	
	
	public TargetDTO(int id, String sentence, String pattern, int grage,
			int lang) {
		super();
		Id = id;
		Sentence = sentence;
		Pattern = pattern;
		this.grage = grage;
		this.lang = lang;
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
	public int getLang() {
		return lang;
	}
	public void setLang(int lang) {
		this.lang = lang;
	}
	
}
