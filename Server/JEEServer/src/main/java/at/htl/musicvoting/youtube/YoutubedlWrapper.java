package at.htl.musicvoting.youtube;

import at.htl.musicvoting.dao.YoutubeVideoDao;
import at.htl.musicvoting.model.AvailabilityStatus;
import at.htl.musicvoting.model.ObjectYoutubeVideo;
import at.htl.musicvoting.model.YoutubeVideo;
import at.htl.musicvoting.rest.PlaylistResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ResourceBundle;
@Stateless
public class YoutubedlWrapper {

    @Inject
    private YoutubeVideoDao dao;

    @Inject
    private PlaylistResource playlistResource;

    public synchronized void fetchMp3FileFromYoutube(ObjectYoutubeVideo ytvideo) {
        if(dao.existsInDatabase(ytvideo.getVideoId()))
            return;
        String path = ResourceBundle.getBundle("config").getString("youtubeFolder")+ "\\" + ytvideo.getVideoId() + ".mp3" ;
        YoutubeVideo newVideo = new YoutubeVideo(path, ytvideo.getArtist() ,ytvideo.getTitle(),ytvideo.getVideoId(), ytvideo.getThumbNail(), AvailabilityStatus.DOWNLOADING);
        dao.persist(newVideo);
        playlistResource.broadcastDonwload(newVideo.getVideoId(), newVideo.getStatus());
        DownloadThread thread = new DownloadThread(ytvideo.getVideoId(), path, (Integer exitCode) -> {
            dao.updateToDownloaded(newVideo, exitCode);
            playlistResource.broadcastDonwload(newVideo.getVideoId(), newVideo.getStatus());
            return null;
        });
        thread.start();
    }
}
