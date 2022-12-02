package com.example.donor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.donor.model.Donor;

public interface Repo extends MongoRepository<Donor,String>{  //model class name,data type of id

	
	@Query("{'bloodgroup': ?0}")
	List<Donor> findByBloodgroup(String bloodgroup);
	
	@Query("{'age': ?0}")
	List<Donor> findByAge(int age);
}

//blue is the main query
//this where the function gets called and it is predefined