package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketChatApplication.class, args);
	}

	/**
	 * Login Page
	 */
	@GetMapping("/")
	public ModelAndView login(HttpSession session) {
		session.invalidate();

		return new ModelAndView("login");
	}

	/**
	 * Chatroom Page
	 */
	@GetMapping("/index")
	public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
		if (username == null || username.isEmpty()) {
			return new ModelAndView("redirect:/");
		}

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("username", username);
		modelAndView.setViewName("chat");

		return modelAndView;
	}
}
