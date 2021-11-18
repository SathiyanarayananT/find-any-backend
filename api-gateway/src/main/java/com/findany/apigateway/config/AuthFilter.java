package com.findany.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.findany.apigateway.constants.AppConstants.X_USER_ID;

@RefreshScope
@Component
public class AuthFilter implements GatewayFilter {

    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request) && this.isUserMissing(request))
                return this.onError(exchange, "Required Headers missing in request", HttpStatus.UNAUTHORIZED);

            final String token = this.getAuthHeader(request);
            final String username = this.getUserHeader(request);
            if (!jwtTokenUtil.validateToken(token, username))
                return this.onError(exchange, "Auth token is invalid", HttpStatus.UNAUTHORIZED);

        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION);
    }

    private String getUserHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty(X_USER_ID).get(0);
    }

    private boolean isUserMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(X_USER_ID);
    }
}
