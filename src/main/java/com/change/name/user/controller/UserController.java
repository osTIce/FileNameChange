package com.change.name.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	@GetMapping("/user/login.do")
	public String login() {
		return "login";
	}
	
	@GetMapping("/user/join.do")
	public String join() {
		return "join";
	}
	
}
