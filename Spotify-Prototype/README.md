# Spotify - Prototyp

Dies ist ein Prototyp mit dem die Authentifizierung, das Abfragen und Abspielen von Songs beschränkt möglich ist. Bei der Realisierung
wurde die [Spotify Web API](https://developer.spotify.com/documentation/web-api/) verwendet, die die Authentifizierung bereitgestellt. 
Für das Abspielen der Musik wurde die [Spotify Web Playback SDK](https://developer.spotify.com/documentation/web-playback-sdk/quick-start/) verwendet, wo es aktuell noch Probleme gibt.  

### Fazit zur Web API und zum Web Playback SDK
Zum jetzigen Zeitpunkt ist es möglich sich mit seinem Spotify Premium Account einzuloggen
und mit einem anderen Gerät den Browser als aktuelles Gerät festzulegen, um dann Musik abspielen zu lassen.
Leider habe ich dafür noch keine Lösung gefunden, dass sich der Browser selbst als aktuelles Gerät festlegt.  
Ein bereits gelöstes Problem war, dass das Anmelden und Abspielen der Musik nur dann von Spotify
bewilligt wird, wenn man den Login-Request per Server schickt. Deshalb läuft der Prototyp einstweilen auf node.js
    
### Entwickler des Prototyps:
Mario Lengauer



## Prototyp starten
Da der Login bzw. das Abspilen der Musik nur über abfragen des Servers bewilligt wird, wird ein node.js Server benötigt

    $ cd authorization_code
    $ node app.js

Öffne `http://localhost:8888`
