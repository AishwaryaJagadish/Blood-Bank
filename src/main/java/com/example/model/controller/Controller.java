package com.example.model.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.OutputModel;
import com.example.model.Patient;
import com.example.model.repository.Repo;
import com.example.model.services.PatientService;
import com.example.model.services.SequenceGenerator;
import com.example.patient.PatientApplication;


@RestController
@EnableCaching
public class Controller {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	@Autowired
	private PatientService patientService;
	
	Logger logger = LoggerFactory.getLogger(PatientApplication.class);
	
	@PostMapping("/patients")
	public ResponseEntity<?> createPatient(@RequestBody Patient patient){
		patientService.createPatient(patient);
		
			return new ResponseEntity<Patient>(patient,HttpStatus.OK);
	}
	
	@GetMapping("/patients")
	public ResponseEntity<?> showPatient(){
			return new ResponseEntity<List<Patient>>(patientService.getAll(),HttpStatus.OK);
	}
	
	@PutMapping("/patients/{id}")
	public ResponseEntity<?> updatePatient(@RequestBody Patient patient,@PathVariable String id){
		return new ResponseEntity<>(patientService.updatePatient(patient,id),HttpStatus.OK);
	}
	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<?> deletePatient(@PathVariable String id){
		return new ResponseEntity<>(patientService.deletePatient(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<?> getSinglePatient(@PathVariable String id) throws Exception{
		return new ResponseEntity<Patient>(patientService.getSinglePatient(id),HttpStatus.OK);
	}
	
	@GetMapping("/patients/blood/{bloodgroup}")
	public List<Patient> findPatient(@PathVariable(value="bloodgroup") String bloodgroup){
		List<Patient> bloodgroupres = repo.findByBloodgroup(bloodgroup);
		return bloodgroupres;
		
	}
	
	@GetMapping("/patients/age/{age}")
	public ResponseEntity<?> findAge(@PathVariable(value="age") int age){
		List<Patient> ageres = repo.findByAge(age);
		if(ageres.size()>0)
		{
			return new ResponseEntity<List<Patient>>(ageres,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Age not present", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/patients/hospital/{id}")
    public OutputModel getOutput(@PathVariable String id) {
        return patientService.getOutput(id);
    }

}
