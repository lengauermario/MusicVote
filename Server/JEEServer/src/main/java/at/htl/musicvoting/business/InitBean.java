package at.htl.musicvoting.business;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.util.ResourceBundle;

@ApplicationScoped
public class InitBean {

    @Inject
    private InitDatabase initDatabase;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init){
        System.out.println("**********************init************************");
        ResourceBundle rb = ResourceBundle.getBundle("config");
        if(Boolean.valueOf(rb.getString("init")))
        {
            initDatabase.initialize();
        }


    }
}
