package at.htl.musicvoting.youtube;

import at.htl.musicvoting.dao.YoutubeVideoDao;
import at.htl.musicvoting.model.AvailabilityStatus;
import at.htl.musicvoting.model.YoutubeResponseObject;
import at.htl.musicvoting.model.YoutubeVideo;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ResourceBundle;
@Stateless
public class YoutubedlWrapper {

    @Inject
    YoutubeVideoDao dao;

    public synchronized void fetchMp3FileFromYoutube(YoutubeResponseObject ytvideo) {
        if(dao.existsInDatabase(ytvideo.getVideoId()))
            return;
        final YoutubeVideo newVideo = new YoutubeVideo(ResourceBundle.getBundle("config").getString("youtubeFolder")+ "\\" + ytvideo.getTitle() + ".mp3", ytvideo.getArtist() ,ytvideo.getTitle(),ytvideo.getVideoId(), ytvideo.getThumbNail(), AvailabilityStatus.DOWNLOADING);
        dao.persist(newVideo);
        DownloadThread thread = new DownloadThread(ytvideo.getVideoId(), (Integer exitCode) -> {
            dao.updateToDownloaded(newVideo, exitCode);
            return null;
        });
        thread.start();
    }
}
