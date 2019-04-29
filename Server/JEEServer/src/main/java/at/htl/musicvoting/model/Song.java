package at.htl.musicvoting.model;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name="Song.findAll" , query = "select s from Song s order by s.title"),
        @NamedQuery(name="Song.findById", query = "select s from Song s where s.id = :ID")
})
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SONG")
@DiscriminatorColumn
public class Song implements Comparable<Song> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DTYPE", insertable = false, updatable = false)
    private String dType;

    private long lengthOfMp3;
    private int bitrate;
    private boolean bitrateVBR;
    private String track, artist, title, album;
    private int realeaseYear, genre;
    private String comment, url, path;



    @Transient
    private int votes;

    @XmlTransient
    @JsonbTransient
    @Transient
    private LocalDateTime addedToPlaylist;

    //region Constructors
    public Song(String path, long lengthOfMp3, int bitrate, boolean bitrateVBR, String track, String artist, String title, String album, int year, int genre, String comment, String url) {
        this.path = path;
        this.lengthOfMp3 = lengthOfMp3;
        this.bitrate = bitrate;
        this.bitrateVBR = bitrateVBR;
        this.track = track;
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.realeaseYear = year;
        this.genre = genre;
        this.comment = comment;
        this.url = url;
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

    public long getLengthOfMp3() {
        return lengthOfMp3;
    }

    public void setLengthOfMp3(long lengthOfMp3) {
        this.lengthOfMp3 = lengthOfMp3;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public boolean isBitrateVBR() {
        return bitrateVBR;
    }

    public void setBitrateVBR(boolean bitrateVBR) {
        this.bitrateVBR = bitrateVBR;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getRealeaseYear() {
        return realeaseYear;
    }

    public void setRealeaseYear(int year) {
        this.realeaseYear = year;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
