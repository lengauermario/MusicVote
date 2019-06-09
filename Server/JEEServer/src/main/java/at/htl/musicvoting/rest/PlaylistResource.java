package at.htl.musicvoting.rest;

import at.htl.musicvoting.converter.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Playlist;
import at.htl.musicvoting.rest.adapter.PlaylistAdapter;
import at.htl.musicvoting.rest.response_object.ObjectPlaylistSong;
import at.htl.musicvoting.model.Song;
import at.htl.musicvoting.rest.auth.annotation.Secured;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.LinkedList;
import java.util.List;


@Singleton
@Path("playlist")
public class PlaylistResource {
    @Context
    private Sse sse;
    private volatile SseBroadcaster sseBroadcaster;

    @Inject
    private PlaylistHandler playlist;

    @Inject
    private SongDao dao;

    @PostConstruct
    public void init(){
        this.sseBroadcaster = sse.newBroadcaster();
    }

    @GET
    @Path("/connect")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink eventSink){
        System.out.println("new client connected");
        eventSink.send(sse.newEvent("Welcome!"));
        sseBroadcaster.register(eventSink);
    }

    @POST
    @Path("/add/song")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void addSong(@QueryParam("id") Long id){
        if(id == null)
            return;
        Song song = dao.getByID(id);
        if(song != null && !playlist.contains(id)){
            System.out.println("new song added to playlist");
            playlist.addSong(song);
            broadcastChange();
        }

    }

    @POST
    @Path("/remove/song")
    @Secured
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeSong(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.removeSong(id);
            broadcastChange();
            broadcastRemovement(id);
        }
    }



    @POST
    @Path("/add/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void vote(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.addVote(id);
            broadcastChange();
        }
    }

    @POST
    @Path("/remove/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeVote(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.removeVote(id);
            broadcastChange();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(){
        Playlist entity = playlist.getPlaylist();
        return Response.ok(PlaylistAdapter.marshall(entity)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/peek")
    public Response peek(){
        Song song = playlist.peek();
        if(song != null){
            return Response.ok(Converter.SongToObjectPlaylistSong(song)).build();
        }
        return Response.noContent().build();
    }


    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pop")
    public Response pop(){
        Song song = playlist.playSong();
        if(song == null)
            song = playlist.playRandom();
        else
            broadcastRemovement(song.getId());
        broadcastChange();
        broadcastNextSong(song);
        return Response.ok(song).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/timestamp")
    public Response getCurrentTimestamp(){
        return Response.ok(playlist.getPlaylist().getTimestamp()).build();
    }

    public void broadcastChange(){
        OutboundSseEvent event = sse.newEventBuilder()
                .name("change")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(PlaylistAdapter.marshall(playlist.getPlaylist()))
                .build();
        sseBroadcaster.broadcast(event);
    }

    private void broadcastRemovement(Long id) {
        OutboundSseEvent event = sse.newEventBuilder()
                .name("removement")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("id", id).build())
                .build();
        sseBroadcaster.broadcast(event);
    }

    public void broadcastNextSong(Song song){
        OutboundSseEvent event = sse.newEventBuilder()
                .name("song_started")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Converter.SongToObjectPlaylistSong(song))
                .build();
        sseBroadcaster.broadcast(event);
    }

}
