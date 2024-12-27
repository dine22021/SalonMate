package com.example.SalonMate.controller;

import com.example.SalonMate.model.User;
import com.example.SalonMate.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Show the registration form
    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";  // Refers to sign-up.html
    }

    // Handle registration form submission
    @PostMapping("/signup")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {
        // Check if the user already exists
        if (userService.isUserExists(username)) {
            model.addAttribute("error", "Username already exists");
            return "sign-up";
        }

        // Register user with encoded password
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.registerUser(user);

        // Redirect to login page upon successful registration
        return "redirect:/home";
    }

    // Show the login form
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  // Refers to login.html
    }

    // Handle login form submission
    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username, 
                            @RequestParam("password") String password,
                            HttpSession session, 
                            Model model) {
        System.out.println("Entered validate user");
        if (userService.validateUser(username, password)) {
            System.out.println(username);
            session.setAttribute("username", username);
            // Redirect to dashboard page upon successful login
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    // Show dashboard page after successful login
    @GetMapping("/home")
    public String home() {
        return "home";  // Refers to home.html
    }

    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";  // Refers to about-us.html
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear all session attributes
        return "redirect:/login";
    }

}
