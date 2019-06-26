# MusicVoting - Bedienungsanleitung

### 1. Repository clonen

```bash
git clone https://github.com/lengauermario/MusicVote.git
```

### 2. Docker-Netzwerk starten

Für diese Aufgabe existiert bereits ein Shell-Script!

```bash
chmod +x startup.sh
./startup.sh
```

Folgende Eingaben sind erforderlich:

```
ip-address of Server
192.186.1.101 (Beispielswert, ifconfig für eigene IP-Adresse)
```

```
init of database: (true/false)
true (im Normalfall immer true nehmen)
```

```
password for admin:
Grillfest2019 (Passwort für die Abspielseite)
```

```
folder of mp3 files:
/home/leonie/mp3/playlist (Dieser Wert passt für diesen Computer)
```

```
WLAN SSID:
FRITZ!Box
```

```
WLAN password:
0000
```

Nun wird der Server sowie die Client-Projekte gebuildet und in einem Docker-Netzwerk gestartet!



### 3. Webseiten aufrufen

###### Beitrittseite

Um den Gästen einen einfachen Zugang zu MusicVoting zu ermöglichen, steht eine Website unter dem Port 8083 zur Verfügung.

![](./images/beitrittseite.png)



###### Abspielseite

Auf dem Computer, mit dem die Musik abgespielt werden soll, muss die Website auf dem Port 8082 aufgerufen werden.

![](./images/abspielseite.png)



###### Mobile Client

Gäste können auf der Website unter dem Port 8081 für die Musik abstimmen. Auf diese Seite gelangen sie auch mithilfe eines QR-Codes auf der Betrittseite!

<img src="./images/mobileclient.png" width="250px">


