package com.calloor.word.service.impl;

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

import com.calloor.word.model.WordVO;
import com.calloor.word.service.WordService;
import com.rudgjs.standard.MenuService;
import com.rudgjs.standard.impl.MenuServiceImplV1;

public class WordServiceImplV1_2 implements WordService {

	protected List<WordVO> wordList;
	protected Scanner scan;
	protected Random rd;
	protected MenuService menuService;//myJDK
	protected String strEng;
	protected int score; // 시작 점수
	protected int rCount;
	
	
	
	public WordServiceImplV1_2() {

		wordList = new ArrayList<WordVO>();
		scan = new Scanner(System.in);
		Random rd = new Random();
		strEng = new String(); // String 형 english
		rCount = 3;// 재도전 3회
		
		this.loadWord();
		this.selectMenu();

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

		System.out.println("파일을 불러오시겠습니까");
		this.readCount();
		System.out.println();
		while (true) {
			WordVO vo = this.getWord();
			
			int count = vo.getCount();
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
				vo.setCount(count);
				wordList.add(vo);	
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
		
		this.saveCount();
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

	public void saveCount() {
		System.out.println("점수를 저장하시려면 Yes 를 입력하세요");
		System.out.println(">>");
		String strYesNo = scan.nextLine();
		if(strYesNo.equals("Yes")) {
			String fileName = null;
			while(true) {
				System.out.println("저장할 파일 이름을 입력해주세요");
				System.out.println(" >> ");
				fileName = scan.nextLine();
				if(fileName.equals("")) {
					System.out.println("파일의 이름을 무조건 입력해주세요");
					continue;
				}
				break;
			}
			String strFileName = "src/com/calloor/word/" + fileName ;
			
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
		
		}
	
	public void readCount() {
		while(true) {
		System.out.println("파일이름을 입력해주세요");
		System.out.print(" >> ");
		
		String fileName = scan.nextLine();
				
		String readFile = "src/com/calloor/word/" + fileName ;
		
		FileReader fileReader = null;
		BufferedReader buffer = null;
		
			try {
				fileReader = new FileReader(readFile);
				buffer = new BufferedReader(fileReader);
				String reader = null;
				Integer reader1 = null;
				while(true) {
					reader = buffer.readLine();
					if(reader == null) break;
					
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
	public void selectMenu() {
		// TODO 시작화면
		
		List<String> menuList = new ArrayList<String>();
		menuList.add("게임 시작");
		menuList.add("점수 불러오기");
		menuList.add("점수 저장");
		menuService = new MenuServiceImplV1("뤼팡 단어 게임", menuList);
		while(true) {
			Integer intMenu = menuService.selectMenu();
			if(intMenu == null) {
				System.out.println("종료!");
				break;
			}
			if(intMenu == 1) {
				this.viewWord();
			}else if(intMenu == 2) {
				this.readCount();
			}else if(intMenu ==3) {
				this.saveCount();
			}
		}
	}
	
	protected String[] splitWords() {
		
		WordVO vo = this.getWord();
		this.strEng = vo.getEnglish();
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
