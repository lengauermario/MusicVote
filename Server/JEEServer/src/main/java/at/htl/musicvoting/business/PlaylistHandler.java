package at.htl.musicvoting.business;

import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Playlist;
import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PlaylistHandler {

    @Inject
    PlaylistHolder playlistHolder;

    @Inject
    SongDao dao;
    private Song currentSong;
    private List<Song> playlist = new LinkedList<Song>();
    private Comparator<Song> comparator = Comparator.comparing(Song::getVotes).reversed()
            .thenComparing(Song::getAddedToPlaylist);



    public Song getCurrentSong() {
        return currentSong;
    }

    public Playlist getPlaylist(){
        //playlist.sort(comparator);
        return playlistHolder.getPlaylist();
    }

    private void updatePlaylist(){
        playlist.sort(comparator);
        playlistHolder.updatePlaylist(playlist);
    }

    public synchronized void addSong(Song song){
        if(get(song.getId()) == null){
            song.setAddedToPlaylist(LocalDateTime.now());
            song.resetVotes();
            playlist.add(song);
        }
        else{
            addVote(song.getId());
        }
        updatePlaylist();
    }

    public void removeSong(long id){
        Song song = get(id);
        if(song != null){
            playlist.remove(song);
            updatePlaylist();
        }
    }

    private Song get(Long id) {
        for (Song song: playlist) {
            if(song.getId().equals(id))
                return song;
        }
        return null;
    }

    public boolean contains(Long id){
        return get(id) != null;
    }

    public void addVote(long id){
        Song song = get(id);
        if(song != null){
            song.increaseVotes();
            updatePlaylist();
        }
    }

    public void removeVote(long id){
        Song song = get(id);
        if(song != null){
            song.decreaseVotes();
            updatePlaylist();
        }
    }

    public Song playSong(){
        Song s = null;
        if(playlist.size() > 0){
            s = playlist.get(0);
            playlist.remove(s);
            updatePlaylist();
        }
        currentSong = s;
        return currentSong;
    }

    public Song peek(){
        return currentSong;
    }

    public Song playRandom() {
        Song song = dao.getRandom();
        currentSong = song;
        return song;
    }
}
