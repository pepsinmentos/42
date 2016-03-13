package com.example.fortwo.client;

import java.util.Random;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		
		addGetChatHandler();
		return true;
	}
	
	private void addGetChatHandler(){
	
		Button btn = (Button)findViewById(R.id.get_chat_button);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
					Toast.makeText(ChatActivity.this, "Getting chats from server", Toast.LENGTH_LONG).show();
					View linearLayout = findViewById(R.id.chat_view);
					
					TextView valueTV = new TextView(ChatActivity.this);
					valueTV.setText("Hallo hallow");
					Random rand = new Random();
					valueTV.setId(rand.nextInt());
					valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					
					((LinearLayout)linearLayout).addView(valueTV);
				
			}
		});
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
