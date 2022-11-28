package com.example.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.model.Patient;

public interface Repo extends MongoRepository<Patient,String> {
	
	@Query("{'bloodgroup': ?0}")
	List<Patient> findByBloodgroup(String bloodgroup);
	
	@Query("{'age': ?0}")
	List<Patient> findByAge(int age);

}
