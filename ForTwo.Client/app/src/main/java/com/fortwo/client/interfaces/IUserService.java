package com.fortwo.client.interfaces;

import com.example.fortwo.client.model.User;

public interface IUserService {
	User getUserByEmail(String email);
}
