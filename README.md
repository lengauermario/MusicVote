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
#### 🛠 Status: Fertig
Die Einbinding von Youtube funktioniert bereits in vollem Umfang. User können nach Youtube Videos suchen und herunterladen und zur Playlist hinzufügen.

### :computer: Server
#### 🛠 Status: In Arbeit
Der Server befindet sich bereits in einem fortgeschrittenen Stadium. Clients können sich beim Server conntected, werden dann von Änderungen der Playlist per SSE informiert. Weitere Informationen befinden sich [hier](/Server).

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
- Derby
- HTML5
- CSS
- JavaScript
- Polymer LitElement


## :lock: Lizenz:

Copyright (c) 2019 MusicVoting </br>
Hiermit wird unentgeltlich jeder Person, die eine Kopie der Software und der zugehörigen Dokumentationen (die "Software") erhält, die Erlaubnis erteilt, sie uneingeschränkt zu nutzen, inklusive und ohne Ausnahme mit dem Recht, sie zu verwenden, zu kopieren, zu verändern, zusammenzufügen, zu veröffentlichen, zu verbreiten, zu unterlizenzieren und/oder zu verkaufen, und Personen, denen diese Software überlassen wird, diese Rechte zu verschaffen, unter den folgenden Bedingungen:

Der obige Urheberrechtsvermerk und dieser Erlaubnisvermerk sind in allen Kopien oder Teilkopien der Software beizulegen.

DIE SOFTWARE WIRD OHNE JEDE AUSDRÜCKLICHE ODER IMPLIZIERTE GARANTIE BEREITGESTELLT, EINSCHLIEẞLICH DER GARANTIE ZUR BENUTZUNG FÜR DEN VORGESEHENEN ODER EINEM BESTIMMTEN ZWECK SOWIE JEGLICHER RECHTSVERLETZUNG, JEDOCH NICHT DARAUF BESCHRÄNKT. IN KEINEM FALL SIND DIE AUTOREN ODER COPYRIGHTINHABER FÜR JEGLICHEN SCHADEN ODER SONSTIGE ANSPRÜCHE HAFTBAR ZU MACHEN, OB INFOLGE DER ERFÜLLUNG EINES VERTRAGES, EINES DELIKTES ODER ANDERS IM ZUSAMMENHANG MIT DER SOFTWARE ODER SONSTIGER VERWENDUNG DER SOFTWARE ENTSTANDEN.

