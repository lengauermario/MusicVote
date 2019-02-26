package at.htl.musicvoting.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class YoutubeResponseObject {
    String videoId;
    String title;
    String artist;
    String thumbNail;
    AvailabilityStatus status;

    public YoutubeResponseObject(String videoId, String title, String channel, String thumbNail, AvailabilityStatus status) {
        this.videoId = videoId;
        this.title = title;
        this.artist = channel;
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public YoutubeResponseObject() {
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
