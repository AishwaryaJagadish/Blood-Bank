package com.example.doctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
	
	@Bean
	public ValidatingMongoEventListener validationMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}
	
	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}	

}

//before sending the data to the database, it comes here to check for the constraints like null and notnull defined in the model classss