Deploy :

1) builder les images docker 

se deplacer dans QuizzAPI/
docker build -t quizzendirect-dev/quizapi:latest .

se deplacer dans quizzendirect
docker build -t quizzendirect-dev/qed:latest .

2) modifier son fichier host
sudo gedit /etc/hosts
ajouter à la fin du fichier 
127.0.0.1 quizendirect.info-univ-angers.fr

3)lancer le docker compose :
docker-compose up -d


4) Aller sur son navigateur favori et acceder à quizendirect.info-univ-angers.fr
(sachant que l'on peut toujours acceder à l'application par le localhost)

commandes pratiques :
docker-compose logs
docker-compose restart
