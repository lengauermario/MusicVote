package at.htl.musicvoting.dao;

import at.htl.musicvoting.model.Song;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Random;

@Stateless
public class SongDao {
    @PersistenceContext
    public EntityManager em;

    public List<Song> findAll(){
        TypedQuery query = em.createNamedQuery("Song.findAll", Song.class);
        List<Song> songs = query.getResultList();
        return songs;
    }

    public List<Song> find(String term){
        String sql = "select s from Song s where";
        String[] elements = term.split(" ");
        for (String el: elements) {
            sql += " lower(s.title || ' ' ||s.artist) like lower('%" + el + "%') and";
        }
        sql = sql.substring(0, sql.length()-3);
        sql+= "order by s.title";
        TypedQuery query = em.createQuery(sql, Song.class);
        return query.setMaxResults(100).getResultList();
    }

    public void persist(Song newSong) {
        em.persist(newSong);
    }

    public Song getByID(long id) {
        TypedQuery query = em.createNamedQuery("Song.findById", Song.class).setParameter("ID", id);
        try{
            return (Song)query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public Song getRandom() {
        List<Long> ids = em.createQuery("select s.id from Song s", Long.class).getResultList();
        return this.getByID(ids.get(new Random().nextInt(ids.size()-1)));
    }
}

