package com.fortwo.client.services;

import java.util.Objects;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.model.User;
import com.fortwo.client.interfaces.IUserService;

import android.util.Log;

public class UserServiceStub implements IUserService {

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		Log.d(LoggingConstants.LOGGING_TAG, "Looking up email: " + email);
		if(Objects.equals(email, "pieter.roodt@gmail.com"))	
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Returning Pieter");
			return new User("Pieter Roodt", email, 1);
		}
		
		if(Objects.equals(email, "ldaoyi@gmail.com"))
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Returning Daoyi");
			return new User("Daoyi Liu", email, 2);
		}
		
		Log.d(LoggingConstants.LOGGING_TAG, "Returning No one");
		return new User();
	}

	@Override
	public int getSenderId() {
		return 2;
	}

	@Override
	public int getRecipientId() {
		return 1;
	}

	@Override
	public int setSenderId(int senderId) {
		return getSenderId();
	}

	@Override
	public int setRecipientId(int recipientId) {
		return getRecipientId();
	}

	@Override
	public String getUserName(int userId) {
		if(userId == 1)
			return "Pieter";
		if(userId == 2)
			return "Daoyi";

		return "Unknown";
	}

}
