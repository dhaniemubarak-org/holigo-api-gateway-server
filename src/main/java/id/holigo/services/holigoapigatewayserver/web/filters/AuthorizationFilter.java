package id.holigo.services.holigoapigatewayserver.web.filters;

import javax.jms.JMSException;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import id.holigo.services.common.model.OauthDto;
import id.holigo.services.holigoapigatewayserver.services.OauthService;
import id.holigo.services.holigoapigatewayserver.web.validators.RouterValidator;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RefreshScope
@Component
public class AuthorizationFilter implements GatewayFilter {
    @Autowired
    private RouterValidator routerValidator;

    @Autowired
    private OauthService oauthService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecure.test(request)) {
            log.info("Filter is running");
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            OauthDto oauthDto = new OauthDto();
            final String token = this.getAuthHeader(request);
            log.info("Token in filter -> {}", token);
            try {
                oauthDto = oauthService.getOauth(token);
                if (!oauthDto.isValid()) {
                    log.info("token invalid!");
                    return this.onError(exchange, "Unauthorization", HttpStatus.UNAUTHORIZED);
                }
            } catch (JsonProcessingException | JMSException e) {
                log.info("invalid catch");
                return this.onError(exchange, "Unauthorization", HttpStatus.UNAUTHORIZED);
            }
            log.info("Exchange is mutate ...");
            this.populateRequestWithHeaders(exchange, oauthDto);
            return chain.filter(exchange);
        }
        log.info("Open");
        return chain.filter(exchange);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, OauthDto oauthDto) {
        exchange.getRequest().mutate().header("user-id", oauthDto.getSubject()).header("user_id", oauthDto.getSubject()).build();
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        String authorizationHeader = request.getHeaders().getOrEmpty("Authorization").get(0);
        String token = null;
        if (authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring("Bearer ".length());
            log.info("Extract token : {}", token);
        } else {
            log.info("Bearer not detected");
        }
        return token;
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
