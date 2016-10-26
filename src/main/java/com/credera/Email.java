package com.credera;

import org.springframework.mail.MailException;

import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;

/**
 * Created by drpresident on 10/25/16.
 */
public class Email {

  public Email(){ this(""); }

  public Email(String address){ this(address,""); }

  public Email(String address, String subject){ this(address,subject,""); }

  public Email(String address, String subject, String body) {
    this.address = address;
    this.subject = subject;
    this.body = body;
    this.props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "465");
    props.put("mail.smtp.secketfactory.port", "465");
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

    auth = new Authenticator(){
      protected PasswordAuthentication getPassAuth(){
        return new PasswordAuthentication("podsurfer3", "podgroup3");
      }
    };

    this.session = Session.getInstance(props, auth);
  };

  public boolean send(String address, String subject, String body) {
    this.body = body;
    return send(address, subject);
  }

  public boolean send(String address, String subject){
    this.subject = subject;
    return send(address);
  }

  public boolean send(String address){
    this.address = address;
    return send();
  }

  public boolean send(){

    Message msg = new MimeMessage(session);

    try{
      System.out.println("Creating message...");
      msg.setFrom(new InternetAddress("podsurfer3@gmail.com"));

      if(address != "")
        msg.addRecipient(Message.RecipientType.TO,
          new InternetAddress(address));
      if(subject != "");
        msg.setSubject(subject);
      if(body != "")
        msg.setText(body);

      System.out.println("attempting to send...");
      Transport.send(msg);
      System.out.println("Email to " + address + " sent successfully");

      return true;
    }
    catch(Exception ex){
      System.err.println(ex.getMessage());
      return false;
    }
  }

  public void setBody(String body){ this.body = body; }
  public String getBody(){ return body; }

  public void setSubject(String subject){ this.subject = subject; }
  public String getSubject(){ return subject; }

  public void setAddress(String address){ this.address = address;}
  public String getAddress(){ return address; }

  private String address;
  private String subject;
  private String body;
  private Session session;
  private Properties props;
  private Authenticator auth;
}

