package edu.homasapienss.weather.controllers;

import edu.homasapienss.weather.dto.UserDto;
import edu.homasapienss.weather.services.AuthenticationService;
import edu.homasapienss.weather.services.AuthorizeService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AuthorizeService authorizeService;

    @Autowired
    public AuthenticationController(AuthenticationService authService, AuthorizeService authorizeService) {
        this.authService = authService;
        this.authorizeService = authorizeService;
    }

    @GetMapping("/sign-in")
    public String signIn(HttpServletRequest req) {
        if (authorizeService.isUserAuthorized(req)) {
            return "redirect:/";
        }
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp(HttpServletRequest req) {
        if (authorizeService.isUserAuthorized(req)) {
            return "redirect:/";
        }
        return "sign-up";
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<UUID> signIn(@ModelAttribute UserDto userDto, HttpServletRequest req) {
        UUID uuid = authorizeService.loginUser(userDto);
        ResponseCookie cookie = ResponseCookie.from("SESSION_UUID", uuid.toString())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(uuid);
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<UserDto> signUp(@ModelAttribute UserDto userDto, HttpServletRequest req) {
        UserDto signedUpUser = authService.registrationUser(userDto);
        return ResponseEntity.ok(signedUpUser);
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<Void> logout(HttpServletRequest req) {
        authorizeService.logoutUser(req);

        ResponseCookie expiredCookie = ResponseCookie.from("SESSION_UUID", "")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .build();
    }
}
