package com.example.fortwo.client;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.fortwo.client.interfaces.IUserService;
import com.fortwo.client.services.UserServiceStub;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

	protected IUserService userService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userService = new UserServiceStub();
		setContentView(R.layout.activity_login);

		addLoginListener();   
	}

	private void addLoginListener(){
		Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				// Lookup user id based on email
				// Lookup partner's email based on Id   
				TextView emailTextView = (TextView)findViewById(R.id.email);
				TextView partnerEmailTextView = (TextView)findViewById(R.id.email_partner);
				
				String email = emailTextView.getText().toString();				
				String partnerEmail = partnerEmailTextView.getText().toString();
				
				int userId = userService.getUserByEmail(email).getUserId();
				Log.d(LoggingConstants.LOGGING_TAG, "UserId: " + userId + " Email: " + email);
				int partnerUserId = userService.getUserByEmail(partnerEmail).getUserId();
				Log.d(LoggingConstants.LOGGING_TAG, "PartnerUserId: " + partnerUserId + " Email: " + partnerEmail);
				
				SharedPreferences prefs = getSharedPreferences(KeyValueConstants.SharedPreferencesName, MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(KeyValueConstants.UserIdKey, userId);
				editor.putInt(KeyValueConstants.PartnerUserIdKey, partnerUserId);
				editor.commit();
				
				
				/*SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putBoolean(PASSWORD_SET, true);
				editor.commit();
				
				
				SharedPreferences prefs = getPreferences(MODE_PRIVATE);
				Boolean passpointsSet = prefs.getBoolean(PASSWORD_SET, false);
		
				*/
				
				Intent i = new Intent(LoginActivity.this, ChatActivity.class);
				startActivity(i);
			}
		});
	}
	
	
}
