package com.scut.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.scut.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 鉴权过滤器 验证token
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取响应
        ServerHttpResponse response = exchange.getResponse();
        //如果是注册、登录请求则放行
        if (request.getURI().getPath().contains("/user/login") || request.getURI().getPath().contains("/user/submit")) {
            return chain.filter(exchange);
        }
        //获取请求头
        HttpHeaders headers = request.getHeaders();
        //请求头中获取令牌
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        log.info("有人访问");
        //判断令牌是否为空
        if (token.isEmpty()) {
            //响应中放入返回的状态码, 没有权限访问
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //返回相应
            return response.setComplete();
        }

        try {
            //如果令牌存在，解析jwt令牌，判断该令牌是否合法，如果不合法，则向客户端返回错误信息
            Claims claims = JwtUtil.parse(token);
            int result = JwtUtil.verifyToken(claims);
            if(result == 0){
                //合法，则向header中重新设置userId
                String _USER_ID = (String)claims.get("_USER_ID");
//                Integer id = (Integer) claims.get("id");
                log.info("find userid:{} from uri:{}",_USER_ID,request.getURI());
                //重新设置token到header中
                ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("_USER_ID", _USER_ID);
                }).build();
                exchange.mutate().request(serverHttpRequest).build();
            }
        }catch (Exception e){
            e.printStackTrace();
            //解析jwt令牌出错, 说明令牌过期或者伪造等不合法情况出现
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //放行
        return chain.filter(exchange);
//        //不放行
//        JSONObject message = new JSONObject();
//        message.put("data", "没有权限");
//        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = response.bufferFactory().wrap(bits);
//        response.setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
//        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
//        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
