package com.fortwo.client.services;

import com.example.fortwo.client.constants.LoggingConstants;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GetChatService extends WakefulRepeatableService {

	ChatService chatService;
	
	public GetChatService() {		
		super("GetChatService");
		
		Log.d(LoggingConstants.LOGGING_TAG, "GetChatService constructor called");
	}
	
	


	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(LoggingConstants.LOGGING_TAG, "I got as far as the intent");
		super.onHandleIntent(intent);
	}




	@Override
	protected void executeBackgroundService(Intent intent) throws Exception {
		
		Log.d(LoggingConstants.LOGGING_TAG, "I AM GETTING MESSAGES");
	}
	
	@Override
	protected long getRepeatingInterval(){
		return INTERVAL_ONE_MINUTE;
	}
	
	protected Intent getServiceIntent(Context context){
		return new Intent(context, GetChatService.class);
	}
}
