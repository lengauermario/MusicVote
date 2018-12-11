package at.htl.business;

import at.htl.model.Song;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class InitBean {

    private List<Song> songs;

    @PersistenceContext
    EntityManager em;

    public InitBean(){
        songs = new ArrayList<>();
    }

    @PostConstruct
    private void init() throws InvalidDataException, IOException, UnsupportedTagException {
        File folder = new File("/home/jonas/Schreibtisch/Jonas/Schule/4BHIF/SYP/Projekt/ReadFiles/src/main/resources/mp3");
        if(folder.exists() && folder.isDirectory()){
            File arr[] = folder.listFiles();
            RecursiveRead(arr,0);

        }

    }

    private void RecursiveRead(File[] arr, int level) throws InvalidDataException, IOException, UnsupportedTagException {
        for (File f : arr){
            if (f.isFile()){
                String test = getFileExtension(f.getAbsolutePath());
                if(getFileExtension(f.getAbsolutePath()).equalsIgnoreCase("mp3")){
                    ReadMp3File(f.getAbsolutePath());
                }
            }
            else if(f.isDirectory()){
                RecursiveRead(f.listFiles(),level + 1);
            }

        }
    }

    @Transactional
    private void ReadMp3File(String path) throws InvalidDataException, IOException, UnsupportedTagException {
        Mp3File mp3file = new Mp3File(path);
        Song newSong = new Song(path, mp3file.getLengthInSeconds(), mp3file.getBitrate(), mp3file.isVbr(), mp3file.getSampleRate(), mp3file.hasId3v1Tag(),
                mp3file.hasId3v2Tag(), mp3file.hasCustomTag(), mp3file.getId3v2Tag().getTrack(), mp3file.getId3v2Tag().getArtist(), mp3file.getId3v2Tag().getTitle(),
                mp3file.getId3v2Tag().getAlbum(), Integer.parseInt(mp3file.getId3v2Tag().getYear()), mp3file.getId3v2Tag().getGenre(), mp3file.getId3v2Tag().getComment(),
                mp3file.getId3v2Tag().getComposer(), mp3file.getId3v2Tag().getPublisher(), mp3file.getId3v2Tag().getOriginalArtist(), mp3file.getId3v2Tag().getAlbumArtist(),
                mp3file.getId3v2Tag().getCopyright(), mp3file.getId3v2Tag().getUrl(), mp3file.getId3v2Tag().getEncoder());
        em.persist(newSong);
        songs.add(newSong);
    }

    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
