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
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.interfaces.IChatRepository;
import com.fortwo.client.interfaces.IUserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
//import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler;

public class ChatService {
	HubProxy hub;
    HubConnection connection;
	ChatReceivedListener chatReceivedListener;
	private final String serverHostName = "http://fourtwo.co.za/";
	//private final String serverHostName = "http://10.0.2.2:8080/";
    //private final String serverHostName = "http://localhost:8080/";
	protected IChatRepository chatRepository;
    protected Context context;
    protected IUserService userService;


	public ChatService(Context context, IUserService userService) {

        this.context = context;
		//Platform.loadPlatformComponent(new AndroidPlatformComponent());
		chatRepository = new SQLLiteChatRepository(context);
        this.userService = userService;


	}

    public void startListenForChats() {
        connectToHub();
    }

    public void stopListenForChats(){
        disconnectFromHub();
    }

	private void connectToHub(){
		String host = serverHostName +  "signalr";

        connection = new HubConnection(host);
        Log.d("PR", "Go connection");
        try {
            connection.setCredentials(new Credentials() {

                @Override
                public void prepareRequest(Request request) {
                    request.addHeader("UserId", Integer.toString(userService.getSenderId()));
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

    private void disconnectFromHub(){
        if(hub != null) {
            hub = null;
        }
        if(connection != null) {
            connection.stop();
            connection = null;
        }

    }

	public List<ChatLine> getUnreadChatLines(){
        HttpURLConnection connection = null;
        try {
			String host = serverHostName + "api/chat/messages/" + userService.getSenderId();
            Log.d(LoggingConstants.LOGGING_TAG, host);
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
            ChatLine[] chats =  g.fromJson(response.toString(), ChatLine[].class);
            Log.d(LoggingConstants.LOGGING_TAG, chats.toString() + " " + chats.length);
            if(chats != null) {
                for(ChatLine c : chats){
                    chatRepository.saveChatLine(c);
                    acknowledge(c.getChatLineId());
                }
            }
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

	public void sendChat(ChatLine chatLine) {
        chatLine.setChatLineId(UUID.randomUUID().toString());
        chatRepository.saveChatLine(chatLine);
		hub.invoke("Send", chatLine);
	}

	public void addMessage(ChatLine chatLine) {
		chatRepository.saveChatLine(chatLine);
		chatReceivedListener.ChatReceived(chatLine);
        acknowledge(chatLine.getChatLineId());
	}

    public List<ChatLine> getAllChats(int lastChatId){
        try {
            return chatRepository.getChatLines(lastChatId);
        }
        catch(Exception ex) {
            Log.e(LoggingConstants.LOGGING_TAG, ex.toString());
            return new ArrayList<ChatLine>();
        }
    }

	public void addChatReceivedListener(ChatReceivedListener listener) {
		chatReceivedListener = listener;
	}

    public boolean acknowledge(String chatLineId)
    {
        HttpURLConnection connection = null;
        try {
            String host = serverHostName + "api/chat/acknowledge/" + chatLineId;
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

            boolean success =  new Gson().fromJson(response.toString(), boolean.class);

            return success;
        }
        catch(Exception ex) {
            System.out.println(ex.toString());

        }
        finally{
            if(connection != null) {
                connection.disconnect();
            }
        }
        return false;
    }

}
