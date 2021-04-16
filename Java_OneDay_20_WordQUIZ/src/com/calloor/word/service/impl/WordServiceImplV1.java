package com.calloor.word.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.calloor.word.model.WordVO;
import com.calloor.word.service.WordService;

public class WordServiceImplV1 implements WordService {

	List<WordVO> wordList;
	Scanner scan;
	Random rd;

	public WordServiceImplV1() {

		wordList = new ArrayList<WordVO>();
		scan = new Scanner(System.in);
		Random rd = new Random();
		this.loadWord();
		this.viewWord();

	}

	@Override
	public void loadWord() {
		// TODO 단어 불러오기

		String wordName = "src/com/calloor/word/word.txt";

		FileReader fileReader = null;
		BufferedReader buffer = null;

		try {
			fileReader = new FileReader(wordName);
			buffer = new BufferedReader(fileReader);

			while (true) {
				String reader = buffer.readLine();
				if (reader == null)
					break;

				String[] words = reader.split(":");
				WordVO vo = new WordVO();
				vo.setEnglish(words[0]);
				vo.setKorea(words[1]);
				wordList.add(vo);

			} // end while

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void printWord() {
		// TODO 출력

		int nSize = wordList.size();
		System.out.println("=".repeat(50));
		for (int i = 0; i < nSize; i++) {
			WordVO vo = wordList.get(i);
			System.out.printf("영어 : [%s]\t\t뜻 : [%s]\n", vo.getEnglish(), vo.getKorea());
		}
		System.out.println("=".repeat(50));

	}

	@Override
	public void viewWord() {
		// TODO Auto-generated method stub

		int count = 0;

		while (true) {
			WordVO vo = this.getWord();
			String strEng = vo.getEnglish();
			String[] strWords = strEng.split("");
			for (int i = 0; i < 100; i++) {
				int index1 = rd.nextInt(strWords.length);
				int index2 = rd.nextInt(strWords.length);

				String temp = strWords[index1];
				strWords[index1] = strWords[index2];
				strWords[index2] = temp;
			}

			System.out.println("=".repeat(50));
			System.out.println("제시된 영 단어를 바르게 배열하시오(QUIT : 게임종료) 점수 : " + count);
			System.out.println(vo.getEnglish());
			System.out.println(Arrays.toString(strWords));
			System.out.println("=".repeat(50));
			System.out.print(" >>");
			String strInput = scan.nextLine();
			if (strInput.trim().equals("QUIT")) {
				break;
			} else if (strInput.equalsIgnoreCase(vo.getEnglish())) {
				count += 3;
				System.out.println("정답입니다. 뜻은 : " + vo.getKorea());
				System.out.println("3점을 획득하셨습니다. 누적 점수 : " + count);
				continue;
			} else {

				System.out.println("오답입니다. 점수 : " + count);
			}
			int rcount = 3;

			while (rcount > 0) {
				System.out.println("재도전 : Yes, 게임종료 : QUIT, 이어하기 : Go");
				System.out.println("재도전 횟수 : " + rcount + ", 점수 : " + count);
				String strMenu = scan.nextLine();
				if (strMenu.trim().equals("QUIT")) {
					break;
				} else if (strMenu.trim().equals("Yes")) {
					System.out.println("찬스를 사용하시겠습니까? 1점이 차감됩니다");
					System.out.println("Yes or No : >>");
					String strChance = scan.nextLine();
					if (strChance.equals("Yes")) {
						if (count <= 0) {
							System.out.println("점수가 부족해 찬스를 사용할 수 없습니다");
							break;
						}
						count--;
						System.out.println("찬스 : " + vo.getKorea() + ", 점수 : " + count);

					} else if (strChance.equals("No")) {

					}

					else {
						System.out.println("잘못 입력하셨습니다 다시 입력해주세요");
						continue;
					}

					System.out.print("입력 : >>");
					String strInput1 = scan.nextLine();
					if (strInput1.equalsIgnoreCase(vo.getEnglish())) {
						System.out.println("정답입니다. 뜻은 : " + vo.getKorea());
						count += 2;
						System.out.println("누적 점수는 : " + count);
						break;
					} else {
						System.out.println("오답입니다");
						System.out.println();
						rcount--;
						continue;
					}
				} else if (strMenu.equals("Go")) {
					break;
				} else {
					System.out.println("메뉴선택을 잘못하셨습니다");
					continue;
				}
			}

		} // end while
	}

	@Override
	public WordVO getWord() {
		// TODO 꺼내오기
		rd = new Random();
		int nSize = wordList.size();
		int num = rd.nextInt(nSize);

		WordVO wordVO = wordList.get(num);

		return wordVO;

	}

	public void makeChance(String strMenu, int count, int rcount) {

	}

	public void makeWord() {

	}
}
