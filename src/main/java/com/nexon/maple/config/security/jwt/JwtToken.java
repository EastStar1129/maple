package com.nexon.maple.config.security.jwt;

import com.nexon.maple.config.security.auth.PrincipalDetails;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Date;

@Component
@ConfigurationProperties(prefix="jwt")
@Getter
public class JwtToken {

    @Value("${jwt.subject}")
    private String subject;
    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.name}")
    private String name;
    @Value("${jwt.prefix}")
    private String prefix;
    @Value("${jwt.header}")
    private String header;

    public Date getDate() {
        return new Date(System.currentTimeMillis() + (1000 * 60 * 10));
    }

    /*
        TODO : 분석 못끝냄
     */
    public String generateToken(PrincipalDetails principalDetails) throws IOException, ServletException {
//        String jwtToken = JWT.create()
//                .withSubject(subject)
//                .withExpiresAt(getDate())
//                .withClaim(name, principalDetails.getUserInfo().getName())
//                .sign(Algorithm.HMAC512(key));

        System.out.println("my token : " + prefix + " " + principalDetails.getUsername());
        return prefix + " " + principalDetails.getUsername();
    }

    public String getUserName(String jwtHeader) throws IOException, ServletException {
//        if(jwtHeader != null && jwtHeader.startsWith(prefix)) {
//            try {
//                jwtHeader = jwtHeader.replace(prefix, "");
//                String username = JWT.require(Algorithm.HMAC512(key))
//                        .build()
//                        .verify(jwtHeader)
//                        .getClaim(name)
//                        .asString();
//                return username;
//            }catch (SignatureVerificationException e) {
//                //log.error(e)
//            }catch (JWTDecodeException e) {
//                //log.error(e)
//            }catch (TokenExpiredException e) {
//                //만료된 토큰
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return "";
    }
}
