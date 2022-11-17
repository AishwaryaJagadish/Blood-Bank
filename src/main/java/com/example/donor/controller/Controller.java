package com.example.donor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
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
import org.springframework.web.client.RestTemplate;

import com.example.donor.DonorApplication;
import com.example.donor.model.Donor;
import com.example.donor.model.Hospital;
import com.example.donor.model.OutputModel;
import com.example.donor.repository.Repo;
import com.example.donor.service.DonorService;
import com.example.donor.service.SequenceGenerator;

@RestController
@EnableCaching
public class Controller {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	@Autowired
	private DonorService donorService;
	
	Logger logger = LoggerFactory.getLogger(DonorApplication.class);
	
	@PostMapping("/donors")
	public ResponseEntity<?> createDonor(@RequestBody Donor donor){
		donorService.createDonor(donor);
			return new ResponseEntity<Donor>(donor,HttpStatus.OK);
	}	
//	try {
//		repo.save(donor);
//		return new ResponseEntity<Donor>(donor,HttpStatus.OK);
//	}
//	catch(Exception e) {
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//	}
	
	@GetMapping("/donors")
	public ResponseEntity<?> showDonor(){
			return new ResponseEntity<List<Donor>>(donorService.getAll(),HttpStatus.OK);
	}
//	try {
//		List<Donor> gdonors = repo.findAll();
//		return new ResponseEntity<List<Donor>>(gdonors,HttpStatus.OK);
//	}
//	catch(Exception e)
//	{
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//	}
	
	@PutMapping("/donors/{id}")
	public ResponseEntity<?> updateDonor(@RequestBody Donor donor,@PathVariable String id){
		return new ResponseEntity<>(donorService.updateDonor(donor,id),HttpStatus.OK);
	}
	
	@DeleteMapping("/donors/{id}")
	public ResponseEntity<?> deleteDonor(@PathVariable String id){
		return new ResponseEntity<>(donorService.deleteDonor(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/donors/{id}")
	public ResponseEntity<?> getSingleDonor(@PathVariable String id) throws Exception{
		return new ResponseEntity<Donor>(donorService.getSingleDonor(id),HttpStatus.OK);
	}
	
	@GetMapping("/donors/blood/{bloodgroup}")
	public List<Donor> findDonor(@PathVariable(value="bloodgroup") String bloodgroup){
		List<Donor> bloodgroupres = repo.findByBloodgroup(bloodgroup);
		return bloodgroupres;
		
	}
	
	@GetMapping("/donors/age/{age}")
	public ResponseEntity<?> findAge(@PathVariable(value="age") int age){
		List<Donor> ageres = repo.findByAge(age);
		if(ageres.size()>0)
		{
			return new ResponseEntity<List<Donor>>(ageres,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Age not present", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/donors/hospital/{id}")
    public OutputModel getOutput(@PathVariable String id) {
        return donorService.getOutput(id);
    }
}
