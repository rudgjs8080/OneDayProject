package com.calloor.word.model;

public class WordVO {
	private String korea;
	private String english;
	private Integer count = 10;
	public String getKorea() {
		return korea;
	}
	public void setKorea(String korea) {
		this.korea = korea;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "WordVO [한글=" + korea + ", 영어=" + english + ", count=" + count + "]";
	}
	
	
	
}
