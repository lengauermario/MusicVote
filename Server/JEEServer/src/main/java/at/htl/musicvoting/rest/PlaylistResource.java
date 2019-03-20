package at.htl.musicvoting.rest;

import at.htl.musicvoting.converter.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.AvailabilityStatus;
import at.htl.musicvoting.model.ObjectPlaylistSong;
import at.htl.musicvoting.model.Song;

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
    public void addSong(@QueryParam("id") long id){
        System.out.println("new song added to playlist");
        Song song = dao.getByID(id);
        playlist.addSong(song);
        OutboundSseEvent event = sse.newEventBuilder()
                .name("add_song")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Converter.SongToObjectPlaylistSong(song))
                .build();
        sseBroadcaster.broadcast(event);
    }

    @POST
    @Path("/remove/song")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeSong(@QueryParam("id") long id){
        System.out.println("song removed from playlist");
        playlist.removeSong(id);
        OutboundSseEvent event = sse.newEventBuilder()
                .name("remove_song")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("id", id).build())
                .build();
        sseBroadcaster.broadcast(event);
    }

    @POST
    @Path("/add/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void vote(@QueryParam("id") long id){
        System.out.println("new vote");
        playlist.addVote(id);
        OutboundSseEvent event = sse.newEventBuilder()
                .name("add_vote")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("id", id).build())
                .build();
        sseBroadcaster.broadcast(event);
    }

    @POST
    @Path("/remove/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeVote(@QueryParam("id") long id){
        System.out.println("remove vote");
        playlist.removeVote(id);
        OutboundSseEvent event = sse.newEventBuilder()
                .name("remove_vote")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("id", id).build())
                .build();
        sseBroadcaster.broadcast(event);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist(){
        List<Song> songs = playlist.getPlaylist();
        List<ObjectPlaylistSong> entity = Converter.SongsToObjectPlaylistSongList(new LinkedList<Song>(songs));
        return Response.ok(entity).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/peek")
    public Response peek(){
        Song song = playlist.peek();
        return Response.ok(song).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pop")
    public Response pop(){
        Song song = playlist.playSong();
        return Response.ok(song).build();
    }

    public void broadcastDonwload(String id, AvailabilityStatus status) {
        System.out.println("new video downlaoded");
        OutboundSseEvent event = sse.newEventBuilder()
                .name("video_download")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("videoId", id).add("status", status.toString()).build())
                .build();
        sseBroadcaster.broadcast(event);
    }
}
