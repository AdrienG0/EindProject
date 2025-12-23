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

import java.security.Principal;
import java.util.Optional;
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

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            Principal principal,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            Model model
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Naam mag niet leeg zijn.");
            return "profile";
        }

        if (email == null || !EHB_EMAIL_PATTERN.matcher(email).matches()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Gebruik een geldig EHB-adres: voornaam.achternaam@ehb.be (kleine letters).");
            return "profile";
        }

        Optional<User> existing = userRepository.findByEmail(email);
        if (existing.isPresent() && !existing.get().getEmail().equals(user.getEmail())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Er bestaat al een account met dit e-mailadres.");
            return "profile";
        }

        String oldEmail = user.getEmail();

        user.setName(name.trim());
        user.setEmail(email.trim());
        userRepository.save(user);

        if (!oldEmail.equalsIgnoreCase(email.trim())) {
            return "redirect:/logout";
        }

        return "redirect:/profile?updated";
    }

    @PostMapping("/profile/password")
    public String updatePassword(
            Principal principal,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userRepository.findByEmail(principal.getName()).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        if (currentPassword == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Huidig wachtwoord is niet correct.");
            return "profile";
        }

        if (newPassword == null || !PASSWORD_PATTERN.matcher(newPassword).matches()) {
            model.addAttribute("user", user);
            model.addAttribute("error", "Nieuw wachtwoord moet minstens 9 tekens hebben en minstens 1 speciaal teken bevatten.");
            return "profile";
        }

        if (confirmNewPassword == null || !newPassword.equals(confirmNewPassword)) {
            model.addAttribute("user", user);
            model.addAttribute("error", "De nieuwe wachtwoorden komen niet overeen.");
            return "profile";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "redirect:/profile?success";
    }
}
