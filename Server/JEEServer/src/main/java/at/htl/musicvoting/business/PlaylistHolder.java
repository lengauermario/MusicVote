package at.htl.musicvoting.business;

import at.htl.musicvoting.model.Playlist;
import at.htl.musicvoting.model.Song;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class PlaylistHolder {

    private Playlist playlist;

    @PostConstruct
    public void init(){
        playlist = new Playlist(getTimestamp(), new LinkedList<>());
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void updatePlaylist(List<Song> songs){
        playlist.increaseUpdateId();
        playlist.setTimestamp(getTimestamp());
        playlist.setSongs(songs);
    }

    private long getTimestamp(){
        return System.currentTimeMillis();
    }
}
