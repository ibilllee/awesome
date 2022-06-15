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
                .claim("_USER_ID",String.valueOf(createTokenParam.getId()))
                .setExpiration(new Date(System.currentTimeMillis()+time))
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                .compact();
        return jwtToken;
    }
    public static Claims parse(String token){
        try{
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return claims;
//        Date expirationTime = claims.getExpiration();
        }catch (ExpiredJwtException e){
            throw new RuntimeException("token 已经过期");
        }catch (Exception e){
            throw new RuntimeException("token 解析异常");
        }
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return 0：有效，1：失效，token过期  2：失效，解析异常
     */
    public static int verifyToken(Claims claims) {
        if(claims==null){
            return 2;
        }
        try {
            if(claims.getExpiration().before(new Date(System.currentTimeMillis()))){
                //token已经过期
                return 1;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        }catch (Exception e){
            return 2;
        }
        return 0;
    }
}
