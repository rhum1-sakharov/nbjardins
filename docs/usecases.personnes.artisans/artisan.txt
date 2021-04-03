[Artisan]-(Créer un devis)
[Artisan]-(Rechercher un devis)
[Artisan]-(Supprimer un devis)
[Artisan]-(Afficher mes devis)
[Artisan]-(Changer le statut d'un devis)
[Artisan]-(Envoyer un devis par mail)

@startuml
actor client
node app
node car
node tshirt

car -> app
app -> client
client -> tshirt
@enduml