package com.example.fortwo.client;

import java.util.Random;

import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.model.Chat;
import com.fortwo.client.services.ChatService;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity {

	private ChatService chatService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		chatService = new ChatService();
		//getInitialChats();
		
		setChatButtonHandler();
		
	}
	
	private void setChatButtonHandler(){
		((Button)findViewById(R.id.chat_submit_button)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText chatTextInput = (EditText)findViewById(R.id.chat_text_input);
				String chatMessage = chatTextInput.getText().toString();
				
				chatService.sendChat(new Chat(chatMessage, 1, 1));
				
				addChatLine(chatMessage);				
				
				chatTextInput.getText().clear();
			}
		});
		
	}
	
	
	private void addChatLine(String chatMessage){
		try {

			View linearLayout = findViewById(R.id.chat_view);

			TextView valueTV = new TextView(ChatActivity.this);
			valueTV.setBackgroundResource(R.drawable.chat_text);
			valueTV.setPadding(10, 10, 10, 10);
	
			
			valueTV.setText(chatMessage);
			
			Random rand = new Random();
			valueTV.setId(rand.nextInt());			
			LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams.setMargins(12, 12, 12, 12);
			valueTV.setLayoutParams(layoutParams);
			
			Log.d(LoggingConstants.LOGGING_TAG, Integer.toString(((LayoutParams)valueTV.getLayoutParams()).leftMargin)); 
			
			((LinearLayout) linearLayout).addView(valueTV);
			
		} catch (Exception e) {
			Log.e("PR", e.toString());
		}
	}
	
	private void getInitialChats(){

		AsyncTask<Void, Void, Chat> getChatTask = new AsyncTask<Void, Void, Chat>() {

			@Override
			protected Chat doInBackground(Void... params) {
				Toast.makeText(ChatActivity.this, "Getting chats from server", Toast.LENGTH_LONG).show();
				return chatService.getChat();
			}

			@Override
			protected void onPostExecute(Chat result) {
				Log.d(LoggingConstants.LOGGING_TAG, "got chat successfully");
				addChatLine(result.getMessage());

			}

		};
		
		getChatTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
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
