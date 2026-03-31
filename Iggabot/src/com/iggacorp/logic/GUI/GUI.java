package com.iggacorp.logic.GUI;

import java.io.BufferedWriter;
import java.io.File;
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
		gui = new GUI();
		new Main();
		loadLogs();
	}
	//Makes frame
	public static JFrame frame;
	public GUI() throws Exception {
		frame = new JFrame("Iggabot"); 
		frame.setIconImage(ImageIO.read(new File(PATH + "/Images/Teto.jpg")));
		frame.setSize(847,602);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Panel());
		frame.setResizable(false);
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
