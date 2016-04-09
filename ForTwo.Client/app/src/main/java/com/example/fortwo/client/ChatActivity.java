
package com.example.fortwo.client;

import java.util.List;
import java.util.Random;

import com.example.fortwo.client.constants.KeyValueConstants;
import com.example.fortwo.client.constants.LoggingConstants;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.interfaces.IUserService;
import com.fortwo.client.services.ChatService;
import com.fortwo.client.services.NotificationService;
import com.fortwo.client.services.UserService;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends Activity implements ChatReceivedListener {

    private ChatService chatService;
    private IUserService userService;
    private NotificationService notificationService;
    private int lastChatId = 0;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LoggingConstants.LOGGING_TAG, "OnStart called");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                chatService.getUnreadChatLines();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                loadChatLines();
            }
        }.execute();


        new Runnable() {
            @Override
            public void run() {
                chatService.addChatReceivedListener(ChatActivity.this);
                chatService.startListenForChats();
            }
        }.run();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LoggingConstants.LOGGING_TAG, "OnStop called");
        chatService.stopListenForChats();
        chatService.addChatReceivedListener(notificationService);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LoggingConstants.LOGGING_TAG, "OnResume called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LoggingConstants.LOGGING_TAG, "OnPause called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LoggingConstants.LOGGING_TAG, "OnDestroy called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(LoggingConstants.LOGGING_TAG, "OnCreate called");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        userService = new UserService(this);
        chatService = new ChatService(ChatActivity.this, userService);
        notificationService = new NotificationService(this);
        setContentView(R.layout.activity_chat);

        setChatButtonHandler();
        setChatTextInput();
    }

    private void loadChatLines(){
        new AsyncTask<Void, Void, List<ChatLine>>() {
            @Override
            protected void onPostExecute(List<ChatLine> chatHistory) {
                super.onPostExecute(chatHistory);
                for (int i = chatHistory.size() - 1; i >= 0; i--)
                {
                    ChatLine c = chatHistory.get(i);
                    addChatLine(c.getID() + ": " + c.getMessage());
                    lastChatId = c.getID();
                }
            }

            @Override
            protected List<ChatLine> doInBackground(Void... voids) {
                return chatService.getAllChats(lastChatId);
            }
        }.execute();
    }

    private void setChatButtonHandler() {
        ((Button) findViewById(R.id.chat_submit_button)).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                EditText chatTextInput = (EditText) findViewById(R.id.chat_text_input);
                String chatMessage = chatTextInput.getText().toString();

                chatService.sendChat(new ChatLine(userService.getSenderId(), userService.getRecipientId(), chatMessage));

                loadChatLines();
               // addChatLine(chatMessage);

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

            ScrollView scrollView = (ScrollView) findViewById(R.id.chat_view);
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

            ((LinearLayout) linearLayout).addView(valueTV);

            scrollView.fullScroll(ScrollView.FOCUS_DOWN);

        } catch (Exception e) {
            Log.e("PR", e.toString());
        }
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

       loadChatLines();

    }
}
