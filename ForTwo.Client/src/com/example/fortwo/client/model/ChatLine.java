package com.example.fortwo.client.model;

import java.util.Date;

public class ChatLine {
	private long ChatLineId;
	private int SenderId;
	private int RecipientId;
	private String Message;	
	private int Status;
	private Date SentOn;
	private Date CreatedOn;
	
	
	public long getChatLineId() {
		return ChatLineId;
	}

	public void setChatLineId(long chatLineId) {
		ChatLineId = chatLineId;
	}

	public int getSenderId() {
		return SenderId;
	}

	public void setSenderId(int senderId) {
		SenderId = senderId;
	}

	public int getRecipientId() {
		return RecipientId;
	}

	public void setRecipientId(int recipientId) {
		RecipientId = recipientId;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Date getSentOn() {
		return SentOn;
	}

	public void setSentOn(Date sentOn) {
		SentOn = sentOn;
	}

	public Date getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(Date createdOn) {
		CreatedOn = createdOn;
	}

	

	public ChatLine(){
	
	}

	public ChatLine(int senderId, int recipientId, String message) {
		super();
		this.SenderId = senderId;
		this.RecipientId = recipientId;
		this.Message = message;
	}
	
	
	
	
	

}
