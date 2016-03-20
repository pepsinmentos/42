package com.fortwo.server.model;

import java.util.Date;

public class Chat {
	private String message;
	private Date createdOn;
	private long chatId;
	private long userId;
	private long recipientId;
	
	public Chat(){
		
	}
	
	
	
	public Chat(String message, long chatId, long userId, long recipientId) {	
		this.message = message;
		this.chatId = chatId;
		this.userId = userId;
		this.recipientId = recipientId;
		this.createdOn = new Date();
	}



	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getChatId() {
		return chatId;
	}
	public void setChatId(long chatId) {
		this.chatId = chatId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getRecipientId(){
		return recipientId;
	}
	public void setRecipientId(long recipientId){
		this.recipientId = recipientId;
	}
	

}
