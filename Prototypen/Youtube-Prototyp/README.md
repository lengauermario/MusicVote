# Youtube - Prototyp

Dies ist ein Prototyp mit dem die Authentifizierung, das Abfragen und Abspielen von Youtube-Videos bereits möglich ist. Bei der Realisierung
wurde die [Youtube-API](https://developers.google.com/youtube/v3/) verwendet, die bereits das komplette Authentifizierung 
bereitgestellt wird. Das Projekt musste bei Google bekannt gegeben werden und läuft derzeit nur am "http://localhost:5500/index.html".

### Fazit zur [Youtube-API](https://developers.google.com/youtube/v3/)
Zum jetztigen Zeitpunkt scheint die umfangreiche API zur Abfrage und zum Abspielen von Youtube-Videos für das Projekt geeignet zu sein,
da noch keine Funktionalität die benötigt wird, nicht vorhanden ist. Alle Funktionalitäten sind umfangreich und mit Beispielen dokumentiert, 
was die Arbeit damit ungemein leicht macht. Die API stellt bereits die komplette Authentifizierung über einen Google-Account zur Verfügung. 
Auch für eine REST-Abfrage steht bereits der ganze Code zur Verfügung. Zum Abspielen der Videos bietet Youtube die 
[Youtube-iFrame-API](https://developers.google.com/youtube/iframe_api_reference?hl=de) an die sich um alles kümmert. Der Player wird asynchron
runtergeladen, sobald er bereit ist, können Videos abgespielt werden, über den Aufruf: player.loadVideoById(<VIDEO_ID>, <START_SECONDS>, <SUGGESTED_QUALITY>);</br>
Der Player feuert bei folgenden 4 Events: onReady(), onPlaybackQualityChange(), onStateChange() und onError() für die kinderleicht 
Eventhandler-Methoden implementiert werden können. Für mehr Informationen zu Youtube-iFrame-API klicken sie bitte [hier](https://developers.google.com/youtube/iframe_api_reference?hl=de)

### Probleme bei der Filterung nach Videos in der Kategorie "Music"
Nach der Einführung der Filterung, konnten folgende Probleme bemerkt werden. Eine Videos haben auf Youtube die Kategorie "Music" ohne 
tatsächlich ein Music-Video zu sein. So zum Beispiel bekommt man folgenden Response wenn man "Kollegah" eingibt:
![Response](https://github.com/lengauermario/MusicVote/blob/master/Youtube-Prototyp/images/searchApiExplorer.PNG)
Die CategoryId wurde hier auf 10 gesetzt, was "Music" entspricht.
![Category](https://github.com/lengauermario/MusicVote/blob/master/Youtube-Prototyp/images/categories.PNG)
Die Vermutung, dass die Filterung nicht funktionieren würde ist auch nicht richitg, da man wenn man genau dieses Video abfragt folgenden
Response erhält.
![Response](https://github.com/lengauermario/MusicVote/blob/master/Youtube-Prototyp/images/videosApiExplorer.PNG)
Nun gibt es die Möglichkeit, dieses Phänomen zu ignorieren oder die Filterung aufzuheben und jedes Video abfragen zu können.

### Entwickler des Prototyps:
Leon Kuchinka
