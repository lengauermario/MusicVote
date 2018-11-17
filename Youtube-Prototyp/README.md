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

### Entwickler des Prototyps:
Leon Kuchinka
