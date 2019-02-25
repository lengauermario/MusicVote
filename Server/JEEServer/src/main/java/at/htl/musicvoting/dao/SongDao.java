package at.htl.musicvoting.dao;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class SongDao {
    @PersistenceContext
    public EntityManager em;

    public List<Song> findAll(){
        TypedQuery query = em.createNamedQuery("Song.findAll", Song.class);
        List<Song> songs = query.getResultList();
        return songs;
    }

    public void persist(Song newSong) {
        em.persist(newSong);
    }

    public Song getByID(long id) {
        TypedQuery query = em.createNamedQuery("Song.findById", Song.class).setParameter("ID", id);
        Song song = (Song) query.getSingleResult();
        return song;
    }
}

