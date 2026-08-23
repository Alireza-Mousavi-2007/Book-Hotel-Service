package org.bookhotel.bookinghotelsystem.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public class JwtToken {

    @Value("${security.jwt}")
    private String secretKey;
    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    public String tokenMaker(String username, List<String> authorities) {

        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .withClaim("authorities", authorities)
                .sign(algorithm);
    }

    public DecodedJWT tokenVerifier(String token) {
        var verified = JWT.require(algorithm).build();
        return verified.verify(token);
    }

}
