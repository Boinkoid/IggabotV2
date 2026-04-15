package com.iggacorp;

import com.iggacorp.logic.Main;
import com.iggacorp.logic.GUI.GUI;

public class Starter {

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(Site.class, args);//Figure out how to make it so it doesnt print to System.out
		new GUI();
		new Main();
//		new UserFiler(); the user filer, broken for rn
		GUI.loadLogs();
		while(Main.IGGABOT_CHANNEL==null) {}//do nothing, just waits till the channel var initializes otherwise it causes huge issues
		GUI.clearConsole();
		GUI.frame.setVisible(true);
	}

}
