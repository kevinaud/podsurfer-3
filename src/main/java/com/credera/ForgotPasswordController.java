package com.credera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

import com.podsurferAPI.PodsurferAPI;

@Controller
public class ForgotPasswordController {

  public ForgotPasswordController(){
    email = new Email();
    password = "";
  }

  @ResponseBody @RequestMapping(value="/forgot-password", method=RequestMethod.POST)
  public Response forgotPassword(@RequestBody String address){
    System.out.println("address: " + address);
    Response response = new Response();

    email.setAddress(address);
    email.setSubject("Podsurfer Password Recovery");
    email.setBody("Temporary Password" + password);

    if(email.send()){
      response.setMessage("success");
      response.setSuccess(true);
    }
    else {
      response.setMessage("error");
      response.setSuccess(false);
    }
    return response;
  }

  @Autowired
  private PodsurferAPI api;

  private Email email;
  private String password;
}
