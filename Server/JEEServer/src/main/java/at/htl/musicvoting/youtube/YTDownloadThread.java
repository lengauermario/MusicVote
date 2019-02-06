package at.htl.musicvoting.youtube;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.function.Function;

public class YTDownloadThread extends Thread {
    String videoId;
    Function<Integer, Void> callback;
    public YTDownloadThread(String id, Function<Integer, Void> callback){
        videoId = id;
        this.callback = callback;
    }

    @Override
    public void run() {
        String s;
        Process p;
        int exitcode = 1;
        try {
            p = Runtime.getRuntime().exec("youtube-dl -o " + ResourceBundle.getBundle("config").getString("youtubeFolder") + "\\%(title)s.mp3 https://www.youtube.com/watch?v=" + videoId + " -x -j --audio-format mp3");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();

            System.out.println ("exit: " + p.exitValue());
            exitcode = p.exitValue();
            p.destroy();
        } catch (Exception e) {

        }
        System.out.println("here");
        callback.apply(exitcode);
    }
}
