package com.fortwo.client.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.model.ChatLine;
import com.example.fortwo.client.model.User;
import com.fortwo.client.interfaces.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class UserService implements IUserService {

	Context context;
	protected int senderId;
	protected int recipientId;
	private final String serverHostName = "http://fourtwo.co.za/";

	public UserService (Context context){
		this.context = context;
	}
	@Override
	public User getUserByEmail(String email) {

		/*
		User user = null;

		HttpURLConnection connection = null;
		try {
			String host = serverHostName + "api/user/" + email;
			URL url = new URL(host);
			connection = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputline;
			StringBuffer response = new StringBuffer();
			while((inputline = in.readLine()) != null)
			{
				response.append(inputline);
			}
			in.close();

			System.out.println(response);
			Gson g =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
			user  =  g.fromJson(response.toString(), User.class);
			if(user != null) {

			}
		}
		catch(Exception ex) {
			System.out.println(ex.toString());

		}
		finally{
			if(connection != null) {
				connection.disconnect();
			}
		}

		return user;
		*/

		if(Objects.equals(email, "pieter.roodt@gmail.com"))
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Returning Pieter");
			return new User("Pieter Roodt", email, 1);
		}

		if(Objects.equals(email, "test@gmail.com"))
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Returning Tester p");
			return new User("Tester McTest", email, 3);
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
		if(senderId == 0) {
			SharedPreferences prefs = context.getSharedPreferences(KeyValueConstants.SharedPreferencesName, context.MODE_PRIVATE);
			senderId = prefs.getInt(KeyValueConstants.UserIdKey, 0);
		}
		return  senderId;
	}

	@Override
	public int getRecipientId() {
		if(recipientId == 0) {
			SharedPreferences prefs = context.getSharedPreferences(KeyValueConstants.SharedPreferencesName, context.MODE_PRIVATE);
			recipientId = prefs.getInt(KeyValueConstants.PartnerUserIdKey, 0);
		}
		return recipientId;
	}

	@Override
	public int setSenderId(int senderId) {
		SharedPreferences prefs = context.getSharedPreferences(KeyValueConstants.SharedPreferencesName, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KeyValueConstants.UserIdKey, senderId);
		editor.commit();
		return getSenderId();
	}

	@Override
	public int setRecipientId(int recipientId) {
		SharedPreferences prefs = context.getSharedPreferences(KeyValueConstants.SharedPreferencesName, context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(KeyValueConstants.PartnerUserIdKey, recipientId);
		editor.commit();
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
