package at.htl.Business;

import at.htl.model.Song;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sun.media.sound.InvalidDataException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static EntityManager em;
    private static List<Song> songs;

    public static void main(String[] args) throws IOException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        em = Persistence.createEntityManagerFactory("myPU").createEntityManager();
        em.getTransaction().begin();
        em.persist(new Song());
        em.getTransaction().commit();
        songs = new ArrayList<>();
        init();
    }

    private static void init() throws IOException, InvalidDataException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        File folder = new File("/home/jonas/Schreibtisch/Jonas/Schule/4BHIF/SYP/Projekt/ReadFiles/src/main/resources/mp3");
        if(folder.exists() && folder.isDirectory()){
            File arr[] = folder.listFiles();
            RecursiveRead(arr,0);

        }
    }

    private static void RecursiveRead(File[] arr, int level) throws IOException, InvalidDataException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        for (File f : arr){
            if (f.isFile()){
                if(getFileExtension(f.getAbsolutePath()).equalsIgnoreCase("mp3")){
                    ReadMp3File(f.getAbsolutePath());
                }
            }
            else if(f.isDirectory()){
                RecursiveRead(f.listFiles(),level + 1);
            }

        }
    }

    private static void ReadMp3File(String path) throws InvalidDataException, IOException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        Mp3File mp3file = new Mp3File(path);
        Song newSong = new Song(path, mp3file.getLengthInSeconds(), mp3file.getBitrate(), mp3file.isVbr(), mp3file.getSampleRate(), mp3file.hasId3v1Tag(),
                mp3file.hasId3v2Tag(), mp3file.hasCustomTag(), mp3file.getId3v2Tag().getTrack(), mp3file.getId3v2Tag().getArtist(), mp3file.getId3v2Tag().getTitle(),
                mp3file.getId3v2Tag().getAlbum(), Integer.parseInt(mp3file.getId3v2Tag().getYear()), mp3file.getId3v2Tag().getGenre(), mp3file.getId3v2Tag().getComment(),
                mp3file.getId3v2Tag().getComposer(), mp3file.getId3v2Tag().getPublisher(), mp3file.getId3v2Tag().getOriginalArtist(), mp3file.getId3v2Tag().getAlbumArtist(),
                mp3file.getId3v2Tag().getCopyright(), mp3file.getId3v2Tag().getUrl(), mp3file.getId3v2Tag().getEncoder(),0);
        em.getTransaction().begin();
        em.persist(newSong);
        em.getTransaction().commit();
        songs.add(newSong);
    }

    public static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

}
