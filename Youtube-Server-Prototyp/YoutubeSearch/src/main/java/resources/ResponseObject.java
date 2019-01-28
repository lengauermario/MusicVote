package resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseObject {
    String videoId;
    String title;
    String channel;
    String thumbNail;

    public ResponseObject(String videoId, String title, String channel, String thumbNail) {
        this.videoId = videoId;
        this.title = title;
        this.channel = channel;
        this.thumbNail = thumbNail;
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }
}
