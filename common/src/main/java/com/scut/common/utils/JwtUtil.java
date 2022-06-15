package com.scut.common.utils;

import com.scut.common.dto.request.CreateTokenParam;
import io.jsonwebtoken.*;

import javax.xml.crypto.Data;
import java.util.Date;

public class JwtUtil {
    private static long time = 1000*60*60*24;//一天的有效时间
    private static String signature = "awesome";

    public static String createToken(CreateTokenParam createTokenParam){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload
                .claim("username",createTokenParam.getUsername())
                .claim("email",createTokenParam.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(String.valueOf(createTokenParam.getId()))
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }
    public void parse(String token){
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String username = claims.get("username").toString();
        String email = claims.get("email").toString();
        Date expirationTime = claims.getExpiration();

    }
}
