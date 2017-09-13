package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.Car;
import com.revature.rideshare.domain.User;

public interface CarRepository extends JpaRepository<Car, Long> {

	/**
	 * Finds a Car by the User.
	 * 
	 * @param user the User of the Car we are searching for
	 * @return the Car we were searching for
	 * @see com.revature.rideshare.domain.Car The Car class
	 */
	Car findByUser(User user);

}
