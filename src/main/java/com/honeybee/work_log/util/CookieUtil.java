package com.honeybee.work_log.util;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class CookieUtil {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                cookie.setValue("");
                response.addCookie(cookie);
            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        byte[] decodeCookie = Base64.getUrlDecoder().decode(cookie.getValue());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(decodeCookie);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            Object o = ois.readObject();
            return cls.cast(o);
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }
}
