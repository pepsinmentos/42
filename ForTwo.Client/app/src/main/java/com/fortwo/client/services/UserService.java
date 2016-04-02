package com.fortwo.client.services;

import com.example.fortwo.client.model.User;

public class UserService implements IUserService{

	@Override
	public User getUserByEmail(String email) {
		
		return new User();
		
	}

}
