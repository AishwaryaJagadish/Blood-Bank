package com.example.donor.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.donor.model.DonorES;

public interface RepoElastic extends ElasticsearchRepository<DonorES,Integer>{

}
