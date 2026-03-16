package com.iggacorp.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;


public class Main {

	public static final Path PATH = Path.of("C:/NONWINDOWS/Iggabot/");
	public static JDA bot;
	public Guild guild;
	

	//Random bullshit GO!!
	public Main() throws Exception {
		JDALogger.setFallbackLoggerEnabled(false);
		bot = setupBot();
		bot.addEventListener(new Interactions(bot));
		bot.awaitReady();
		guild = bot.getGuildById("1460789003933454482");
	}
	//Makes the bot
	private static JDA setupBot() throws Exception {
		return JDABuilder.createDefault(Files.readString(Path.of(PATH + "/Logs/Static/Key.txt")), EnumSet.allOf(GatewayIntent.class)).build();
	}
	public static String getNWordCount() {
		String i = "0";
		try {
			i = Files.readString(Path.of(PATH + "/Logs/Output/Count.txt"));
		} catch (IOException e) {e.printStackTrace();}
		return i;
	}

}
