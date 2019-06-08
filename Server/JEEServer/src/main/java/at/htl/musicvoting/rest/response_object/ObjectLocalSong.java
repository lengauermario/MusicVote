package at.htl.musicvoting.rest.response_object;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObjectLocalSong {
    private Long id;
    private String title;
    private String artist;

    public ObjectLocalSong(Long id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public ObjectLocalSong() {
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
}
