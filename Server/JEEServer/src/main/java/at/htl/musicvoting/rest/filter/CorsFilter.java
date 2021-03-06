package at.htl.musicvoting.rest.filter;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext cres) throws IOException {

        var tmp = requestContext.getHeaders();
        String origin;
        if(tmp != null){
            var originlist = tmp.get("Origin");
            if(originlist != null)
                origin = originlist.get(0);
            else
                origin = "*";
        }
        else
            origin = "*";
        cres.getHeaders().add("Access-Control-Allow-Origin", origin);
        cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }

}
