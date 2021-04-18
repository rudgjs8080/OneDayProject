package com.callor.word.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.callor.word.WordVO;
import com.callor.word.service.WordService;
import com.rudgjs.standard.MenuService;
import com.rudgjs.standard.impl.MenuServiceImplV1;

public class WordServiceImplV1 implements WordService {

	protected List<WordVO> wordList;
	protected List<String> menuList;
	protected MenuService menuService;
	protected Scanner scan;
	protected Random rd;
	protected String strEng;
	protected String strKor;
	protected int score;
	protected int rCount;
	public WordServiceImplV1() {

		wordList = new ArrayList<WordVO>();
		menuList = new ArrayList<String>();
		menuService = new MenuServiceImplV1("뤼팡의 영단어 퀴즈", menuList);
		scan = new Scanner(System.in);
		rd = new Random();
		String strEng = new String();
		String strKor = new String();// 힌트용
		score = 10; // 시작 점수
		rCount = 3; // 힌트 횟수
		this.loadWord();
	}

	@Override
	public void selectMenu() {
		// TODO Auto-generated method stub
		List<String> menuList = new ArrayList<String>();
		menuList.add("게임 시작");
		menuList.add("점수 불러오기");
		menuList.add("점수 저장");
		menuService = new MenuServiceImplV1("뤼팡 단어 게임", menuList);
		while (true) {
			Integer intMenu = menuService.selectMenu();
			if (intMenu == null) {
				System.out.println("종료!");
				break;
			}
			if (intMenu == 1) {
				this.viewWord();
			} else if (intMenu == 2) {
				this.readScore();
			} else if (intMenu == 3) {
				this.saveScore();
			}
		}
	}

	@Override
	public void loadWord() {
		// TODO Auto-generated method stub
		String wordName = "src/com/callor/word/word.txt";

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
			System.out.println("파일을 찾을수 없습니다");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("파일을 읽는 중 오류 발생");
		}

	}

	@Override
	public void printWord() {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer viewWord() {
		// TODO Auto-generated method stub
		while (true) {
			String[] strEng = this.splitWords();// 조건 단어를 배열화
			System.out.println("=".repeat(60));
			System.out.println("제시된 영 단어를 바르게 배열하시오 (QUIT : 게임 종료) 현재 점수 : "+ this.score);
			System.out.println(Arrays.toString(strEng));// 배열화 한 단어를 나열
			System.out.println("=".repeat(60));
			Integer hint = this.hintWord();
			if (hint == null) {
				continue;
			}else if(hint.equals("QUIT")) {
				return null;
			}
		}

	}

	@Override
	public Integer hintWord() {
		// TODO 힌트 및 유효성 검사 null 값 반환을 위해 Integer형 변환
		while (true) {
			System.out.print("입력 >>");
			String strInput = scan.nextLine();
			if (strInput.equalsIgnoreCase(this.strEng)) {
				System.out.println("뜻 : " + this.strKor);
				System.out.println("정답입니다 3점을 획득하셨습니다");
				this.score += 3;
				return null;
			} else if (strInput.equalsIgnoreCase(this.strEng) == false) {
				System.out.println("오답을 입력하셨습니다");
				Integer hintOrPass = this.HintOrPass();
				if (hintOrPass == null) {
					return null;
				} else if (hintOrPass == 1) {
					continue;
				}
			}
		}

	}

	public Integer HintOrPass() {
		
		while (true) {

			System.out.println("1. 힌트, 2. 건너뛰기");
			System.out.println("입력 >>");
			String strHint = scan.nextLine();
			if (strHint.equalsIgnoreCase("1")) {
				if (this.rCount == 0) {
					System.out.println("힌트를 모두 사용하셨습니다");
					return null;
				} else {
					System.out.println("뜻 : " + this.strKor + "힌트 횟수 : " + this.rCount);
					this.score--;
					this.rCount--;
					return 1;
				}
			} else if (strHint.equals("2")) {
				System.out.println("건너뛰기를 선택하셨습니다");
				return null;
			} else {
				System.out.println("선택을 잘못하셨습니다");
				continue;
			}

		}

	}

	@Override
	public void saveScore() {
		// TODO Auto-generated method stub
	
			String fileName = null;
			while (true) {
				System.out.println("저장할 파일 이름을 입력해주세요");
				System.out.println(" >> ");
				fileName = scan.nextLine();
				if (fileName.equals("")) {
					System.out.println("파일의 이름을 무조건 입력해주세요");
					continue;
				}
				break;
			}
			String strFileName = "src/com/calloor/word/" + fileName;

			FileWriter fileWriter = null;
			PrintWriter out = null;

			try {
				fileWriter = new FileWriter(strFileName);
				out = new PrintWriter(fileWriter);

				WordVO vo = wordList.get(0);
				out.print(vo.getCount());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.flush();
			out.close();
		
	}

	@Override
	public void readScore() {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("파일이름을 입력해주세요");
			System.out.print(" >> ");

			String fileName = scan.nextLine();

			String readFile = "src/com/calloor/word/" + fileName;

			FileReader fileReader = null;
			BufferedReader buffer = null;

			try {
				fileReader = new FileReader(readFile);
				buffer = new BufferedReader(fileReader);
				String reader = null;
				Integer reader1 = null;
				while (true) {
					reader = buffer.readLine();
					if (reader == null)
						break;

					reader1 = Integer.valueOf(reader);
					WordVO vo = new WordVO();
					vo.setCount(reader1);
					wordList.add(vo);
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("입력하신 파일명의 파일이 존재하지 않습니다");
				continue;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(fileName + "을 읽어오셨습니다");
			break;
		}

	}

	@Override
	public WordVO getWord() {
		// TODO 단어 꺼내오기

		rd = new Random();
		int nSize = wordList.size();
		int num = rd.nextInt(nSize);

		WordVO wordVO = wordList.get(num);

		return wordVO;
	}

	protected String[] splitWords() {

		WordVO vo = this.getWord();
		this.strEng = vo.getEnglish();
		this.strKor = vo.getKorea();
		String[] strWords = this.strEng.split("");
		for (int i = 0; i < 100; i++) {
			int index1 = rd.nextInt(strWords.length);
			int index2 = rd.nextInt(strWords.length);

			String temp = strWords[index1];
			strWords[index1] = strWords[index2];
			strWords[index2] = temp;
		}
		return strWords;
	}

}
