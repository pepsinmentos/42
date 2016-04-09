package com.fortwo.client.interfaces;

import com.example.fortwo.client.model.User;

public interface IUserService {
	User getUserByEmail(String email);
	int getSenderId();
	int getRecipientId();
	int setSenderId(int senderId);
	int setRecipientId(int recipientId);
}
