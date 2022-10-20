package com.tyc.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tyc.exception.AuthServiceException;
import com.tyc.exception.ErrorType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {

    @Value("${myjwt.secretkey}")
    private String secretKey;
    @Value("${myjwt.audience}")
    private String audience;
    @Value("${myjwt.issuer}")
    private String issuer;
    public String createToken(Long authId) {
        String token = null;
        try {
            /**
             * Token yayimcisi -> BilgeadamAuth
             * Token uretim tarihi
             * Token ne kadar bir sure ile gecerli olacak
             * Token icerisine tekrar kullanabilmek uzere Claim nesneleri koyabiliriz
             * Bu nesneler Key-Value seklinde bilgi tutarlar ve public olarak goruntulenebilirler
             * Token bilgisinin sifrelenmesi gereklidir. Bu nedenle imzalama yontemi secilmeli ve
             * belirlenen algoritma ile sign islemi yapilmalidir.
             */
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            token = JWT.create()
                    .withAudience(audience) // Kitle
                    .withIssuer(issuer) // Yayimci
                    .withIssuedAt(new java.util.Date()) // Olusturma zamani
                    .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60))) // Gecersiz olma zamani (60 sn sonra)
                    .withClaim("authId", authId) // Kullanilacak bilgiler
                    .sign(algorithm); // Sifreleme-imzalama islemi yapilir
            return token;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Long> getByIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withAudience(audience) // Kitle
                    .withIssuer(issuer) // Yayimci
                    .build(); // Kod olusturulur
            DecodedJWT decodedToken = jwtVerifier.verify(token); // Tokenin gecerliligi dogrulanir
            if(decodedToken == null) throw new AuthServiceException(ErrorType.INVALID_TOKEN);
            Long authId = decodedToken.getClaim("authId").asLong();
            return Optional.of(authId);
        } catch (Exception e) {
            throw new AuthServiceException(ErrorType.INVALID_TOKEN);
        }
    }
}
