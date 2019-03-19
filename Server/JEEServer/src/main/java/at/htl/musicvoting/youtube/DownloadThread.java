package at.htl.musicvoting.youtube;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.function.Function;

public class DownloadThread extends Thread {
    private String videoId;
    private String path;
    private Function<Integer, Void> callback;

    public DownloadThread(String id, String path, Function<Integer, Void> callback){
        videoId = id;
        this.path = path;
        this.callback = callback;
    }

    @Override
    public void run() {
        String s;
        Process p;
        int exitcode = 1;
        try {
            p = Runtime.getRuntime().exec("youtube-dl -o " + path + " https://www.youtube.com/watch?v=" + videoId + " -x --audio-format mp3");
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
