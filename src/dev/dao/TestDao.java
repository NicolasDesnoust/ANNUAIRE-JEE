package dev.dao;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dev.model.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestDao {

	@Autowired
	IDao dao;
	
	static Date date, date2, date3;
	static Person person, person2, person3;
	static Group group, group2, group3, group4, group5;

	@BeforeClass
	public static void beforeAll() {
		group = new Group ("M1");
		group2 = new Group ("M2");
		group3 = new Group ("M3");
		group4 = new Group ("M4");
		group5 = new Group ("M5");
		
		date = new Date(1000000);
		date2 = new Date(1100000);
		date3 = new Date(1200000);
		
		person = new Person("d23451265", "DESNOUST", "Nicolas",
				"nicolas.desnoust@etu.univ-amu.fr", "https://www.sitewebnico.com", 
				  date, "mdpdenico");

		person2 = new Person("n47580304", "NIABA", "Victoria",
				"victoria.niaba@etu.univ-amu.fr", "https://www.sitewebvitou.com", 
				  date2, "mdpdevitou");
		
		person3 = new Person("d14786254", "DUPONT", "Julien",
				  "julien.dupont@etu.univ-amu.fr", "https://www.sitewebjulien.com", 
				  date3, "mdpjulien");
		
		person.setGroup(group);
		person2.setGroup(group2);
		person3.setGroup(group5);
	}

	@AfterClass
	public static void afterAll() {
		
	}

	@Before
	public void setUp() {
		// pour plus tard
	}

	@After
	public void tearDown() {
		// pour plus tard
	}

	@Test
	public void testAddandFind () {
		dao.add(group);
		dao.add(person);
		
		String expected = person.getId();
		 
		Person result = dao.find(Person.class, expected);
		 
		String value =  result.getId();
		 
		assertEquals (expected, value);
	}
	
	@Test
	public void testUpdate () {
		dao.add(group2);
		dao.add(person2);
		
		String expected = "Vitou";
		person2.setFirstname(expected);
		
		dao.update(person2);
		
		Person result = dao.find(Person.class, person2.getId());
		
		String value = result.getFirstname();
		
		assertTrue (value.equals(expected));
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
	public void testFindAll () {
		dao.add(group3);
		dao.add(group4);
		
		Collection<Group> resultList = dao.findAll(Group.class);
		
		assertTrue(groupIsInside(group3, resultList));
		assertTrue(groupIsInside(group4, resultList));
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
	public void testFindAllPersonsInGroup () {
		dao.add(group5);
		dao.add(person3);
		
		Collection<Person> resultList = dao.findAllPersonsInGroup(group5.getId());
		
		assertTrue(resultList.size() == 1);
		assertTrue(personIsInside(person3, resultList));
	}
}