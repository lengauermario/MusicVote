package at.htl.musicvoting.rest.adapter;

import at.htl.musicvoting.converter.LocalDateTimeXmlAdapter;
import at.htl.musicvoting.model.Song;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.time.format.DateTimeFormatter;

public class SongAdapter {

    public static JsonObject marshall(Song song){
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("id", song.getId());
        json.add("title", song.getTitle());
        json.add("artist", song.getArtist());
        json.add("addedToPlaylist", song.getAddedToPlaylist());
        json.add("votes", song.getVotes());
        return json.build();
    }
}
