package at.htl.musicvoting.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Playlist {

    Long timestamp;

    List<Song> songs;

    public Playlist(Long timestamp, List<Song> songs) {
        this.timestamp = timestamp;
        this.songs = songs;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
