package com.example.model.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Patient;

public interface Repo extends MongoRepository<Patient,String> {

}
