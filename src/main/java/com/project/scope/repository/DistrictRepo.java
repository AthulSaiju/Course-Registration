package com.project.scope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.scope.model.District;



public interface DistrictRepo extends JpaRepository<District, Integer>{
	List<District> findByStateId(int stateId);

}
