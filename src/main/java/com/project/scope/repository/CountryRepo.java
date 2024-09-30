package com.project.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.scope.model.Country;



public interface CountryRepo extends JpaRepository<Country, Integer> {

}
