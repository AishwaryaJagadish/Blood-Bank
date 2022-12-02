package com.example.elasticservice.kafka;

import com.example.elasticservice.model.PatientES;
import com.example.elasticservice.repository.PatientRepository;
import com.example.patient.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PatientConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientConsumer.class);

    @Autowired
    private PatientRepository repo;

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId= "${spring.kafka.consumer.group-id}"
    )
    public void consume(Patient event){
        LOGGER.info(String.format("got patient in elastic service after registration => %s",event.toString()));
        PatientES patient = new PatientES();
        patient.setId(event.getId());
        patient.setAge(event.getAge());
        patient.setEmail(event.getEmail());
        patient.setGender(event.getGender());
        patient.setBloodgroup(event.getBloodgroup());
        patient.setHid(event.getHid());
        patient.setName(event.getName());
        patient.setDepartment(event.getDepartment());
        repo.save(patient);
        LOGGER.info("saved patient to es db ");
    }
    // add here to save the data to a database
}
