package com.project.scope.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.scope.model.Country;
import com.project.scope.model.District;
import com.project.scope.model.State;
import com.project.scope.repository.CountryRepo;
import com.project.scope.service.UserService;



@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	CountryRepo repo;
	
	 @Autowired
	    private UserService userService;
	
	@GetMapping("/country/{id}")
	public ResponseEntity<Country> countryById(@PathVariable("id") int id){
		Country c = repo.findById(id).orElseThrow();
		return  ResponseEntity.ok(c);
	}
	@GetMapping("/countries")
	public ResponseEntity<List<Country>> countries(){
		List<Country> list = repo.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/states/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountry(@PathVariable int countryId) {
        List<State> states = userService.getStatesByCountryId(countryId);
        System.out.println(states);
        return ResponseEntity.ok(states);
    }
	
	 @GetMapping("/districts/{stateId}")
	    public ResponseEntity<List<District>> getDistrictsByState(@PathVariable int stateId) {
	        List<District> districts = userService.getDistrictsByStateId(stateId);
	        return ResponseEntity.ok(districts);
	 }  

}
