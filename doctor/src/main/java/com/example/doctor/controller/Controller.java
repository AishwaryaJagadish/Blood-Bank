package com.example.doctor.controller;

import com.example.doctor.model.Doctor;
import com.example.doctor.model.OutputModel;
import com.example.doctor.repository.Repo;
import com.example.doctor.service.SequenceGenerator;
import com.example.Doctor.service.DoctorService;
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
	private DoctorService doctorService;
	
	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	@PostMapping("/doctors")
	public ResponseEntity<?> createdoctor(@RequestBody Doctor doctor){
		doctorService.createDoctor(doctor);
		
			return new ResponseEntity<Doctor>(doctor,HttpStatus.OK);
	}	
//	try {
//		repo.save(doctor);
//		return new ResponseEntity<doctor>(doctor,HttpStatus.OK);
//	}
//	catch(Exception e) {
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
//	}
	
	@GetMapping("/doctors")
	public ResponseEntity<?> showdoctor(){
			return new ResponseEntity<List<Doctor>>(doctorService.getAll(),HttpStatus.OK);
	}
//	try {
//		List<doctor> gdoctors = repo.findAll();
//		return new ResponseEntity<List<doctor>>(gdoctors,HttpStatus.OK);
//	}
//	catch(Exception e)
//	{
//		logger.error(e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//	}
	
	@PutMapping("/doctors/{id}")
	public ResponseEntity<?> updatedoctor(@RequestBody Doctor doctor,@PathVariable String id){
		return new ResponseEntity<>(doctorService.updateDoctor(doctor,id),HttpStatus.OK);
	}
	
	@DeleteMapping("/doctors/{id}")
	public ResponseEntity<?> deletedoctor(@PathVariable String id){
		return new ResponseEntity<>(doctorService.deletedoctor(id),HttpStatus.OK);
	}
	
	
	@GetMapping("/doctors/{id}")
	public ResponseEntity<?> getSingledoctor(@PathVariable String id) throws Exception{
		return new ResponseEntity<Doctor>(doctorService.getSingleDoctor(id),HttpStatus.OK);
	}
	
	@GetMapping("/doctors/specialization/{specialization}")
	public List<Doctor> finddoctor(@PathVariable(value="specialization") String specialization){
		List<Doctor> doctors = repo.findBySpecialization(specialization);
		return doctors;
		
	}
	
	@GetMapping("/doctors/age/{age}")
	public ResponseEntity<?> findAge(@PathVariable(value="age") int age){
		List<Doctor> ageres = repo.findByAge(age);
		if(ageres.size()>0)
		{
			return new ResponseEntity<List<Doctor>>(ageres,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("Age not present", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/doctors/hospital/{id}")
    public OutputModel getOutput(@PathVariable String id) {
        return doctorService.getOutput(id);
    }
}
