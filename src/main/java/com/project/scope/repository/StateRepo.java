package com.project.scope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.scope.model.State;



public interface StateRepo extends JpaRepository<State, Integer> {
	
	 List<State> findByCountryId(int countryId);

}
