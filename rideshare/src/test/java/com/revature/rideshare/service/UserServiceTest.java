package com.revature.rideshare.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.dao.UserRepository;
import com.revature.rideshare.domain.User;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService = new UserServiceImpl();

	//TODO: Determine if getAll() for users should be inside User or Admin service, then either re-implement or remove test.
	
	/*
	@Test
	public void testGetAll() {
		int mockUserListSize = 5;
		
		List<User> mockUserList = new ArrayList<>();
		for (int i = 0; i < mockUserListSize; i++) {
			mockUserList.add(new User());
		}
		
		when(userRepository.findAll()).thenReturn(mockUserList);
		
		List<User> carList = userService.getAll();
		
		verify(userRepository, atLeastOnce()).findAll();
		
		assertNotNull(carList);
		assertTrue(carList.size() == mockUserListSize);
	}
*/	
	@Test
	public void testAddUser() {
		User mockUser = new User();
		
		userService.addUser(mockUser);
		
		verify(userRepository, atLeastOnce()).saveAndFlush(Matchers.same(mockUser));
	}
	
	@Test
	public void testGetUser() {
		Random rng = new Random();
		
		for (int i = 0; i < 3; i++) {
			long mockId = rng.nextLong();
			User mockUser = new User();
			
			when(userRepository.getOne(Matchers.eq(mockId))).thenReturn(mockUser);
			
			User user = userService.getUser(mockId);
			
			verify(userRepository, atLeastOnce()).getOne(Matchers.eq(mockId));
			
			assertTrue(user == mockUser);
		}
	}
	
	@Test
	public void testGetUserBySlackId() {
		Random rng = new Random();
		
		for (int i = 0; i < 3; i++) {
			String mockId = String.valueOf(rng.nextInt());
			User mockUser = new User();
			
			when(userRepository.findBySlackId(Matchers.same(mockId))).thenReturn(mockUser);
			
			User user = userService.getUserBySlackId(mockId);
			
			verify(userRepository, atLeastOnce()).findBySlackId(Matchers.same(mockId));
			
			assertTrue(user == mockUser);
		}
	}
	
	@Test
	public void testRemoveUser() {
		User mockUser = new User();
		
		userService.removeUser(mockUser);
		
		verify(userRepository, atLeastOnce()).delete(Matchers.same(mockUser));
	}
	
	@Test
	public void testUpdateUser() {
		User mockUser = new User();
		
		userService.updateUser(mockUser);
		
		verify(userRepository, atLeastOnce()).saveAndFlush(Matchers.same(mockUser));
	}
	
	@Test
	public void testCreateUser() {
		UserDetails mockUser = new User();
		
		userService.createUser(mockUser);
		
		verify(userRepository, atLeastOnce()).saveAndFlush(Matchers.same((User) mockUser));
	}
	
	@Test
	public void testDeleteUser() {
		Random rng = new Random();
		
		for (int i = 0; i < 3; i++) {
			String mockId = String.valueOf(rng.nextInt());
			User mockUser = new User();
			
			when(userRepository.findBySlackId(Matchers.same(mockId))).thenReturn(mockUser);
			
			userService.deleteUser(mockId);
			
			verify(userRepository, atLeastOnce()).findBySlackId(Matchers.same(mockId));
			verify(userRepository, atLeastOnce()).delete(Matchers.same(mockUser));
		}
	}
	
	@Test
	public void testUpdateUser2() {
		UserDetails mockUser = new User();
		
		userService.updateUser(mockUser);
		
		verify(userRepository, atLeastOnce()).saveAndFlush(Matchers.same((User) mockUser));
	}
	
	@Test
	public void testUserExistsNotNull() {
		testUserExists(new User(), true);
	}
	
	@Test
	public void testUserExistsNull() {
		testUserExists(null, false);
	}
	
	@Test
	public void testLoadUserByUsername() {
		Random rng = new Random();
		
		for (int i = 0; i < 3; i++) {
			String mockId = String.valueOf(rng.nextInt());
			User mockUser = new User();
			
			when(userRepository.findBySlackId(Matchers.same(mockId))).thenReturn(mockUser);
			
			User user = (User) userService.loadUserByUsername(mockId);
			
			verify(userRepository, atLeastOnce()).findBySlackId(Matchers.same(mockId));
			
			assertTrue(user == mockUser);
		}
	}
	
	private void testUserExists(User mockUser, boolean validity) {
		Random rng = new Random();
		
		for (int i = 0; i < 3; i++) {
			String mockId = String.valueOf(rng.nextInt());
			
			when(userRepository.findBySlackId(Matchers.same(mockId))).thenReturn(mockUser);
			
			boolean valid = userService.userExists(mockId);
			
			if (validity) {
				assertTrue(valid);
			} else {
				assertFalse(valid);
			}
		}
	}
	
}
