package com.buffet.hrmanagement.controller;

import com.buffet.hrmanagement.model.User;
import com.buffet.hrmanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Store user information in session
            session.setAttribute("loggedInUser", user);
            return "redirect:/"; // Redirect to the navigation menu
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String showHomePage(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        return "index"; // Show the navigation menu
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }
        userService.save(user);
        return "redirect:/login";
    }
}
