# MusicVoting :notes:

Repository für MusicVoting v1 (Einschränkung auf lokale Mp3-Files)

## :bookmark_tabs: Inhaltsverzeichnis
- [Einleitung](#arrow_forward-einleitung)
- [Verwendung](#rocket-verwendung)
- [Aktueller Stand](#hammer-aktueller-stand)
- [Dokumente](#closed_book-dokumente)
- [Entwickler](#guardsman-entwickler)
- [Technologien](#wrench-technologien)
- [Lizenz](#lock-lizenz)

## :arrow_forward: Einleitung
Was ist nerviger als schlechte Musik auf einer Party? 
Weil wir finden, dass es nichts Schlimmeres gibt, entwickeln wir MusicVoting. MusicVoting ermöglicht jedem Partygast ganz einfach mittels Smartphone mitzubestimmen welche Musik gespielt werden soll.

## :ferris_wheel: Systemarchitektur
![](/docs/depl_diagram.png)

## :rocket: Verwendung
Folgende Tools müssen installiert sein:
- maven
- docker
- docker-compose 

https://get.docker.com/ stellt ein Script zur Installation von Docker auf Linux zur Verfügung.

Für das Builden und Starten des Systems gibt es ein Shell-Script im Root des Repositories:
```
chmod +x startup.sh
 ./startup.sh
```
Folgende Inputs eingeben: 
(z.B.:)
```
ip-address of Server
192.168.1.100 
init of database: (true/false)
true
password for admin:
passme
folder of mp3 files:
/home/mp3
WLAN SSID:
MyWlan
WLAN password:
passme
```

Nach diesen Eingaben wird eine Betrittseite (Port 8083), der AbspielClient (Port: 8082) und der Mobile Client (Port: 8081) sowie der Server gebuildet und gestartet. Ab diesem Zeitpunkt ist MusicVoting einsatzbereit. Die Beitrittseite auf Port 8083 kann auf einem Bildschirem für die Gäste angezeigt werden. Diese können sich dann per QR Code mit dem WLAN verbinden und auf die Mobile Client Seite gelangen.

## :hammer: Aktueller Stand

Version v1.0.0 von MusicVoting (Einschränkung auf lokale Mp3-Files) ist bereit für den Einsatz beim Grillfest der HTL Leonding!

##  :closed_book: Dokumente:	
- [Systemarchitektur](/docs/Systemarchitektur.pdf)
- [UseCase-Diagramm](/docs/usecase.pdf)

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

