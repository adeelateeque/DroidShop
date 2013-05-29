package com.droidshop.api.model.user;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@MappedSuperclass
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull(groups = { PUT.class, DELETE.class }, message = "id: Missing Required Field")
	@Id
	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@NotNull(groups = POST.class, message = "firstName: Missing Required Field")
	@JsonProperty
	@Column(length = 30, nullable = false)
	@Basic(optional = false)
	protected String firstName;

	@JsonProperty
	@Column(length = 10, nullable = false)
	@Basic(optional = false)
	protected String middleName;

	@NotNull(groups = POST.class, message = "lastName: Missing Required Field")
	@JsonProperty
	@Column(length = 30, nullable = false)
	@Basic(optional = false)
	protected String lastName;

	@NotNull(groups = POST.class)
	@JsonProperty
	@Column(length = 30, nullable = false)
	@Basic(optional = false)
	protected String userName;

	@NotNull(groups = POST.class)
	protected String password;

	@Enumerated(EnumType.STRING)
	protected GenderType gender;

	@JsonProperty
	protected UserStatus status;

	@JsonProperty
	protected String statusCode;

	@NotNull(groups = POST.class, message = "dateOfBirth: Missing Required Field")
	@JsonProperty
	@Temporal(DATE)
	protected Date dateOfBirth;

	@JsonProperty("created_at")
	@Temporal(TIMESTAMP)
	protected Date createdAt;

	@JsonProperty("updated_at")
	@Temporal(TIMESTAMP)
	protected Date updatedAt;

	@Pattern(regexp = ".+@.+\\.[a-z]+", message = "Invalid {user.email}")
	@Size(min = 3, max = 45, message = "{user.email}")
	@Basic(optional = false)
	@Column(name = "email", unique = true)
	protected String email;

	@Basic(optional = true)
	@Size(min = 3, max = 50, message = "Invalid {user.address}")
	@Column(name = "address")
	protected String address;

	@Basic(optional = true)
	@Size(min = 3, max = 75, message = "Invalid {user.city}")
	@Column(name = "city")
	protected String city;

	@Basic(optional = true)
	@Size(min = 3, max = 75, message = "Invalid {user.country}")
	@Column(name = "country")
	protected String country;

	protected String telephoneNo;

	protected String handphoneNo;

	protected String secretQuestion;

	protected String secretAnswer;

	public User()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public UserStatus getStatus()
	{
		return status;
	}

	public void setStatus(UserStatus status)
	{
		this.status = status;
		this.statusCode = status.getStatusCode();
	}

	public String getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(String statusCode)
	{
		this.statusCode = statusCode;
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

	public GenderType getGender()
	{
		return gender;
	}

	public void setGender(GenderType gender)
	{
		this.gender = gender;
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

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += ((userName != null) ? userName.hashCode() : 0);

		return hash;
	}

	@Override
	public boolean equals(Object object)
	{
		if (!(object instanceof User))
		{
			return false;
		}

		User other = (User) object;

		if (((this.userName == null) && (other.userName != null))
				|| ((this.userName != null) && !this.userName.equals(other.userName)))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "User{" + "id=" + id + ", nameFirst='" + firstName + '\'' + ", nameMiddle='" + middleName + '\''
				+ ", nameLast='" + lastName + '\'' + ", username='" + userName + '\'' + ", password='" + password + '\''
				+ ", userStatus=" + status + ", userStatusCode='" + statusCode + '\'' + ", dateOfBirth=" + dateOfBirth
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
	}
}
