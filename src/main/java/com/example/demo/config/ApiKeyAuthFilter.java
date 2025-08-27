package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private final String expectedApiKey;

    // List of paths that don't require API key
    private static final List<String> PUBLIC_PATHS = List.of(
        "/",
        "/h2-console",
        "/swagger-ui",
        "/v3/api-docs",
        "/uploads"
    );

    public ApiKeyAuthFilter(String expectedApiKey) {
        this.expectedApiKey = expectedApiKey.trim(); // Trim any spaces from .env
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        String path = request.getRequestURI();

        // Skip API key check for public paths
        for (String publicPath : PUBLIC_PATHS) {
            if (path.equals(publicPath) || path.startsWith(publicPath + "/")) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Allow OPTIONS requests for CORS
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(API_KEY_HEADER);

        // Debug prints
        System.out.println("Request Path: " + path);
        System.out.println("Expected API Key: '" + expectedApiKey + "'");
        System.out.println("Received API Key: '" + apiKey + "'");

        if (apiKey == null || !apiKey.trim().equals(expectedApiKey)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Forbidden: Invalid API Key");
            return;
        }

        // Set authentication for Spring Security
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("apiKeyUser", null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
