package at.htl.musicvoting.rest;


import at.htl.musicvoting.converter.Converter;
import at.htl.musicvoting.business.PlaylistHandler;
import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.rest.response_object.ObjectLocalSong;
import at.htl.musicvoting.model.Song;
import at.htl.musicvoting.rest.auth.annotation.Secured;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.parser.Entity;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Stateless
@Path("song")
public class SongsEndpoint {
    @PersistenceContext
    EntityManager em;
    @Inject
    private SongDao dao;

    @Inject
    private PlaylistHandler playlist;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("find")
    public Response listSongs(@QueryParam("queryTerm") String queryTerm) {
//        List<Song> songs = dao.find(queryTerm);
//        List<ObjectLocalSong> entity = Converter.SongsToObjectLocalSongList(songs);
//        return Response.ok().entity(entity).build();
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(em);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Song.class)
                .get();
        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onField("title")
                .matching(queryTerm)
                .createQuery();
        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Song.class);
        List<Song> results = jpaQuery.getResultList();
        return Response.ok().entity(Converter.SongsToObjectLocalSongList(results)).build();
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
