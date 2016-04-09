package com.fortwo.client.interfaces;

import com.example.fortwo.client.model.ChatLine;

import java.util.List;

/**
 * Created by Vivi on 2016/04/03.
 */
public interface IChatRepository {
    ChatLine saveChatLine(ChatLine chatLine);
    List<ChatLine> getChatLines(int lastChatId);
}
