package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.services.AuthenticationService;
import edu.homasapienss.weather.services.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final AuthorizeService authorizeService;

    @Autowired
    public AuthenticationController(AuthenticationService authService, AuthorizeService authorizeService) {
        this.authService = authService;
        this.authorizeService = authorizeService;
    }

    @GetMapping("/login")
    public String signIn(HttpServletRequest req, HttpServletResponse resp) {
        if (authorizeService.isUserAuthorized(req, resp)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/register")
    public String signUp(HttpServletRequest req, HttpServletResponse resp) {
        if (authorizeService.isUserAuthorized(req, resp)) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/login")
    public String signIn(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        authorizeService.loginUser(userDto, resp);
        return "redirect:/";
    }

    @PostMapping("/register")
    public String signUp(@ModelAttribute UserDto userDto, HttpServletResponse resp) {
        try {
            authService.registrationUser(userDto);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "register";
        }
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        authorizeService.logoutUser(req, resp);
        return "redirect:/auth/login";
    }
}
