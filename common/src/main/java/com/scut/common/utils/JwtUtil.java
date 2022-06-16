package com.scut.common.utils;

import com.scut.common.constant.HttpConstant;
import com.scut.common.dto.request.CreateTokenParam;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    private static final long expiredMills = 15 * 24 * 60 * 60 * 1000;//15天有效期
    private static final String signature = "das@!#^11vfiuq35u98-4y287qe1";

    public static String createToken(CreateTokenParam createTokenParam) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim(HttpConstant.USER_ID_HEADER, String.valueOf(createTokenParam.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMills))
                //signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    public static Claims parse(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (ExpiredJwtException e) {
            log.info("Token:{} 已经过期", token);
            return null;
        } catch (Exception e) {
            log.info("Token:{} 解析异常", token);
            return null;
        }
    }

    /**
     * 是否过期
     *
     * @param claims
     * @return 0：有效，1：失效，token过期  2：失效，解析异常
     */
    public static int verifyToken(Claims claims) {
        if (claims == null) {
            return 2;
        }
        try {
            if (claims.getExpiration().before(new Date(System.currentTimeMillis()))) {
                //token已经过期
                return 1;
            }
        } catch (ExpiredJwtException ex) {
            return 1;
        } catch (Exception e) {
            return 2;
        }
        return 0;
    }
}
