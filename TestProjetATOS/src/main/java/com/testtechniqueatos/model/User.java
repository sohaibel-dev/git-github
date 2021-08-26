package com.testtechniqueatos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@Column(name = "gender",  length = 20)
	private String gender;

	@NotNull
	@Column(nullable = false, unique = true, length = 45)
	private String email;
	
	@NotNull
	@Column(nullable = false, length = 64)
	private String password;
	
	@NotNull
	@Column(name = "first_name", nullable = false, length = 20)
	private String firstName;
	
	@NotNull
	@Column(name = "last_name", nullable = false, length = 20)
	private String lastName;

	
	@Pattern(regexp="(^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$)?|^$")
	@Column(name = "phoneNumber", nullable = true, length = 45)
	private String phoneNumber;
	
	//@Min(18)
	@Column(name = "birthdate", nullable = false, length = 20)
	private String birthdate;

	@Pattern(regexp="(?i)^france$")
	@Column(name = "country", nullable = false, length = 20)
	private String country;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
}
