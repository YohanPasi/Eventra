package com.eventra.controller;

import com.eventra.model.User;
import com.eventra.service.UserService;
import com.eventra.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private UserService userService = new UserService();

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        boolean success = userService.registerUser(user);

        if (!success) {
            return "register"; // later show error message
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletResponse response) {

        User user = userService.login(email, password);

        if (user == null) {
            return "redirect:/login?error=true";
        }

        String token = JwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
        
        // Store JWT in a cookie so the browser remembers the session
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/events";
    }
}
