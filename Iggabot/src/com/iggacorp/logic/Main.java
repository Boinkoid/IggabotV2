package com.iggacorp.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.EnumSet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;


public class Main implements Runnable {

	public static final Path PATH = Path.of("C:/NONWINDOWS/Iggabot/");
	public static JDA bot;
	public Guild guild;
	
	//Bot thread
	@Override
	public void run() {
		
	}

	//Random bullshit GO!!
	public Main() throws Exception {
		bot = setupBot();
		bot.addEventListener(new Interactions(bot));
		bot.awaitReady();
		guild = bot.getGuildById("1460789003933454482");
		new Thread(this).start();
	}
	//Makes the bot
	@SuppressWarnings("resource")
	private static JDA setupBot() throws Exception {
		return JDABuilder.createDefault(new BufferedReader(new FileReader(new File(PATH + "/Logs/Static/Key.txt"))).readLine(), EnumSet.allOf(GatewayIntent.class)).build();
	}

}
