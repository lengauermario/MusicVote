package at.htl.musicvoting.model;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "YoutubeVideo.getByVideoId", query = "select y from YoutubeVideo y where y.videoId = :VIDEOID")
public class YoutubeVideo extends Song {
    private String videoId;
    private String thumbNail;
    private AvailabilityStatus status;

    public YoutubeVideo(String path, String artist, String title, String videoId, String thumbNail, AvailabilityStatus status) {
        this.setPath(path);
        this.setArtist(artist);
        this.setTitle(title);
        this.videoId = videoId;
        this.thumbNail = thumbNail;
        this.status = status;
    }

    public YoutubeVideo() {

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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
