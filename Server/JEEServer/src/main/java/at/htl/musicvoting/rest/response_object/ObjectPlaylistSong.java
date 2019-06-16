package at.htl.musicvoting.rest.response_object;

import at.htl.musicvoting.converter.LocalDateTimeXmlAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDateTime;

public class ObjectPlaylistSong {
    private Long id;
    private String title;
    private String artist;
    private String thumbNail;
    private int votes;
    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private Long time;

    public ObjectPlaylistSong(Long id, String title, String artist, String thumbNail, int votes, Long time) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.thumbNail = thumbNail;
        this.votes = votes;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
