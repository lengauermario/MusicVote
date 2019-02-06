package at.htl.musicvoting.model;

public class ResponseObject {
    Long id;
    String title;
    String artist;
    AvailabilityStatus status;

    public ResponseObject(Long id, String title, String artist, AvailabilityStatus status) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.status = status;
    }

    public ResponseObject() {
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

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }
}
