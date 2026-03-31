package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.iggacorp.logic.GUI.GUI;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;


public class Main {

	public static final Path PATH = Path.of(getMainPath());
	private static final String IGGACORP = "1211473025514475613";
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
	//Returns all the text channels
	public static void getTextChannels(){
		String str = "";
		for(Channel i : bot.getTextChannels()) {
			str += i.getName() + ": " + i.getId() + "\n";
		}
		System.out.println(str);
	}
	//Makes the igg files for AI
	public static void makeIggFiles() throws Exception {
		getAllChats();
		BufferedWriter w = new BufferedWriter(new FileWriter(new File(PATH + "/Logs/Training/IggChats.txt")));
		for(String e : Files.readAllLines(Path.of(PATH + "/Logs/Training/IggChats.txt"))) {
			if((e.split(":")[0].equals("1411486569386213376")||e.split(":")[0].equals("1113320968589414544"))&&e.split(":").length>1&&!e.contains("https//")&&!e.contains("@")) {
				w.write(e.split(":")[1] + "\n");
			}
		}
		w.flush();
		w.close();
		resetAI();
		System.out.println("Made Igg Files!");
	}
	//Resets the igg ai
	public static void resetAI() {
		Interactions.igg = Interactions.iggAI();
		bot.getChannelById(TextChannel.class, IGGABOT_CHANNEL).sendMessage("Reset AI!").queue();
		System.out.println("Reset AI!");
	}
	//Gets all chats from the appropriate channels
	public static void getAllChats() throws Exception{
		ArrayList<List<Message>> guh = new ArrayList<>();
		for(TextChannel i : bot.getTextChannels()) {
			if(!(i.getId().equals("1213318763651268649")||i.getId().equals("1222991601857597571")||i.getId().equals("1213320041676935168")||i.getId().equals("1258796845061378131")||i.getId().equals("1483303021243928656")||i.getId().equals("1242571289173168258")||i.getId().equals("1215154094289854464")||i.getId().equals("1211478753549746246")||i.getId().equals("1422661790147547156")||i.getId().equals("1213322706284581005")||i.getId().equals("1211473025514475618")||i.getId().equals("1474627951688679639")||i.getId().equals("1214061647258320946")||i.getId().equals("1393513733715923124")||i.getId().equals("1427841583503376384")||i.getId().equals("1213671602252947466")||i.getId().equals("1363296825280430182")||i.getId().equals("1393507211925393418")||i.getId().equals("1393508040837435502")||i.getId().equals("1222703528699760782")||i.getId().equals("1213319202908405800")||i.getId().equals("1220930784475742359")||i.getId().equals("1293692890756943924")||i.getId().equals("1421596963907829913")||i.getId().equals("1259173841784143902")||i.getId().equals("1327781164211114086")||i.getId().equals("1215813550094946374")))
			guh.add(i.getIterableHistory().submit().get());
		}
		String str = "";
		for(List<Message> b : guh) {
			for(Message i : b) {
				str += i.getAuthor().getGlobalName() + ": " + i.getContentDisplay() + "\n";
			}
		}
		BufferedWriter w = new BufferedWriter(new FileWriter(PATH + "/Logs/Training/IggacorpChats.txt"));
		w.write(str);
		w.flush();
		w.close();
		System.out.println("Got all chats!");
	}
	//Returns the main path
	private static String getMainPath() {
		Path currentRelativePath = Paths.get("");
		String i = currentRelativePath.toAbsolutePath().toString();
		return i + "/src/Resources/";
	}
	//Returns the iggabot ai channel for easier resetting
	public static Category getIggabotChannelCategory() {
		Category i = null;
		for(Category e : guild.getCategories()) {
			for(Channel e1 : e.getChannels()){
				if(e1.getId().equals(IGGABOT_CHANNEL)) {
					i=e;
				}
			}
		}
		return i;
	}
	//Returns the iggabot channel
	public static String getIggabotChannel() {
		try {
			bot.awaitReady();
		} catch (Exception e) {e.printStackTrace();}
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