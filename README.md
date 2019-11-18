# ANNUAIRE-JEE

![Screenshot de l'écran d'accueil](screenshots/Accueil.png?raw=true "Ecran d'accueil")

## Tester le projet
- Télécharger Eclipse EE 2019.
- Créer un nouveau Projet de type "Dynamic Web Project".
- Convertir le projet à Maven.
- Ajouter la librairie JUnit 5.
- Ajouter au projet un nouveau Server Runtime Environment basé sur Apache Tomcat v9.0.
- Renseigner les informations de connection à un service de gestion de bases de données relationnelles dans les fichiers suivants:
  - DevJEE/src/dev/dao/SpringConfiguration.java (hibernate.dialect)
  - DevJEE/src/config.properties
  NB: la base de données doit être créée au préalable.
- Lancer le fichier DevJEE/src/dev/dao/TestDao.java pour vérifier le bon fonctionnement et peupler la base de données.
- Lancer le fichier DevJEE/WebContent/index.jsp sur le serveur.

## Consignes du projet
L'objectif de ce mini projet est de gérer à l'aide de la technologie JEE un annuaire de personnes. Plus précisément,
- Une personne est représentée par un ensemble d'informations : identifiant, nom, prénom, adresse électronique, site WEB, date de naissance et mot de passe.
- Chaque personne est placée dans un groupe. Un groupe est composé de quelques dizaines de personnes (par exemple les étudiants du M1 ILD 2017/2018). Un groupe a donc un nom et un identifiant.

![Screenshot de l'édition d'un profil](screenshots/Edition.png?raw=true "Edition d'un profil")

- L'application doit présenter une liste de groupes, une liste de personnes de chaque groupe et une vue détaillée de chaque personne (sauf adresse électronique et date de naissance). Une fonction de recherche doit être offerte.

![Screenshot du résultat d'une recherche](screenshots/Recherche.png?raw=true "Recherche")

- L'application doit être fonctionnelle si nous avons plusieurs milliers de personnes et plusieurs centaines de groupes.
- L'application doit permettre à chaque personne de modifier sa propre description.

![Screenshot de l'affichage d'un profil](screenshots/Affichage.png?raw=true "Affichage d'un profil")

- Les personnes présentes dans l'annuaire peuvent avoir accès à toutes les informations (y compris les adresses électroniques et les dates de naissance).

![Screenshot de l'écran de connexion](screenshots/Connexion.png?raw=true "Connexion")
