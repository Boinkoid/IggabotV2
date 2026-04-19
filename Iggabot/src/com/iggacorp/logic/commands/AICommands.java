package com.iggacorp.logic.commands;

import com.iggacorp.logic.AI;
import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AICommands extends ListenerAdapter {

	//Makes the iggAI, leaving room for more people to become AI
			public static AI igg = iggAI();
		//Makes the IggAI
		public static AI iggAI() {
			AI i = null;
			try {
				i = new AI(Main.PATH + "/Logs/Training/IggChats.txt","llama3");
			} catch (Exception e) {e.printStackTrace();}
			return i;
		}
		//Whether the iggaAI chatting is enabled
			public static boolean CHAT_ENABLED = false;
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				if(event.getAuthor().isBot()) {return;}
				//Chatting
				if(CHAT_ENABLED&&event.getChannel().getId().equals(Main.IGGABOT_CHANNEL)) {
					String str = igg.chat(event.getAuthor().getName() + " said " + event.getMessage().getContentDisplay());
					if(str.toLowerCase().contains("nigga")||str.toLowerCase().contains("nigger")) {
						str = "Bot said the N Word. This is not intended and will be reviewed.";
					}
					event.getChannel().sendMessage(str).queue();
				}
			}
}
