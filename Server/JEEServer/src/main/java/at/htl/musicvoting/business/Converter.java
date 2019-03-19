package at.htl.musicvoting.business;

import at.htl.musicvoting.model.*;

import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static ObjectLocalSong SongToObjectLocalSong(Song song){
        ObjectLocalSong objectLocalSong = new ObjectLocalSong(song.getId(), song.getTitle(), song.getArtist(), AvailabilityStatus.AVAILABLE);
        if(song instanceof YoutubeVideo ){
            YoutubeVideo ytsong = (YoutubeVideo)song;
            objectLocalSong.setStatus(ytsong.getStatus());
        }
        return objectLocalSong;
    }

    public static List<ObjectLocalSong> SongsToObjectLocalSongList(List<Song> songs){
        List<ObjectLocalSong> objectLocalSongList = new LinkedList<>();
        songs.forEach(s -> objectLocalSongList.add(SongToObjectLocalSong(s)));
        return objectLocalSongList;
    }

    public static ObjectPlaylistSong SongToObjectPlaylistSong(Song song){
        ObjectPlaylistSong object = new ObjectPlaylistSong(song.getId(), song.getTitle(), song.getArtist(), "default", song.getVotes(), song.getAddedToPlaylist());
        if(song instanceof YoutubeVideo){
            YoutubeVideo ytsong = (YoutubeVideo)song;
            object.setThumbNail(ytsong.getThumbNail());
        }
        return object;
    }
    public static List<ObjectPlaylistSong> SongsToObjectPlaylistSongList(List<Song> songs){
        List<ObjectPlaylistSong> objectPlaylistSongList = new LinkedList<>();
        songs.forEach(s -> objectPlaylistSongList.add(SongToObjectPlaylistSong(s)));
        return objectPlaylistSongList;
    }
}
