package in.NotesLink.config;

import in.NotesLink.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Dummy Username and Password
//  Username - Rohit Chaurasiya and Password - Rohit

//Making Our Own Security Chain
@Configuration
//It tells Spring Boot that you are overriding the default security configuration. Without it, Spring Boot’s auto-configured security rules will be used.
@EnableWebSecurity  //This tells to see here for Security and not implement the Default
public class SecurityConfig{

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;

    //SecurityFilterChain is essentially a list of servlet filters that intercept incoming HTTP requests and apply various security checks before allowing the request to reach your controllers.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //The below code disables CSRF
        http.csrf(customizer -> customizer.disable());

        //This Tells that any HTTP request on the server needs to be authenticated
        http.authorizeHttpRequests(request -> request
                .requestMatchers("api/user/register","api/user/login").permitAll()
                .anyRequest().authenticated());

        //But Only with the above we cannot see the homepage as we have no where to keep authentication credentials
        //This is for the Browser to Login for Authentication
        // http.formLogin(Customizer.withDefaults());

        //But Only with the above FormLogin, For Postman which does only API request we get HTML code for Form
        //But not the Output that we need
        //So, we need to implement this,
        http.httpBasic(Customizer.withDefaults());

        //Spring Security will not create or use an HTTP session to store the authentication state.
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //Telling to run jwtFilter before running UsernamePasswordAuthenticationFilter
        //This is done to verify jwtToken
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }

    //UserDetailsService → fetches user details from a source (like DB).
    //AuthenticationProvider → verifies if the provided credentials are valid.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        //Tells the provider how to verify passwords.
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
          provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        //Tells the provider where to get user details when someone tries to log in.
        provider.setUserDetailsService(userDetailsService);
        return  provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //UserDetailsService is a core interface used to load user-specific data for authentication.
    //Here, We are implementing our own UserDetailsSevice
    //fetches user details from a source (like DB)
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        //Creating Users so that they can login using these Credentials
//        //This is not the ideal way we need to use Database for this
//        UserDetails user1 = User.withDefaultPasswordEncoder().password("Rohit123").username("Rohit").roles("USER").build();
//        UserDetails user2 = User.withDefaultPasswordEncoder().password("Ram123").username("Ram").roles("USER").build();
//
//        //Returning the UserDetailsService for these two users
//        return  new InMemoryUserDetailsManager(user1,user2);
//    }
}
