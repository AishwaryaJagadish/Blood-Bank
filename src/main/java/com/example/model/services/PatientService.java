package com.example.model.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.model.OutputModel;
import com.example.model.Patient;

public interface PatientService {
		
	public Patient createPatient(Patient patient);
		
	public List<Patient> getAll();
	
	public ResponseEntity<?> updatePatient(Patient patient,String id);
	
	public ResponseEntity<?> deletePatient(String id);
	
	public Patient getSinglePatient(String id) throws Exception;
	
	public OutputModel getOutput(String id);

	}



