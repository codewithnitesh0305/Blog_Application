package com.springboot.Service;

import com.springboot.Entity.User;

public interface UserService {

	public User saveUser(User user, String url);
	public boolean existsByEmail(String email);
	public void sendEmail(User user, String url);
	public boolean verfiyAccount(String verfiyCode);
}
