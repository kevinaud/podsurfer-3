package com.credera;

import org.springframework.mail.MailException;

import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.*;

/**
 * Created by drpresident on 10/25/16.
 */
public class Email {

  private String address;
  private String subject;
  private String body;
  private Session session;
  private Properties props;
  private Message msg;
  private Transport transport;
  private String error = "";
  private static String email = "podsurfer3@gmail.com";
  private static String password  = "podgroup3";
  private static String host = "smtp.gmail.com";
  private static String tlsport = "587";
  private static String sslport = "465";

  public Email(){ this(""); }

  public Email(String address){ this(address,""); }

  public Email(String address, String subject){ this(address,subject,""); }

  public Email(String address, String subject, String body) {
    this.address = address;
    this.subject = subject;
    this.body = body;
    this.props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", sslport);
    props.put("mail.smtp.socketFactory.port", sslport);
    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.user", email);
    props.put("mail.smtp.password", password);
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

    session = Session.getDefaultInstance(props);
    msg = new MimeMessage(session);

    try{
      msg.setFrom(new InternetAddress(email));

      if(address != "")
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(address));
      if(subject != "")
        msg.setSubject(subject);
      if(body != "")
        msg.setText(body);

      System.out.println("attempting to send...");
      transport = session.getTransport("smtp");
      transport.connect(host, email, password);
      transport.sendMessage(msg, msg.getAllRecipients());
      transport.close();
      System.out.println("Email to " + address + " sent successfully");

      error = "";
      return true;
    }
    catch(Exception ex){
      System.err.println(ex.getMessage());
      error = ex.getMessage();
      return false;
    }
  }

  public void setBody(String body){ this.body = body; }
  public String getBody(){ return body; }

  public void setSubject(String subject){ this.subject = subject; }
  public String getSubject(){ return subject; }

  public void setAddress(String address){ this.address = address;}
  public String getAddress(){ return address; }

  public String getError(){ return error; }
  public void clear(){
    address = "";
    subject = "";
    body = "";
    error = "";
  }
}
