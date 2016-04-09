package com.fortwo.client.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.fortwo.client.ChatActivity;
import com.example.fortwo.client.R;
import com.example.fortwo.client.listeners.ChatReceivedListener;
import com.example.fortwo.client.model.ChatLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vivi on 2016/04/08.
 */
public class NotificationService implements ChatReceivedListener {
    private List<ChatLine> chatLines = new ArrayList<ChatLine>();
    private int notificationId = 1;
    Context context;
    public NotificationService(Context context){
        this.context = context;
    }

    @Override
    public void ChatReceived(ChatLine chatLine) {
        chatLines.add(chatLine);
        DoNotification();
    }

    public void ChatsReceived(List<ChatLine> chats) {
        for(ChatLine c: chats)
        {
            chatLines.add(c);
        }

        DoNotification();

    }

    private void DoNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("ForTwo")
                .setContentText(chatLines.size() + " unread messages")
                .setNumber(chatLines.size())
                .setAutoCancel(true)
                .setLights(333333, 500, 5000)
                .setVibrate(new long[] { 500, 500, 500 });

        Intent resultIntent = new Intent(context, ChatActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotifyManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(notificationId, mBuilder.build());
    }
}
