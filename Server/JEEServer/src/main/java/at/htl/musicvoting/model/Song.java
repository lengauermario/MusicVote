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
    private int sampleRate;
    private boolean hasId3v1Tag, hasId3v2Tag, hasCustomTag;
    private String track, artist, title, album;
    private int realeaseYear, genre;
    private String comment, composer, publisher, originalArtist, albumArtist, copyright, url, encoder, path;



    @Transient
    private int votes;

    @XmlTransient
    @JsonbTransient
    @Transient
    private LocalDateTime addedToPlaylist;

    //region Constructors
    public Song(String path, long lengthOfMp3, int bitrate, boolean bitrateVBR, int sampleRate, boolean hasId3v1Tag, boolean hasId3v2Tag, boolean hasCustomTag, String track, String artist, String title, String album, int year, int genre, String comment, String composer, String publisher, String originalArtist, String albumArtist, String copyright, String url, String encoder) {
        this.path = path;
        this.lengthOfMp3 = lengthOfMp3;
        this.bitrate = bitrate;
        this.bitrateVBR = bitrateVBR;
        this.sampleRate = sampleRate;
        this.hasId3v1Tag = hasId3v1Tag;
        this.hasId3v2Tag = hasId3v2Tag;
        this.hasCustomTag = hasCustomTag;
        this.track = track;
        this.artist = artist;
        this.title = title;
        this.album = album;
        this.realeaseYear = year;
        this.genre = genre;
        this.comment = comment;
        this.composer = composer;
        this.publisher = publisher;
        this.originalArtist = originalArtist;
        this.albumArtist = albumArtist;
        this.copyright = copyright;
        this.url = url;
        this.encoder = encoder;
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

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public boolean isHasId3v1Tag() {
        return hasId3v1Tag;
    }

    public void setHasId3v1Tag(boolean hasId3v1Tag) {
        this.hasId3v1Tag = hasId3v1Tag;
    }

    public boolean isHasId3v2Tag() {
        return hasId3v2Tag;
    }

    public void setHasId3v2Tag(boolean hasId3v2Tag) {
        this.hasId3v2Tag = hasId3v2Tag;
    }

    public boolean isHasCustomTag() {
        return hasCustomTag;
    }

    public void setHasCustomTag(boolean hasCustomTag) {
        this.hasCustomTag = hasCustomTag;
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

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOriginalArtist() {
        return originalArtist;
    }

    public void setOriginalArtist(String originalArtist) {
        this.originalArtist = originalArtist;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEncoder() {
        return encoder;
    }

    public void setEncoder(String encoder) {
        this.encoder = encoder;
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
