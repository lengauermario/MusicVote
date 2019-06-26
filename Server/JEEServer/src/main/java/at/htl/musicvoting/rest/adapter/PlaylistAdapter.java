package at.htl.musicvoting.rest.adapter;

import at.htl.musicvoting.model.Playlist;
import at.htl.musicvoting.model.Song;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.time.format.DateTimeFormatter;

public class PlaylistAdapter {
    public static JsonObject marshall(Playlist playlist){
        JsonObjectBuilder json = Json.createObjectBuilder();
        json.add("updateId", playlist.getUpdateId());
        JsonArrayBuilder arr = Json.createArrayBuilder();
        playlist.getSongs().forEach(s -> {
            arr.add(SongAdapter.marshall(s));
        });
        json.add("songs", arr);
        return json.build();
    }
}
