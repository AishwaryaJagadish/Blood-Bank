package com.example.elasticservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "patientidx",shards = 1, replicas = 0, refreshInterval = "1s", createIndex = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientES {
    @Id
    private String id;

    private String  name;

    private int age;


    private String gender;


    private String phone;


    private String email;


    private int hid;


    private String bloodgroup;


    private String department;
}
