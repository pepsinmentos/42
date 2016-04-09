package com.fortwo.client.services;

import com.example.fortwo.client.ChatActivity;
import com.example.fortwo.client.ForTwoApplication;
import com.example.fortwo.client.R;
import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.model.ChatLine;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;

public class GetChatService extends WakefulRepeatableService {

	ChatService chatService;
	NotificationService notificationService;
	
	public GetChatService() {		
		super("GetChatService");
		chatService = new ChatService(ForTwoApplication.getInstance(), new UserService(ForTwoApplication.getInstance()));
		notificationService = ForTwoApplication.getNotificationService();
		Log.d(LoggingConstants.LOGGING_TAG, "GetChatService constructor called");
	}
	
	


	@Override
	protected void onHandleIntent(Intent intent) {
		super.onHandleIntent(intent);
	}




	@Override
	protected void executeBackgroundService(Intent intent) throws Exception {
		List<ChatLine> chatLines = chatService.getUnreadChatLines();
		Log.d(LoggingConstants.LOGGING_TAG, chatLines.toString() + " " + chatLines.size());
		if(chatLines != null && chatLines.size() > 0)
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Found chats. Building notification");
			try {
				notificationService.ChatsReceived(chatLines);
			}catch (Exception ex) {
				Log.e(LoggingConstants.LOGGING_TAG, ex.toString());
				for(StackTraceElement ste: ex.getStackTrace())
				Log.e(LoggingConstants.LOGGING_TAG, ste.toString());
			}
		}
	}
	
	@Override
	protected long getRepeatingInterval(){
		return 30000;
	}
	
	protected Intent getServiceIntent(Context context){
		return new Intent(context, GetChatService.class);
	}
}
