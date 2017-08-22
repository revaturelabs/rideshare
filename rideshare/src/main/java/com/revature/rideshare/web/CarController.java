package com.revature.rideshare.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.User;
import com.revature.rideshare.service.AuthService;
import com.revature.rideshare.service.CarService;

@RestController
@RequestMapping("car")
public class CarController {

	@Autowired
	private CarService carService;
	
	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	@Autowired
	private AuthService authService;

	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping
	public List<Car> getAll() {
		return carService.getAll();
	}

	@PostMapping
	public boolean addCar(@RequestHeader(name = "X-JWT-RIDESHARE") String token, @RequestBody Car newCar) {
		User u = authService.getUserFromToken(token);
		newCar.setUser(u);
		carService.addCar(newCar);
		return true;
	}

	@PostMapping("/updateCar")
	public boolean updateCar(@RequestHeader(name = "X-JWT-RIDESHARE") String token, @RequestBody Car newCar) {
		User u = authService.getUserFromToken(token);
		Car oldCar = carService.getCarForUser(u);
		
		newCar.setUser(u);
		newCar.setCarId(oldCar.getCarId());
		carService.updateCar(newCar);
		
		return true;
	}
	
	@PostMapping("/removeCar")
	public void removeCar(@RequestBody Car car) {
		carService.removeCar(car);
	}

	@GetMapping("/myCar")
	public Car getCar(@RequestHeader(name = "X-JWT-RIDESHARE") String token) {
		User u = authService.getUserFromToken(token);
		return carService.getCarForUser(u);
	}
}