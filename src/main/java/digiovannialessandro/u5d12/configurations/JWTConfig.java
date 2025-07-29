package digiovannialessandro.u5d11.configurations;

import digiovannialessandro.u5d11.ecxeptions.UnauthorizedException;
import digiovannialessandro.u5d11.entities.Dipendente;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTConfig {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Dipendente dipendente) {


        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
                .subject(String.valueOf(dipendente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String accessToken) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(accessToken);
        } catch (Exception ex) {

            throw new UnauthorizedException("Problemi con il token! Effettuare di nuovo il login!"); // --> 401
        }

    }}
