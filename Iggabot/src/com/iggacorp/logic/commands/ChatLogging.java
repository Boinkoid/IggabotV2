package com.iggacorp.logic.commands;

import java.io.FileWriter;

import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ChatLogging extends ListenerAdapter {

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) {return;}
		//Logs
		if(exclude(event)) {
			log("(" + event.getMessage().getAuthor().getId() + ")" + event.getMessage().getAuthor().getName() + ": " + event.getMessage().getContentRaw());
		}
	}
	//Logs the message into the ServerLogs file
	private void log(String str) {
		try(FileWriter w = new FileWriter(Main.PATH + "/Logs/Output/ServerLogs.txt",true)) {
			w.write(str + "\n");
		} catch (Exception e) {e.printStackTrace();}
	}
	//All of the channels that wont be logged, like logs or boombox
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
