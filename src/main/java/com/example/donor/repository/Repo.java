package com.example.donor.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.donor.model.Donor;

public interface Repo extends MongoRepository<Donor,String>{  //model class name,data type of id

	
//	@Query("{'bloodgroup': ?0}")
//	List<Donor> findByBloodgroup(String bloodgroup);
}
