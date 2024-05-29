package com.springboot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.Entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public boolean existsByEmail(String email);
	public User findByEmail(String email);
}
