package com.calloor.word.service;

import com.calloor.word.model.WordVO;

public interface WordService {
	public void loadWord();
	public void printWord();
	public void viewWord();
	public WordVO getWord();
	public void selectMenu();
}
