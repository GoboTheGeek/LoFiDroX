# LoFiDroX
Partager facilement vos fichiers sans utiliser le cloud. 

## En bref
LoFiDroX est prévu pour être hébergé à la maison sur un Raspberry Pi 3+. Cette application Web fonctionne avec Apache TomEE v9, Java 11 et HSqlDB v2.6.

## Stack technique
### Front end
Le front end repose sur 5 composants:
- le classique Bootstrap 5
- Svelte pour l'aspect SPA
- MaterielDesign pour les pictogrammes
- DownloadJS pour permettre le téléchargement des fichiers
- MomentJS pour la gestion des dates et heures

### Back end
Ici, ni Spring ni Hibernate! "Light is right" donc le nombre de composants est réduit:
- Apache Deltaspike pour la partie CDI, ORM et sécurité
- Apache Commons IO, Lang3, Codec pour différents aspects techniques
- Apache OpenJPA pour la gestion des requêtes 
- HSqlDB pour la partie base de données relationnelles
- UrlRewriteFilter pour faciliter la cohabitation de la gestion SPA et les services REST.

## Installation

### Apache TomEE
Rien de spécial à faire pour LoFiDroX, l'installation de base est suffisante. Bien sûr, il faut penser à changer de port, à configurer le HTTPS.

### HSqlDB
Ici, il faut déployer mon application ["HSqlServer"](https://github.com/GoboTheGeek/HSqlServer) dans le serveur TomEE. 

C'est tout, le serveur TomEE est prêt à recevoir LoFiDroX! 

