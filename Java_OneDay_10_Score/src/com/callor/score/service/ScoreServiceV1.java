package com.callor.score.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.callor.score.model.ScorelistVO;

public class ScoreServiceV1 {

	protected List<ScorelistVO> scoreList;
	protected Scanner scan;

	public ScoreServiceV1() {
		scoreList = new ArrayList<ScorelistVO>();
		scan = new Scanner(System.in);

	}

	public void selectMenu() {

		while (true) {
			System.out.println("=".repeat(60));
			System.out.println("빛고을 고등학교 성적처리 프로젝트 2021");
			System.out.println("=".repeat(60));
			System.out.println("1. 학생별 성적입력");
			System.out.println("2. 학생 성적 리스트 출력");
			System.out.println("QUIT. 업무종료");
			System.out.println("=".repeat(60));
			System.out.println("업무선택 >> ");
			String strMenu = scan.nextLine();

			if (strMenu.equals("QUIT")) {
				System.out.println("업무가 종료되었습니다");
				break;
			}

			Integer intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("업무택에 오류가 있습니다");
				System.out.println(" 1 or 2 or QUIT 중 하나를 입력해주세요");

			}
			if (intMenu == 1) {
				this.inputScore();
			} else if (intMenu == 2) {
				this.printAllList();
				
			} else if(intMenu > 2 || intMenu <= 0) {
				System.out.println("업무 선택에 오류가 있습니다");
				continue;
			}

		}

	}

	public void inputScore() {
		Integer kor;
		Integer eng;
		Integer math;
		Integer sci;
		Integer his;
		Integer sum;
		Float avg;
		while (true) {
			ScorelistVO vo = new ScorelistVO();
			System.out.println("=".repeat(60));
			System.out.println("학생이름을 입력하세요(입력을 중단하려면 QUIT 를 입력해주세요");
			System.out.println("=".repeat(60));
			System.out.print("이름 >> ");
			String name = scan.nextLine();

			if (name.equals("QUIT")) {
				return;
			}

			System.out.println("=".repeat(60));
			System.out.println(name + "학생의 성적을 입력하세요 " + "(성적 범위 : 0 ~ 100, 입력을 중단하려면 QUIT");
			System.out.println("=".repeat(60));

			while (true) {
				System.out.print("국어 >> ");
				String strKor = scan.nextLine();
				try {
					kor = Integer.valueOf(strKor);
				} catch (Exception e) {
					System.out.println("정수값만 입력하세요");
					continue;
				}
				if (kor < 0 || kor > 100) {
					System.out.println("점수는 0 ~ 100 까지 입력하세요");
				} else {
					break;
				}

			}
			sum = kor;

			while (true) {
				System.out.print("영어 >> ");
				String strEng = scan.nextLine();
				try {
					eng = Integer.valueOf(strEng);
				} catch (Exception e) {
					System.out.println("정수값만 입력하세요");
					continue;
				}
				if (eng < 0 || eng > 100) {
					System.out.println("점수는 0 ~ 100 까지 입력하세요");
				} else {
					break;
				}

			}
			sum += eng;

			while (true) {
				System.out.print("수학 >> ");
				String strMath = scan.nextLine();
				try {
					math = Integer.valueOf(strMath);
				} catch (Exception e) {
					System.out.println("정수값만 입력하세요");
					continue;
				}
				if (math < 0 || math > 100) {
					System.out.println("점수는 0 ~ 100 까지 입력하세요");
				} else {
					break;
				}
			}
			sum += math;

			while (true) {
				System.out.print("과학 >> ");
				String strSci = scan.nextLine();
				try {
					sci = Integer.valueOf(strSci);
				} catch (Exception e) {
					System.out.println("정수값만 입력하세요");
					continue;
				}
				if (sci < 0 || sci > 100) {
					System.out.println("점수는 0 ~ 100 까지 입력하세요");
				} else {
					break;
				}
			}
			sum += sci;

			while (true) {
				System.out.print("국사 >> ");
				String strHis = scan.nextLine();
				try {
					his = Integer.valueOf(strHis);
				} catch (Exception e) {
					System.out.println("정수값만 입력하세요");
					continue;
				}
				if (his < 0 || his > 100) {
					System.out.println("점수는 0 ~ 100 까지 입력하세요");
				} else {
					break;
				}
			}
			sum += his;

			avg = (float) (sum / 5);

			vo.setName(name);
			vo.setKor(kor);
			vo.setEng(eng);
			vo.setMath(math);
			vo.setSci(sci);
			vo.setHis(his);
			vo.setSum(sum);
			vo.setAvg(avg);

			scoreList.add(vo);

			this.printScorelist(vo);

		} // while end

	}

	public void printScorelist(ScorelistVO vo) {

		System.out.println("=".repeat(60));
		System.out.println(vo.getName() + " 학생의 성적이 추가되었습니다");
		System.out.println("=".repeat(60));
		System.out.println("국어 : " + vo.getKor());
		System.out.println("영어 : " + vo.getEng());
		System.out.println("수학 : " + vo.getMath());
		System.out.println("과학 : " + vo.getSci());
		System.out.println("국사 : " + vo.getHis());

	}

	public void printAllList() {
		int totalKor = 0;
		int totalEng = 0;
		int totalMath = 0;
		int totalSci = 0;
		int totalHis = 0;
		int allTotal = 0;
		float allAvg = 0;

		System.out.println("=".repeat(60));
		System.out.println("순번\t국어\t영어\t수학\t과학\t국사\t총점\t평균");
		System.out.println("-".repeat(60));
		for (int i = 0; i < scoreList.size(); i++) {
			ScorelistVO vo = scoreList.get(i);
			System.out.print((i + 1) + "\t");
			System.out.print(vo.getKor() + "\t");
			System.out.print(vo.getEng() + "\t");
			System.out.print(vo.getMath() + "\t");
			System.out.print(vo.getSci() + "\t");
			System.out.print(vo.getHis() + "\t");
			System.out.print(vo.getSum() + "\t");
			System.out.printf("%.2f\n", vo.getAvg()); // 소수점 표현이 안됨
			totalKor += vo.getKor();
			totalEng += vo.getEng();
			totalMath += vo.getMath();
			totalSci += vo.getSci();
			totalHis += vo.getHis();
			allTotal += vo.getSum();
			allAvg += vo.getAvg();

		}
		System.out.println("=".repeat(60));
		System.out.printf("총점\t%d\t%d\t%d\t%d\t%d\t%d\t%f\t", totalKor, totalEng, totalMath, totalSci, totalHis,
				allTotal, allAvg / scoreList.size());
		System.out.println("=".repeat(60));
	}

}