package at.htl.musicvoting.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name="Song.findAll" , query = "select s from Song s order by s.title"),
        @NamedQuery(name="Song.findById", query = "select s from Song s where s.id = :ID"),
        @NamedQuery(name="Song.find", query="select s from Song s where lower(s.title) like lower(:TERM) or lower(s.artist) like lower(:TERM)")
})
@Table(name = "SONG")
public class Song implements Comparable<Song> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artist, title, path;

    @Transient
    private int votes;

    @XmlTransient
    @JsonbTransient
    @Transient
    private LocalDateTime addedToPlaylist;

    //region Constructors
    public Song(String path,String artist, String title) {
        this.path = path;
        this.artist = artist;
        this.title = title;
    }

    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public Song(){

    }
    //endregion

    //region Getter and Setter

    public LocalDateTime getAddedToPlaylist() {
        return addedToPlaylist;
    }

    public void setAddedToPlaylist(LocalDateTime addedToPlaylist) {
        this.addedToPlaylist = addedToPlaylist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void resetVotes(){
        this.votes = 0;
    }

    public void increaseVotes() {
        this.votes++;
    }

    public void decreaseVotes() {
        this.votes--;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public int compareTo(Song song) {
        return Integer.compare(song.getVotes(), this.getVotes());
    }
    //endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
