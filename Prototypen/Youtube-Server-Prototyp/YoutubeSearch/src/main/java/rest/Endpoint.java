package rest;

import resources.ResponseObject;
import resources.Search;
import resources.YoutubedlWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Path("video")
public class Endpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFirstVideo(@QueryParam("queryTerm") String param) throws UnsupportedEncodingException {
        String queryTerm = java.net.URLDecoder.decode(param, "UTF-8");
        List<ResponseObject> res = Search.getInstance().getVideos(queryTerm);

        return Response
            .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity(res)
                .build();
    }

    @GET
    @Path("/dl")
    public Response downloadVideo(@QueryParam("id") String id){
        if(YoutubedlWrapper.fetchMp3FileFromYoutube(id)){
            return Response.ok().build();
        }
        return Response.serverError().build();
    }

}
