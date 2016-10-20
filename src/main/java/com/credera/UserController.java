package com.credera;

import java.io.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.podsurferAPI.PodsurferAPI;

@Controller
public class UserController {
	@Autowired
	private PodsurferAPI api;
	
	@ResponseBody @RequestMapping("/sign-up")
	public Response signUpUser(@RequestBody User user){
		System.out.println(user.getName());
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		System.out.println(user.getToken());

		return api.signUpUser(user);
	}

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody Response loginUser(@RequestBody User user) {
    System.out.println(user.getName());
    System.out.println(user.getEmail());
    System.out.println(user.getPassword());

    return api.loginUser(user);
	}

}
