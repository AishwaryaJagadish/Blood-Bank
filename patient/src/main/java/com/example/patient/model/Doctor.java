package com.example.patient.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Doctor implements Serializable {
	
	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";
	
	@Id
	private String id;
	
	@NotNull(message = "Name cannot be null")
	@Pattern(regexp = "[A-Z]+",flags=Pattern.Flag.CASE_INSENSITIVE)
	private String  name;
	
	@Digits(integer = 2, fraction = 2)
	private int age;
	
	@Pattern(regexp = "^male$|^female$",flags=Pattern.Flag.CASE_INSENSITIVE)
	private String gender;
	
	@NotNull(message = "Phone Number cannot be null")
	@Pattern(regexp = "[0-9]{10}")
	private String phone;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Enter the email in the proper format")
	private String email;
	
	@NotNull(message = "Hid cannot be null")
	private int hid;

	@NotNull(message = "Specialization cannot be null")
	private String specialization;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getHid() {
		return hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
