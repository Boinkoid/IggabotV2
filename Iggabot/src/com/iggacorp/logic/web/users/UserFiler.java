package com.iggacorp.logic.web.users;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

public class UserFiler{

	public static final Path PATH_TO_WEB = Path.of(Paths.get("").toAbsolutePath() + "/src/com/iggacorp/logic/web/");
	private static final Path PATH_TO_USER_FILES = Path.of(PATH_TO_WEB + "/users/UserFiles");
	private static final File ALL_FILES = new File(PATH_TO_USER_FILES + "");



	//The alphabetical order of the games included in the activities
	public static String[] GAMES = getGames();
	public UserFiler() {
		for(Member i : Main.guild.getMembers()) {
			File f = new File(PATH_TO_USER_FILES + "/" + i.getId() + ".txt");
			if(!f.exists()) {
				createUserFile(i.getId());
			}
		}
		for(File f : ALL_FILES.listFiles()) {
			boolean b = false;
			for(Member i : Main.guild.getMembers()) {
				b = !i.getId().equals(f.getName());
			}
			if(b) {
				deleteFile(f.getName());
			}
		}
	}
	//Game file logic
	private static String[] getGames() {
		String[] arr = {};
		try {
			List<String> list = Files.readAllLines(Path.of(Main.PATH + "/Games.txt"));
			arr = new String[list.size()];
			for(int j = 0; j<list.size()-1; j++) {
				arr[j] = list.get(j);
			}
		} catch (Exception e) {e.printStackTrace();}
		Arrays.sort(arr,String.CASE_INSENSITIVE_ORDER);
		for(String i : arr)
			saveToGameFile(i);
		return arr;
	}
	public static void newGame() {
		JFrame f = new JFrame("New Game");
		JTextArea t = new JTextArea();
		t.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER) {
					saveToGameFile(t.getText());
					f.dispose();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		f.add(t);
		f.setSize(800,800);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(true);
	}
	private static void saveToGameFile(String i) {
		if(i!=null) {
			try(BufferedWriter w = new BufferedWriter(new FileWriter(new File(Main.PATH + "/Games.txt"),true))) {
				w.write(textFormatting(i)+"\n");
				w.flush();
				GAMES = getGames();
			} catch (Exception e) {e.printStackTrace();}
		}
		for(String j : GAMES) {
			System.out.println(j);
		}
	}
	private static String textFormatting(String i) {
		String str = "";
		//Capitalize first letter no matter what
		str = i.substring(0,1).toUpperCase() + i.substring(2).toLowerCase();
		//Capitalize the first letter of every subsequent word
		String[] guh = str.split(" ");
		if(guh.length>0) {
			str = "";
			for(String j : guh) {
				str += j.substring(0,1).toUpperCase() + j.substring(2).toLowerCase();
			}
		}
		return str;
	}
	//User logic
	//Creates the user file with default stats
	public static void createUserFile(String string) {
		File f = new File(PATH_TO_USER_FILES + string);
		if(f.exists()) {
			f.delete();
		}
		try(BufferedWriter w = new BufferedWriter(new FileWriter(f))) {
			f.createNewFile();
			String str = "";
			for(String t : Files.readAllLines(Path.of(PATH_TO_USER_FILES + "/Template.txt"))) {
				str += t + "\n";
			}
			w.write(str);
			w.flush();
		} catch (Exception e) {e.printStackTrace();}
	}
	//Deletes the user upon leaving
	public static void deleteFile(String id) {
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				f.delete();
			}
		}
	}
	public static boolean bypass = false;
	public static void resetAllUserFiles() {
		if(!bypass) {
			Main.bot.getTextChannelById(Main.general.getId()).sendMessage("@everyone Iggacoin has been reset, I appologize to those affected but it had to be done.").queue();
		}
		for(File f : ALL_FILES.listFiles()) {
			createUserFile(f.getName());
		}
	}
	public static File getUserFile(User u) {
		File f = new File(PATH_TO_USER_FILES + "/" + u.getId() + ".txt");
		if(!f.exists()) {
			System.out.println("Idk how but it got fucked up, please advise");
		}
		return f;
	}
	//Gets the value specified by the first string
	public static int getValue(String val, String id) {
		int i = 0;
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				try {
					for(String str : Files.readAllLines(f.toPath())) {
						if(str.split(":")[0].equals(val)) {
							i = Integer.parseInt(str.split(":")[1]);
						}
					}
				} catch(Exception e) {e.printStackTrace();}
			}
		}
		return i;
	}
	//Sets the variable val to the integer i
	public static void setValue(String val, int i, String id) {
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				try(BufferedWriter w = new BufferedWriter(new FileWriter(f))){
					String str = "";
					for(String b : Files.readAllLines(f.toPath())) {
						if(b.split(":")[0].equals(val)) {
							str += b.split(":")[0] + ":" + i + "\n";
						} else {
							str += b + "\n";
						}
					}
					w.write(str);
				} catch(Exception e) {e.printStackTrace();}
			}
		}
	}
	//Adds i to the current value of val
	public static void addToValue(String val, int i, String id) {
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				try(BufferedWriter w = new BufferedWriter(new FileWriter(f))){
					String str = "";
					for(String b : Files.readAllLines(f.toPath())) {
						if(b.split(":")[0].equals(val)) {
							str += b.split(":")[0] + ":" + (i + getValue(val,id)) + "\n";
						} else {
							str += b + "\n";
						}
					}
					w.write(str);
				} catch(Exception e) {e.printStackTrace();}
			}
		}
	}
	//Subtracts i from the current value of val
	public static void subtractFromValue(String val, int i, String id) {
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				try(BufferedWriter w = new BufferedWriter(new FileWriter(f))){
					String str = "";
					for(String b : Files.readAllLines(f.toPath())) {
						if(b.split(":")[0].equals(val)) {
							str += b.split(":")[0] + ":" + (getValue(val,id)-i) + "\n";
						} else {
							str += b + "\n";
						}
					}
					w.write(str);
				} catch(Exception e) {e.printStackTrace();}
			}
		}
	}
	//Multiplies the current val by i
	public static void multiplyValue(String val, int i , String id) {
		for(File f : ALL_FILES.listFiles()) {
			if(f.getName().equals(id)) {
				try(BufferedWriter w = new BufferedWriter(new FileWriter(f))){
					String str = "";
					for(String b : Files.readAllLines(f.toPath())) {
						if(b.split(":")[0].equals(val)) {
							str += b.split(":")[0] + ":" + (getValue(val,id)*i) + "\n";
						} else {
							str += b + "\n";
						}
					}
					w.write(str);
				} catch(Exception e) {e.printStackTrace();}
			}
		}
	}
}