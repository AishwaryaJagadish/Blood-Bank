package com.example.hospital.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hospital.model.Hospital;

public interface HospitalRepo extends MongoRepository<Hospital,Integer>{

}
