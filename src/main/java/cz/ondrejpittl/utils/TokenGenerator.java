package cz.ondrejpittl.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class TokenGenerator {

    private static final String SECRET = "_@lFr3D";


    public static String generateToken(User user) {
        if(Dev.MOCK_TOKEN_MODE) return "aaa";

        try {
            String identity = TokenGenerator.buildIdentity(user);
            return JWT.create()
                    .withSubject(String.valueOf(user.getId()))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withIssuer("auth0")
                    .sign(Algorithm.HMAC256(identity));
        } catch (UnsupportedEncodingException | JWTCreationException exception){
            //UTF-8 encoding ERR | Invalid cfg
            return null;
        }
    }

    public static boolean verifyToken(String token, User user) {
        try {

            String identity = TokenGenerator.buildIdentity(user);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(identity))
                    .withIssuer("auth0")

                    .build();
            return verifier.verify(token) != null; // asi

        } catch (UnsupportedEncodingException | JWTVerificationException exception){
            //UTF-8 encoding not supported
            return false;
        }
    }

    private static String buildIdentity(User user) {
        return user.getFirstName() + TokenGenerator.SECRET
                + user.getEmail() + TokenGenerator.SECRET;
    }
}



