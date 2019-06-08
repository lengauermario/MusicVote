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
        StringBuilder sql = new StringBuilder("select s from Song s");
        if (term != null && term.length() > 0) {
            String[] elements = term.split(" ");
            sql.append(" where ");
            for(int i = 0;i<elements.length;i++){
                sql.append("lower(s.title || ' ' || s.artist) like lower('%" + elements[i] + "%')");
                if(i < elements.length -1)
                    sql.append(" and ");
            }
        }
        sql.append(" order by s.title");
        TypedQuery query = em.createQuery(sql.toString(), Song.class);
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

