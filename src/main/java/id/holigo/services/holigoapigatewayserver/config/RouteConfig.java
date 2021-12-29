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
                return builder.routes().route("holigo-otp-service",
                                r -> r.path("/api/v1/otp/register", "/api/v1/otp/login", "/api/v1/otp/register/*")
                                                .uri("lb://holigo-otp-service"))
                                .route("holigo-oauth-service-login",
                                                r -> r.path("/api/v1/login").uri("lb://holigo-oauth-service"))
                                .route("holigo-oauth-service-test",
                                                r -> r.path("/api/v1/test").filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-oauth-service"))
                                .route("holigo-user-service",
                                                r -> r.path("/api/v1/users", "/api/v1/users/**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-user-service"))
                                .route("holigo-guest-service",
                                                r -> r.path("/api/v1/guests").uri("lb://holigo-user-service"))
                                .route("holigo-prepaid-pulsa-service", r -> r
                                                .path("/api/v1/prepaid/pulsa/products**", "/api/v1/prepaid/pulsa/fare",
                                                                "/api/v1/prepaid/pulsa/book")
                                                .uri("lb://holigo-prepaid-pulsa-service"))
                                .route("holigo-product-service-prepaid",
                                                r -> r.path("/api/v1/prepaid**").uri("lb://holigo-product-service"))
                                .route("holigo-product-service-postpaid",
                                                r -> r.path("/api/v1/postpaid**").uri("lb://holigo-product-service"))
                                .route("holigo-transaction-service",
                                                r -> r.path("/api/v1/transactions**")
                                                                .uri("lb://holigo-transaction-service"))
                                .build();
        }
}
