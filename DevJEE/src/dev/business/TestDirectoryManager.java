package dev.business;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collections;
import java.util.Date;
import java.util.List;
//import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.dao.IDao;
import dev.dao.SpringConfiguration;
import dev.model.*;
import dev.web.User;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@ActiveProfiles("DirectoryManager-test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestDirectoryManager {
	
	@Autowired
	IDirectoryManager manager;
	
	@Autowired
	IDao dao;
	
	private User anonymous, nicolas, victoria;
	private Person person, person2, person3;
	private Group group, group2;
	static Date date, date2;

	@BeforeClass
	public static void beforeAll() {
		date = new Date(1000000);
		date2 = new Date(2000000);
	}

	@AfterClass
	public static void afterAll() {
		
	}

	@Before
	public void setUp() {
		anonymous = new User();
		nicolas = new User();
		victoria = new User();
		
		nicolas.setId("d23451265");
		nicolas.setName("DESNOUST");
		nicolas.setFirstname("Nicolas");
		
		victoria.setId("n47580304");
		victoria.setName("NIABA");
		victoria.setFirstname("Victoria");
		
		group = new Group("M1");
		group.setId((long) 1);
		group2 = new Group("M2");
		group2.setId((long) 2);
		
		person = new Person("d23451265", "DESNOUST", "Nicolas",
				"nicolas.desnoust@etu.univ-amu.fr", "www.sitewebnico.com", 
				  date, "mdpdenico");

		person2 = new Person("n47580304", "NIABA", "Victoria",
				"victoria.niaba@etu.univ-amu.fr", "www.sitewebvitou.com", 
				  date2, "mdpdevitou");
		
		person3 = new Person("d23451265", "DESNOUST", "Nico",
				"nico.desnoust@etu.univ-amu.fr", "www.siteweb.com", 
				  date, "mdpdenic");
	}

	@After
	public void tearDown() {
		// pour plus tard
	}
	
    @Test
    public void testGetPersonAsAnonymous() {
    	
    	// GIVEN
        Mockito.when(dao.find(Person.class, person.getId()))
            .thenReturn(person);

        // WHEN 
        Person result = manager.findPerson(anonymous, person.getId());

        // THEN
        assertEquals(result.getPassword(), null);
        assertEquals(result.getBirthday(), null);
        assertEquals(result.getAddress(), null);
        assertEquals(result.getId(), person.getId());

    }
    
    @Test
    public void testGetPersonAsHimself() {
    	
    	// GIVEN
        Mockito.when(dao.find(Person.class, person.getId()))
            .thenReturn(person);

        // WHEN
        Person result = manager.findPerson(nicolas, person.getId());

        // THEN
        assertEquals(result.getBirthday(), person.getBirthday());
        assertEquals(result.getAddress(), person.getAddress());
        assertEquals(result.getPassword(), person.getPassword());
        assertEquals(result.getId(), person.getId());

    }
    
    @Test
    public void testGetPersonAsSomeoneElse() {
    	
    	// GIVEN
        Mockito.when(dao.find(Person.class, person.getId()))
            .thenReturn(person);

        // WHEN 
        Person result = manager.findPerson(victoria, person.getId());

        // THEN
        assertEquals(result.getBirthday(), person.getBirthday());
        assertEquals(result.getAddress(), person.getAddress());
        assertEquals(result.getPassword(), null);
        assertEquals(result.getId(), person.getId());

    }
    
    @Test
    public void testGetGroup() {
    	
    	// GIVEN
        Mockito.when(dao.find(Group.class, group.getId()))
            .thenReturn(group);

        // WHEN 
        Group result = manager.findGroup(group.getId());

        // THEN
        assertEquals(result.getName(), group.getName());

    }
    
	// retourne true si un groupe appartient a une collection de groupes, sinon false
	private boolean groupIsInside (Group groupToFind, Collection<Group> list) {
		long id = groupToFind.getId();

		for (Group group : list) {
			if (group.getId() == id)
				return true;
		}
		return false;
	}
    
    @Test
    public void testGetAllGroups() {
    	Collection<Group> groups = new ArrayList<Group>();
    	groups.add(group);
    	groups.add(group2);
    	
    	// GIVEN
        Mockito.when(dao.findAll(Group.class))
            .thenReturn(groups);

        // WHEN 
        Collection<Group> result = manager.findAllGroups();

        // THEN
        assertTrue(result.size() == 2);
        assertTrue(groupIsInside (group, result));
        assertTrue(groupIsInside (group2, result));

    }
    
	// retourne true si une personne appartient a une collection de personnes, sinon false
	private boolean personIsInside (Person personToFind, Collection<Person> list) {
		String id = personToFind.getId();
		for (Person person : list) {
			if (person.getId().equals(id))
				return true;
		}
		return false;
	}
	
    @Test
    public void testGetAllPersonsAsAnonymous() {
    	Collection<Person> persons = new ArrayList<Person>();
    	persons.add(person);
    	persons.add(person2);
    	
    	// GIVEN
        Mockito.when(dao.findAllPersonsInGroup((long) 1))
            .thenReturn(persons);

        // WHEN 
        Collection<Person> result = manager.findAllPersons(anonymous, (long) 1);

        // THEN
        assertTrue(result.size() == 2);
        assertTrue(personIsInside (person, result));
        assertTrue(personIsInside (person2, result));
        assertEquals(person.getAddress(), null);
        assertEquals(person.getBirthday(), null);
        assertEquals(person.getPassword(), null);
        assertEquals(person2.getAddress(), null);
        assertEquals(person2.getBirthday(), null);
        assertEquals(person2.getPassword(), null);

    }
    
    @Test
    public void testGetAllPersonsAsLoggedIn() {
    	Collection<Person> persons = new ArrayList<Person>();
    	persons.add(person);
    	persons.add(person2);
    	
    	// GIVEN
        Mockito.when(dao.findAllPersonsInGroup((long) 1))
            .thenReturn(persons);

        // WHEN 
        Collection<Person> result = manager.findAllPersons(anonymous, (long) 1);

        // THEN
        assertTrue(result.size() == 2);
        assertTrue(personIsInside (person, result));
        assertTrue(personIsInside (person2, result));
        assertEquals(person.getPassword(), null);
        assertEquals(person2.getPassword(), null);

    }
    
    @Test
    public void testLoginSuccess() {
    	
    	// GIVEN
        Mockito.when(dao.find(Person.class, person.getId()))
            .thenReturn(person);

        // WHEN 
        boolean connected = manager.login(anonymous, person.getId(), person.getPassword());

        // THEN
        assertEquals(anonymous.getId(), person.getId());
        assertEquals(anonymous.getName(), person.getName());
        assertEquals(anonymous.getFirstname(), person.getFirstname());
        assertTrue(connected);
    }
    
    @Test
    public void testLoginFail() {
    	
    	// GIVEN
        Mockito.when(dao.find(Person.class, person.getId()))
            .thenReturn(person);

        // WHEN 
        boolean connected = manager.login(anonymous, person.getId(), "");

        // THEN
        assertEquals(anonymous.getId(), "Anonymous");
        assertEquals(anonymous.getName(), "Anonymous");
        assertEquals(anonymous.getFirstname(), "Anonymous");
        assertFalse(connected);

    }
    
    @Test
    public void testLogout() {

        // WHEN 
        manager.logout(nicolas);

        // THEN
        assertEquals(nicolas.getId(), anonymous.getId());
        assertEquals(nicolas.getName(), anonymous.getName());
        assertEquals(nicolas.getFirstname(), anonymous.getFirstname());

    }
    
    @Test
    public void testSavePersonSuccess() {

    	// GIVEN
        Mockito.when(dao.update(person))
        	.thenReturn(person3);
    	
        // WHEN 
        Person result = manager.savePerson(nicolas, person);

        // THEN
        assertEquals(result.getName(), person3.getName());

    }
    
    @Test
    public void testSavePersonFail() {

    	// GIVEN
        Mockito.when(dao.update(person))
        	.thenReturn(person3);
    	
        // WHEN 
        Person result = manager.savePerson(anonymous, person);

        // THEN
        assertEquals(result, null);

    }
    
}