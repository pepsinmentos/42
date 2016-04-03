package com.fortwo.client.services;

import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.interfaces.IChatRepository;

import java.util.List;

/**
 * Created by Vivi on 2016/04/03.
 */
public abstract class ChatRepository implements IChatRepository {

    protected static final String COLUMN_NAME_CHAT_LINE_ID = "chatLineId";

    protected static final String COLUMN_NAME_SENDER_ID = "senderId";
    protected static final String COLUMN_NAME_RECIPIENT_ID = "recipientId";
    protected static final String COLUMN_NAME_MESSAGE = "message";
    protected static final String COLUMN_NAME_STATUS = "status";
    protected static final String COLUMN_NAME_SENT_ON = "sentOn";
    protected static final String COLUMN_NAME_CREATED_ON = "createdOn";

    public ChatRepository()
    {

    }

    @Override
    public abstract ChatLine saveChatLine(ChatLine chatLine) ;

    @Override
    public abstract List<ChatLine> getChatLinesPaged(int pageSize, int pageIndex);

}
