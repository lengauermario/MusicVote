package at.htl.musicvoting.business;

import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Song;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.ejb.Stateless;
import javax.imageio.IIOException;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

@Stateless
public class InitDatabase {

    @Inject
    private SongDao dao;

    public void initialize() throws IOException, InvalidDataException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        File folder = new File(ResourceBundle.getBundle("config").getString("startFolder"));
        if(folder.exists() && folder.isDirectory()){
            File arr[] = folder.listFiles();
            recursiveRead(arr,0);
        }
    }

    private void recursiveRead(File[] arr, int level) throws IOException, InvalidDataException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        for (File f : arr){
            if (f.isFile()){
                if(getFileExtension(f.getAbsolutePath()).equalsIgnoreCase("mp3")){
                    readMp3File(f.getAbsolutePath());
                }
            }
            else if(f.isDirectory()){
                recursiveRead(f.listFiles(),level + 1);
            }

        }
    }

    private void readMp3File(String path) throws IOException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        Mp3File mp3file = new Mp3File(path);
        Song newSong = new Song(path, mp3file.getLengthInSeconds(), mp3file.getBitrate(), mp3file.isVbr(),
                    mp3file.getId3v2Tag().getTrack(), mp3file.getId3v2Tag().getArtist(), mp3file.getId3v2Tag().getTitle(),
                    mp3file.getId3v2Tag().getAlbum(), Integer.parseInt(mp3file.getId3v2Tag().getYear()), mp3file.getId3v2Tag().getGenre(), mp3file.getId3v2Tag().getComment(),
                    mp3file.getId3v2Tag().getUrl());
        dao.persist(newSong);
    }

    private String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
