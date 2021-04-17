package com.callor.word;

import com.callor.word.service.WordService;
import com.callor.word.service.impl.WordServiceImplV1;

public class WordEx_01 {

	public static void main(String[] args) {
		
		WordService wsV1 = new WordServiceImplV1();
		wsV1.selectMenu();
	}

}
