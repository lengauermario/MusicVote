package at.htl.musicvoting.business;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PlaylistHandler {
    Song currentSong;

    List<Song> playlist = new LinkedList<Song>();
    Comparator<Song> comparator = Comparator.comparing(Song::getVotes).reversed()
            .thenComparing(Song::getAddedToPlaylist);

    public List<Song> getPlaylist(){
        playlist.sort(comparator);
        return playlist;
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
    }

    private Song get(Long id) {
        for (Song song: playlist) {
            if(song.getId().equals(id))
                return song;
        }
        return null;
    }
    public void addVote(long id){
        Song song = get(id);
        if(song != null){
            song.increaseVotes();
        }
    }

    public Song playSong(){
        Song s = getPlaylist().get(0);
        playlist.remove(s);
        currentSong = s;
        return currentSong;
    }

    public Song peek(){
        return currentSong;
    }
}
