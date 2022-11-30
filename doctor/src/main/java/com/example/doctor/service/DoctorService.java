package com.example.Doctor.service;


import com.example.doctor.model.Doctor;
import com.example.doctor.model.OutputModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DoctorService {

	public Doctor createDoctor(Doctor doctor);


	public List<Doctor> getAll();
	
	public ResponseEntity<?> updateDoctor(Doctor doctor,String id);


	public ResponseEntity<?> deletedoctor(String id);
	
	public Doctor getSingleDoctor(String id) throws Exception;


	public OutputModel getOutput(String id);


}
