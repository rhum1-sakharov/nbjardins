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

* core

      domain        
      usecase

* adapters  
    
      primaries
         application
           springapp
                    
      secondaries
         mails




###Front, angular

####Remarque
Les fichiers finissant par **-dist** doivent être copiés et renommé sans le **-dist**.