package com.springboot.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.Entity.User;
import com.springboot.Repository.UserRepository;
@Service
public class CustomeUserService implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = repository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User with email "+username+" not found");
		}else {
			return new CustomeUser(user);
		}
		
	}

}
