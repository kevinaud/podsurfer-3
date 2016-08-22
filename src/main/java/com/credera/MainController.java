package com.credera;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/")
	public @ResponseBody String home() {
		return "It works! :)";
	}
	
	@RequestMapping("/map")
	public @ResponseBody String map() {
		return "map";
	}
}