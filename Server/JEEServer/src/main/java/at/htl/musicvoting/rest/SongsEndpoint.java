package at.htl.musicvoting.rest;


import at.htl.musicvoting.business.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.ObjectLocalSong;
import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Stateless
@Path("song")
public class SongsEndpoint {
    @Inject
    private SongDao dao;

    @Inject
    private PlaylistHandler playlist;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("findall")
    public Response listSongs(){
        List<Song> songs = dao.findAll();
        List<ObjectLocalSong> entity = Converter.SongsToObjectLocalSongList(songs);
        return Response.ok().entity(entity).build();
    }
    @GET
    @Produces("audio/mp3")
    @Path("getmp3")
    public Response get(@QueryParam("id")long id) {
        Song song = dao.getByID(id);
        File mp3file = new File(song.getPath());
        return Response
                .status(200)
                .entity(mp3file)
                .build();
    }
}
