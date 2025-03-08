package org.aldo.api.handler;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.aldo.api.data.entities.Professor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtHandler {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Professor professor) {
        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(professor.getEmail())
                .claim("accessRole", professor.getAccessRole().name())
                .issueTime(Date.from(issuedAt))
                .notBeforeTime(Date.from(issuedAt.plus(5, ChronoUnit.SECONDS)))
                .expirationTime(Date.from(issuedAt.plus(24, ChronoUnit.HOURS)))
                .build();
        Payload payload = new Payload(claims.toJSONObject());

        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
        try{
            jwsObject.sign(new MACSigner(secret.getBytes()));
        } catch (JOSEException e){
            throw new RuntimeException("Error while generating token", e);
        }

        System.out.println("Generating new access token at: " + new Date() + "with expiration at: " + claims.getExpirationTime());

        return jwsObject.serialize();
    }

    public boolean isValidToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            if (!signedJWT.verify(verifier)) {
                return false;
            }
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expiration == null || !expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return "invalid";
    }

    public String getEmailFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public Date getExpirationDateFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
