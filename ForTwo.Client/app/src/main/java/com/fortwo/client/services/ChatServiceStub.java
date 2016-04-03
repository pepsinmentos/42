package com.fortwo.client.services;

import com.example.fortwo.client.model.ChatLine;
import com.fortwo.client.interfaces.IChatService;

public class ChatServiceStub implements IChatService {
	
	@Override
	public ChatLine getChat() {
		// TODO Auto-generated method stub
		return stubChat();
	}

	@Override
	public ChatLine sendChat(ChatLine chat) {
		return chat;
	}
	
	
	private ChatLine stubChat()
	{
		return new ChatLine(1,1, "Stub message");
	}

}
