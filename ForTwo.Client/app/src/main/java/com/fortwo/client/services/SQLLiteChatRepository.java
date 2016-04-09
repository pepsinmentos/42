package com.fortwo.client.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fortwo.client.model.ChatLine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vivi on 2016/04/03.
 */
public class SQLLiteChatRepository extends ChatRepository {

    private static final String SQL_TABLE_NAME = "tblChatLine";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + SQL_TABLE_NAME + " (ID INTEGER PRIMARY KEY, " + COLUMN_NAME_CHAT_LINE_ID + " TEXT, "
            + COLUMN_NAME_SENDER_ID + " INTEGER, " + COLUMN_NAME_RECIPIENT_ID + " INTEGER, " + COLUMN_NAME_MESSAGE + " TEXT, "
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
       // values.put(COLUMN_NAME_SENT_ON, formatDate(chatLine.getSentOn()));
        //values.put(COLUMN_NAME_CREATED_ON, formatDate(chatLine.getCreatedOn()));

        db.insert(SQL_TABLE_NAME, null, values);
        return chatLine;
    }

    @Override
    public List<ChatLine> getChatLines(int lastChatId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ChatLine> chatLines = new ArrayList<ChatLine>();

        String[] projection =  {
                COLUMN_NAME_CHAT_LINE_ID,
                COLUMN_NAME_SENDER_ID,
                COLUMN_NAME_RECIPIENT_ID,
                COLUMN_NAME_MESSAGE,
                COLUMN_NAME_STATUS,
                COLUMN_NAME_SENT_ON,
                COLUMN_NAME_CREATED_ON
        };

        String sortOrder = "ID ASC";


        String where = "";
      /*  int lastId = getLastId();
        if(lastId > 100)
        {
            where = "ID > " + (lastId - 100);
        }
*/
        Cursor c = db.rawQuery("SELECT * FROM " + SQL_TABLE_NAME + " WHERE ID > " + lastChatId + " ORDER BY ID DESC LIMIT 50", null);
        //Cursor c = db.query(SQL_TABLE_NAME, projection, where, null, "", "",  sortOrder);
       // c.moveToFirst();
        while(c.moveToNext())
        {
            ChatLine chat = new ChatLine();
            chat.setChatLineId(c.getString(c.getColumnIndex(COLUMN_NAME_CHAT_LINE_ID)));
            chat.setSenderId(c.getInt(c.getColumnIndex(COLUMN_NAME_SENDER_ID)));
            chat.setRecipientId(c.getInt(c.getColumnIndex(COLUMN_NAME_RECIPIENT_ID)));
            chat.setMessage(c.getString(c.getColumnIndex(COLUMN_NAME_MESSAGE)));
            chat.setStatus(c.getInt(c.getColumnIndex(COLUMN_NAME_STATUS)));
            chat.setID(c.getInt(c.getColumnIndex(COLUMN_NAME_ID)));

            //chat.setSentOn(new Date(c.getString(c.getColumnIndex(COLUMN_NAME_SENT_ON))));
            //chat.setCreatedOn(new Date(c.getString(c.getColumnIndex(COLUMN_NAME_CREATED_ON))));
            chatLines.add(chat);
        }

        c.close();
        return chatLines;
    }

    private int getLastId(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(ID) as ID FROM " + SQL_TABLE_NAME,new String[] {"ID"}  );
        c.moveToFirst();

        c.close();
        return c.getInt(c.getColumnIndex("ID"));
    }
}
