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
                                                .path("/api/v1/prepaid/PLNPRE/products**",
                                                                "/api/v1/prepaid/PLNPRE/fare",
                                                                "/api/v1/prepaid/PLNPRE/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-electricities-service"))
                                .route("holigo-prepaid-ewallet-service", r -> r
                                                .path("/api/v1/prepaid/EWAL**", "/api/v1/prepaid/DWAL**",
                                                                "/api/v1/prepaid/EWAL/products**",
                                                                "/api/v1/prepaid/DWAL/products**",
                                                                "/api/v1/prepaid/EWAL/fare",
                                                                "/api/v1/prepaid/DWAL/fare",
                                                                "/api/v1/prepaid/EWAL/book",
                                                                "/api/v1/prepaid/DWAL/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-ewallet-service"))
                                .route("holigo-prepaid-games-service", r -> r
                                                .path("/api/v1/prepaid/GAME**", "/api/v1/prepaid/GAME/products**",
                                                                "/api/v1/prepaid/GAME/fare",
                                                                "/api/v1/prepaid/GAME/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-prepaid-games-service"))
                                .route("holigo-postpaid-pdam-service", r -> r
                                                .path("/api/v1/postpaid/PAM**", "/api/v1/postpaid/PAM/products**",
                                                                "/api/v1/postpaid/PAM/fare",
                                                                "/api/v1/postpaid/PAM/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-pdam-service"))
                                .route("holigo-postpaid-electricities-service", r -> r
                                                .path("/api/v1/postpaid/PLNPOST/products**",
                                                                "/api/v1/postpaid/PLNPOST/fare",
                                                                "/api/v1/postpaid/PLNPOST/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-electricities-service"))
                                .route("holigo-postpaid-telephone-service", r -> r
                                                .path("/api/v1/postpaid/TLP/products**",
                                                                "/api/v1/postpaid/TLP/fare",
                                                                "/api/v1/postpaid/TLP/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-telephone-service"))
                                .route("holigo-postpaid-multifinance-service", r -> r
                                                .path("/api/v1/postpaid/MFN/products**",
                                                                "/api/v1/postpaid/MFN/fare",
                                                                "/api/v1/postpaid/MFN/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-multifinance-service"))
                                .route("holigo-postpaid-insurance-service", r -> r
                                                .path("/api/v1/postpaid/INS/products**",
                                                                "/api/v1/postpaid/INS/fare",
                                                                "/api/v1/postpaid/INS/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-insurance-service"))
                                .route("holigo-postpaid-internet-service", r -> r
                                                .path("/api/v1/postpaid/NETV/products**",
                                                                "/api/v1/postpaid/NETV/fare",
                                                                "/api/v1/postpaid/NETV/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-internet-service"))
                                .route("holigo-postpaid-creditcard-service", r -> r
                                                .path("/api/v1/postpaid/CC/products**",
                                                                "/api/v1/postpaid/CC/fare",
                                                                "/api/v1/postpaid/CC/book")
                                                .filters(f -> f.filter(authorizationFilter))
                                                .uri("lb://holigo-postpaid-creditcard-service"))
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
                                                r -> r.path("/api/v1/paymentMethods")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-payment-service"))
                                .route("holigo-payment-service",
                                                r -> r.path("/api/v1/payments", "/api/v1/payments**",
                                                                "/api/v1/payments/**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-payment-service"))
                                .route("holigo-faq-service",
                                                r -> r.path("/api/v1/faq**", "/api/v1/faq/**",
                                                                "/api/v1/faq/**",
                                                                "/api/v1/faq/prepaid**",
                                                                "/api/v1/faq/postpaid**",
                                                                "/api/v1/faq/question/**",
                                                                "/api/v1/faq/topic/**")
                                                                .uri("lb://holigo-faq-service"))
                                .route("holigo-user-bank-account-service",
                                                r -> r.path("/api/v1/listBank**", "/api/v1/userBank**",
                                                                "/api/v1/userBank/**",
                                                                "/api/v1/userBank/list**",
                                                                "/api/v1/userBank/delete/**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-user-bank-account-service"))
                                .route("holigo-invoice-service",
                                                r -> r.path("/web/v1/invoice/**").uri("lb://holigo-invoice-service"))
                                .route("hotel-external-service",
                                                r -> r.path("/api/v1/tiket/hotels**", "/api/v1/tiket/rooms**",
                                                                "/api/v1/traveloka/hotels**",
                                                                "/api/v1/traveloka/rooms**")
                                                                .uri("lb://hotel-external-service"))
                                .route("holigo-referral-service",
                                                r -> r.path("/api/v1/userReferral**")
                                                                .uri("lb://holigo-user-service"))
                                .route("holigo-destination-service",
                                                r -> r.path("/api/v1/destinations**", "/api/v1/airports**")
                                                                .uri("lb://holigo-destination-service"))
                                .route("holigo-live-chat-service",
                                                r -> r.path("/api/v1/chat/**", "/api/v1/rooms/**")
                                                                .filters(f -> f.filter(authorizationFilter))
                                                                .uri("lb://holigo-live-chat-service"))
                                .route("holigo-live-chat-stomp-service",
                                                r -> r.path("/websocket-chat**", "/app/messages**",
                                                                "/chat/messages/**")
                                                                .uri("lb://holigo-live-chat-service"))
                                .build();
        }
}
