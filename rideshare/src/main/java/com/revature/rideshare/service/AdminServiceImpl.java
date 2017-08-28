package com.revature.rideshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.dao.UserRepository;
import com.revature.rideshare.domain.User;

@Component("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public void toggleUserBanStatus(User user) {
		if(user.isBanned()){			//if banned, unban
			user.setBanned(false);
		}else{
			user.setBanned(true);		//else ban
		}
		userRepo.saveAndFlush(user);
	}
}
