package com.credera;

public class MentorResponse {
  String name;
  String email;
  String university;

  public MentorResponse(String nm, String em, String univ) {
    this.name = nm;
    this.email = em;
    this.university = univ;
  }

  public void setName(String nm) {
    this.name = nm;
  }

  public String getName() {
    return this.name;
  }

  public void setEmail(String em) {
    this.email = em;
  }

  public String getEmail() {
    return this.email;
  }

  public void setUniversity(String univ) {
    this.university = univ;
  }

  public String getUniversity() {
    return this.university;
  }
}
