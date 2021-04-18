package com.callor.word.service;

import com.callor.word.WordVO;

public interface WordService {

	public void selectMenu();

	public void loadWord();

	public void printWord();

	public Integer viewWord();

	public Integer hintWord();

	public void saveScore();

	public void readScore();

	public WordVO getWord();

}
