package com.iggacorp;

import com.iggacorp.logic.Main;
import com.iggacorp.logic.GUI.GUI;

public class Starter {

	public static void main(String[] args) throws Exception {
		new GUI();
		new Main();
		GUI.loadLogs();
		while(Main.IGGABOT_CHANNEL==null) {}//do nothing, just waits till the channel var initializes otherwise it causes huge issues
		GUI.clearConsole();
		GUI.frame.setVisible(true);
	}

}
