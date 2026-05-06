package com.eventra.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {

    public static String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getRoleFromRequest(HttpServletRequest request) {
        String token = getJwtFromCookies(request);
        if (token == null) return null;
        return JwtUtil.getRoleFromToken(token);
    }

    public static String getUsernameFromRequest(HttpServletRequest request) {
        String token = getJwtFromCookies(request);
        if (token == null) return null;
        return JwtUtil.getUsernameFromToken(token);
    }
}
