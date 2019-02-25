package at.htl.musicvoting.business;

import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Song;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Persistence;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class PlaylistHandlerTest {


    static SongDao dao;
    PlaylistHandler playlistHandler;

    @BeforeClass
    public static void initDao(){
        dao = new SongDao();
        dao.em = Persistence.createEntityManagerFactory("myPU").createEntityManager();
    }


    @Before
    public void init(){
        playlistHandler = new PlaylistHandler();
    }

    @Test
    public void test010_addSongTwice() {
        Song song1 = dao.getByID(1);
        playlistHandler.addSong(song1);
        playlistHandler.addSong(song1);
        List<Song> playlist = playlistHandler.getPlaylist();
        assertThat(playlist.isEmpty(), is(false));
        assertThat(playlist.size(), is(1));
    }
    @Test
    public void test011_addSongTwiceShouldIncreaseVotes() {
        Song song1 = dao.getByID(1);
        playlistHandler.addSong(song1);
        playlistHandler.addSong(song1);
        List<Song> playlist = playlistHandler.getPlaylist();
        assertThat(song1.getVotes(), is(1));
    }

    @Test
    public void test020_PeekShouldNotHarmPlaylist(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.peek();
        assertThat(playlistHandler.getPlaylist().size(), is(3));
    }


    @Test
    public void test030_PlaySongShouldRemoveFirst(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.playSong();
        List<Song> playlist = playlistHandler.getPlaylist();
        assertThat(playlist.size(), is(2));
    }
    @Test
    public void test031_PlaySongShouldSetCurrentSong(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(1l));
    }
    @Test
    public void test040_PlayListShouldBeSortedByTimeOfInsert(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(1l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(2l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(3l));
    }
    @Test
    public void test041_PlayListShouldBeSortedByVotes(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.addVote(3);
        playlistHandler.addVote(3);
        playlistHandler.addVote(3);
        playlistHandler.addVote(2);
        playlistHandler.addVote(2);
        playlistHandler.addVote(1);

        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(3l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(2l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(1l));
    }
    @Test
    public void test042_PlayListShouldBeSortedByVotesAndTimeOfInsert(){
        playlistHandler.addSong(dao.getByID(1));
        playlistHandler.addSong(dao.getByID(2));
        playlistHandler.addSong(dao.getByID(3));
        playlistHandler.addSong(dao.getByID(4));
        playlistHandler.addVote(4);
        playlistHandler.addVote(3);
        playlistHandler.addVote(3);
        playlistHandler.addVote(3);
        playlistHandler.addVote(2);
        playlistHandler.addVote(2);
        playlistHandler.addVote(1);

        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(3l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(2l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(1l));
        playlistHandler.playSong();
        assertThat(playlistHandler.currentSong.getId(), is(4l));
    }

    @Test
    public void test050_AddVote(){
        Song song = dao.getByID(1);
        playlistHandler.addSong(song);
        playlistHandler.addVote(1);
        assertThat(song.getVotes(), is(1));
    }

    @Test
    public void test051_AddVoteToSongWhichIsNotInPlaylist(){
        Song song = dao.getByID(1);
        playlistHandler.addSong(song);
        playlistHandler.addVote(10);
        assertThat(song.getVotes(), is(0));
    }

    @Test
    public void test060_TimeOfInsert(){
        LocalDateTime time = LocalDateTime.now();
        Song song = dao.getByID(1);
        playlistHandler.addSong(song);
        Duration duration = Duration.between(time, song.getAddedToPlaylist());
        Long millis = duration.toMillis();
        assertThat(millis, lessThan(1000l));
    }

}