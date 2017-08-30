package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.User;

public interface CarRepository extends JpaRepository<Car, Long> {

	Car findByUser(User user);

}
