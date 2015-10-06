package kr.co.bluezine;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Encode {
	static long startTime = System.nanoTime();
	double time;
	File inputFile;
	File outputFile;
	File logFile;
	String password;
	byte[] key;
	
	BufferedOutputStream writer;
	BufferedInputStream reader;
	
	public Encode(String password, String inputFile) {
		this.password = password;
		this.inputFile = new File(inputFile);
		int n = inputFile.lastIndexOf(".");
		this.outputFile = new File(inputFile.substring(0, n) + "-encode" + inputFile.substring(n));
		this.logFile = new File(inputFile.substring(0, n) + "-inform.txt");
		key = password.getBytes();
	}
	
	public void go() throws Exception {
		if (inputFile.length() >= 1610612736) {
			System.out.println("1.5GB exceed file does not support this program.");
			return;
		}
		try {
			reader = new BufferedInputStream(new FileInputStream(inputFile));
		} catch (FileNotFoundException e) {
			System.out.println("Input File Missing!");
			return;
		}
		try {
			writer = new BufferedOutputStream(new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			
		}
		
		
		new Doing().start();
	}
	
	class Doing extends Thread {

		@Override
		public void run() {
			int data = -1;
			long len = inputFile.length()/10;
			long len2 = inputFile.length()/10;
			int count = 0;
			star(count);
			for (int i=0;;i++) {
				try {
					data = reader.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					writer.write(data^key[i%key.length]);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (data<0) {
					try {
						writer.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				if (len2 == i) {
					len2 = len2 + len;
					count++;
					star(count);
				}
			}
			System.out.println();
			StringBuffer buffer = new StringBuffer();
			buffer.append("Output File : " + outputFile.toString() + " ( " + outputFile.length() + " Bytes)\n");
			buffer.append("Encryption Key : " + password + "\n");			
			time = (System.nanoTime() - startTime) / 1000000000.0;
			buffer.append("Execute Time : " + time + " s\n");
			try {
				FileWriter logFileWriter = new FileWriter(logFile);
				System.out.println(buffer);
				logFileWriter.append(buffer.toString());
				logFileWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		void star(int count) {
			for (int i=0;i<100;i++) {
				System.out.println();
			}
			System.out.println("Input File : " + inputFile.toString() + " ( " + inputFile.length() + " Bytes)");
			System.out.print("Progress : ");
			for (int i=0;i<count;i++) {
				System.out.print("★");
			}
			for (int i=10-count;i>0;i--) {
				System.out.print("☆");
			}
			System.out.println(" ( " + count*10 + "% )");
		}
	}
}
