package com.fortwo.client.services;

import com.example.fortwo.client.model.User;
import com.fortwo.client.interfaces.IUserService;

public class UserService implements IUserService {

	@Override
	public User getUserByEmail(String email) {
		
		return new User();
		
	}

}
