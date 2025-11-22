package in.NotesLink.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    //Here, the keys will change everytime the server restarts. So, Previous users will not be able to validated.

    //This string will be used to sign JWT tokens so that they can be verified later
    //Always try to keep the Secret in your application.properties
    private final String secretKey;

    public JWTService() {

        //Generates SecretKey everytimes the server starts
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGenerator.generateKey();
            //Converts the SecretKey to String so that we can easily use it
            this.secretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //Generates token for the users
    public String generateToken(String username) {
        //To put additional data inside JWT payload (like roles, permissions), Currently it's empty
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)  //adding the map claims which we created earlier
                .subject(username) //user identifier
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .and()
                .signWith(getKey()) //Signs the token with your secret key
                .compact(); //Converts everything into a compact JWT string

    }

    //Generates SecretKey for each user
    private SecretKey getKey() {
        byte[] sk = Decoders.BASE64.decode(secretKey); //Convering the String SecretKey into byte[]
        return Keys.hmacShaKeyFor(sk); //returns the key
    }

    //Below codes are redundant which you can copy and paste
    //Below code is used for Token Validation
    // Extracts the username (subject) from the JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // subject = username
    }

    // Generic method to extract any claim from token using a resolver function
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token); // get all claims first
        return claimResolver.apply(claims); // apply resolver to get specific claim
    }

    // Extracts all claims (payload) from JWT after verifying signature
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey()) // verify token signature using secret key
                .build()
                .parseSignedClaims(token) // parse and validate JWT
                .getPayload(); // return payload (body of JWT)
    }

    // Validates token: checks username match + token not expired
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUsername(token); // get username from token
        return (userName.equals(userDetails.getUsername()) // username must match
                && !isTokenExpired(token)); // token must not be expired
    }

    // Checks if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // compare expiry date with current date
    }

    // Extracts expiration date from token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // get 'exp' claim
    }

}
