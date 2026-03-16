package com.iggacorp.logic.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.iggacorp.logic.Main;

public class GUI {

	private static final Path PATH = Main.PATH;
	public static GUI gui;

	//Main EDT
	public static void main(String[] args) throws Exception {
		new Main();
		gui = new GUI();
		loadLogs();
		//makeIggFiles();
	}

	/*private static void makeIggFiles() throws Exception {
		BufferedReader r = new BufferedReader(new FileReader(new File(PATH + "/Logs/Training/The Iggacorp Files.txt")));
		BufferedWriter w = new BufferedWriter(new FileWriter(new File(PATH + "/Logs/Training/IggChats.txt")));
		for(String e : r.readAllLines()) {
			if((e.split(":")[0].equals("1411486569386213376")||e.split(":")[0].equals("1113320968589414544"))&&e.split(":").length>1&&!e.contains("https//")&&!e.contains("@")) {
				w.write(e.split(":")[1] + "\n");
			}
		}
		w.flush();
	}*/

	//Makes frame
	public GUI() throws Exception {
		JFrame frame = new JFrame("Iggabot"); 
		frame.setIconImage(ImageIO.read(new File(PATH + "/Images/Teto.jpg")));
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Panel());
		frame.setVisible(true);
	}

	//Loads the ouput logs
	static BufferedWriter outLog = create(PATH + "/Logs/Output/Log.txt");
	static BufferedWriter errLog = create(PATH + "/Logs/Output/Err.txt");;
	static OutputStream out = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			JTextArea txt = Panel.txt;
			String i = String.valueOf((char)b);
			txt.append(i);
			txt.setCaretPosition(txt.getDocument().getLength());
			outLog.write(i);
			outLog.flush();
		}
	};
	static OutputStream err = new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			JTextArea txt = Panel.txt;
			String i = String.valueOf((char)b);
			txt.append(i);
			txt.setCaretPosition(txt.getDocument().getLength());
			errLog.write(i);
			errLog.flush();
		}
	};
	private static void loadLogs() {
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));
	}
	private static BufferedWriter create(String string) {
		BufferedWriter i = null;
		try {
		i = new BufferedWriter(new FileWriter(new File(string)));
		} catch(Exception e) {e.printStackTrace();}
		return i;
	}
}
