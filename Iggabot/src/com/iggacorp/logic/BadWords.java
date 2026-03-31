package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dv8tion.jda.api.entities.User;

public class BadWords {

	/* TODO
	 * 
	 * debug the code
	 * test it out
	 * make sure it works
	 * when it inevitably doesnt, cook hard lowk
	 * 
	 * rn this is in development
	 * currently im gonna redo everything, bc rn its kinda sucky
	 * the goal is that it will make a file with a users discord ID and it will contain all of the swears the user has said
	 * Lowk might just delete this bc it kinda sucks. If i do go forward tho, i wanna put in a leaderboard
	 * 
	 * */
	BigInteger[] count;
	String[] swears;
	public static File FILE;
	public BadWords(String[] str, File file) {
		FILE=file;
		swears=str;
		count = new BigInteger[str.length-1];
		for(int i = 0; i<str.length-1; i++) {
			count[i] = new BigInteger(getSwearFromFile(str[i],FILE));
		}
	}
	public void add(String msg) {
		String str = "";
		for(int i = 0; i<swears.length-1; i++) {
			count[i] = count[i].add(new BigInteger(msg.toLowerCase().split(swears[i]).length+""));
			str += swears[i] + ": " + count[i].toString() + "\n";
		}
		try(BufferedWriter w = new BufferedWriter(new FileWriter(FILE))){
			w.write(str);
			w.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	private String getSwearFromFile(String swear, File file) {
		List<String> list;
		String rtrn = "";
		try {
			list = Files.readAllLines(Path.of(file.getAbsolutePath()));
			for(String i : list) {
				String[] j = i.split(":");
				if(j[0].equals(swear)) {
					rtrn=j[1];
				}
			}
		} catch(Exception e) {e.printStackTrace();}
		return rtrn;
	}
	public String slashGet(User user) {
		Map<String, BigInteger> swearCount = new HashMap<>();
		BigInteger totalSwears = BigInteger.ZERO;
		// Initialize swear counts
		for (String swear : swears) {
			swearCount.put(swear.toLowerCase(), BigInteger.ZERO);
		}
		List<String> lines;
		try {
			lines = Files.readAllLines(Path.of(Main.PATH + "/Logs/ServerLogs.txt"));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error reading log file.";
		}
		for (String line : lines) {
			String[] parts = line.split(":", 2);
			if (parts.length < 2) continue;
			String userId = parts[0].trim();
			String message = parts[1].toLowerCase();
			if (!userId.equals(user.getId())) continue;
			// Count swears
			for (String swear : swears) {
				String lowerSwear = swear.toLowerCase();
				int index = 0;
				while ((index = message.indexOf(lowerSwear, index)) != -1) {
					// Increment per-swear count
					swearCount.put(lowerSwear, swearCount.get(lowerSwear).add(BigInteger.ONE));
					// Increment total count
					totalSwears = totalSwears.add(BigInteger.ONE);
					index += lowerSwear.length();
				}
			}
		}
		// Build output
		StringBuilder result = new StringBuilder();
		result.append(user.getAsMention() + " said " 
				+ totalSwears.toString() 
				+ " swears, this is the distribution:\n");
		for (String swear : swears) {
			result.append(swear)
			.append(":")
			.append(swearCount.get(swear.toLowerCase()))
			.append("\n");
		}
		return result.toString();
	}
	public String slashGet() {
		BigInteger i = new BigInteger("0");
		for(BigInteger j : count) {
			i = i.add(j);
		}
		String rtrn = "The server has sworn " + i.toString() + " times, the distribution of which:\n";
		for(int a = 0; a<swears.length-1; a++) {
			rtrn += swears[a] + ": " + count[a].toString() + "\n";
		}
		return rtrn;
	}

}
