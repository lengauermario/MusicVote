package at.htl.musicvoting.youtube;

import at.htl.musicvoting.model.YoutubeResponseObject;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;


import java.io.*;
import java.util.*;


public class Search {

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
    private static String API_KEY;
    private static Search instance;

    public static Search getInstance() {
        if(instance == null)
            instance = new Search();
        return instance;
    }
    private Search()
    {
        String apiKey = "AIzaSyBnABJA3xX04oC3DHIUJDspuCDBGFbhQEk";
        ResourceBundle rb = ResourceBundle.getBundle("youtube");
        API_KEY = rb.getString("apikey");
    }
    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private YouTube youtube;

    public List<YoutubeResponseObject> getVideos(String queryTerm) {
        try {
            youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            search.setKey(API_KEY);
            search.setQ(queryTerm);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title, snippet/channelTitle, snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<YoutubeResponseObject> youtubeResponseObjectList = new LinkedList<YoutubeResponseObject>();
            for (SearchResult res: searchResultList) {
                youtubeResponseObjectList.add(new YoutubeResponseObject(res.getId().getVideoId(), res.getSnippet().getTitle(), res.getSnippet().getChannelTitle(), res.getSnippet().getThumbnails().getDefault().getUrl()));
            }
            return youtubeResponseObjectList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new LinkedList<YoutubeResponseObject>();
    }

}
