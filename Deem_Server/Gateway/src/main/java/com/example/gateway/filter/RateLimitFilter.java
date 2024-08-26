package com.example.gateway.filter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


@Component
public class RateLimitFilter extends AbstractGatewayFilterFactory<RateLimitFilter.Config> {

    private static final long THRESHOLD = 10; //не более 10 запросов в секунду
    private static final long PERIOD = 6000;

    private final ConcurrentHashMap<String, RequestData> requestCounts = new ConcurrentHashMap<>();

    public RateLimitFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String clientIP = request.getRemoteAddress().getAddress().getHostAddress();
            long currentTime = System.currentTimeMillis();

            requestCounts.merge(clientIP, new RequestData(currentTime, 1), (oldValue, newValue) -> {
                if (currentTime - oldValue.timestamp < PERIOD) {
                    oldValue.count++;
                } else {
                    oldValue.timestamp = currentTime;
                    oldValue.count = 1;
                }
                return oldValue;
            });

            RequestData requestData = requestCounts.get(clientIP);
            if (requestData.count > THRESHOLD) {
                response.setStatusCode(HttpStatusCode.valueOf(423));
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        });
    }


    private static class RequestData {
        long timestamp;
        int count;

        RequestData(long timestamp, int count) {
            this.timestamp = timestamp;
            this.count = count;
        }
    }
    public static class Config {

    }
}

