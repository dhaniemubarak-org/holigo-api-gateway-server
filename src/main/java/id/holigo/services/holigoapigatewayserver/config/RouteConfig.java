package id.holigo.services.holigoapigatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("holigo-user-service", r -> r.path("/api/v1/users*", "/api/v1/users/*").uri("lb://holigo-user-service"))
                .build();
    }
}
