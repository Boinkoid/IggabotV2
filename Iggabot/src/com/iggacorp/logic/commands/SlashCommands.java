package com.iggacorp.logic.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.iggacorp.logic.Main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class SlashCommands extends ListenerAdapter {

	//Adds the commands into the server 
	public SlashCommands(JDA bot) {
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
}
