package com.boot.angular.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USER_CONTACT")
public class UserContact implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PHONE_NUMBER")
	private BigInteger phoneNumber;
	
	@Column(name = "ADDRESS_LINE_1")
	private String addressLine1;

	@Column(name = "ADDRESS_LINE_2")
	private String addressLine2;
	
	@Column(name = "ADDRESS_LINE_3")
	private String addressLine3;

	
	public UserContact() {
		// hibernate
	}

	public UserContact(User user, String email, BigInteger phoneNumber, String addressLine1, String addressLine2,
			String addressLine3) {
		super();
		this.user = user;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigInteger getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(BigInteger phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	
	public UserContact update(UserContact userContact) {
		if (null != userContact.getEmail()) {
			this.email = userContact.getEmail();
		}
		if (null != userContact.getPhoneNumber()) {
			this.phoneNumber = userContact.getPhoneNumber();
		}
		if (null != userContact.getAddressLine1()) {
			this.addressLine1 = userContact.getAddressLine1();
		}
		if (null != userContact.getAddressLine2()) {
			this.addressLine2 = userContact.getAddressLine2();
		}
		if (null != userContact.getAddressLine3()) {
			this.addressLine3 = userContact.getAddressLine3();
		}
		return this;
	}

}
