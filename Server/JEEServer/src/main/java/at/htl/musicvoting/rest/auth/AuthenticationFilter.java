package at.htl.musicvoting.rest.auth;

import at.htl.musicvoting.rest.auth.annotation.Secured;
import com.google.common.hash.Hashing;

import javax.ws.rs.NameBinding;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ResourceBundle;


@Provider
@Secured
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        var cookies = containerRequestContext.getCookies();
        if(cookies.containsKey("token"))
            checkToken(cookies.get("token"), containerRequestContext);
        else
            abort(containerRequestContext);
}

    private void checkToken(Cookie cookie, ContainerRequestContext context) {
        try {
            String password = ResourceBundle.getBundle("config").getString("password");
            String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
            if(!cookie.getValue().equals(sha256hex)){
                abort(context);
            }
        } catch (Exception e){
            System.err.println("Fehler bei der Authentifikation");
            abort(context);
        }

    }

    private void abort(ContainerRequestContext context){
        context.abortWith( Response.status(Response.Status.UNAUTHORIZED).build());
    }
}
