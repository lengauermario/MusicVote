package resources;/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */



import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import javax.ejb.Stateful;
import java.io.*;
import java.util.*;


public class Search {

    /**
     * Define a global variable that identifies the name of a file that
     * contains the developer's API key.
     */
    private static final String PROPERTIES_FILENAME = "/home/leon/Desktop/YoutubeSearch/src/main/resources/youtube.properties";

    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
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

    public List<ResponseObject> getVideos(String queryTerm) {
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
            search.setMaxResults(10l);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            List<ResponseObject> responseObjectList = new LinkedList<ResponseObject>();
            for (SearchResult res: searchResultList) {
                responseObjectList.add(new ResponseObject(res.getId().getVideoId(), res.getSnippet().getTitle(), res.getSnippet().getChannelTitle(), res.getSnippet().getThumbnails().getDefault().getUrl()));
                /*System.out.println("-----------Video---------");
                System.out.println("Id: " + res.getId().getVideoId());
                System.out.println("Title: " + res.getSnippet().getTitle());
                System.out.println("Channel: " + res.getSnippet().getChannelTitle());
                System.out.println("Thumbnail: " + res.getSnippet().getThumbnails().getDefault().getUrl());*/
            }
            return responseObjectList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new LinkedList<ResponseObject>();
    }

}
