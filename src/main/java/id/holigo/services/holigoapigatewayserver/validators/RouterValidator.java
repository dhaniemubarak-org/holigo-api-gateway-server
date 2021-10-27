package id.holigo.services.holigoapigatewayserver.validators;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {
        public static final List<String> openApiEndpoints = List.of("/api/v1/user/6285718187373", "/api/v1/otp",
                        "/api/v1/register", "/api/v1/authenticate", "/h2-console");

        public Predicate<ServerHttpRequest> isSecure = request -> openApiEndpoints.stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
