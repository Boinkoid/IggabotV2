package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Interactions extends ListenerAdapter {


	public static Swears SWEAR_COUNT = new Swears();
	public static Slurs SLUR_COUNT = new Slurs();
	public static AI igg = iggAI();
	public Interactions(JDA bot) {
		bot.updateCommands().addCommands(
				Commands.slash("cheer", "cheers for the user")
					.addOption(OptionType.USER, "user", "3 Cheers for the user!",true),
				Commands.slash("sex", "Sex eachother")
					.addOption(OptionType.USER, "user", "Sex's the user", true),
				Commands.slash("idea", "Give me your ideas")
					.addOption(OptionType.STRING, "idea", "Please give me your ideas, im desperate T-T", true)

				).queue();
	}
	public static AI iggAI() {
		AI i = null;
		try {
			i = new AI(Main.PATH + "/Logs/Training/IggChats.txt","llama3");
		} catch (Exception e) {e.printStackTrace();}
		return i;
	}
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		switch(event.getName()) {
		default->event.reply("If you somehow did this, contact delta with the exact command you did").setEphemeral(true).queue();
		case "cheer" ->event.reply("3 cheers for " + event.getOption("user").getAsMember().getUser().getEffectiveName() + "\nHip Hip Hooray!\nHip Hip Hooray!\nHip Hip Hooray!").queue();
		case "sex" ->event.reply("Not implemented yet! Yell at butter to get the images!!!").setEphemeral(true).queue();
		case "idea" ->{
			event.reply("Thank you for the idea! I'll get back to you about adding it").setEphemeral(true).queue();
			String str = "";
			str = event.getMember().getUser().getGlobalName() + ": " + event.getOption("idea").getAsString();
			try(BufferedWriter w = new BufferedWriter(new FileWriter(Main.PATH + "/Logs/Output/Ideas.txt",true))){
				w.append(str + "\n");
				w.flush();
			} catch(Exception e) {e.printStackTrace();}
		}
		}
	}
	public static boolean CHAT_ENABLED = false;
	BufferedWriter ServerLogs = create(Main.PATH + "/Logs/Output/ServerLogs.txt");
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Message msg = event.getMessage();
		if(event.getAuthor().isBot()) {return;}
		//Logs
		if(exclude(event)) {
			try {
				ServerLogs.write(event.getAuthor().getEffectiveName() + ": " + event.getMessage().getContentDisplay() + "\n");
				ServerLogs.flush();
			} catch (IOException e) {e.printStackTrace();}
		}
		//Admin Commands
		if(event.getMessage().getContentRaw().startsWith("i!")&&isAdmin(event)) {
			String str = event.getMessage().getContentRaw().substring(2);
			if(str.contains("help")) {
				event.getMessage().reply(
						"Admin commands:" +
						"i!help <- Displays this list" +
						"i!chat true/false <- Enables/Disables Iggabot Chatting in  #¡-✧┊iggabot"/* +
						"i!activity tru/false <- Enables/Disables Iggabot Activities" +
						"i! <- " +
						"i! <- " +
						"i! <- "*/ 
					).queue();
			}
			if(str.contains("chat")) {
				if(str.substring(5).toLowerCase().contains("true")) {
					CHAT_ENABLED = true;
					event.getChannel().sendMessage("Iggabot chatting enabled!").queue();
					System.out.println("Iggabot chatting enabled!");
				} else {
					CHAT_ENABLED = false;
					event.getChannel().sendMessage("Iggabot chatting disabled!").queue();
					System.out.println("Iggabot chatting disabled!");
				}
			}
		}
		//Chatting
		if(CHAT_ENABLED&&event.getChannel().getId().equals(Main.IGGABOT_CHANNEL)) {
			String str = igg.chat(event.getMessage().getContentDisplay());
			if(str.toLowerCase().contains("nigga")||str.toLowerCase().contains("niggabot")||str.toLowerCase().contains("niggagi")||str.toLowerCase().contains("nigger")) {
				str = "Bot said the N Word, this is bad and is currently being removed.";
			}
			event.getChannel().sendMessage(str).queue();
		}
		if(containsSwears(msg.getContentDisplay())) {
			SWEAR_COUNT=new BigInteger(SWEAR_COUNT.add(new BigInteger(getSwearAmount(msg.getContentDisplay()))).toString());
		}
		if(containsSlurs(msg.getContentDisplay())) {
			
		}
	}
	private String getSwearAmount(String str) {
		String shit[]
		return null;
	}
	String[] SLURS = {"fag","faggot","fagot","tranny","dyke","lesbo","","",""};
	private boolean containsSlurs(String str) {
		str=str.toLowerCase();
		for(String i : SLURS) {
			if(str.contains(i)) {
				return true;
			}
		}
		return false;
	}
	private boolean containsSwears(String str) {
		str=str.toLowerCase();
		return (str.contains("fuck")||str.contains("shit")||str.contains("dick")||str.contains("cum")||str.contains("ass")||str.contains("bitch")||str.contains("bastard")||str.contains("")||);
	}
	private boolean isAdmin(MessageReceivedEvent event) {
		return (event.getMember().getRoles().contains(event.getGuild().getRolesByName("Admin", true).get(0)) || event.getMember().getRoles().contains(event.getGuild().getRolesByName("IT Tech", true).get(0)));
	}
	private BufferedWriter create(String string) {
		BufferedWriter i = null;
		try {
			i = new BufferedWriter(new FileWriter(new File(string)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	private boolean exclude(MessageReceivedEvent event) {
		boolean b = true;
		switch(event.getChannel().getId()) {
		case "1215154094289854464" ->{b=false;}
		case "1214061647258320946" ->{b=false;}
		case "1327781164211114086" ->{b=false;}
		case "1393513733715923124" ->{b=false;}
		case "1422661790147547156" ->{b=false;}
		case "1427841583503376384" ->{b=false;}
		case "1259173841784143902" ->{b=false;}
		case "1211478753549746246" ->{b=false;}
		case "1216513357591351466" ->{b=false;}
		case "1220928851287474226" ->{b=false;}
		case "1364069427670417518" ->{b=false;}
		case "1213322706284581005" ->{b=false;}
		case "1422657603665662034" ->{b=false;}
		case "1474627951688679639" ->{b=false;}
		case "1363296825280430182" ->{b=false;}
		case "1213320041676935168" ->{b=false;}
		case "1215813550094946374" ->{b=false;}
		case "1213318763651268649" ->{b=false;}
		case "1213319202908405800" ->{b=false;}
		case "1293692890756943924" ->{b=false;}
		case "1393508040837435502" ->{b=false;}
		case "1393507211925393418" ->{b=false;}
		case "1421596963907829913" ->{b=false;}
		case "1393507487226794075" ->{b=false;}
		case "1213671602252947466" ->{b=false;}
		}
		return b;
	}
}
