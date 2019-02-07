package at.htl.musicvoting.business;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class PlaylistHandler {

    /*PriorityQueue<Song> playlist = new PriorityQueue<Song>(new Comparator<Song>() {
        @Override
        public int compare(Song song, Song t1) {
            return song.compareTo(t1);
        }
    });*/
    SortedSet<Song> playlist = new TreeSet<>(Comparator.comparing(Song::getVotes).reversed()
            .thenComparing(Song::getAddedToPlaylist));

    public SortedSet<Song> getAll(){
        return playlist;
    }

    public synchronized void push(Song song){
        if(contains(song.getId())){
            addVote(song.getId());
            return;
        }
        song.setAddedToPlaylist(LocalDateTime.now());
        song.resetVotes();
        playlist.add(song);
    }

    public Song pop(){
        Song s = playlist.first();
        playlist.remove(s);
        return s;
    }

    public Song peek(){
        return playlist.first();
    }

    public boolean contains(long id){
        for (Song song: playlist) {
            if(song.getId() == id)
                return true;
        }
        return false;
    }

    public void addVote(long id){
        for (Song song: playlist) {
            if(song.getId() == id)
            {
                playlist.remove(song);
                song.increaseVotes();
                playlist.add(song);
                return;
            }
        }
    }
}
