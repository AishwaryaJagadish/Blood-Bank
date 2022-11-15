package com.example.donor.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.donor.model.Donor;
import com.example.donor.model.OutputModel;

public interface DonorService {

	public Donor createDonor(Donor donor);
	
	public List<Donor> getAll();
	
	public ResponseEntity<?> updateDonor(Donor donor,String id);
	
	public ResponseEntity<?> deleteDonor(String id);
	
	public ResponseEntity<?> getSingleDonor(String id);
	
	public OutputModel getOutput(String id);
	
}
