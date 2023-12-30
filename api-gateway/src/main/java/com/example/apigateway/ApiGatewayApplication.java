package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("produto_route", r -> r
						.path("/api/produto/**")
						.filters(f -> f.stripPrefix(2)
								.addRequestHeader("hello", "world")
								.addRequestHeader("Allow-Origins", "Pong"))
						.uri("lb://PRODUTO-CLIENT"))
				.build();
	}
}