package at.htl.musicvoting.business;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import java.util.PriorityQueue;

@Stateless
public class PlaylistHandler {

    PriorityQueue<Song> playlist = new PriorityQueue<Song>();

    public PriorityQueue getAll(){
        return playlist;
    }

    public void push(Song song){
        if(!contains(song.getId())){
            song.resetVotes();
            playlist.add(song);
        }
    }

    public Song pop(){
        return playlist.poll();
    }

    public Song peek(){
        return playlist.peek();
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
