package com.example.fortwo.client;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.BootListener;
import com.fortwo.client.services.GetChatService;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ForTwoApplication extends Application implements BootListener{

	private static ForTwoApplication instance;
	
	@Override
	public void onCreate(){
		super.onCreate();
		instance = this;
	}
	
	public static ForTwoApplication getInstance() {	
		return instance;
	}
	@Override
	public void onBootComplete(Context context, Intent intent) {
		scheduleServices();
	}
	
	protected void scheduleServices(){
		Log.d(LoggingConstants.LOGGING_TAG, "Scheduling services!");
		new GetChatService().scheduleService(this);
	}

}
