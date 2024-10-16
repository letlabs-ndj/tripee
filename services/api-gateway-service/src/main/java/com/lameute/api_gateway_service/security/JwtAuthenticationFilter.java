package com.lameute.api_gateway_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements WebFilter {
    @Autowired
    JwtService jwtService;

    @Autowired
    private CustomUserDetailService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestToken = exchange.getRequest().getHeaders().getFirst("Authorization");
        final String jwt;
        String userEmail;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            jwt = requestToken.substring(7);
            userEmail = jwtService.extractUserEmail(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails user = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isValid(jwt, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());

                    SecurityContext context = new SecurityContextImpl(authenticationToken);

                    // Set the Principal header in the request
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("email", userEmail)
                            .build();
                    // Create a new ServerWebExchange with the modified request
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(modifiedRequest)
                            .build();

                    return chain.filter(modifiedExchange).contextWrite(
                            ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
            }
        }
        return chain.filter(exchange);
    }
}
