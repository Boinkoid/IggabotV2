package com.iggacorp.logic.commands;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinLeave extends ListenerAdapter {

	//NOT IMPLEMENTED FOR NOW, NEEDS RULES AND GOODBYE MESSAGE
		@Override
		public void onGuildMemberJoin(GuildMemberJoinEvent event) {
			//UserFiler.createUserFile(event.getUser().getId());
		}
		@Override
		public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
			//UserFiler.deleteFile(event.getUser().getId());
		}
}
