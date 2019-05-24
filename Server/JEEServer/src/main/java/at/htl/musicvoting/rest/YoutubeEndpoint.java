package at.htl.musicvoting.rest;

import at.htl.musicvoting.youtube.Search;
import at.htl.musicvoting.youtube.YoutubedlWrapper;
import at.htl.musicvoting.rest.response_object.ObjectYoutubeVideo;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Path("video")
@Stateless
public class YoutubeEndpoint {

    @Inject
    private YoutubedlWrapper youtubedl;

    @Inject
    private Search youtubeSearch;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ObjectYoutubeVideo> getVideos(@QueryParam("queryTerm") String param) throws UnsupportedEncodingException {
        String queryTerm = java.net.URLDecoder.decode(param, "UTF-8");
        List<ObjectYoutubeVideo> res = youtubeSearch.getVideos(queryTerm);
        return res;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/dl")
    public Response downloadVideo(ObjectYoutubeVideo video){
        youtubedl.fetchMp3FileFromYoutube(video);
        return Response.ok().build();
    }

}
