package at.htl.musicvoting.rest;

import at.htl.musicvoting.converter.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.AvailabilityStatus;
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
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("add_song")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Converter.SongToObjectPlaylistSong(song))
                    .build();
            sseBroadcaster.broadcast(event);
        }

    }

    @POST
    @Path("/remove/song")
    @Secured
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeSong(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.removeSong(id);
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("remove_song")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder().add("id", id).build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
    }

    @POST
    @Path("/add/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void vote(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.addVote(id);
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("add_vote")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder().add("id", id).build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
    }

    @POST
    @Path("/remove/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeVote(@QueryParam("id") Long id){
        if(id != null && playlist.contains(id)){
            playlist.removeVote(id);
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("remove_vote")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder().add("id", id).build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
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
        OutboundSseEvent event = sse.newEventBuilder()
                .name("remove_song")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("id", song.getId()).build())
                .build();
        sseBroadcaster.broadcast(event);
        broadcastNextSong(song);
        return Response.ok(song).build();
    }

    public void broadcastNextSong(Song song){
        OutboundSseEvent event = sse.newEventBuilder()
                .name("song_started")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Converter.SongToObjectPlaylistSong(song))
                .build();
        sseBroadcaster.broadcast(event);
    }

    public void broadcastDownload(String id, AvailabilityStatus status) {
        OutboundSseEvent event = sse.newEventBuilder()
                .name("video_download")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder().add("videoId", id).add("status", status.toString()).build())
                .build();
        sseBroadcaster.broadcast(event);
    }
}
