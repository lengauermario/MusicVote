package at.htl.musicvoting.rest;

import at.htl.musicvoting.converter.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Playlist;
import at.htl.musicvoting.rest.adapter.PlaylistAdapter;
import at.htl.musicvoting.rest.adapter.SongAdapter;
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
    public void init() {
        this.sseBroadcaster = sse.newBroadcaster();
        this.sseBroadcaster.onClose(x -> System.out.println("connection to client closed"));
        this.sseBroadcaster.onError((x, y) -> System.out.println("error"));
    }

    @GET
    @Path("/connect")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink eventSink) {
        System.out.println("new client connected");
        eventSink.send(sse.newEvent("Welcome!"));
        sseBroadcaster.register(eventSink);
    }

    @POST
    @Path("/add/song")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void addSong(@QueryParam("id") Long id) {
        if (id == null)
            return;
        Song song = dao.getByID(id);
        if (song != null && !playlist.contains(id)) {
            System.out.println("song added");
            playlist.addSong(song);
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("add_song")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder()
                            .add("updateId", this.playlist.getPlaylist().getUpdateId())
                            .add("song", SongAdapter.marshall(song))
                            .build())
                    .build();
            sseBroadcaster.broadcast(event);
        }

    }

    @POST
    @Path("/remove/song")
    @Secured
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeSong(@QueryParam("id") Long id) {
        if (id != null && playlist.contains(id)) {
            System.out.println("song removed");
            playlist.removeSong(id);
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("remove_song")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder()
                            .add("updateId", this.playlist.getPlaylist().getUpdateId())
                            .add("id", id)
                            .build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
    }


    @POST
    @Path("/add/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void vote(@QueryParam("id") Long id) {
        if (id != null && playlist.contains(id)) {
            playlist.addVote(id);
            System.out.println("vote added");
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("add_vote")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder().add("updateId", this.playlist.getPlaylist().getUpdateId()).add("id", id).build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
    }

    @POST
    @Path("/remove/vote")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void removeVote(@QueryParam("id") Long id) {
        if (id != null && playlist.contains(id)) {
            playlist.removeVote(id);
            System.out.println("vote removed");
            OutboundSseEvent event = sse.newEventBuilder()
                    .name("remove_vote")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(Json.createObjectBuilder().add("updateId", this.playlist.getPlaylist().getUpdateId()).add("id", id).build())
                    .build();
            sseBroadcaster.broadcast(event);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylist() {
        Playlist entity = playlist.getPlaylist();
        return Response.ok(PlaylistAdapter.marshall(entity)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/peek")
    public Response peek() {
        Song song = playlist.peek();
        if (song != null) {
            return Response.ok(Converter.SongToObjectPlaylistSong(song)).build();
        }
        return Response.noContent().build();
    }


    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pop")
    public Response pop() {
        Song song = playlist.playSong();
        if (song == null)
            song = playlist.playRandom();
        else {
            OutboundSseEvent event = sse.newEventBuilder()
                .name("remove_song")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Json.createObjectBuilder()
                        .add("updateId", this.playlist.getPlaylist().getUpdateId())
                        .add("id", song.getId())
                        .build())
                .build();
        }
        broadcastNextSong(song);
        return Response.ok(song).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/version")
    public Response getUpdateId() {
        return Response.ok(playlist.getPlaylist().getUpdateId()).build();
    }

    public void broadcastChange() {
        OutboundSseEvent event = sse.newEventBuilder()
                .name("change")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(PlaylistAdapter.marshall(playlist.getPlaylist()))
                .build();
        sseBroadcaster.broadcast(event);
    }

    public void broadcastNextSong(Song song) {
        OutboundSseEvent event = sse.newEventBuilder()
                .name("song_started")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(Converter.SongToObjectPlaylistSong(song))
                .build();
        sseBroadcaster.broadcast(event);
    }

}
