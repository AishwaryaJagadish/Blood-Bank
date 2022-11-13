package com.example.donor.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

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
import org.springframework.web.client.RestTemplate;

import com.example.donor.DonorApplication;
import com.example.donor.model.Donor;
import com.example.donor.model.Hospital;
import com.example.donor.model.OutputModel;
import com.example.donor.repository.Repo;
import com.example.donor.service.SequenceGenerator;

@RestController
public class Controller {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	Logger logger = LoggerFactory.getLogger(DonorApplication.class);
	
	@PostMapping("/donors")
	public ResponseEntity<?> createDonor(@RequestBody Donor donor){
		donor.setId("DSCE_"+String.valueOf(seqservice.getSequenceNum(Donor.SEQUENCE_NAME)));
			repo.save(donor);
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
			List<Donor> gdonors = repo.findAll();
			return new ResponseEntity<List<Donor>>(gdonors,HttpStatus.OK);
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
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
			  Donor newobj = donoroptional.get();
			  if(donor.getName()!=null)
			  newobj.setName(donor.getName());
			  if(donor.getAge()!=0)
			  newobj.setAge(donor.getAge());
			  if(donor.getGender()!=null)
			  newobj.setGender(donor.getGender());
			  if(donor.getBloodgroup()!=null)
			  newobj.setBloodgroup(donor.getBloodgroup());
			  if(donor.getPhone()!=null)
			  newobj.setPhone(donor.getPhone());
			  if(donor.getEmail()!=null)
			  newobj.setEmail(donor.getEmail());
			  repo.save(newobj);
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
		Donor donor = repo.findById(id).get();
        String uri = "http://localhost:8061/hosp/{hid}";
        Map<String,Integer> uriparam = new HashMap<>();
        uriparam.put("hid", donor.getHid());
        RestTemplate restTemplate = new RestTemplate();
        Hospital res = restTemplate.getForObject(uri,Hospital.class, uriparam );
        logger.info(res.toString());
        OutputModel om = new OutputModel();
        om.setId(donor.getId());
        om.setName(donor.getName());
        om.setAge(donor.getAge());
        om.setBloodgroup(donor.getBloodgroup());
        om.setEmail(donor.getEmail());
        om.setGender(donor.getGender());
        om.setPhone(donor.getPhone());
        om.setHid(donor.getHid());
        om.setHosName(res.getHosName());
        return om;
    }
	
	
	
}
