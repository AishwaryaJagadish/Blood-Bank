package com.example.doctor.service;


import com.example.doctor.model.Doctor;
import com.example.doctor.model.Hospital;
import com.example.doctor.model.OutputModel;
import com.example.doctor.repository.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements com.example.Doctor.service.DoctorService {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	Logger logger = LoggerFactory.getLogger(com.example.Doctor.service.DoctorService.class);

	@Override
	public Doctor createDoctor(Doctor doctor) {
		doctor.setId("D_"+String.valueOf(seqservice.getSequenceNum(Doctor.SEQUENCE_NAME)));
		repo.save(doctor);
		return doctor;
	}

	@Override
	public List<Doctor> getAll() {
		List<Doctor> gdoctors = repo.findAll();
		return gdoctors;
	}

	@Override
	public ResponseEntity<?> updateDoctor(Doctor doctor, String id) {
		try {
			Optional<Doctor> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
			  Doctor newobj = doctoroptional.get();
			  if(doctor.getName()!=null)
			  newobj.setName(doctor.getName());
			  if(doctor.getAge()!=0)
			  newobj.setAge(doctor.getAge());
			  if(doctor.getGender()!=null)
			  newobj.setGender(doctor.getGender());
			  if(doctor.getSpecialization()!=null)
			  newobj.setSpecialization(doctor.getSpecialization());
			  if(doctor.getPhone()!=null)
			  newobj.setPhone(doctor.getPhone());
			  if(doctor.getEmail()!=null)
			  newobj.setEmail(doctor.getEmail());
			  repo.save(newobj);
			  return new ResponseEntity<>("Updated the doctor in database",HttpStatus.OK);
			}
			else
			{
				logger.error("doctor not found in the database");
				return new ResponseEntity<>("doctor not found in the database",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}


	@Override
	public ResponseEntity<?> deletedoctor(String id){
		try {
			Optional<Doctor> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
				repo.deleteById(id);
				return new ResponseEntity<>("Deleted the doctor from the database",HttpStatus.OK);
			}
			else
			{
				logger.error("doctor not found in the database");
				return new ResponseEntity<>("doctor not found in the database",HttpStatus.NOT_FOUND);
			}

		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Cacheable(key="#id",value="Doctor")
	public Doctor getSingleDoctor(String id) throws Exception {
		try {
			Optional<Doctor> doctoroptional = repo.findById(id);
			if(doctoroptional.isPresent()) {
				System.out.println("Database");
//				return new ResponseEntity<doctor>(doctoroptional.get(),HttpStatus.OK);
				return doctoroptional.get();
			}
			else
			{
				logger.error("doctor not found in the database");
//				return new ResponseEntity<>("doctor not found in the database",HttpStatus.NOT_FOUND);
				throw new Exception("doctor not found in the database");
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}



	@Override
	public OutputModel getOutput(String id) {
		Doctor doctor = repo.findById(id).get();
        String uri = "http://localhost:8063/hosp/{hid}";
        Map<String,Integer> uriparam = new HashMap<>();
        uriparam.put("hid", doctor.getHid());
        RestTemplate restTemplate = new RestTemplate();
        Hospital res = restTemplate.getForObject(uri,Hospital.class, uriparam );
        logger.info(res.toString());
        OutputModel om = new OutputModel();
        om.setId(doctor.getId());
        om.setName(doctor.getName());
        om.setAge(doctor.getAge());
        om.setBloodgroup(doctor.getSpecialization());
        om.setEmail(doctor.getEmail());
        om.setGender(doctor.getGender());
        om.setPhone(doctor.getPhone());
        om.setHid(doctor.getHid());
        om.setHosName(res.getHosName());
        return om;
	}

}
