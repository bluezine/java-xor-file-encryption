package kr.co.bluezine;

import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws Exception {
		try {
			for (int i=0;i<100;i++) {
				System.out.println();
			}
			System.out.println("Input File : " + args[1].replace("\\", "\\\\"));
			System.out.println("Encryption Key : " + args[0]);
			System.out.println();
			System.out.println("you must copy original file");
			System.out.print("execute? (y) ");
			Scanner scan = new Scanner(System.in);
			String q = scan.next();
			if (q.equals("y")) {
				new Encode(args[0], args[1].replace("\\", "\\\\")).go();
			}
			
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("============= Help Message =============");
			System.out.println("password.jar password infile");
			System.out.println("password.jar password infile");
			System.out.println("========================================");
		}
	}
}
