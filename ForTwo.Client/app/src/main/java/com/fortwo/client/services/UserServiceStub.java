package com.fortwo.client.services;

import java.util.Objects;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.model.User;

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

}
