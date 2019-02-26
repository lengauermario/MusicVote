package at.htl.musicvoting.business;

import at.htl.musicvoting.model.*;

import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static ResponseObject SongToResponeObject(Song song){
        ResponseObject responseObject = new ResponseObject(song.getId(), song.getTitle(), song.getArtist(), AvailabilityStatus.AVAILABLE);
        if(song instanceof YoutubeVideo ){
            YoutubeVideo ytsong = (YoutubeVideo)song;
            responseObject.setStatus(ytsong.getStatus());
        }
        return responseObject;
    }

    public static List<ResponseObject> SongsToResponse(List<Song> songs){
        List<ResponseObject> responseObjectList = new LinkedList<>();
        songs.forEach(s -> responseObjectList.add(SongToResponeObject(s)));
        return responseObjectList;
    }
}
