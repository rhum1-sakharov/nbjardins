# nbjardins 


### Lancer l'implementation springboot (back)

```bash

mvn clean install

cd application/springapp

mvn clean package spring-boot:repackage

```

#Back, réalisé en clean architecture

* domain
* usecase
* adapter
* config
* application

L'application dépend de la config
La config dépend de l'adapter
L'adapter dépend du usecase
Le usecase dépend du domain

#Front, angular
