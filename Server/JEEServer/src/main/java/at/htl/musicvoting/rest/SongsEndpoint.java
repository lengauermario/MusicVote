package at.htl.musicvoting.rest;


import at.htl.musicvoting.business.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.ResponseObject;
import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.SortedSet;

@Stateless
@Path("song")
public class SongsEndpoint {
    @Inject
    SongDao dao;

    @Inject
    PlaylistHandler playlist;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("findall")
    public Response listSongs(){
        List<Song> songs = dao.findAll();
        List<ResponseObject> entity = Converter.SongsToResponse(songs);
        return Response.ok().entity(entity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("playlist/peek")
    public Response peek(){
        Song song = playlist.peek();
        return Response.ok(song).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("playlist/pop")
    public Response pop(){
        Song song = playlist.pop();
        return Response.ok(song).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("playlist")
    public Response getPlaylist(){
        SortedSet<Song> songs = playlist.getAll();
        List<ResponseObject> entity = Converter.SongsToResponse(new LinkedList<Song>(songs));
        return Response.ok(entity).build();
    }

    @POST
    @Path("playlist/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToPlaylist(@QueryParam("id")long id){
        Song song = dao.getByID(id);
        playlist.push(song);
        return Response.ok().build();
    }

    @POST
    @Path("playlist/addvote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addVote(@QueryParam("id")long id){
        playlist.addVote(id);
        return Response.ok().build();
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
