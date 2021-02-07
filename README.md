Deploy :

1) modifier son fichier host
sudo gedit /etc/hosts
ajouter à la fin du fichier 
127.0.0.1 quizendirect.info-univ-angers.fr

2) Lancer le docker compose, il build maintenant les images pour vous:
docker-compose up -d


3) Aller sur son navigateur internet favori et acceder à quizendirect.info-univ-angers.fr
(sachant que l'on peut toujours acceder à l'application par le localhost)

commandes pratiques :
docker-compose logs -f
docker-compose restart
docker-compose up -d --build ( api | frontapp | proxy )
