package com.example.donor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.donor.model.Donor;
import com.example.donor.repository.Repo;

@Service
public class DonorServiceImpl implements DonorService {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;

	@Override
	public Donor createDonor(Donor donor) {
		donor.setId("DSCE_"+String.valueOf(seqservice.getSequenceNum(Donor.SEQUENCE_NAME)));
		repo.save(donor);
		return donor;
	}

	@Override
	public List<Donor> getAll() {
		List<Donor> gdonors = repo.findAll();
		return gdonors;
	}

}
