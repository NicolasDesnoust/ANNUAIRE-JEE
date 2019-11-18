# ANNUAIRE-JEE

Pour tester ce projet:
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

