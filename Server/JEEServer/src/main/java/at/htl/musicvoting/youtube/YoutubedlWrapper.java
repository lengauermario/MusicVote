package at.htl.musicvoting.youtube;

import at.htl.musicvoting.dao.YoutubeVideoDao;
import at.htl.musicvoting.model.AvailabilityStatus;
import at.htl.musicvoting.rest.response_object.ObjectYoutubeVideo;
import at.htl.musicvoting.model.YoutubeVideo;
import at.htl.musicvoting.rest.PlaylistResource;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.File;
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
        String fileName =  ytvideo.getVideoId() + ".mp3";
        String path = ResourceBundle.getBundle("config").getString("youtubeFolder")+ "\\" + fileName;
        YoutubeVideo newVideo = new YoutubeVideo(path, ytvideo.getArtist() ,ytvideo.getTitle(),ytvideo.getVideoId(), ytvideo.getThumbNail(), AvailabilityStatus.DOWNLOADING);
        dao.persist(newVideo);
        playlistResource.broadcastDownload(newVideo.getVideoId(), newVideo.getStatus());
        DownloadThread thread = new DownloadThread(ytvideo.getVideoId(), path, (Integer exitCode) -> {
            dao.updateToDownloaded(newVideo, exitCode);
            playlistResource.broadcastDownload(newVideo.getVideoId(), newVideo.getStatus());
            try{
                File oSourceFile = new File(path);
                MediaFile oMediaFile = new MP3File(oSourceFile);
                ID3V2_3_0Tag oID3V2_3_0Tag = new ID3V2_3_0Tag();
                oID3V2_3_0Tag.setArtist(newVideo.getArtist());
                oID3V2_3_0Tag.setTitle(newVideo.getTitle());
                oMediaFile.setID3Tag(oID3V2_3_0Tag);
                oMediaFile.sync();
            } catch (ID3Exception e) {
                e.printStackTrace();
            }

            return null;
        });
        thread.start();
    }
}
