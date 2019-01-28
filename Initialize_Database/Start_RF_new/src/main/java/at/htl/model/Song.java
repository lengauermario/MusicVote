package at.htl.model;

import javax.persistence.*;

@Entity
@Table(name = "SONG")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long lengthOfMp3;
    private int bitrate;
    private int votes;
    private boolean bitrateVBR;
    private int sampleRate;
    private boolean hasId3v1Tag, hasId3v2Tag, hasCustomTag;
    private String track, artist, title, album;
    private int realeaseYear, genre;
    private String comment, composer, publisher, originalArtist, albumArtist, copyright, url, encoder, path;


    //region Constructors
    public Song(String path, long lengthOfMp3, int bitrate, boolean bitrateVBR, int sampleRate, boolean hasId3v1Tag, boolean hasId3v2Tag, boolean hasCustomTag, String track, String artist, String title, String album, int year, int genre, String comment, String composer, String publisher, String originalArtist, String albumArtist, String copyright, String url, String encoder,int votes) {
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
        this.votes = votes;
    }

    public Song(){

    }
    //endregion

    //region Getter and Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
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
    //endregion

}
