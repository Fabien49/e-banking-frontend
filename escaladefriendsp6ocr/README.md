#Projet 6: Les Amis de l'Escalade

##Build packaging
```
mvm clean package
```

## Run
```
cd target
java -jar nom_du_fichier.jar
```

##Start Web page
- http://localhost:8080

##Commande de base

git add .
git status
git commit -m "message"
git push (origin master)

mvn spring-boot:run

## Application.propertie

```
#PROD
#spring.profiles.active=prod
#DEV
spring.profiles.active=prod
```