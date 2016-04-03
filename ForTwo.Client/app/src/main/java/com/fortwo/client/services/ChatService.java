package com.fortwo.client.services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
	//private final String serverHostName = "http://fourtwo.co.za/";
	private final String serverHostName = "http://localhost:8080/";

	public ChatService(final int senderId) {
		this.senderId = senderId;
		//Platform.loadPlatformComponent(new AndroidPlatformComponent());

		ConnectToHub();

	}

	private void ConnectToHub(){
		String host = serverHostName +  "signalr";
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

	public List<ChatLine> getUnreadChatLines(){
        HttpURLConnection connection = null;
        try {
			String host = serverHostName + "api/chat?userId=1";
            URL url = new URL(serverHostName);
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
            ChatLine[] chats =  g.fromJson(response.toString(), ChatLine[].class);
            return Arrays.asList(chats);
        }
        catch(Exception ex) {
            System.out.println(ex.toString());

        }
        finally{
            if(connection != null) {
                connection.disconnect();
            }
        }
        return new ArrayList<ChatLine>();
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
