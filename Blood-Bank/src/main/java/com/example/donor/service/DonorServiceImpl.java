package com.example.donor.service;

import com.example.donor.DonorApplication;
import com.example.donor.model.Donor;
import com.example.donor.model.Hospital;
import com.example.donor.model.OutputModel;
import com.example.donor.repository.Repo;
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
public class DonorServiceImpl implements DonorService {
	
	@Autowired
	private Repo repo;
	
	@Autowired
	private SequenceGenerator seqservice;
	
	Logger logger = LoggerFactory.getLogger(DonorApplication.class);

	@Override
	public Donor createDonor(Donor donor) {
		donor.setId("Donor_"+String.valueOf(seqservice.getSequenceNum(Donor.SEQUENCE_NAME)));
		repo.save(donor);
		return donor;
	}

	@Override
	public List<Donor> getAll() {
		List<Donor> gdonors = repo.findAll();
		return gdonors;
	}

	@Override
	public ResponseEntity<?> updateDonor(Donor donor, String id) {
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
			  Donor newobj = donoroptional.get();
			  if(donor.getName()!=null)
			  newobj.setName(donor.getName());
			  if(donor.getAge()!=0)
			  newobj.setAge(donor.getAge());
			  if(donor.getGender()!=null)
			  newobj.setGender(donor.getGender());
			  if(donor.getBloodgroup()!=null)
			  newobj.setBloodgroup(donor.getBloodgroup());
			  if(donor.getPhone()!=null)
			  newobj.setPhone(donor.getPhone());
			  if(donor.getEmail()!=null)
			  newobj.setEmail(donor.getEmail());
			  repo.save(newobj);
			  return new ResponseEntity<>("Updated the donor in database",HttpStatus.OK);
			}
			else
			{
				logger.error("Donor not found in the database");
				return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> deleteDonor(String id) {
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
			  repo.deleteById(id);
			  return new ResponseEntity<>("Deleted the donor from the database",HttpStatus.OK);
			}
			else
			{
				logger.error("Donor not found in the database");
				return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
			}
			
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	@Cacheable(key="#id",value="Donor")
	public Donor getSingleDonor(String id) throws Exception {
		try {
			Optional<Donor> donoroptional = repo.findById(id);
			if(donoroptional.isPresent()) {
				System.out.println("Database");
//				return new ResponseEntity<Donor>(donoroptional.get(),HttpStatus.OK);
				return donoroptional.get();
			}
			else
			{
				logger.error("Donor not found in the database");
//				return new ResponseEntity<>("Donor not found in the database",HttpStatus.NOT_FOUND);
				throw new Exception("Donor not found in the database");
			}
		  }
		catch(Exception e) {
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		  }
		
	}

	@Override
	public OutputModel getOutput(String id) {
		Donor donor = repo.findById(id).get();
        String uri = "http://localhost:8063/hosp/{hid}";
        Map<String,Integer> uriparam = new HashMap<>();
        uriparam.put("hid", donor.getHid());
        RestTemplate restTemplate = new RestTemplate();
        Hospital res = restTemplate.getForObject(uri,Hospital.class, uriparam );
        logger.info(res.toString());
        OutputModel om = new OutputModel();
        om.setId(donor.getId());
        om.setName(donor.getName());
        om.setAge(donor.getAge());
        om.setBloodgroup(donor.getBloodgroup());
        om.setEmail(donor.getEmail());
        om.setGender(donor.getGender());
        om.setPhone(donor.getPhone());
        om.setHid(donor.getHid());
        om.setHosName(res.getHosName());
        return om;
	}

}
