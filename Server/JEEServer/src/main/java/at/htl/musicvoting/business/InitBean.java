package at.htl.musicvoting.business;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.ResourceBundle;

@ApplicationScoped
public class InitBean {

    @PersistenceContext
    EntityManager em;

    @Inject
    private InitDatabase initDatabase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init){
        System.out.println("**********************init************************");
        ResourceBundle rb = ResourceBundle.getBundle("config");
        if(Boolean.valueOf(rb.getString("init")))
        {
            initDatabase.initialize();
        }
        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(em);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
