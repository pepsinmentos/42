package com.fortwo.client.services;

import java.util.Date;

import com.example.fortwo.client.ForTwoApplication;
import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;

import android.app.AlarmManager;
import android.app.Application;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

public abstract class RepeatableService extends IntentService {
	protected static final int REQUEST_CODE = 101;
	
	private String serviceName;
	
	protected boolean reschedule;
	
	public boolean isReschedule(){
		return reschedule;
	}
	
	public static final long INTERVAL_ONE_MINUTE = 1000 * 60;
    public static final long INTERVAL_FIVE_MINUTE = 5 * INTERVAL_ONE_MINUTE;
    public static final long INTERVAL_FIFTEEN_MINUTES = 15 * 60 * 1000;
    public static final long INTERVAL_HALF_HOUR = 2*INTERVAL_FIFTEEN_MINUTES;
    public static final long INTERVAL_HOUR = 2*INTERVAL_HALF_HOUR;
    public static final long INTERVAL_HALF_DAY = 12*INTERVAL_HOUR;
    public static final long INTERVAL_DAY = 2*INTERVAL_HALF_DAY;
    public static final long INTERVAL_WEEK = 7*INTERVAL_DAY;
    
    public RepeatableService(String name){
    	super(name);
    	serviceName = name;
    }

	@Override
	protected void onHandleIntent(Intent intent) {
		try{
			Log.d(LoggingConstants.LOGGING_TAG, "Starting service: " + serviceName);
			recordExecutionTime();
			executeBackgroundService(intent);
			
			Log.d(LoggingConstants.LOGGING_TAG, "Service completed: " + serviceName);
		}
		catch(Exception e){
			Log.e(LoggingConstants.LOGGING_TAG, e.toString());
		}
		
		
	}
	
	 protected void recordExecutionTime() {
		 SharedPreferences prefs = getSharedPreferences(serviceName, MODE_PRIVATE);
	        prefs.edit().putLong(serviceName + "_last_run", System.currentTimeMillis()).commit();
	    }
	     
	    protected long getLastExecutionTime() {
	        return getSharedPreferences(KeyValueConstants.SharedPreferencesName, MODE_PRIVATE).getLong(serviceName + "_last_run", 0);
	    }
	
	abstract protected long getRepeatingInterval();
	 
    protected String getServiceName() {
        return serviceName;
    }
 
    protected abstract Intent getServiceIntent(Context context);
 
    protected abstract void executeBackgroundService(Intent intent) throws Exception;
 
    public void scheduleService(Application context, boolean forceReshedule) {
 
        if (isScheduled()) {
     
            if (forceReshedule || isReschedule()) {
                cancelSchedule();
            }
            else {
                Log.d(LoggingConstants.LOGGING_TAG, getServiceName() + " is already scheduled.");
                if (getLastExecutionTime() > 0 && System.currentTimeMillis() > getLastExecutionTime() + getRepeatingInterval() * 2) {
                    Log.e(LoggingConstants.LOGGING_TAG, "We somehow missed the scheduled execution, lets schedule now");
                }
                else {
                    return; //we are good
                }
            }
        }
     
        scheduleServiceImpl(context);
         
    }
 
    public void scheduleService(Application context) {
        scheduleService(context, false);
    }
 
    protected void scheduleServiceImpl(Application context) {
        Intent intent = getServiceIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        long interval = getRepeatingInterval();
        scheduleAlarm(am, pendingIntent, interval);
    }
 
    protected void scheduleAlarm(AlarmManager am, PendingIntent pendingIntent, long interval) {
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval,
                interval, pendingIntent);
     
        Log.d(LoggingConstants.LOGGING_TAG, "Scheduled " + getServiceName() + " to run at " + new Date(System.currentTimeMillis() + interval));
    }
 
    protected boolean isScheduled() {
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), REQUEST_CODE,
                getServiceIntent(getApplicationContext()), PendingIntent.FLAG_NO_CREATE);
        return (pendingIntent != null);
    }
 
    protected void cancelSchedule() {
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), REQUEST_CODE,
                getServiceIntent(getApplicationContext()), PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
        	Log.d(LoggingConstants.LOGGING_TAG, "Cancelling last schedule for " + getServiceName());
            AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
            am.cancel(pendingIntent);
        }
     
    }
 
    @Override
    public Context getApplicationContext() {
         
        try {
            return super.getApplicationContext();
        }
        catch (Exception e) {
            return ForTwoApplication.getInstance();
        }
    }
    
    
}
