package com.fortwo.client.services;

import com.example.fortwo.client.ChatActivity;
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
	
	public GetChatService() {		
		super("GetChatService");
		
		Log.d(LoggingConstants.LOGGING_TAG, "GetChatService constructor called");
	}
	
	


	@Override
	protected void onHandleIntent(Intent intent) {
		super.onHandleIntent(intent);
	}




	@Override
	protected void executeBackgroundService(Intent intent) throws Exception {
		List<ChatLine> chatLines = new ChatService(1).getUnreadChatLines();
		if(chatLines != null)
		{
			Log.d(LoggingConstants.LOGGING_TAG, "Found chats. Building notification");
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.icon)
					.setContentTitle("ForTwo")
					.setContentText("Unread messages");

			Intent resultIntent = new Intent(this, ChatActivity.class);

			PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			mBuilder.setContentIntent(resultPendingIntent);

			NotificationManager mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			mNotifyManager.notify(001, mBuilder.build());
		}
		Log.d(LoggingConstants.LOGGING_TAG, "I AM GETTING MESSAGES");
	}
	
	@Override
	protected long getRepeatingInterval(){
		return 15000;
	}
	
	protected Intent getServiceIntent(Context context){
		return new Intent(context, GetChatService.class);
	}
}
