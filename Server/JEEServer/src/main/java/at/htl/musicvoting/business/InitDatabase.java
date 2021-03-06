package at.htl.musicvoting.business;

import at.htl.musicvoting.dao.SongDao;
import at.htl.musicvoting.model.Song;
import com.mpatric.mp3agic.Mp3File;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.File;
import java.util.ResourceBundle;

@Stateless
public class InitDatabase {

    @Inject
    private SongDao dao;

    public void initialize() {
        try{
            File folder = new File(ResourceBundle.getBundle("config").getString("startFolder"));
            if(folder.exists() && folder.isDirectory()){
                File arr[] = folder.listFiles();
		System.out.println("Found " + arr.length + " files in folder");
                recursiveRead(arr,0);
            }
        }catch (Exception ex){
            System.out.println("ERROR: Read source recursive");
        }
    }

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
            System.out.println("ERROR: In recursiveRead Method");
        }
    }

    private void readMp3File(String path) {
        try{
            Mp3File mp3file = new Mp3File(path);

            String artist = mp3file.getId3v2Tag().getArtist() == null ? "Various Artist" : mp3file.getId3v2Tag().getArtist();
            String title = mp3file.getId3v2Tag().getTitle() == null ? getSongTitle(path) : mp3file.getId3v2Tag().getTitle();

            Song newSong = new Song(path,artist, title);
            dao.persist(newSong);
        }catch (Exception ex){
            System.out.println("ERROR: Could not read File");
        }
    }

    private String getSongTitle(String path) {
        try {
            String fileName = new File(path).getName();
            int pos = fileName.lastIndexOf(".");
            if (pos == -1){
                return fileName;
            }
            return fileName.substring(0, pos);
        }catch (Exception ex){
            System.out.println("ERROR: Could not read Filename");
        }
        return "";
    }

    private String getFileExtension(String fullName) {
        try{
            String fileName = new File(fullName).getName();
            int dotIndex = fileName.lastIndexOf('.');
            return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        }catch (Exception ex){
            System.out.println("ERROR: Could not read file extension");
        }
        return "";
    }
}
