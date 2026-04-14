package com.iggacorp.logic.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Site {

	private static boolean ENABLED = true;
	public static void toggle() {
		ENABLED = !ENABLED;
		
	}
	
}
