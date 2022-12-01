package com.example.elasticservice.repository;

import com.example.elasticservice.model.PatientES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PatientRepository extends ElasticsearchRepository<PatientES, String> {
}
