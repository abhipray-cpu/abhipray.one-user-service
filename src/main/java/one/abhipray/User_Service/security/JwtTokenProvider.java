package one.abhipray.User_Service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component
@Slf4j
public class JwtTokenProvider {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 hour expiration

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("username", userDetails.getUsername())
                .claim("email", userDetails.getEmail())
                .claim("userId", userDetails.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Check if the token is valid and not expired
    public boolean validateToken(String token) {

        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        } catch (SignatureException e) {
            log.error("there is an error with the signature of you token ");
        }
        return false;
    }

    // Extract the username from the JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Map<String, String> getUserDetails(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("username", claims.get("username", String.class));

        // Correctly handle the userId claim as Integer and convert to String
        Integer userId = claims.get("userId", Integer.class);
        userDetails.put("userId", userId.toString());

        userDetails.put("email", claims.get("email", String.class));
        return userDetails;
    }
}