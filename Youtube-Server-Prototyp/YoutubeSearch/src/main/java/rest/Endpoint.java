package rest;

import resources.Search;
import resources.YoutubedlWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;

@Path("video")
public class Endpoint {

    @GET
    @Path("/{queryTerm}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFirstVideo(@PathParam("queryTerm") String param) throws UnsupportedEncodingException {
        String queryTerm = java.net.URLDecoder.decode(param, "UTF-8");
        String res = Search.getInstance().getVideo(queryTerm);
        YoutubedlWrapper.fetchMp3FileFromYoutube(res);
        return res;
    }

}
