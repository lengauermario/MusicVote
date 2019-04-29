package at.htl.musicvoting.rest.response_object;

import at.htl.musicvoting.model.AvailabilityStatus;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObjectYoutubeVideo {
    private String videoId;
    private String title;
    private String artist;
    private String thumbNail;
    private AvailabilityStatus status;

    public ObjectYoutubeVideo(String videoId, String title, String channel, String thumbNail, AvailabilityStatus status) {
        this.videoId = videoId;
        this.title = title;
        this.artist = channel;
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public ObjectYoutubeVideo() {
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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

    public void setArtist(String channel) {
        this.artist = channel;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }
}
