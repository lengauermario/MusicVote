package at.htl.musicvoting.converter;

import at.htl.musicvoting.model.*;
import at.htl.musicvoting.rest.response_object.ObjectLocalSong;
import at.htl.musicvoting.rest.response_object.ObjectPlaylistSong;

import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static ObjectLocalSong SongToObjectLocalSong(Song song){
        return new ObjectLocalSong(song.getId(), song.getTitle(), song.getArtist());
    }

    public static List<ObjectLocalSong> SongsToObjectLocalSongList(List<Song> songs){
        List<ObjectLocalSong> objectLocalSongList = new LinkedList<>();
        songs.forEach(s -> objectLocalSongList.add(SongToObjectLocalSong(s)));
        return objectLocalSongList;
    }

    public static ObjectPlaylistSong SongToObjectPlaylistSong(Song song){
       return new ObjectPlaylistSong(song.getId(), song.getTitle(), song.getArtist(), "default", song.getVotes(), song.getAddedToPlaylist());
    }
    public static List<ObjectPlaylistSong> SongsToObjectPlaylistSongList(List<Song> songs){
        List<ObjectPlaylistSong> objectPlaylistSongList = new LinkedList<>();
        songs.forEach(s -> objectPlaylistSongList.add(SongToObjectPlaylistSong(s)));
        return objectPlaylistSongList;
    }
}
