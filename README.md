# MusicVoting :notes:

## :bookmark_tabs: Inhaltsverzeichnis
- [Einleitung](#arrow_forward-einleitung)
- [System Architektur](#ferris_wheel-system-architektur)
- [Aktueller Stand](#hammer-aktueller-stand)
  - [GUI](#iphone-gui)
  - [Youtube-Einbindung](#musical_note-youtube-einbindung)
  - [Server](#computer-server)
- [Dokumente](#closed_book-dokumente)
- [Entwickler](#guardsman-entwickler)
- [Technologien](#wrench-technologien)
- [Lizenz](#lock-lizenz)

## :arrow_forward: Einleitung
Was ist nerviger als schlechte Musik auf einer Party? Und weil wir auch finden, dass es nichts Schlimmeres gibt, entwickeln wir MusicVoting. MusicVoting ermöglicht jedem Partygast ganz einfach mittels Smartphone mitzubestimmen welche Musik gespielt werden soll.


## :ferris_wheel: System Architektur
![sysarch](/img/systemarchitektur.png)

## :hammer: Aktueller Stand

### :iphone: GUI
#### 🛠 Status: In Arbeit
Die GUI befindet sich momentan noch in Entwicklung. Alles zum aktuellen GUI-Prototypem finden Sie [hier](/GUI_Prototype/my-app).

### :musical_note: Youtube-Einbindung
#### 🛠 Status: In Arbeit
Die vollständige Abwicklung der Youtube abfragen und der Download der entsprechenden Audiodatei funktioniert derzeit nur im Rahmen eines Prototypen in dem die Machbarkeit der Youtube-Suche ohne Authentifikation des jeweiligen Users bestätigt wurde. Alle weiteren Informationen sind [hier](/Youtube-Server-Prototyp).

### :computer: Server
#### 🛠 Status: In Arbeit
Die Initialisierung der Datenbank des Servers ist bereits implementiert, siehe [hier](/Initialize_Database/Start_RF_new). Die Bereitstellung der MP3-Files mittels RESTful Service befindet sich im Prototypstadium, d.h. es existiert eine Methode die hardcoded ein bestimmtes MP3-File zur Verfügung stellst. Ein Clientprototyp der dieses MP3-File abfragt und abspielt ist auch bereits implementiert. Weiter Details befinden sich [hier](/Abspiel%20Server-Client%20Prototyp).

##  :closed_book: Dokumente:

- [Projektantrag](docs/Projektantrag.pdf)
- [Projektspezifikation](docs/projektspezifikation.md)
- [Systemarchitektur](/img/systemarchitektur.png)


## :guardsman: Entwickler:
- [Leon Kuchinka](https://github.com/leonkuchinka)
- [Mario Lengauer](https://github.com/lengauermario)
- [Jonas Schürz](https://github.com/jonasschuerz)

## :wrench: Technologien:

- Jakarta EE
- MySQL
- HTML5
- Bulma
- CSS
- JavaScript
- Polymer LitElement


## :lock: Lizenz:

Copyright (c) 2019 MusicVoting </br>
Hiermit wird unentgeltlich jeder Person, die eine Kopie der Software und der zugehörigen Dokumentationen (die "Software") erhält, die Erlaubnis erteilt, sie uneingeschränkt zu nutzen, inklusive und ohne Ausnahme mit dem Recht, sie zu verwenden, zu kopieren, zu verändern, zusammenzufügen, zu veröffentlichen, zu verbreiten, zu unterlizenzieren und/oder zu verkaufen, und Personen, denen diese Software überlassen wird, diese Rechte zu verschaffen, unter den folgenden Bedingungen:

Der obige Urheberrechtsvermerk und dieser Erlaubnisvermerk sind in allen Kopien oder Teilkopien der Software beizulegen.

DIE SOFTWARE WIRD OHNE JEDE AUSDRÜCKLICHE ODER IMPLIZIERTE GARANTIE BEREITGESTELLT, EINSCHLIEẞLICH DER GARANTIE ZUR BENUTZUNG FÜR DEN VORGESEHENEN ODER EINEM BESTIMMTEN ZWECK SOWIE JEGLICHER RECHTSVERLETZUNG, JEDOCH NICHT DARAUF BESCHRÄNKT. IN KEINEM FALL SIND DIE AUTOREN ODER COPYRIGHTINHABER FÜR JEGLICHEN SCHADEN ODER SONSTIGE ANSPRÜCHE HAFTBAR ZU MACHEN, OB INFOLGE DER ERFÜLLUNG EINES VERTRAGES, EINES DELIKTES ODER ANDERS IM ZUSAMMENHANG MIT DER SOFTWARE ODER SONSTIGER VERWENDUNG DER SOFTWARE ENTSTANDEN.

