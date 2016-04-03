package com.fortwo.client.interfaces;

import com.example.fortwo.client.model.ChatLine;

public interface IChatService {
	ChatLine getChat();
	ChatLine sendChat(ChatLine chat);

}
