package com.example.patient.controller;

import com.example.patient.model.OutputModel;
import com.example.patient.model.Patient;
import com.example.patient.repository.Repo;
import com.example.patient.service.PatientService;
import com.example.patient.service.SequenceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableCaching
public class Controller {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	@Autowired
	private PatientService patientService;
	
	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@PostMapping("/patients")
	public ResponseEntity<?> createpatient(@RequestBody Patient patient){
		patientService.createPatient(patient);
		
			return new ResponseEntity<Patient>(patient,HttpStatus.OK);
	}	
//	try {
//		repo.save(patient);
//		return new ResponseEntity<patient>(patient,HttpStatus.OK);
//	}
//	catch(Exception e) {
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//	}
	
	@GetMapping("/patients")
	public ResponseEntity<?> showpatient(){
			return new ResponseEntity<List<Patient>>(patientService.getAll(),HttpStatus.OK);
	}
//	try {
//		List<patient> gpatients = repo.findAll();
//		return new ResponseEntity<List<patient>>(gpatients,HttpStatus.OK);
//	}
//	catch(Exception e)
//	{
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//	}
	
	@PutMapping("/patients/{id}")
	public ResponseEntity<?> updatepatient(@RequestBody Patient patient,@PathVariable String id){
		return new ResponseEntity<>(patientService.updatePatient(patient,id),HttpStatus.OK);
	}
	
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<?> deletepatient(@PathVariable String id){
		return new ResponseEntity<>(patientService.deletePatient(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/patients/{id}")
	public ResponseEntity<?> getSinglepatient(@PathVariable String id) throws Exception{
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
