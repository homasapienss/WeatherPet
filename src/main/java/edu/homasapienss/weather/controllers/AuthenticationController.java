package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.services.AuthenticationService;
import edu.homasapienss.weather.services.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String signIn(Model model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("userDto", new UserDto("", ""));
        return authorizeService.isUserAuthorized(req, resp) ? "redirect:/" : "login";
    }

    @GetMapping("/register")
    public String signUp(Model model, HttpServletRequest req, HttpServletResponse resp) {
        model.addAttribute("userDto", new UserDto("", ""));
        return authorizeService.isUserAuthorized(req, resp) ? "redirect:/" : "register";
    }

    @PostMapping("/login")
    public String signIn(@Valid @ModelAttribute UserDto userDto,
                         BindingResult bindingResult,
                         HttpServletResponse resp
                         ) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        authorizeService.loginUser(userDto, resp);
        return "redirect:/";
    }

    @PostMapping("/register")
    public String signUp(@Valid @ModelAttribute UserDto userDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        authService.registrationUser(userDto);
        return "redirect:/auth/login";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        authorizeService.logoutUser(req, resp);
        return "redirect:/auth/login";
    }
}
