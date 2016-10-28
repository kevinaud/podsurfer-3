package com.credera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

import com.podsurferAPI.PodsurferAPI;
import java.security.SecureRandom;
import java.util.Random;
import java.math.BigInteger;
import java.util.HashMap;

@Controller
public class PasswordRecoveryController{

  public PasswordRecoveryController(){
    email = new Email();
    password = "";
    map = new HashMap<String,String>();
  }

  @ResponseBody @RequestMapping(value="/password-recovery", method=RequestMethod.POST)
  public Response passwordRecovery(@RequestBody String address){
    System.out.println("address: " + address);
    map.putIfAbsent(address, randomString());
    System.out.println(map.get(address));
    Response response = new Response();

    email.setAddress(address);
    email.setSubject("Podsurfer Password Recovery");
    email.setBody("Recovery Link: http://localhost:8080/password-recovery/"
      + map.get(address));

    if(email.send()){
      response.setMessage("success");
      response.setSuccess(true);
    }
    else {
      response.setMessage(email.getError());
      response.setSuccess(false);
    }
    return response;
  }

  @ResponseBody @RequestMapping(value="/password-recovery/{code}", method=RequestMethod.GET)
  public Response Recover(@PathVariable String code){
    Response response = new Response();

    System.out.println(code);
    response.setSuccess(false);
    response.setMessage("Not Yet Implemented");

    return response;
  }

  private String randomString(){
    rand = new Random();
    return new BigInteger(130, rand).toString(32);
  }

  @Autowired
  private PodsurferAPI api;

  private Email email;
  private String password;
  private Random rand;
  private HashMap<String, String> map;
}
