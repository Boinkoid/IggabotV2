package com.iggacorp.logic.commands;

import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AdminCommands extends ListenerAdapter {

	//Admin commands list
	String ADMIN_COMMAND_HELP = "Admin commands:\n" +
			"i!help <- Displays this list\n" +
			"i!chat true/false <- Enables/Disables Iggabot Chatting in  #¡-✧┊iggabot\n" +
			"i!molest @user <- spams the user with balls\n" + 
			"i!activity true/false <- Enables/Disables Iggabot Activities\n *IN DEVELOPMENT*"/*not implemented yet
		"i!" +
		"i! <- \n" +
		"i! <- \n" +
		"i! <- \n"*/;
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) {return;}
		//Admin Commands
		if(event.getMessage().getContentRaw().startsWith("i!")) {
			if(isAdmin(event)) {
				event.getMessage().delete().queue();
				String str = event.getMessage().getContentRaw().substring(2);
				//Sends all the commands to the person who asked
				if(str.equals("help")) {
					Main.bot.openPrivateChannelById(event.getMessage().getAuthor().getId())
					.flatMap(e->e.sendMessage(ADMIN_COMMAND_HELP))
					.queue();				
				}
				//Enables iggabot chatting channel
				if(str.equals("chat")) {
					if(str.substring(5).toLowerCase().contains("true")) {
						AICommands.CHAT_ENABLED = true;
						event.getChannel().sendMessage("Iggabot chatting enabled!").queue();
						System.out.println("Iggabot chatting enabled!");
					} else {
						AICommands.CHAT_ENABLED = false;
						event.getChannel().sendMessage("Iggabot chatting disabled!").queue();
						System.out.println("Iggabot chatting disabled!");
					}
				}
				//Sends a lot of balls to the user
				if(str.substring(0,6).equals("molest")) {
					str = str.replace("<", "").replace(">", "").replace("@", "");
					PrivateChannel channel = Main.bot.openPrivateChannelById(str.substring(7)).complete();
					String tmp = "";
					for(int i = 0;i<333;i++) {
						tmp+="balls\n";
					}
					channel.sendMessage(tmp).queue();
				}
				if(str.substring(0,8).equals("activity")) {
					String buhhh = "UNIMPLEMENTED";
					
					event.getMessage().reply("Activities are " + buhhh).queue();
				}
			} else {
				event.getMessage().reply("You don't have admin permissions!").queue();
			}
		}
	}
	//Just a protection against long code :p
	private boolean isAdmin(MessageReceivedEvent event) {
		return (event.getMember().getRoles().contains(event.getGuild().getRolesByName("Admin", true).get(0)) || event.getMember().getRoles().contains(event.getGuild().getRolesByName("IT Tech", true).get(0)));
	}
}
