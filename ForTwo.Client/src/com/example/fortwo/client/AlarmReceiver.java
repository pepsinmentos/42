package com.example.fortwo.client;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.BootListener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiver extends WakefulBroadcastReceiver {
	public static final String EXTRA_SERVICE = "extra-service";
	 
	@Override
	public void onReceive(Context context, Intent intent) {
		if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
			BootListener bootListener = getBootListener();
			if(bootListener != null){
				bootListener.onBootComplete(context, intent);
			}
			Log.e(LoggingConstants.LOGGING_TAG, "The boot listener was NULL!!!");
			return;
		}
		
		String className = intent.getExtras().getString(EXTRA_SERVICE);
		Log.d(LoggingConstants.LOGGING_TAG, "intent received to execute service: " + className);
		if(className == null){
			Log.e(LoggingConstants.LOGGING_TAG, "Service param not specified, its a NOP");
		}
		
		try{
			Class<?> serviceClass = Class.forName(className);
			startWakefulService(context, new Intent(context, serviceClass));
		}
		catch (ClassNotFoundException ex){
			Log.e(LoggingConstants.LOGGING_TAG, ex.toString());
		}
		
	}
	
	private BootListener getBootListener(){
		
		//return new ForTwoApplication();
		return ForTwoApplication.getInstance();
	}

}
