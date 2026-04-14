package com.iggacorp.logic.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discord")
public class Site {
	
	private static boolean ENABLED = true;

	public static void toggle() {
		ENABLED = !ENABLED;
		System.out.println("Discord Activity Enabled: " + ENABLED);
	}

	public static boolean isEnabled() {
		return ENABLED;
	}

	@GetMapping
	public String home() {
		return "home.html";
	}
}