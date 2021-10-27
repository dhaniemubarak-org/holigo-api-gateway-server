package id.holigo.services.holigoapigatewayserver.util;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // public Claims getAllClaimsFromToken(String token) {
    // return
    // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    // }

    // private boolean isTokenExpired(String token) {
    // return this.extractAllClaims(token).getExpiration().before(new Date());
    // }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
