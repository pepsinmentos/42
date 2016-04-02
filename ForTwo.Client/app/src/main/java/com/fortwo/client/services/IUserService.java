package com.fortwo.client.services;

import com.example.fortwo.client.model.User;

public interface IUserService {
	User getUserByEmail(String email);
}
