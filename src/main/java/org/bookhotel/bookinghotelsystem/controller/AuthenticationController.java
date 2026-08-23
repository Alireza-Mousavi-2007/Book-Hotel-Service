package org.bookhotel.bookinghotelsystem.controller;

import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.LoginDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.security.jwt.JwtToken;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final JwtToken jwtToken;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthenticationController(JwtToken jwtToken, AuthenticationManager authenticationManager, UserService userService) {
        this.jwtToken = jwtToken;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        var pocket = new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());
        var auth = authenticationManager.authenticate(pocket);

        List<String> authorities = auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();
        String token = jwtToken.tokenMaker(loginDTO.getUsernameOrEmail(), authorities);

        return ResponseEntity.ok(Map.of("Token", token));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        var user = userService.createUser(userRequestDTO);

        List<String> authorities = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();
        var token = jwtToken.tokenMaker(user.getUsername(), authorities);

        return ResponseEntity.ok(Map.of("Token", token));
    }

}
