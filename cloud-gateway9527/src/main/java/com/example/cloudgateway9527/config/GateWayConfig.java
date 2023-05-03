package com.example.cloudgateway9527.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.function.Function;

@Configuration
public class GateWayConfig
{
    // http://localhost:9527/guonei?uname=vincent
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder)
    {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        /**
         * spring.cloud.gateway.routes[0].id = path_route_atguigu
         * spring.cloud.gateway.routes[0].uri = http://news.baidu.com/guonei
         * spring.cloud.gateway.routes[0].predicates=/guonei
         */
        routes
                .route("path_route_atguigu", r -> r.path("/guonei").uri("http://news.baidu.com/guonei"))
                .route("payment_routh3", paymentRouth3())
                .build();

        return routes.build();
    }

    private Function<PredicateSpec, Buildable<Route>> paymentRouth3() {
        return r -> r.path("/gateway/gw3/**").and().header("X-Request-Id", "\\d+").and().method(HttpMethod.GET, HttpMethod.POST)
                .filters(f -> f.rewritePath("/gateway/gw3/(?<path>.*)", "/payment/gateway/gw2/${path}"))
                .uri("lb://cloud-payment-service");
    }
}