package com.fortwo.client.services;

import com.example.fortwo.client.model.Chat;

public class ChatServiceStub implements IChatService{
	
	@Override
	public Chat getChat() {
		// TODO Auto-generated method stub
		return stubChat();
	}

	@Override
	public Chat sendChat(Chat chat) {
		return chat;
	}
	
	
	private Chat stubChat()
	{
		return new Chat("Stub message", 1,1);
	}

}
