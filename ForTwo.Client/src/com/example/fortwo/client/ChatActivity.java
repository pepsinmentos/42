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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);

		addGetChatHandler();
		return true;
	}

	private void addGetChatHandler() {

		Button btn = (Button) findViewById(R.id.get_chat_button);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(ChatActivity.this, "Getting chats from server", Toast.LENGTH_LONG).show();

				AsyncTask<Void, Void, Chat> getChatTask = new AsyncTask<Void, Void, Chat>() {

					@Override
					protected Chat doInBackground(Void... params) {
						return chatService.getChat();
					}

					@Override
					protected void onPostExecute(Chat result) {
						Log.d(LoggingConstants.LOGGING_TAG, "got chat successfully");
						try {

							View linearLayout = findViewById(R.id.chat_view);

							TextView valueTV = new TextView(ChatActivity.this);
							valueTV.setText(result.getMessage());
							Random rand = new Random();
							valueTV.setId(rand.nextInt());
							valueTV.setLayoutParams(
									new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

							((LinearLayout) linearLayout).addView(valueTV);
						} catch (Exception e) {
							Log.e("PR", e.toString());
						}

					}

				};
				
				getChatTask.execute();

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
