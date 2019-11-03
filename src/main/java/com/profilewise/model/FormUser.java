package com.profilewise.model;

public class FormUser {

	private Long id;
	private UserContact userContact;
	private String firstName;
	private String lastName;
	private String gender;
	private String dob;
	private String username;
	private String password;
	private String email;
	private String userRole;
	private boolean isSupervisor = false;
	
	public FormUser() {
		
	}

	public FormUser(String firstName, String lastName, String gender, String dob, String username, String password, String email, 
			String userRole, boolean isSupervisor, UserContact userContact) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.username = username;
		this.password = password;
		this.email = email;
		this.isSupervisor = isSupervisor;
		this.userRole = userRole;
		this.userContact = userContact;
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
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
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

	public boolean getIsSupervisor() {
		return isSupervisor;
	}

	public void setIsSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}

	public UserContact getUserContact() {
		return userContact;
	}

	public void setUserContact(UserContact userContact) {
		this.userContact = userContact;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
}
