package com.revature.rideshare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.rideshare.domain.PointOfInterestType;

public interface PointOfInterestTypeRepository extends JpaRepository<PointOfInterestType, Long> {

}
