/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.bingo.configuration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author ADMIN
 */

@WebFilter(urlPatterns = "/*", asyncSupported = true)
public class WebSocketFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (isWebSocketRequest(httpRequest)) {
            System.out.println("WebSocket request: " + httpRequest.getRequestURL());
            printRequestHeaders(httpRequest);
        }
        chain.doFilter(request, response);
    }

    private boolean isWebSocketRequest(HttpServletRequest request) {
        String upgradeHeader = request.getHeader("Upgrade");
        return "websocket".equalsIgnoreCase(upgradeHeader);
    }

    private void printRequestHeaders(HttpServletRequest request) {
        System.out.println("Request headers:");
        for (String headerName : Collections.list(request.getHeaderNames())) {
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
    }

    @Override
    public void destroy() {
    }
}
