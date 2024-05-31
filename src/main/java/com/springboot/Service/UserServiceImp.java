package com.springboot.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.springboot.Entity.User;
import com.springboot.Repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserRepository repository;
	
	//Save User Data
	@Override
	public User saveUser(User user, String url) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		
		user.setEnable(false);
		user.setVerficationCode(UUID.randomUUID().toString());
		User saveUser = repository.save(user);
		if(user != null) {
			sendEmail(saveUser , url);
		}
		return saveUser;
	}
	
	//Find Email already exist or not
	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		boolean f = repository.existsByEmail(email);
		return f;
	}
	
	//Remove the session message
	public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
	}
	
	//Send mail for verification 
	@Override
	public void sendEmail(User user, String url) {
		// TODO Auto-generated method stub
		String form = "nk685602@gmail.com";
		String to = user.getEmail();
		String Subject = "Account Verfication";
		String content = "Dear [[name]],<br>"+"Please click the link below to verify your account: <br>"
						+"<h3><a href=\"[[URL]]\" target=\"_self\" >VERIFY</h3><br> "+"Thank Your";
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			System.out.println();
			helper.setFrom(form, "MyBlog");
			helper.setTo(to);
			helper.setSubject(Subject);
			
			content = content.replace("[[name]]", user.getName());
			String siteUrl = url + "/verify?code="+user.getVerficationCode();
			content = content.replace("[[URL]]", siteUrl);
			helper.setText(content, true);
			mailSender.send(message);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Check User is verified or not
	@Override
	public boolean verfiyAccount(String verfiyCode) {
		// TODO Auto-generated method stub
		User user = repository.findByVerficationCode(verfiyCode);
		if(user == null) {
			return false;
		}else {
			user.setEnable(true);
			user.setVerficationCode(null);
			repository.save(user);
			return true;
		}
	}
	
	

}
