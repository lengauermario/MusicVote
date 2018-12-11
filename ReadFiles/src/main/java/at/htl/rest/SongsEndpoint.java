package at.htl.rest;

import at.htl.model.Song;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@ApplicationPath("song")
public class SongsEndpoint {
    @PersistenceContext
    EntityManager em;

    @GET
    public Response listSongs(){
        TypedQuery query = em.createQuery("select s from Song s", Song.class);
        List<Song> songs = query.getResultList();
        return Response.ok().entity(songs).build();
    }

}
