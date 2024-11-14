package com.springboot.ecom.controller;

import com.springboot.ecom.dto.JwtDto;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.UserSecurityService;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private UserService userService;

    @PostMapping("/api/token")
    public ResponseEntity<JwtDto> getToken(@RequestBody User user, JwtDto dto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticationManager.authenticate(auth);

        user = (User) userSecurityService.loadUserByUsername(user.getUsername());
        String jwt = jwtUtil.generateToken(user.getUsername());
        dto.setUsername(user.getUsername());
        dto.setToken(jwt);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<User> signUp(@RequestBody User user) throws InvalidUsernameException {
        User savedUser = userService.signup(user);
        return ResponseEntity.ok(savedUser);
    }


}
