package com.example.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.model.Hospital;
import com.example.hospital.repo.HospitalRepo;

@RestController
public class Controller {
	
	@Autowired 
	private HospitalRepo repo;
	
	 @PostMapping("/hosp")
	    public ResponseEntity<?> insertemp(@RequestBody Hospital hospital){
	        repo.save(hospital);
	        return new ResponseEntity<>("Hospital created", HttpStatus.OK);
	    }
	 
	 @GetMapping("/hosp")
	    public ResponseEntity<?> getall(){
	        List<Hospital> hos = repo.findAll();
	        return new ResponseEntity<List<Hospital>>(hos, HttpStatus.OK);
	    }
	 
	 @GetMapping("/hosp/{hid}")
	    public ResponseEntity<?> getbyId(@PathVariable int hid){
	        Hospital hp = repo.findById(hid).get();
	        return new ResponseEntity<Hospital>(hp, HttpStatus.OK);
	    }
	

}
