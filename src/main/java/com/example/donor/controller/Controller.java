package com.example.donor.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.donor.DonorApplication;
import com.example.donor.model.Donor;
import com.example.donor.repository.Repo;

@RestController
public class Controller {
	
	@Autowired
	private Repo repo;
	//private MongoTemplate mongoTemplate;
	Logger logger = LoggerFactory.getLogger(DonorApplication.class);
	
	@PostMapping("/donors")
	public ResponseEntity<?> createDonor(@RequestBody Donor donor){
		try {
			repo.save(donor);
			return new ResponseEntity<Donor>(donor,HttpStatus.OK);
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("/donors")
	public ResponseEntity<?> showDonor(){
		try {
			List<Donor> gdonors = repo.findAll();
			return new ResponseEntity<List<Donor>>(gdonors,HttpStatus.OK);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/donors/{id}")
	public ResponseEntity<?> updateDonor(@RequestBody Donor donor,@PathVariable String id){
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
			  Donor newobj = donoroptional.get();
			  newobj.setName(donor.getName());
			  newobj.setAge(donor.getAge());
			  newobj.setGender(donor.getGender());
			  newobj.setBloodgroup(donor.getBloodgroup());
			  newobj.setPhone(donor.getPhone());
			  repo.deleteById(id);
			  repo.save(donor);
			  return new ResponseEntity<>("Updated the donor in database",HttpStatus.OK);
			}
			else
			{
				logger.error("Donor not found in the database");
				return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/donors/{id}")
	public ResponseEntity<?> deleteDonor(@PathVariable String id){
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
			  repo.deleteById(id);
			  return new ResponseEntity<>("Deleted the donor from the database",HttpStatus.OK);
			}
			else
			{
				logger.error("Donor not found in the database");
				return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
			}
			
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/donors/{id}")
	public ResponseEntity<?> getSingleDonor(@PathVariable String id)
	{
		try {
		Optional<Donor> donoroptional = repo.findById(id);
		if(donoroptional.isPresent()) {
			return new ResponseEntity<Donor>(donoroptional.get(),HttpStatus.OK);
		}
		else
		{
			logger.error("Donor not found in the database");
			return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
		}
	  }
	catch(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	  }
	}
	
//	@GetMapping("/donors/{bloodgroup}")
//	List<Donor> findAll(@PathVariable(value="bloodgroup")String bloodgroup){
//		Query query = new Query();
//		query.addCriteria(Criteria.where("bloodgroup").is(bloodgroup));
//		return mongoTemplate.find(query,Donor.class);
//	}
	
}
