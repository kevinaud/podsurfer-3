package com.credera;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/")
	public String home() {
		return "index.html";
	}

	@RequestMapping("/map")
	public @ResponseBody String map() {
		return "map";
	}
}
