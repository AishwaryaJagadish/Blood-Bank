package com.example.donor.service;

import java.util.List;

import com.example.donor.model.Donor;

public interface DonorService {

	public Donor createDonor(Donor donor);
	
	public List<Donor> getAll();
	
}
