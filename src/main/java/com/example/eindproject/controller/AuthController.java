package com.example.eindproject.controller;

import com.example.eindproject.model.User;
import com.example.eindproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Pattern EHB_EMAIL_PATTERN =
            Pattern.compile("^[a-z]+\\.[a-z]+@ehb\\.be$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[^A-Za-z0-9]).{9,}$");

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") User user,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {

        if (user.getEmail() == null || !EHB_EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            model.addAttribute("error", "Gebruik een geldig EHB-adres: voornaam.achternaam@ehb.be (kleine letters).");
            return "register";
        }

        if (user.getPassword() == null || !PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            model.addAttribute("error", "Wachtwoord moet minstens 9 tekens hebben en minstens 1 speciaal teken bevatten.");
            return "register";
        }

        if (confirmPassword == null || !user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "De wachtwoorden komen niet overeen.");
            return "register";
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Er bestaat al een account met dit e-mailadres.");
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
