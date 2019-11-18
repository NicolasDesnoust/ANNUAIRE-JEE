package dev.dao;

import java.util.Collection;

import dev.model.Person;

public interface IDao {

	// récupérer toutes les entites d'une classe
	public <T> Collection<T> findAll(Class<T> clazz);

	// récupérer les entites de la classe clazz d'un groupe
	public Collection<Person>  findAllPersonsInGroup(long idGroup);

	// lire une instance de la classe clazz et d'identifiant id
	public <T> T find(Class<T> clazz, Object id);

	// ajoute une entite
	public <T> T add(T entity);
	
	// met a jour une entite
	public <T> T update(T entity);

	public <T> Collection<T> findByStringProperty(Class<T> clazz, String propertyName, String propertyValue);
}