package com.example.hospital.controller;

import com.example.hospital.model.*;
import com.example.hospital.repo.HospitalRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableCaching
public class Controller {

	Logger logger = LoggerFactory.getLogger(Controller.class);
	@Autowired 
	private HospitalRepo repo;

	@Autowired
	private RestTemplate restTemplate;
	
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
	 @Cacheable(key="#id",value="Hospital")
	    public ResponseEntity<?> getbyId(@PathVariable int hid){
	        Hospital hp = repo.findById(hid).get();
	        return new ResponseEntity<Hospital>(hp, HttpStatus.OK);
	 }

	@PostMapping("/hosp/createpatient")
	public ResponseEntity<?> createPatient(@RequestBody Patient patient){
		 Hospital hp = repo.findById(patient.getHid()).get();

		 //Getting doctor with specilization
		String uri = "http://localhost:8062/doctors/specialization/{specialization}";
		Map<String,String> uriparam = new HashMap<>();
		uriparam.put("specialization", patient.getDepartment());
		ResponseEntity<List> response = restTemplate.getForEntity(uri, List.class, uriparam);
		List<Doctor> doctors = response.getBody();

		//Getting Donor details
		String donoruri = "http://localhost:8060/donors/blood/{group}";
		Map<String,String> uriparam2 = new HashMap<>();
		uriparam2.put("group", patient.getBloodgroup());
		ResponseEntity<List> donorResponse = restTemplate.getForEntity(donoruri, List.class,uriparam2);
		List<Donor> donors = donorResponse.getBody();

		PatientOutputModel out = new PatientOutputModel();
		out.setDoctors(doctors);
		out.setPatient(patient);
		out.setDonors(donors);

		return new ResponseEntity<PatientOutputModel>(out, HttpStatus.OK);
	}


}
