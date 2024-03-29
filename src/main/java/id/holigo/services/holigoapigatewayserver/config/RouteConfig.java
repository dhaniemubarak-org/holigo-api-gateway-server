package id.holigo.services.holigoapigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import id.holigo.services.holigoapigatewayserver.web.filters.AuthorizationFilter;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
public class RouteConfig {
    AuthorizationFilter authorizationFilter;

    @Autowired
    public void setAuthorizationFilter(AuthorizationFilter authorizationFilter) {
        this.authorizationFilter = authorizationFilter;
    }

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
                .route("holigo-user-service",
                        r -> r.path("/api/v1/users", "/api/v1/users/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-user-service"))
                .route("holigo-guest-service",
                        r -> r.path("/api/v1/guests").uri("lb://holigo-guest-service"))
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
                .route("holigo-prepaid-streaming-service", r -> r
                        .path("/api/v1/prepaid/STREAMING**", "/api/v1/prepaid/STREAMING/products**",
                                "/api/v1/prepaid/STREAMING/fare",
                                "/api/v1/prepaid/STREAMING/book")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("lb://holigo-prepaid-streaming-service"))
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
                .route("holigo-postpaid-gas-service", r -> r
                        .path("/api/v1/postpaid/GAS/products**",
                                "/api/v1/postpaid/GAS/fare",
                                "/api/v1/postpaid/GAS/book")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("lb://holigo-postpaid-gas-service"))
                .route("holigo-postpaid-banktransfer-service", r -> r
                        .path("/api/v1/postpaid/TF/product**",
                                "/api/v1/postpaid/TF/fare",
                                "/api/v1/postpaid/TF/book",
                                "/api/v1/postpaid/TF**")
                        .filters(f -> f.filter(authorizationFilter))
                        .uri("lb://holigo-postpaid-banktransfer-service"))
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
                                        "/api/v1/faq/topic/**",
                                        "/api/v1/faq/search/**",
                                        "/api/v1/faq/popular/**",
                                        "/api/v1/faq/question/**")
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
                .route("holigo-destination-service-search",
                        r -> r.path("/api/v1/search/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-destination-service"))
                .route("holigo-destination-service",
                        r -> r.path("/api/v1/destinations**",
                                        "/api/v1/airports**",
                                        "/api/v1/stations**",
                                        "/api/v1/countries/**")
                                .uri("lb://holigo-destination-service"))
                .route("holigo-live-chat-service",
                        r -> r.path("/api/v1/chat/**", "/api/v1/rooms/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-live-chat-service"))
                .route("holigo-holiclub-service",
                        r -> r.path("/api/v1/holiclub", "/api/v1/holiclub**",
                                        "api/v1/holiclub/**", "/api/v1/userClubHistory**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-holiclub-service"))
                .route("holigo-coupon-service",
                        r -> r.path("/api/v1/coupons", "/api/v1/coupons**",
                                        "/api/v1/coupons/**", "/api/v1/applyCoupon**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-coupon-service"))
                .route("holigo-point-service",
                        r -> r.path("/api/v1/pointHistories**", "/api/v1/userPoint**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-point-service"))
                .route("holigo-passenger-service",
                        r -> r.path("/api/v1/passengers**",
                                        "/api/v1/passengers/**",
                                        "/api/v1/contactPerson**",
                                        "/api/v1/contactPerson/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-passenger-service"))
                .route("holigo-billing-data-service",
                        r -> r.path("/api/v1/billingData**", "/api/v1/billingData/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-billing-data-service"))
                .route("holigo-hotel-service",
                        r -> r.path("/api/v1/hotels/populars**",
                                        "/api/v1/hotels/type",
                                        "/api/v1/hotels/facility",
                                        "/api/v1/hotels/popularDestinations**",
                                        "/api/v1/hotels/available/**",
                                        "/api/v1/hotels/fare**", "/api/v1/hotels/book**",
                                        "/api/v1/hotel/available**",
                                        "/api/v1/hotels/jobs/**",
                                        "/api/v1/hotels/calendar**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-hotel-service"))
                .route("holigo-hotel-review-service",
                        r -> r.path("/api/v1/hotel/review/**", "/api/v1/hotel/review/images/**",
                                        "/api/v1/hotel/review/ratings/**",
                                        "/api/v1/hotel/review**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-hotel-review-service"))
                .route("holigo-airlines-service",
                        r -> r.path("/api/v1/airlines/availabilities**",
                                        "/api/v1/airlines/fares",
                                        "/api/v1/airlines/fares/**",
                                        "/api/v1/airlines/book",
                                        "/api/v1/airlines/transactions/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-airlines-service"))
                .route("holigo-product-service",
                        r -> r.path("/api/v1/products**", "/api/v1/products/**",
                                        "/api/v1/products/clearManager**")
                                .uri("lb://holigo-product-service"))
                .route("holigo-train-service",
                        r -> r.path("/api/v1/train/availabilities**", "/api/v1/train/fares",
                                        "/api/v1/train/fares/**", "/api/v1/train/book", "/api/v1/train/transactions/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-train-service"))
                .route("holigo-story-service",
                        r -> r.path("/api/v1/stories**", "/api/v1/stories/uploadVideo/**",
                                        "/api/v1/stories/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-story-service"))
                .route("holigo-collection-service",
                        r -> r.path("/api/v1/collection/favorite**",
                                        "/api/v1/collection/favorite/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-collection-service"))
                .route("holigo-config-service",
                        r -> r.path("/api/v1/config/**",
                                        "/api/v1/config**")
                                .uri("lb://holigo-config-service"))
                .route("holigo-email-service",
                        r -> r.path("/api/v1/emailVerifications**",
                                        "/api/v1/emailVerifications/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-email-service"))
                .route("holigo-email-service-verification",
                        r -> r.path("/emailVerifications**")
                                .uri("lb://holigo-email-service"))
                .route("holigo-upcoming-schedule-service",
                        r -> r.path("/api/v1/upcomingSchedule**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-upcoming-schedule-service"))
                .route("holigo-push-notification-service",
                        r -> r.path("/api/v1/pushNotifications**", "/api/v1/pushNotifications/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-push-notification-service"))
                .route("holigo-account-balance-service",
                        r -> r.path("/api/v1/accountStatements**", "/api/v1/accountBalance**",
                                        "/api/v1/creditAccountStatement*")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-account-balance-service"))
                .route("holigo-pin-service",
                        r -> r.path("/api/v1/pin/validate", "/api/v1/pin/users/**")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-pin-service"))
                .route("holigo-deposit-service",
                        r -> r.path("/api/v1/deposit/book")
                                .filters(f -> f.filter(authorizationFilter))
                                .uri("lb://holigo-deposit-service"))
                .build();
    }
}
