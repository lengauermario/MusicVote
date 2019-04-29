package at.htl.musicvoting.business;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PlaylistHandler {
    private Song currentSong;
    private List<Song> playlist = new LinkedList<Song>();
    private Comparator<Song> comparator = Comparator.comparing(Song::getVotes).reversed()
            .thenComparing(Song::getAddedToPlaylist);

    public Song getCurrentSong() {
        return currentSong;
    }

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

    public void removeSong(long id){
        Song song = get(id);
        if(song != null){
            playlist.remove(song);
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
        }
    }

    public void removeVote(long id){
        Song song = get(id);
        if(song != null){
            song.decreaseVotes();
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
