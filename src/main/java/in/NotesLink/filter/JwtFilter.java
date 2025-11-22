package in.NotesLink.filter;

import in.NotesLink.model.NotesLinkUserDetails;
import in.NotesLink.service.JWTService;
import in.NotesLink.service.NotesLinkUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//Extending so that this class will behave like a filter
//and will run once per request
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //From the client we will get Bearer token which looks like (Bearer 39u9304092302099)

        //Gets the Authorization header from the request.
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //Removes "Bearer " and keeps just the token part.
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        // If No authentication has been set yet (avoids overwriting existing authentication)
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //Loads UserDetails for this username from your custom NotesLinkUserDetailsService
            //This lets Spring know what roles/authorities this user has.
            UserDetails userDetails = applicationContext.getBean(NotesLinkUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails)){
                //Creates a Spring Security authentication object
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                //Adds request details (like IP address, session ID)
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //Sets this authentication object into the security context, which means Spring Security now considers this user logged in.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //Passes the request along to the next filter in the chain
        filterChain.doFilter(request,response);
    }
}
