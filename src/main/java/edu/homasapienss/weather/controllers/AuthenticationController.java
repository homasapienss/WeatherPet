package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class AuthenticationController {

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute UserDto userDto) {
        return "/";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<UserDto> signUp(@ModelAttribute UserDto userDto) {
        return ResponseEntity.ok(authService.registrationUser(userDto));
    }
}
