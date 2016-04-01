
package com.example.fortwo.client;

import java.util.Random;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.services.ChatService;

import android.graphics.Typeface;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements ChatReceivedListener {

	private ChatService chatService;
	private int userId;
	private int partnerUserId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);

		SharedPreferences prefs = getSharedPreferences(KeyValueConstants.SharedPreferencesName, MODE_PRIVATE);
		userId = prefs.getInt(KeyValueConstants.UserIdKey, 0);
		Log.d(LoggingConstants.LOGGING_TAG, "Got userId from shared prefs: " + userId);
		partnerUserId = prefs.getInt(KeyValueConstants.PartnerUserIdKey, 0);
		Log.d(LoggingConstants.LOGGING_TAG, "Got partner userId from shared prefs: " + partnerUserId);
		
		AsyncTask<Void, Void, Void> v = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				chatService = new ChatService(userId);
				//getInitialChats();
				chatService.addChatReceivedListener(ChatActivity.this);
				return null;
			}
			
		};
		
		v.execute();
		setChatButtonHandler();
		setChatTextInput();
		

	
		
	}

	private void setChatButtonHandler() {
		((Button) findViewById(R.id.chat_submit_button)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EditText chatTextInput = (EditText) findViewById(R.id.chat_text_input);
				String chatMessage = chatTextInput.getText().toString();

				chatService.sendChat(new ChatLine(userId, partnerUserId, chatMessage));

				addChatLine(chatMessage);

				chatTextInput.getText().clear();
			}
		});

	}

	private void setChatTextInput() {
		EditText textInput = (EditText) findViewById(R.id.chat_text_input);
		Typeface tf = Typeface.createFromAsset(getAssets(), "DaoType.ttf");
		textInput.setTypeface(tf);

	}

	private void addChatLine(String chatMessage) {
		try {

			View linearLayout = findViewById(R.id.chat_container);
			Typeface tf = Typeface.createFromAsset(getAssets(), "DaoType.ttf");

			TextView valueTV = new TextView(ChatActivity.this);
			// valueTV.setBackgroundResource(R.drawable.chat_text);
			valueTV.setTextAppearance(this, R.style.chat_text_style);
			valueTV.setPadding(60, 10, 10, 10);
			valueTV.setTypeface(tf);
			valueTV.setTextSize(20);

			valueTV.setText(chatMessage);

			Random rand = new Random();
			valueTV.setId(rand.nextInt());
			// LayoutParams layoutParams = new
			// LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			// layoutParams.setMargins(12, 12, 12, 12);
			// valueTV.setLayoutParams(layoutParams);

			// Log.d(LoggingConstants.LOGGING_TAG,
			// Integer.toString(((LayoutParams)valueTV.getLayoutParams()).leftMargin));

			((LinearLayout) linearLayout).addView(valueTV);

		} catch (Exception e) {
			Log.e("PR", e.toString());
		}
	}

	private void getInitialChats() {

		AsyncTask<Void, Void, ChatLine> getChatTask = new AsyncTask<Void, Void, ChatLine>() {

			@Override
			protected ChatLine doInBackground(Void... params) {
				Toast.makeText(ChatActivity.this, "Getting chats from server", Toast.LENGTH_LONG).show();
				return chatService.getChat();
			}

			@Override
			protected void onPostExecute(ChatLine result) {
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

	@Override
	public void ChatReceived(final ChatLine chatLine) {
		// TODO Auto-generated method stub
		if (chatLine.getMessage() == null)
			chatLine.setMessage("NULL");

		this.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				addChatLine(chatLine.getMessage());

			}
		});

	}
}
