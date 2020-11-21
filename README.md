# nbjardins 

Application pour un jardinier qui démarrer son activité.

L'application doit présenter les activités du jardinier aux internautes et envoyer les demandes de devis au jardinier.

### Lancer l'implementation springboot (back)

```bash

mvn clean install

cd application/springapp

mvn clean package spring-boot:repackage

```

###Back, réalisé en clean architecture

* domain
* usecase
* adapter
* config
* application

L'application dépend de la config.

La config dépend de l'adapter.

L'adapter dépend du usecase.

Le usecase dépend du domain.


**Toute dépendance dans le sens inverse est interdite !!!**

###Front, angular

####Remarque
Les fichiers finissant par **-dist** doivent être copiés et renommé sans le **-dist**.