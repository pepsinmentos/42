package com.example.fortwo.client;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.fortwo.client.interfaces.IUserService;
import com.fortwo.client.services.UserService;
import com.fortwo.client.services.UserServiceStub;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	protected IUserService userService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		userService = new UserService(this);
		setContentView(R.layout.activity_login);
		Log.d(LoggingConstants.LOGGING_TAG, "Yes I'm alive");
		addLoginListener();
		if(userService.getSenderId() != 0 && userService.getRecipientId() != 0)
		{
			Toast.makeText(this, "Logging in with userid: " + userService.getSenderId(), Toast.LENGTH_LONG).show();
			loadChatActivity();
		}
		else {

		}
	}

	private void addLoginListener(){
		Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
		Log.d(LoggingConstants.LOGGING_TAG, "Loading login listener");
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        // Lookup user id based on email
                        // Lookup partner's email based on Id
						Log.d(LoggingConstants.LOGGING_TAG, "Clicked!");
                        TextView emailTextView = (TextView) findViewById(R.id.email);
                        TextView partnerEmailTextView = (TextView) findViewById(R.id.email_partner);

                        String email = emailTextView.getText().toString();
                        String partnerEmail = partnerEmailTextView.getText().toString();

                        int userId = userService.getUserByEmail(email).getUserId();

                        int partnerUserId = userService.getUserByEmail(partnerEmail).getUserId();

                        userService.setSenderId(userId);
                        userService.setRecipientId(partnerUserId);

                        loadChatActivity();
                    }
                };

				r.run();
			}
		});
	}

	private void loadChatActivity(){
		Intent i = new Intent(LoginActivity.this, ChatActivity.class);
		startActivity(i);
	}
	
	
}
