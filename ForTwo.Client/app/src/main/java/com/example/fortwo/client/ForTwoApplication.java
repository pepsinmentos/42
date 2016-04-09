package com.example.fortwo.client;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.BootListener;
import com.fortwo.client.services.GetChatService;
import com.fortwo.client.services.NotificationService;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ForTwoApplication extends Application implements BootListener{

	private static ForTwoApplication instance;
	private static NotificationService notificationService;
	
	@Override
	public void onCreate(){
		super.onCreate();
		Log.d(LoggingConstants.LOGGING_TAG, "onCreated called for Application");
		instance = this;
		notificationService = new NotificationService(this);
	}
	
	public static ForTwoApplication getInstance() {	
		return instance;
	}
	public static NotificationService getNotificationService() { return notificationService; }
	@Override
	public void onBootComplete(Context context, Intent intent) {
		scheduleServices();
	}
	
	public void scheduleServices(){
		Log.d(LoggingConstants.LOGGING_TAG, "Scheduling services!");
		new GetChatService().scheduleService(this);
	}

}
