# JakarteEE Server

Dies ist das endgültige Projekt für das Backend des Projekts. Alle Meta-Daten der Mp3-Files, die lokal auf dem Rechner vorhanden sind, werden in einer Datenbank festgehalten und stehen für eine Suche vonseiten der Clients zur Verfügung. Jegliche Verwaltung der Playlist wird von diesem Server aus gesteuert. Clients verbinden sich mittles SSE-Eventsource und erhalten Nachrichten sobald sich irgendetwas an der Playlist ändert. Für das Voting ist keinerlei Authentifizierung nötig, wodurch aber auch nicht serverseitig überprüft wird, wer wie viele Votes abgibt. Dies muss clientseitig behandelt werden. Der Admin muss sich allerdings authentifizieren (das Passwort wird im config.properties File gesetzt, mehr dazu im README.md des Repositories) und erhält als Einziger das Recht, Lieder aus der Playlist zu löschen und Musiktitel abzuspielen.

# Entwickler
- Leon Kuchinka
- Jonas Schürz (Initialisierung der Datenbank)
