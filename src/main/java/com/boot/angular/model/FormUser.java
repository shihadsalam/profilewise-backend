package com.boot.angular.model;

public class FormUser {

	private Long id;
	private UserCareer userCareer;
	private String firstName;
	private String lastName;
	private Object dob;
	private String username;
	private String password;
	private String email;
	private String country;
	private boolean isAdmin = false;
	
	public FormUser() {
		
	}

	public FormUser(String firstName, String lastName, Object dob, String username, String password, String email, String country, boolean isAdmin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.isAdmin = isAdmin;
		this.userCareer = new UserCareer();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Object getDob() {
		return dob;
	}

	public void setDob(Object dob) {
		this.dob = dob;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public UserCareer getUserCareer() {
		return userCareer;
	}

	public void setUserCareer(UserCareer userCareer) {
		this.userCareer = userCareer;
	}
	
}
