package at.htl.musicvoting.business;

import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Song;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import javax.ejb.Stateless;
import javax.imageio.IIOException;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

@Stateless
public class InitDatabase {

    @Inject
    private SongDao dao;

    @Transactional
    public void initialize() {
        try{
            File folder = new File(ResourceBundle.getBundle("config").getString("startFolder"));
            if(folder.exists() && folder.isDirectory()){
                File arr[] = folder.listFiles();
                recursiveRead(arr,0);
            }
        }catch (Exception ex){
            System.out.println("error read source recursive");
        }
    }
    @Transactional
    private void recursiveRead(File[] arr, int level) {
        try{
            for (File f : arr){
                if (f.isFile()){
                    if(getFileExtension(f.getAbsolutePath()).equalsIgnoreCase("mp3")){
                        readMp3File(f.getAbsolutePath());
                    }
                }
                else if(f.isDirectory()){
                    recursiveRead(f.listFiles(),level + 1);
                }

            }
        }catch (Exception ex){
            System.out.println("Error in recursiveRead Method");
        }
    }

    @Transactional
    private void readMp3File(String path) {
        try{
            Mp3File mp3file = new Mp3File(path);
            Song newSong;
            if (path == null || mp3file.getId3v2Tag().getArtist() == null || mp3file.getId3v2Tag().getTitle() == null){
                return;
            }
            for (int i = 0; i < 50; i++) {
                newSong = new Song(path,mp3file.getId3v2Tag().getArtist(),mp3file.getId3v2Tag().getTitle());
                dao.persist(newSong);
            }

        }catch (Exception ex){
            System.out.println("could not read File");
        }
    }
    @Transactional
    private String getFileExtension(String fullName) {
        try{
            String fileName = new File(fullName).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }catch (Exception ex){
            System.out.println("Error in getFileExtension Method ");
        }
        return "";
    }
}
