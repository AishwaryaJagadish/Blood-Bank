package com.example.donor.model;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Donor {
	
	@Id
	private String id;
	
	@NotNull(message = "Name cannot be null")
	@Pattern(regexp = "[A-Z]+",flags=Pattern.Flag.CASE_INSENSITIVE)
	private String  name;
	
	@Digits(integer = 2, fraction = 2)
	private int age;
	
	@Pattern(regexp = "^male$|^female$",flags=Pattern.Flag.CASE_INSENSITIVE)
	private String gender;
	
	@NotNull(message = "BloodGroup cannot be null")
	@Pattern(regexp = "[A|B|AB|O]['\'+|'\'-]")
	private String bloodgroup;
	
	@NotNull(message = "Phone Number cannot be null")
	@Pattern(regexp = "[0-9]{10}")
	private String phone;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Enter the email in the proper format")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getBloodgroup() {
		return bloodgroup;
	}

	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
