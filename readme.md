# sp19 - projet de groupe évalué n°1

Mode de travail :	Groupes de 2 personnes. Les groupes travaillent séparément.

Délai : 	29 avril à 23h55 (dernier commit sur github)

## Koulutus
Koulutus (apprendre en Finnois) est une api pour la gestion de formations continues utilisée par des collectivités publiques et des associations. 
Vous avez reçu la spécification OAS3 de l'api et votre rôle consiste à l'implémenter à l'aide de JAX-RS à l'aide du squelette de projet fourni.

## Travail attendu
Votre mission est de 
-	Réaliser l’implémentation de l’API REST Koulutus. Toutes les méthodes indiquées dans la spécification doivent être 
implémentées conformément à cette dernière.
-	Ajouter des tests unitaires pour les différentes classes métiers du projet (à l’exception des getter/setter simples).
-	Ajout de logs : les modifications apportées aux ressources et les différentes erreurs doivent être consignées dans des fichiers logs.

## Livrables
 -  Sources : toutes les sources de l’application, sur github
 - Artefact déployable : un fichier .war de la dernière version de votre application dans le dossier finalwar.
 - Collection postman permettant de tester la bonne fonctionnalité de l'API.


## Dimensions d’évaluation
   ### 1.	Couverture fonctionnelle
   1.1.	Les spécifications de l’API sont entièrement couvertes. Les points non couverts sont documentés le cas échéant.
   
   ### 2.	Architecture de l’application et implémentation
   2.1.	Qualité du code (clean code, lisible et compréhensible, utilisation de l’encapsulation, refactoring pour travailler à des niveaux d’abstraction homogènes, boy scout rule)
   
   2.2.	Respect des principes RESTful
   
   2.3.	Bonne utilisation du Framework JAX-RS.
   
   2.4.	Respect des conventions de style et de nommage 
   
   ### 3.	Gestion des erreurs
   3.1.	Les différents cas de figure sont pris en compte par l’application (valeurs erronées ou manquantes, états incorrects) conformément à la spécification de l’API. En cas d’erreur, l’application la consigne, informe l’utilisateur et réagit de manière adéquate (utilisation des exceptions et des messages).
   
   ### 4.	Méthode de travail
   4.1.	Utilisation des issues. Les tâches identifiées par le groupe ainsi que les bugs et autres fixes sont suivis par la création d’issues sur GitHub. Ces issues sont traitées au fur et à mesure du développement.
   
   4.2.	Suivi de version : l’ensemble du développement est sous contrôle de version en suivant la logique du GitHub flow. Les commits sont faits de manière atomique. Le travail est intégré avec des pull requests.
   
   4.3.	Documentation : L’application est documentée dans le readme. La méthode de développement peut être documentée dans des fichiers annexes, aussi sur GitHub. Le code est documenté à un niveau de détail pertinent. 
