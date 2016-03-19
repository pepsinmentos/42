package com.example.fortwo.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		addLoginListener();   
	}

	private void addLoginListener(){
		Button loginButton = (Button)findViewById(R.id.email_sign_in_button);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {				
				Intent i = new Intent(LoginActivity.this, BasicSetupActivity.class);
				startActivity(i);
			}
		});
	}
	
	
}
