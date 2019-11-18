package dev.business;

import java.util.Collection;

import dev.model.Group;
import dev.model.Person;
import dev.web.User;

public interface IDirectoryManager {

    // chercher une personne
    Person findPerson(User user, String personId);

    // chercher un groupe
    Group findGroup(long groupId);

    // chercher les personnes d'un groupe
    Collection<Person> findAllPersons(User user, long groupId);
    
    // chercher les groupes
    Collection<Group> findAllGroups();

    // identifier un utilisateur
    boolean login(User user, String personId, String password);

    // oublier l'utilisateur
    void logout(User user);

    // enregistrer une personne
    Person savePerson(User user, Person p);

	boolean isConnectedAs(User user, Person person);

	Collection<Person> findAllPersons(User user, String input);

	Collection<Group> findAllGroups(String input);

}