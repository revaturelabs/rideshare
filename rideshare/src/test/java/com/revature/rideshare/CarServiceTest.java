package com.revature.rideshare;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.rideshare.dao.CarRepository;
import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.service.CarService;
import com.revature.rideshare.service.CarServiceImpl;

@RunWith(SpringRunner.class)
public class CarServiceTest {
	
	@Mock
	CarRepository carRepository;
	
	@InjectMocks
	CarService carService = new CarServiceImpl();
	
	@Test
	public void testGetAll() {
		int mockCarListSize = 5;
		
		List<Car> mockCarList = new ArrayList<>();
		for (int i = 0; i < mockCarListSize; i++) {
			mockCarList.add(new Car());
		}
		
		when(carRepository.findAll()).thenReturn(mockCarList);
		
		List<Car> carList = carService.getAll();
		
		verify(carRepository, atLeastOnce()).findAll();
		
		assertNotNull(carList);
		assertTrue(carList.size() == mockCarListSize);
	}
	
	@Test
	public void testGetCarForUser() {
		int mockUserId = 42;
		
		User mockUser = new User();
		mockUser.setUserId(mockUserId);
		
		Car mockCar = new Car();
		mockCar.setUser(mockUser);
		
		when(carRepository.findByUser(Matchers.same(mockUser))).thenReturn(mockCar);
		
		Car car = carService.getCarForUser(mockUser);
		
		verify(carRepository, atLeastOnce()).findByUser(Matchers.same(mockUser));
		
		assertNotNull(car);
		assertNotNull(car.getUser());
		assertTrue(car.getUser().getUserId() == mockUserId);
	}
	
	@Test
	public void testAddCar() {
		Car mockCar = new Car();
		
		carService.addCar(mockCar);
		
		verify(carRepository, atLeastOnce()).saveAndFlush(Matchers.same(mockCar));
	}
	
	@Test
	public void testRemoveCar() {
		Car mockCar = new Car();
		
		carService.removeCar(mockCar);
		
		verify(carRepository, atLeastOnce()).delete(Matchers.same(mockCar));
	}
	
	@Test
	public void testUpdateCar() {
		Car mockCar = new Car();
		
		carService.updateCar(mockCar);
		
		verify(carRepository, atLeastOnce()).saveAndFlush(Matchers.same(mockCar));
	}
	
}
