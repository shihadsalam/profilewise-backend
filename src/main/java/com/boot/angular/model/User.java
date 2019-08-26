package com.boot.angular.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private UserCareer userCareer;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "IS_ADMIN")
	private boolean isAdmin = false;
	
	public User() {
		// hibernate
	}

	public User(String firstName, String lastName, Date dob, String username, String password, String email, String country, boolean isAdmin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.isAdmin = isAdmin;
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
	
	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
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

	public void update(FormUser user) throws ParseException {
		if (!StringUtils.isEmpty(user.getFirstName())) {
			this.firstName = user.getFirstName();
		}
		if (!StringUtils.isEmpty(user.getLastName())) {
			this.lastName = user.getLastName();
		}
		if (!StringUtils.isEmpty(user.getDob())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			this.dob = sdf.parse(user.getDob());
		}
		if (!StringUtils.isEmpty(user.getEmail())) {
			this.email = user.getEmail();
		}
		if (!StringUtils.isEmpty(user.getCountry())) {
			this.country = user.getCountry();
		}
		this.isAdmin = user.getIsAdmin();
		
		if (null != user.getUserCareer()) {
			if (null != this.userCareer) {
				this.userCareer.update(user.getUserCareer());
			}
			else {
				UserCareer userCareer = user.getUserCareer();
				userCareer.setUser(this);
				setUserCareer(userCareer);
			}
		}
	}

}
