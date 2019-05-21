package at.htl.musicvoting;

import at.htl.musicvoting.business.InitDatabase;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Song;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Persistence;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class InitDatabaseTest {
    static SongDao dao;
    String filePath;
    InitDatabase initDatabase;

    @BeforeClass
    public static void initDao(){
        dao = new SongDao();
        dao.em = Persistence.createEntityManagerFactory("myPU").createEntityManager();
    }

    @Before
    public void init(){
        initDatabase = new InitDatabase();
        filePath = "/home/jonas/Schreibtisch/Jonas/Schule/4BHIF/SYP/Projekt/mp3/Webbie/Savage Life 2/03 - Independent (Feat_ Lil' Boosie And Lil' Phat).mp3";
    }

    @Test
    public void test01_LoadTest() throws InvalidDataException, IOException, UnsupportedTagException {
        for (int i = 0; i <= 100000000; i++){
            readMp3File(filePath);
            System.out.println(i);
        }
        assertEquals(dao.findAll().size(), is(100000));
    }


    public void readMp3File(String path) throws InvalidDataException, IOException, UnsupportedTagException, com.mpatric.mp3agic.InvalidDataException {
        Mp3File mp3file = new Mp3File(path);
        Song newSong = new Song(path, mp3file.getLengthInSeconds(), mp3file.getBitrate(), mp3file.isVbr(),
                mp3file.getId3v2Tag().getTrack(), mp3file.getId3v2Tag().getArtist(), mp3file.getId3v2Tag().getTitle(),
                mp3file.getId3v2Tag().getAlbum(), Integer.parseInt(mp3file.getId3v2Tag().getYear()), mp3file.getId3v2Tag().getGenre(), mp3file.getId3v2Tag().getComment(),
                mp3file.getId3v2Tag().getUrl());
        dao.persist(newSong);
    }


}
