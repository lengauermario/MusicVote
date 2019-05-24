package resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class YoutubedlWrapper {
    public static boolean fetchMp3FileFromYoutube(String videoId) {
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("youtube-dl -o /home/leon/Desktop/mp3/%(title)s.(mp3)s https://www.youtube.com/watch?v=" + videoId + " -x --audio-format mp3");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            /*while ((s = br.readLine()) != null)
                System.out.println("line: " + s);*/
            p.waitFor();
            //System.out.println ("exit: " + p.exitValue());
            p.destroy();
            if(p.exitValue() == 1)
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
