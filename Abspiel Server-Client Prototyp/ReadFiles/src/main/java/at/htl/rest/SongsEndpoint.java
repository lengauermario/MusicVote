package at.htl.rest;

import at.htl.model.Song;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Stateless
@Path("song")
public class SongsEndpoint {
    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getSongs")
    public Response listSongs(){
        TypedQuery query = em.createQuery("select s from Song s", Song.class);
        List<Song> songs = query.getResultList();
        return Response.ok().entity(songs).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getHighestVote")
    public Response getHighestVoteSong(){
        TypedQuery query = em.createQuery("SELECT s from Song s order by s.votes DESC ",Song.class);
        Song song = (Song) query.getResultList().get(0);
        return Response.ok().entity(song).build();
    }

    @GET
    @Produces("audio/mp3")
    @Path("playsong")
    public Response get() {
        File song = new File("/home/jonas/Schreibtisch/Jonas/Schule/4BHIF/SYP/Projekt/ReadFiles/src/main/resources/mp3/U2/Window In The Skies/01 - Window In The Skies.mp3");
        return Response.ok().entity(song).build();
    }
}
