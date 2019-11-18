package dev.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.io.Serializable;

@Entity(name = "GroupTab")
public class Group implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// properties
	@Id()
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
    @Column(name = "name", length = 200,
      nullable = false)
	private String name;
   
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
			fetch = FetchType.LAZY, mappedBy = "ownGroup")
	private Set<Person> persons;
	
	// constructors
	public Group() {
		super();
	}
		
	public Group(String name) {
		super();
		this.name = name;
	}
	
	// getters
	public Long getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	/*public Set<Person> getPersons(){
		return persons;
	}
*/
	// setters
	public void setId(Long id){
		this.id=id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	/*public void setPersons(Set<Person> persons){
		this.persons = persons;
	}	*/
}