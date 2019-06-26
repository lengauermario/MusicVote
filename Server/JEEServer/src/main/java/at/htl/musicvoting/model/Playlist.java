package at.htl.musicvoting.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Playlist {

    Long timestamp;
    Long updateId;

    List<Song> songs;

    public Playlist(Long timestamp, List<Song> songs) {
        this.timestamp = timestamp;
        this.songs = songs;
        this.updateId = 0l;
    }
    public void increaseUpdateId(){
        this.updateId++;
    }

    public Long getUpdateId() {
        return updateId;
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
