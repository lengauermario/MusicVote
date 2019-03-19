package at.htl.musicvoting.youtube;

import at.htl.musicvoting.dao.YoutubeVideoDao;
import at.htl.musicvoting.model.AvailabilityStatus;
import at.htl.musicvoting.model.ObjectYoutubeVideo;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;


import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.*;
import java.util.*;

@Stateless
public class Search {

    @Inject
    private YoutubeVideoDao dao;

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;
    private static String API_KEY;
    private YouTube youtube;

    public Search()
    {
        ResourceBundle rb = ResourceBundle.getBundle("youtube");
        API_KEY = rb.getString("apikey");
    }

    public List<ObjectYoutubeVideo> getVideos(String queryTerm) {
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
            List<ObjectYoutubeVideo> objectYoutubeVideoList = new LinkedList<ObjectYoutubeVideo>();
            for (SearchResult res: searchResultList) {
                ObjectYoutubeVideo obj = new ObjectYoutubeVideo(
                        res.getId().getVideoId(),
                        res.getSnippet().getTitle(),
                        res.getSnippet().getChannelTitle(),
                        res.getSnippet().getThumbnails().getDefault().getUrl(),
                        AvailabilityStatus.DOWNLOADABLE);
                if(dao.existsInDatabase(obj.getVideoId())){
                    obj.setStatus(dao.getAvailabilityStatus(obj.getVideoId()));
                }
                objectYoutubeVideoList.add(obj);
            }
            return objectYoutubeVideoList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new LinkedList<ObjectYoutubeVideo>();
    }

}
