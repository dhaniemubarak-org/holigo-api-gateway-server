package id.holigo.services.holigoapigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.holigo.services.holigoapigatewayserver.web.filters.AuthorizationFilter;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
public class RouteConfig {
        @Autowired
        AuthorizationFilter authorizationFilter;

        @Bean
        public RouteLocator routes(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route("holigo-oauth-service-login",
                                                r -> r.path("/api/v1/login").uri("lb://holigo-oauth-service"))
                                .route("holigo-oauth-service-test",
                                                r -> r.path("/api/v1/test").filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-oauth-service"))
                                .route("holigo-user-service",
                                                r -> r.path("/api/v1/user*", "/api/v1/user/**", "/api/v1/users*",
                                                                "/api/v1/users/**")
                                                                // .filters(f -> f.filter(authenticationFilter))
                                                                .uri("lb://holigo-user-service"))
                                .route("holigo-otp-service", r -> r.path("/api/v1/otp/registration")
                                                // .filters()
                                                .uri("lb://holigo-otp-service"))
                                .build();
        }
}
