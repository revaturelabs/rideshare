package com.revature.rideshare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.revature.rideshare.dao.CarRepository;
import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.User;

@Component("carService")
@Transactional
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepo;

	public CarServiceImpl() {
	}

	public void setCarRepo(CarRepository carRepo) {
		this.carRepo = carRepo;
	}

	@Override
	public List<Car> getAll() {
		return carRepo.findAll();
	}

	public Car getCarForUser(User u) {
		return carRepo.findByUser(u);
	}

	@Override
	public void addCar(Car car) {
		carRepo.saveAndFlush(car);
	}

	@Override
	public void removeCar(Car car) {
		carRepo.delete(car);
	}

	@Override
	public void updateCar(Car car) {
		carRepo.saveAndFlush(car);
	}
}
