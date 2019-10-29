package com.boot.angular.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "supervisor_id")
	private User supervisor;
	
	@JsonIgnore
	@OneToMany
	private List<User> reportees;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	private UserContact userContact;
	
	@Column(name = "ROLE")
	private RoleEnum userRole;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "IS_SUPERVISOR")
	private boolean isSupervisor = false;
	
	public User() {
		// hibernate
	}

	public User(String firstName, String lastName, String gender, Date dob, String username, String password,
			String role, boolean isSupervisor, UserContact userContact) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dob = dob;
		this.username = username;
		this.password = password;
		this.userRole = RoleEnum.getRole(role);
		this.isSupervisor = isSupervisor;
		this.userContact = userContact;
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
	
	public User getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(User supervisor) {
		this.supervisor = supervisor;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
	
	public RoleEnum getUserRole() {
		return userRole;
	}

	public void setUserRole(RoleEnum userRole) {
		this.userRole = userRole;
	}
	
	public List<User> getReportees() {
		return reportees;
	}

	public void setReportees(List<User> reportees) {
		this.reportees = reportees;
	}

	public void update(FormUser user) throws ParseException {
		if (!StringUtils.isEmpty(user.getFirstName())) {
			this.firstName = user.getFirstName();
		}
		if (!StringUtils.isEmpty(user.getLastName())) {
			this.lastName = user.getLastName();
		}
		if (!StringUtils.isEmpty(user.getGender())) {
			this.gender = user.getGender();
		}
		if (!StringUtils.isEmpty(user.getDob())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			this.dob = sdf.parse(user.getDob());
		}
		
		this.isSupervisor = user.getIsSupervisor();
		
		if (!StringUtils.isEmpty(user.getUserRole())) {
			this.userRole = RoleEnum.getRole(user.getUserRole());
		}
		
		if (null != user.getUserContact()) {
			if (null != this.userContact) {
				this.userContact.update(user.getUserContact());
			}
			else {
				UserContact userContact = user.getUserContact();
				userContact.setUser(this);
				setUserContact(userContact);
			}
		}
	}

}
