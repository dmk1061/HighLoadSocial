package org.otus.social.lib.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${secret-key}")
    public String SECRET_KEY ;
    public static final String USER_ID = "user_id";

    public String extractUserName(final String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(final String token) {
        return extractClaim (token, Claims::getExpiration);
    }

    public <T>  T extractClaim(final String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims (final String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private  Boolean isTokenExpired (final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken (final UserDetails userDetails, final String id){
        final Map<String, Object>claims = new HashMap<>();
        claims.put(USER_ID, id);
        return createToken(claims, userDetails.getUsername());
    }
    private String createToken(final Map<String, Object> claims, final String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public  Boolean validateToken(final String token, final UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    public  Boolean validateToken(final String token, final String user){
        final String username = extractUserName(token);
        return (username.equals(user)&& !isTokenExpired(token));
    }

    public  String extractJwtFromRequest(final HttpServletRequest request)  {
        final  String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return "";
    }
}
