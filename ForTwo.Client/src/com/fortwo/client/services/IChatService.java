package com.fortwo.client.services;

import com.example.fortwo.client.model.ChatLine;

public interface IChatService {
	ChatLine getChat();
	ChatLine sendChat(ChatLine chat);

}
