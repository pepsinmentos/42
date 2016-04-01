package com.fortwo.client.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.os.AsyncTask;
import android.util.Log;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler;

public class ChatService {
	HubProxy hub;
	ChatReceivedListener chatReceivedListener;
	private final int senderId;

	public ChatService(final int senderId) {
		this.senderId = senderId;
		Platform.loadPlatformComponent(new AndroidPlatformComponent());

		String host = "http://fourtwo.co.za/signalr";
		HubConnection connection = new HubConnection(host);
		Log.d("PR", "Go connection");
		try {
			connection.setCredentials(new Credentials() {

				@Override
				public void prepareRequest(Request request) {
					request.addHeader("UserId", Integer.toString(senderId));
				}
			});
		} catch (Exception ex) {
			Log.e(LoggingConstants.LOGGING_TAG, ex.toString());

		}
		hub = connection.createHubProxy("ChatHub");
		Log.d("PR", "Go hub");
		SignalRFuture<Void> awaitConnection = connection.start();
		Log.d("PR", "Go connection start");
		try {
			awaitConnection.get();
			Log.d("PR", "Go connection get");
		} catch (Exception ex) {
			Log.e("PR", ex.toString());
			for (StackTraceElement st : ex.getStackTrace()) {
				Log.e("PR", st.toString());
			}
		}

		hub.subscribe(ChatService.this);

	}

	public ChatLine getChat() {
		/*
		 * URL url; try { url = new
		 * URL("http://10.0.2.2:8080/server/webapi/chat"); HttpURLConnection con
		 * = (HttpURLConnection)url.openConnection();
		 * con.setRequestMethod("GET"); con.setRequestProperty("Accept",
		 * "application/json");
		 * 
		 * BufferedReader reader = new BufferedReader(new
		 * InputStreamReader(con.getInputStream())); StringBuilder builder = new
		 * StringBuilder(); String line; while((line = reader.readLine()) !=
		 * null) { builder.append(line);
		 * 
		 * }
		 * 
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * Chat chat = mapper.readValue(builder.toString(), Chat.class);
		 * 
		 * return chat;
		 * 
		 * } catch (Exception e) { // TODO Auto-generated catch block
		 * Log.e("PR", e.toString()); }
		 * 
		 * 
		 */
		return new ChatLine();
	}

	public ChatLine getUnreadChatLines(){
		
		try {
			ChatLine s = hub.invoke(ChatLine.class, "GetUnreadMessages", null).get();
			return s;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ChatLine();
	}
	public void sendChat(ChatLine chat) {
		Log.d("PR", "Sending chat now");
		hub.invoke("Send", chat);
	}

	public void addMessage(String m) {
		Log.d("PR", "I GOT THE FUCKING MESSAGE: " + m);
		chatReceivedListener.ChatReceived(new ChatLine(senderId, 1, m));
	}

	public void addChatReceivedListener(ChatReceivedListener listener) {
		chatReceivedListener = listener;
	}
}
