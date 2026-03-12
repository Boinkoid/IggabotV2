package com.iggacorp.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Interactions extends ListenerAdapter {


	public Interactions(JDA bot) {
		bot.updateCommands().addCommands(
				Commands.slash("echo", "Repeats messages back to you."),
				Commands.slash("cheer", "cheers for the user")
				.addOption(OptionType.USER, "user", "3 Cheers for the user!")
				).queue();
	}

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		switch(event.getName()) {
		default->{
			event.reply("If you somehow did this, contact delta with the exact command you did").setEphemeral(true).queue();
		}
		case "echo" ->{
			event.reply("Fuck you").queue();
		}
		case "cheer" ->{
			event.reply("3 cheers for " + event.getOption("user").getAsMember().getUser().getEffectiveName() + "\nHip Hip Hooray!\nHip Hip Hooray!\nHip Hip Hooray!").queue();
		}
		/*case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}
		case "" ->{b=false;

		}*/
		}
	}
	BufferedWriter ServerLogs = create(Main.PATH + "/Logs/Output/ServerLogs.txt");
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(exclude(event)) {
			try {
				ServerLogs.write(event.getAuthor().getEffectiveName() + ": " + event.getMessage().getContentDisplay() + "\n");
				ServerLogs.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
