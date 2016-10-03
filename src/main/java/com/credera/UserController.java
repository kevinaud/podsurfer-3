package com.credera;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.podsurferAPI.PodsurferAPI;



@Controller
public class UserController {
	@Autowired
	private PodsurferAPI api;
	
	@ResponseBody @RequestMapping("/sign-up")
	public Response signUpUser(@RequestBody User newUser){
		return api.signUpUser(newUser);
	}

    @ResponseBody @RequestMapping("/login")
    public Response loginUser(@RequestBody User user) { return api.loginUser(user); }

}
