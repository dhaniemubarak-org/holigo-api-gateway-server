package id.holigo.services.holigoapigatewayserver.web.validators;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {
        public static final List<String> openApiEndpoints = List.of("/api/v1/login");

        public Predicate<ServerHttpRequest> isSecure = request -> openApiEndpoints.stream()
                        .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
