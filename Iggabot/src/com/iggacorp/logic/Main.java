package com.iggacorp.logic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumSet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;


public class Main {

	public static final Path PATH = Path.of(getMainPath());
	private static final String IGGACORP = "1211473025514475613";
	private static final String TESTING = "1460789003933454482";
	public static String IGGABOT_CHANNEL;
	public static JDA bot;
	public static Guild guild;


	//Random bullshit GO!!
	public Main() throws Exception {
		JDALogger.setFallbackLoggerEnabled(false);
		bot = setupBot();
		bot.addEventListener(new Interactions(bot));
		bot.awaitReady();
		IGGABOT_CHANNEL = getIggabotChannel();
		guild = bot.getGuildById(IGGACORP);
	}
	private static String getMainPath() {
		Path currentRelativePath = Paths.get("");
		String i = currentRelativePath.toAbsolutePath().toString();
		return i + "/src/Resources/";
	}
	public static Category getIggabotChannelCategory() {
		Category i = null;
		for(Category e : bot.getCategories()) {
			for(Channel e1 : e.getChannels()){
				if(e1.getId().equals(IGGABOT_CHANNEL)) {
					i=e;
				}
			}
		}
		return i;
	}
	public static String getIggabotChannel() {
		try {
			bot.awaitReady();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String b = "";
		for(TextChannel i : bot.getTextChannels()) {
			if(i.getName().equals("¡-✧┊iggabot")) {
				b = i.getId();
			}
		}
		return b;
	}
	//Makes the bot
	private static JDA setupBot() throws Exception {
		return JDABuilder.createDefault(Files.readString(Path.of("C:/NONWINDOWS/Iggabot/Logs/Static/Key.txt")), EnumSet.allOf(GatewayIntent.class)).build();
	}

}
