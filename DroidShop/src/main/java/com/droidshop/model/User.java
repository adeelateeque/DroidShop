package com.droidshop.model;

import java.io.Serializable;
import java.util.Date;

public class User extends AbstractEntity implements Serializable
{
	private static final long serialVersionUID = -7495897652017488896L;
	protected String firstName;
	protected String lastName;
	protected String userName;

	protected String password;
	protected Gender gender;
	protected Status status;
	protected Date dateOfBirth;
	protected Date createdAt;
	protected Date updatedAt;
	protected String email;

	protected String objectId;
	protected String address;
	protected String sessionToken;
	protected String city;
	protected String gravatarId;
	protected String country;
	protected String avatarUrl;
	protected String telephoneNo;

	protected String handphoneNo;

	protected String secretQuestion;

	protected String secretAnswer;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getObjectId()
	{
		return objectId;
	}

	public void setObjectId(String objectId)
	{
		this.objectId = objectId;
	}

	public String getSessionToken()
	{
		return sessionToken;
	}

	public void setSessionToken(String sessionToken)
	{
		this.sessionToken = sessionToken;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getGravatarId()
	{
		return gravatarId;
	}

	public String getAvatarUrl()
	{
		return avatarUrl;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getTelephoneNo()
	{
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo)
	{
		this.telephoneNo = telephoneNo;
	}

	public String getHandphoneNo()
	{
		return handphoneNo;
	}

	public void setHandphoneNo(String handphoneNo)
	{
		this.handphoneNo = handphoneNo;
	}

	public String getSecretQuestion()
	{
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion)
	{
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer()
	{
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer)
	{
		this.secretAnswer = secretAnswer;
	}

	public void setGravatarId(String gravatarId)
	{
		this.gravatarId = gravatarId;
	}

	public enum Gender
	{
		MALE, FEMALE, UNKNOWN
	}

	public enum Status
	{
		PENDING, ACTIVE, INACTIVE, DELETED;
	}
}
