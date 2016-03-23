package com.fortwo.client.services;

import com.example.fortwo.client.model.Chat;

public interface IChatService {
	Chat getChat();
	Chat sendChat(Chat chat);

}
