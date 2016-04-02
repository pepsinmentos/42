package com.fortwo.client.services;

import com.example.fortwo.client.AlarmReceiver;
import com.example.fortwo.client.constants.LoggingConstants;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public abstract class WakefulRepeatableService extends RepeatableService{
	public WakefulRepeatableService(String name){
		super(name);
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
		try{
			super.onHandleIntent(intent);
		}finally{
			AlarmReceiver.completeWakefulIntent(intent);
		}
	}
	
	protected void scheduleServiceImpl(Application context){
		Intent intent = getAlarmReceiverIntent(context);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager)context.getSystemService(ALARM_SERVICE);
		long interval = getRepeatingInterval();
		scheduleAlarm(am, pendingIntent, interval);
	}
	
	protected Intent getAlarmReceiverIntent(Context context){
		Intent intent = new Intent(context, AlarmReceiver.class);
		Intent serviceIntent = getServiceIntent(context);
		intent.setData(Uri.fromParts("execService", serviceIntent.getComponent().getClassName(), null));
		intent.putExtra(AlarmReceiver.EXTRA_SERVICE, serviceIntent.getComponent().getClassName());
		return intent;
	}
	
	protected boolean isScheduled(){
		Intent intent = getAlarmReceiverIntent(getApplicationContext());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), REQUEST_CODE, intent, PendingIntent.FLAG_NO_CREATE);
		return (pendingIntent != null);
	}
	
	protected void cancelSchedule(){
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), REQUEST_CODE, getAlarmReceiverIntent(getApplicationContext()), PendingIntent.FLAG_NO_CREATE);
		
		 if (pendingIntent != null) {
	            Log.d(LoggingConstants.LOGGING_TAG, "Cancelling last schedule for " + getServiceName());
	            AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
	            am.cancel(pendingIntent);
	        }
	}
}
