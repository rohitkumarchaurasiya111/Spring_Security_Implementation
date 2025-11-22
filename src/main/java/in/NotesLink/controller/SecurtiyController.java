package in.NotesLink.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SecurtiyController {

    @GetMapping("/csrf")
    public CsrfToken getCSRF(HttpServletRequest httpServletRequest){
        //This returns the session Id.
//        return  httpServletRequest.getSession().getId();

        //This will return the Csrf Token
        return  (CsrfToken) httpServletRequest.getAttribute("_csrf");
    }
}
