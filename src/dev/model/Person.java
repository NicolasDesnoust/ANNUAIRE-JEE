package dev.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import dev.web.isEmailAddress;
import dev.web.isWebsite;

//import java.util.Set;
import java.io.Serializable;

@Entity(name = "Person")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// properties
	@Id()
	@Pattern(regexp="[a-z]([0-9]){8}", message="{person.warn.id}")
	private String id;
	
	@NotNull
	@Size(min = 3, message = "{person.warn.name}")
	@Basic(optional = false)
	@Column(name = "name", length = 200,
    nullable = false)
	private String name;
	
	@NotNull
	@Size(min = 3, message = "{person.warn.firstname}")
	@Basic(optional = false)
	@Column(name = "first_name", length = 200,
    nullable = false)
	private String firstname;
	
	@isEmailAddress
	@Basic(optional = false)
	@Column(name = "address", length = 200,
    nullable = false)
	private String address;
	
	@isWebsite
	@Basic()
	@Column(name = "website", length = 200)
	private String website;
	
	@NotNull
	@Basic(optional = false)
	@Temporal(TemporalType.DATE)
	@Column(name = "birthday", nullable = false)
	private Date birthday;
	
	@NotNull(message = "{person.warn.password.not.null}")
	@Size(min = 6, max = 10, message = "{person.warn.password.size}")
	@Basic(optional = false)
	@Column(name = "password", length = 10,
    nullable = false)
	private String password;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ownGroup")
	private Group ownGroup;

	// constructors
	public Person() {
		super();
	}
	
	public Person(String id, String name, String firstname,
				  String address, String website, 
				  Date birthday, String password) {
		super();
		this.id = id;
		this.name = name;
		this.firstname = firstname;
		this.address = address;
		this.website = website;
		this.birthday = birthday;
		this.password = password;
	}
	
	// getters
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}

	public String getFirstname(){
		return firstname;
	}

	public String getAddress(){
		return address;
	}
	
	public String getWebsite(){
		return website;
	}

	public Date getBirthday(){
		return birthday;
	}

	public String getPassword(){
		return password;
	}

	public Group getGroup(){
		return ownGroup;
	}
	
	// setters
	public void setPersonId(String id){
		this.id=id;
	}
	
	public void setName(String name){
		this.name=name;
	}

	public void setFirstname(String firstname){
		this.firstname=firstname;
	}

	public void setAddress(String address){
		this.address=address;
	}
	
	public void setWebsite(String website){
		this.website=website;
	}

	public void setBirthday(Date birthday){
		this.birthday=birthday;
	}

	public void setPassword(String password){
		this.password=password;
	}

	public void setGroup(Group ownGroup){
		this.ownGroup=ownGroup;
	}
}