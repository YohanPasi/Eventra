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
                               @RequestParam String password,
                               org.springframework.ui.Model model) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        boolean success = userService.registerUser(user);

        if (!success) {
            model.addAttribute("error", "Username or Email already exists!");
            return "register"; 
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response) {

        User user = userService.login(username, password);

        if (user == null) {
            return "redirect:/login?error=true";
        }

        String token = JwtUtil.generateToken(user.getName(), user.getRole(), user.getId());
        
        // Store JWT in a cookie so the browser remembers the session
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/events";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Expire the JWT cookie immediately
        Cookie cookie = new Cookie("jwt", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // delete immediately
        response.addCookie(cookie);
        return "redirect:/";
    }
}
