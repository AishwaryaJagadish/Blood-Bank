package com.example.elasticservice;

import com.example.elasticservice.model.PatientES;
import com.example.elasticservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ElasticServiceApplication {

	@Autowired
	private PatientRepository repo;

	@GetMapping("/findAll")
	public Iterable<PatientES> findAllEmployees() {
		return repo.findAll();
	}


	public static void main(String[] args) {
		SpringApplication.run(ElasticServiceApplication.class, args);
	}

}
