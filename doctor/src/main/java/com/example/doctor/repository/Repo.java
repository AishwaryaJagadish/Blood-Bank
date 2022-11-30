package com.example.doctor.repository;

import com.example.doctor.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface Repo extends MongoRepository<Doctor,String>{  //model class name,data type of id

	
	@Query("{'bloodgroup': ?0}")
	List<Doctor> findByBloodgroup(String bloodgroup);
	
	@Query("{'age': ?0}")
	List<Doctor> findByAge(int age);
}

//blue is the main query
//this where the function gets called and it is predefined