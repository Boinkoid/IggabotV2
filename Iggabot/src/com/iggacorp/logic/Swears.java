package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Swears {

	BigInteger[] count;
	public static final File swears = new File(Main.PATH + "/Logs/Output/Swears.txt");
	public Swears(String[] str) {
		count = new BigInteger[str.length-1];
		for(int i = 0; i<str.length-1; i++) {
			count[i] = new BigInteger(getSwear(str[i]));
		}
	}
	public void add(String str) {
		String a = "";
		try {
			List<String> list = Files.readAllLines(Path.of(swears.getAbsolutePath()));
			for() {
				
			}
		} catch(Exception e) {e.printStackTrace();}
		try(BufferedWriter w = new BufferedWriter(new FileWriter(swears))){
			w.write(a);
			w.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	private String getSwear(String string) {
		String a = "";
		try {
		Files.readAllLines(Path.of(swears.getAbsolutePath()));
		} catch(Exception e) {e.printStackTrace();}
		return a;
	}

}
