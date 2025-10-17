package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
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
    @ResponseBody
    public ResponseEntity<UUID> signIn(@ModelAttribute UserDto userDto) {
        UUID uuid = authService.loginUser(userDto);
        ResponseCookie cookie = ResponseCookie.from("SESSION_UUID", uuid.toString())
                .httpOnly(true)
                .secure(false)
                .path("/**")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(uuid);
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<UserDto> signUp(@ModelAttribute UserDto userDto) {
        UserDto signedUpUser = authService.registrationUser(userDto);
        return ResponseEntity.ok(signedUpUser);
    }
}
