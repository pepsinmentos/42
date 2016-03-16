package com.fortwo.client.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.parser.JSONParser;

import com.example.fortwo.client.model.Chat;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;

public class ChatService {

	public ChatService()
	{
		
	}
	
	public Chat getChat()
	{
		URL url;
		try {
			url = new URL("http://10.0.2.2:8080/server/webapi/chat");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
			{
				builder.append(line);
				
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			Chat chat = mapper.readValue(builder.toString(), Chat.class);
			
			return chat;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("PR", e.toString());
		}
		
		
		
		return new Chat();
	}
	
	public void sendChat(Chat chat){
		
	}
	
	
}
