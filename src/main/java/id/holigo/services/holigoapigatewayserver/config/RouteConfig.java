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
                                .route("holigo-otp-service-reset-pin",
                                                r -> r.path("/api/v1/otp/resetPin")
                                                                .filters(f -> f.filter(authorizationFilter))
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
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-pulsa-service"))
                                .route("holigo-prepaid-electricities-service", r -> r
                                                .path("/api/v1/prepaid/PLNPRA/products**", "/api/v1/prepaid/PLNPRA/fare",
                                                                "/api/v1/prepaid/PLNPRA/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-electricities-service"))
                                .route("holigo-prepaid-ewallet-service", r -> r
                                                .path("/api/v1/prepaid/EWAL**", "/api/v1/prepaid/EWAL/products**",
                                                                "/api/v1/prepaid/EWAL/fare",
                                                                "/api/v1/prepaid/EWAL/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-ewallet-service"))
                                .route("holigo-prepaid-games-service", r -> r
                                                .path("/api/v1/prepaid/GAME**", "/api/v1/prepaid/GAME/products**",
                                                                "/api/v1/prepaid/GAME/fare",
                                                                "/api/v1/prepaid/GAME/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-games-service"))
                                .route("holigo-product-service-prepaid",
                                                r -> r.path("/api/v1/prepaid**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-product-service"))
                                .route("holigo-product-service-postpaid",
                                                r -> r.path("/api/v1/postpaid**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-product-service"))
                                .route("holigo-transaction-service",
                                                r -> r.path("/api/v1/transactions**", "/api/v1/transactions/**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-transaction-service"))
                                .route("holigo-payment-service-payment_method",
                                                r -> r.path("/api/v1/payment_methods")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-payment-service"))
                                .route("holigo-payment-service",
                                                r -> r.path("/api/v1/payments", "/api/v1/payments**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-payment-service"))
                                .build();
        }
}
