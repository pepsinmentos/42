package com.fortwo.client.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fortwo.client.model.ChatLine;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vivi on 2016/04/03.
 */
public class SQLLiteChatRepository extends ChatRepository {

    private static final String SQL_TABLE_NAME = "tblChatLine";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + SQL_TABLE_NAME + " (ID INTEGER PRIMARY KEY, " + COLUMN_NAME_CHAT_LINE_ID + " VARCHAR(20), "
            + COLUMN_NAME_SENDER_ID + " INTEGER, " + COLUMN_NAME_RECIPIENT_ID + " INTEGER, " + COLUMN_NAME_MESSAGE + " VARCHAR(max), "
            + COLUMN_NAME_STATUS + " INTEGER, "
            + COLUMN_NAME_SENT_ON + " DATETIME, "
            + COLUMN_NAME_CREATED_ON + " DATETIME)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ForTwo.db";
    protected SQLiteOpenHelper dbHelper;
    protected Context context;

    protected String formatDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public SQLLiteChatRepository(Context context)
    {
        super();
        this.context = context;

        dbHelper = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_TABLE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            }
        };
    }

    @Override
    public ChatLine saveChatLine(ChatLine chatLine) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_CHAT_LINE_ID, chatLine.getChatLineId());
        values.put(COLUMN_NAME_SENDER_ID, chatLine.getSenderId());
        values.put(COLUMN_NAME_RECIPIENT_ID, chatLine.getRecipientId());
        values.put(COLUMN_NAME_MESSAGE, chatLine.getMessage());
        values.put(COLUMN_NAME_STATUS, chatLine.getStatus());
        values.put(COLUMN_NAME_SENT_ON, formatDate(chatLine.getSentOn()));
        values.put(COLUMN_NAME_CREATED_ON, formatDate(chatLine.getCreatedOn()));

        db.insert(SQL_TABLE_NAME, null, values);
        return chatLine;
    }

    @Override
    public List<ChatLine> getChatLinesPaged(int pageSize, int pageIndex) {
        String[] projection =  {

        };

        return null;
    }
}
