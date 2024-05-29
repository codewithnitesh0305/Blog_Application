package com.springboot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.springboot.Entity.User;
import com.springboot.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		return repository.save(user);
	}
	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		boolean f = repository.existsByEmail(email);
		return f;
	}
	
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
	}

}
