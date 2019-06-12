# MusicVoting :notes:

Repository für MusicVoting v1 (Einschränkung auf lokale Mp3-Files)

## :bookmark_tabs: Inhaltsverzeichnis
- [Einleitung](#arrow_forward-einleitung)
- [Verwendung](#rocket-verwendung)
- [System Architektur](#ferris_wheel-system-architektur)
- [Aktueller Stand](#hammer-aktueller-stand)
- [Entwickler](#guardsman-entwickler)
- [Technologien](#wrench-technologien)
- [Lizenz](#lock-lizenz)

## :arrow_forward: Einleitung
Was ist nerviger als schlechte Musik auf einer Party? 
Weil wir finden, dass es nichts Schlimmeres gibt, entwickeln wir MusicVoting. MusicVoting ermöglicht jedem Partygast ganz einfach mittels Smartphone mitzubestimmen welche Musik gespielt werden soll.

## :rocket: Verwendung
Folgende Tools müssen installiert sein:
- maven
- docker
- docker-compose 

https://get.docker.com/ stellt ein Script zur Installation von Docker auf Linux zur Verfügung.

Starten des Systems:
- Builden des Servers:
```
cd /Server/JEEServer
mvn install -DskipTests
```

Konfiguration:
- Setzen der richtigen IP-Adresse für die Clients (.env-Files in den Projektordnern des MobilClients und des Abspielclients):
```
VUE_APP_API_URL=http://<IP-Adresse>:8080/musicvoting/api
```

- Konfigurationsfile am Server:
  - Erstellen des Files config.properties im Ordner Server\JEEServer\src\main\resources
  - Inhalt:
```
init=true
password=<PASSWORT FÜR ADMIN>
startFolder=<ORDNER IN DEM MP3 FILES LIEGEN>
```


- Builden und Starten des Docker-Netzwerks
```
docker-compose build
docker-compose up
```


## :ferris_wheel: System Architektur
![sysarch](/img/systemarchitektur.png)

## :hammer: Aktueller Stand

Version v1.0.0 von MusicVoting (Einschränkung auf lokale Mp3-Files) ist bereit für den Einsatz beim Grillfest der HTL Leonding!


## :guardsman: Entwickler:
- [Leon Kuchinka](https://github.com/leonkuchinka)
- [Mario Lengauer](https://github.com/lengauermario)
- [Jonas Schürz](https://github.com/jonasschuerz)

## :wrench: Technologien:

- Jakarta EE
- Derby DB
- HTML5
- CSS
- JavaScript
- VueJS
- Docker


## :lock: Lizenz:

Copyright (c) 2019 MusicVoting </br>
Hiermit wird unentgeltlich jeder Person, die eine Kopie der Software und der zugehörigen Dokumentationen (die "Software") erhält, die Erlaubnis erteilt, sie uneingeschränkt zu nutzen, inklusive und ohne Ausnahme mit dem Recht, sie zu verwenden, zu kopieren, zu verändern, zusammenzufügen, zu veröffentlichen, zu verbreiten, zu unterlizenzieren und/oder zu verkaufen, und Personen, denen diese Software überlassen wird, diese Rechte zu verschaffen, unter den folgenden Bedingungen:

Der obige Urheberrechtsvermerk und dieser Erlaubnisvermerk sind in allen Kopien oder Teilkopien der Software beizulegen.

DIE SOFTWARE WIRD OHNE JEDE AUSDRÜCKLICHE ODER IMPLIZIERTE GARANTIE BEREITGESTELLT, EINSCHLIEẞLICH DER GARANTIE ZUR BENUTZUNG FÜR DEN VORGESEHENEN ODER EINEM BESTIMMTEN ZWECK SOWIE JEGLICHER RECHTSVERLETZUNG, JEDOCH NICHT DARAUF BESCHRÄNKT. IN KEINEM FALL SIND DIE AUTOREN ODER COPYRIGHTINHABER FÜR JEGLICHEN SCHADEN ODER SONSTIGE ANSPRÜCHE HAFTBAR ZU MACHEN, OB INFOLGE DER ERFÜLLUNG EINES VERTRAGES, EINES DELIKTES ODER ANDERS IM ZUSAMMENHANG MIT DER SOFTWARE ODER SONSTIGER VERWENDUNG DER SOFTWARE ENTSTANDEN.

