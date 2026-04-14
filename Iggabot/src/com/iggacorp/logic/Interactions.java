package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.iggacorp.logic.web.Site;
import com.iggacorp.logic.web.users.UserFiler;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Interactions extends ListenerAdapter {

	//Makes the iggAI, leaving room for more people to become AI
	public static AI igg = iggAI();
	//Adds the commands into the server 
	public Interactions(JDA bot) {
		bot.updateCommands().addCommands(
				Commands.slash("cheer", "cheers for the user")
				.addOption(OptionType.USER, "user", "3 Cheers for the user!",true),
				Commands.slash("sex", "Sex eachother")
				.addOption(OptionType.USER, "user", "Sex's the user", true),
				Commands.slash("idea", "Give me your ideas")
				.addOption(OptionType.STRING, "idea", "Please give me your ideas, im desperate T-T", true),
				Commands.slash("swear","Displays the swear count")
				.addOption(OptionType.USER, "user", "Shows the amount of time the user has sworn")
				//Add in leaderboard and coinflip commands
				
				).queue();
	}

	//Makes the IggAI
	public static AI iggAI() {
		AI i = null;
		try {
			i = new AI(Main.PATH + "/Logs/Training/IggChats.txt","llama3");
		} catch (Exception e) {e.printStackTrace();}
		return i;
	}

	//Logic for all the slash commands
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

	//Whether the iggaAI chatting is enabled
	public static boolean CHAT_ENABLED = false;
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

	//The logic that happens whenever a message is sent
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()) {return;}
		//Logs
		if(exclude(event)) {
			log("(" + event.getMessage().getAuthor().getId() + ")" + event.getMessage().getAuthor().getName() + ": " + event.getMessage().getContentRaw());
		}
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
						CHAT_ENABLED = true;
						event.getChannel().sendMessage("Iggabot chatting enabled!").queue();
						System.out.println("Iggabot chatting enabled!");
					} else {
						CHAT_ENABLED = false;
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
					Site.toggle();
				}
			} else {
				event.getMessage().reply("You don't have admin permissions!").queue();
			}
		}
		//Chatting
		if(CHAT_ENABLED&&event.getChannel().getId().equals(Main.IGGABOT_CHANNEL)) {
			String str = igg.chat(event.getMessage().getContentDisplay());
			if(str.toLowerCase().contains("nigga")||str.toLowerCase().contains("nigger")) {
				str = "Bot said the N Word. This is not intended and will be reviewed.";
			}
			event.getChannel().sendMessage(str).queue();
		}
		for(int i = 0; i<15; i++) {
			System.out.println("\"" + i + ". \" +\n");
		}
	}

	//Logs the message into the ServerLogs file
	private void log(String str) {
		try(FileWriter w = new FileWriter(Main.PATH + "/Logs/Output/ServerLogs.txt",true)) {
			w.write(str + "\n");
		} catch (Exception e) {e.printStackTrace();}
	}

	//NOT IMPLEMENTED FOR NOW, NEEDS RULES AND GOODBYE MESSAGE
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		UserFiler.createUserFile(event.getUser().getId());
	}
	@Override
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		UserFiler.deleteFile(event.getUser().getId());
	}

	//Just a protection against long code :p
	private boolean isAdmin(MessageReceivedEvent event) {
		return (event.getMember().getRoles().contains(event.getGuild().getRolesByName("Admin", true).get(0)) || event.getMember().getRoles().contains(event.getGuild().getRolesByName("IT Tech", true).get(0)));
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
