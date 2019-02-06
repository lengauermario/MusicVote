# JakarteEE Server

This is the final project for our server, all the functionality is not split up to the different prototypes anymore. 
All the functionality will be implemented here. 

# Status
The server can already store all the meta data of the local mp3 files in the database, search for Youtube-Videos, Download them and
control a playlist. For all these functions, there are rest endpoint to call them. 

# Getting started
In order to start the server you need to have a Wildfly configured with a Derby datasource called DbDS. You need to have a property file called 
'youtube.properties' in your resource folder with following properties:

```
apikey={YOUR_APIKEY}
```

You also have to have a file called 'config.properties' in the same folder where the following properties can be found:
```
init=true //whether you whant to init your database at startup
startFolder=C:\\ ... //the folder where your mp3 files are
youtubeFolder=C:\\ ... //the folder where you want to store the downloaded youtube videos
```
If you haven't downloaded youtube-dl, have a look [here](https://github.com/lengauermario/MusicVote/tree/master/Youtube-Server-Prototyp)

# Known Issues
If two not identical Youtube videos with the exact same title are downloaded, we can run into problems.

# Documentation
Youtube Request do work the same as [here](https://github.com/lengauermario/MusicVote/tree/master/Youtube-Server-Prototyp).
For downloading videos you have to pass the ResponseObject you get at the request in the body. 

to be continued...

# Creator
Leon Kuchinka
Jonas Schürz (Initialization of database)
