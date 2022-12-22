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
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthorizationFilter implements GatewayFilter {

    private RouterValidator routerValidator;

    private OauthService oauthService;

    @Autowired
    public void setRouterValidator(RouterValidator routerValidator) {
        this.routerValidator = routerValidator;
    }

    @Autowired
    public void setOauthService(OauthService oauthService) {
        this.oauthService = oauthService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecure.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            OauthDto oauthDto;
            final String token = this.getAuthHeader(request);
            try {
                oauthDto = oauthService.getOauth(token);
                if (!oauthDto.isValid()) {
                    return this.onError(exchange, "Unauthorization", HttpStatus.UNAUTHORIZED);
                }
            } catch (JsonProcessingException | JMSException e) {
                return this.onError(exchange, "Unauthorization", HttpStatus.UNAUTHORIZED);
            }
            this.populateRequestWithHeaders(exchange, oauthDto);
            return chain.filter(exchange);
        }
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
        }
        return token;
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
