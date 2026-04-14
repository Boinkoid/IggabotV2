package com.iggacorp.logic.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

class DiscordInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!Site.isEnabled()) {
            response.setContentType("text/html");
            response.getWriter().write("""
                <html>
                    <body style="text-align:center; font-family:sans-serif;">
                        <h1>🎰 Casino Closed</h1>
                        <p>Come back later.</p>
                    </body>
                </html>
            """);
            return false;
        }

        return true;
    }
}
@Configuration
class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DiscordInterceptor())
                .addPathPatterns("/discord/**");
    }
}
@RestController
@RequestMapping("/discord/play")
class PageController {

    @GetMapping("/{game}")
    public String loadGame(@PathVariable String game) {

        return switch (game.toLowerCase()) {

            case "blackjack" -> iframe("blackjack");
            case "poker" -> iframe("poker");
            case "slots" -> iframe("slots");
            case "roulette" -> iframe("roulette");
            case "texasholdem" -> iframe("texasholdem");

            default -> "<h1>Game not found</h1>";
        };
    }

    private String iframe(String game) {
        return """
            <html>
                <body style="margin:0;">
                    <iframe src="/games/%s/index.html" width="100%%" height="100%%" style="border:none;"></iframe>
                </body>
            </html>
        """.formatted(game);
    }
}