package dev.business;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;

import dev.dao.*;
import dev.model.Group;
import dev.model.Person;
import dev.web.User;

@Service("directoryManager")
@ContextConfiguration(classes = SpringConfiguration.class)
public class DirectoryManager implements IDirectoryManager {
	
	    @Autowired()
	    IDao dao;

	    public DirectoryManager() {
	    }

		@Override
		public Person findPerson(User user, String personId) {
	        Person p = dao.find(Person.class, personId);
	        
	        if (p == null)
	            return p;
	        
	        // masque le mot de passe si l'utilisateur n'est pas
	        // la personne selectionnee
	        if (!user.getId().equals(personId)) {
	        	
	        	p.setPassword(null);
	        	
		        // empeche l'utilisateur de voir adresse et date de naissance
		        // si il est anonyme
		        if (user.getId().equals("Anonymous")) {
		        	p.setAddress(null);
		        	p.setBirthday(null);
		        }
	        }
	        
	        return p;
		}

		@Override
		public Group findGroup(long groupId) {
			Group g = dao.find(Group.class, groupId);
	        
	        return g;
		}
		
		@Override
		public Collection<Person> findAllPersons(User user, String input) {
			Collection<Person> persons = dao.findByStringProperty(Person.class, "name", "%" + input + "%");
	        
	        // masque le mot de passe meme si ce dernier n'est pas affiche
        	for (Person person : persons) {
        		person.setPassword(null);
        	}
	        	
	        // empeche l'utilisateur de voir adresse et date de naissance
	        // si il est anonyme
	        if (user.getId().equals("Anonymous")) {
	        	for (Person person : persons) {
	        		person.setAddress(null);
	        		person.setBirthday(null);
	        	}
	        }
			
	        return persons;
		}
		
		@Override
		public Collection<Group> findAllGroups(String input) {
			Collection<Group> groups = dao.findByStringProperty(Group.class, "name", "%" + input + "%");
			
	        return groups;
		}

		@Override
		public Collection<Person> findAllPersons(User user, long groupId) {
			Collection<Person> persons = dao.findAllPersonsInGroup(groupId);
	        
	        // masque le mot de passe meme si ce dernier n'est pas affiche
        	for (Person person : persons) {
        		person.setPassword(null);
        	}
	        	
	        // empeche l'utilisateur de voir adresse et date de naissance
	        // si il est anonyme
	        if (user.getId().equals("Anonymous")) {
	        	for (Person person : persons) {
	        		person.setAddress(null);
	        		person.setBirthday(null);
	        	}
	        }
			
	        return persons;
		}

		@Override
		public boolean login(User user, String personId, String password) {
			Person person = dao.find(Person.class, personId);
			
			if (person != null) {
				if (person.getPassword().equals(password)) {
					user.setId(person.getId());
					user.setName(person.getName());
					user.setFirstname(person.getFirstname());
					
					return true;
				}
			}
			
			return false;
		}

		@Override
		public void logout(User user) {
	    	user.setId("Anonymous");
	    	user.setName("Anonymous");
	    	user.setFirstname("Anonymous");
		}
		
		@Override
		public boolean isConnectedAs(User user, Person person) {
			if (user.getId().equals(person.getId()))
				return true;
			return false;
		}

		@Override
		public Person savePerson(User user, Person p) {
			if (user.getId().equals(p.getId())) {
				return dao.update(p);
			}
			
			return null;
		}

		@Override
		public Collection<Group> findAllGroups() {
			
			return dao.findAll(Group.class);
		}
	}