package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Interactions extends ListenerAdapter {


	AI ai = iggAI();
	public Interactions(JDA bot) {
		bot.updateCommands().addCommands(
				Commands.slash("cheer", "cheers for the user")
				.addOption(OptionType.USER, "user", "3 Cheers for the user!",true),
				Commands.slash("sex", "Sex eachother")
				.addOption(OptionType.USER, "user", "Sex's the user", true),
				Commands.slash("chat", "Lets Iggabot start chatting")
				.addOption(OptionType.BOOLEAN, "boolean", "Sets chatting state",true)

				).queue();
	}

	private AI iggAI() {
		AI i = null;
		try {
			i = new AI(Main.PATH + "/Logs/Training/IggChats.txt","llama3");
		} catch (Exception e) {e.printStackTrace();}
		return i;
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		switch(event.getName()) {
		default->{
			event.reply("If you somehow did this, contact delta with the exact command you did").setEphemeral(true).queue();
		}
		case "cheer" ->{
			event.reply("3 cheers for " + event.getOption("user").getAsMember().getUser().getEffectiveName() + "\nHip Hip Hooray!\nHip Hip Hooray!\nHip Hip Hooray!").queue();
		}
		case "sex" ->{
			event.reply("Not implemented yet! Yell at butter to get the images!!!").setEphemeral(true).queue();
		}
		case "" ->{

		}
		}
	}
	public static boolean CHAT_ENABLED = false;
	BufferedWriter ServerLogs = create(Main.PATH + "/Logs/Output/ServerLogs.txt");
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) {return;}
		if(exclude(event)) {
			try {
				ServerLogs.write(event.getAuthor().getEffectiveName() + ": " + event.getMessage().getContentDisplay() + "\n");
				ServerLogs.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(event.getMessage().getContentRaw().startsWith("i!")&&isAdmin(event)) {
			String str = event.getMessage().getContentRaw().substring(2);
			if(str.contains("chat")) {
				if(str.substring(5).toLowerCase().contains("true")) {
					CHAT_ENABLED = true;
					event.getChannel().sendMessage("Iggabot chatting enabled!").queue();
				} else {
					CHAT_ENABLED = false;
					event.getChannel().sendMessage("Iggabot chatting disabled!").queue();
				}
			}
		}
		if(CHAT_ENABLED&&event.getChannel().getId().equals("1211473025514475617")) {
			event.getChannel().sendMessage(ai.chat(event.getMessage().getContentDisplay())).queue();
		}
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
