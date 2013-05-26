package com.droidshop.api.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Temporal;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(groups={PUT.class, DELETE.class}, message = "id: Missing Required Field")
    @Id
    @Basic(optional=false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull(groups=POST.class, message = "firstName: Missing Required Field")
    @JsonProperty
    @Column(length=30, nullable = false)
    @Basic(optional = false)
	private String firstName;

    @JsonProperty
    @Column(length=10, nullable = false)
    @Basic(optional = false)
    private String middleName;

    @NotNull(groups=POST.class, message = "lastName: Missing Required Field")
    @JsonProperty
    @Column(length=30, nullable = false)
    @Basic(optional = false)
    private String lastName;

    @NotNull(groups=POST.class)
    @JsonProperty
    @Column(length=30, nullable = false)
    @Basic(optional = false)
    private String userName;

    @NotNull(groups=POST.class)
    private String password;

    @JsonProperty
    private UserStatus status;

    @JsonProperty
    private String statusCode;

    @NotNull(groups=POST.class, message = "dateOfBirth: Missing Required Field")
    @JsonProperty
	@Temporal(DATE)
	private Date dateOfBirth;

    @JsonProperty("created_at")
    @Temporal(TIMESTAMP)
	private Date createdAt;

    @JsonProperty("updated_at")
    @Temporal(TIMESTAMP)
	private Date updatedAt;

    public User() {}

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
        this.statusCode = status.getStatusCode();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nameFirst='" + firstName + '\'' +
                ", nameMiddle='" + middleName + '\'' +
                ", nameLast='" + lastName + '\'' +
                ", username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + status +
                ", userStatusCode='" + statusCode + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
