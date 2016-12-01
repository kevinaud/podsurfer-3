package com.credera;

public class Response {

	private Boolean success;
	private String message;
	
	public Response() {
		success = false;
		message = "An unexpected error occured";
	}
	
	public Response(Boolean success) {
		this.success = success;
		message = "";
	}
	
	public Response(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
