package com.scut.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.scut.common.constant.HttpConstant;
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

/**
 * 鉴权过滤器 验证token
 */
@Component
@Slf4j
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();//获取请求
        ServerHttpResponse response = exchange.getResponse();//获取响应
        //如果是注册、登录请求则放行
        if (request.getURI().getPath().contains("/user/login") ||
                request.getURI().getPath().contains("/user/submit")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = request.getHeaders();//获取请求头
        String token = headers.getFirst(HttpConstant.AUTHORIZATION);//请求头中获取令牌
        if (token == null || token.isEmpty()) { //判断令牌是否为空
            return rejectRequest(response);
        }

        try {
            //如果令牌存在，解析jwt令牌，判断该令牌是否合法，如果不合法，则向客户端返回错误信息
            Claims claims = JwtUtil.parse(token);
            if (claims == null) return rejectRequest(response);
            int result = JwtUtil.verifyToken(claims);
            if (result == 0) {
                //合法，则向header中重新设置userId
                String _USER_ID = (String) claims.get(HttpConstant.USER_ID_HEADER);
                //重新设置token到header中
                ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                    httpHeaders.remove(HttpConstant.USER_ID_HEADER);
                    httpHeaders.add(HttpConstant.USER_ID_HEADER, _USER_ID);
                }).build();
                exchange.mutate().request(serverHttpRequest).build();
            } else
                return rejectRequest(response);
        } catch (Exception e) {
            log.error("Token 解析异常: {}", e.getMessage());
            return rejectRequest(response);
        }
        //放行
        return chain.filter(exchange);
    }

    private Mono<Void> rejectRequest(ServerHttpResponse response) {
        JSONObject message = new JSONObject();
        message.put("code", "401");
        message.put("msg", "Unauthorized");
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
