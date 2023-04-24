package com.devoteam.pmo.controller;

import com.devoteam.pmo.entity.JwtRequest;
import com.devoteam.pmo.entity.JwtResponse;
import com.devoteam.pmo.entity.RefreshTokenRequest;
import com.devoteam.pmo.service.JwtService;
import com.devoteam.pmo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.createJwtToken(jwtRequest);
    }


    @PostMapping("/refreshToken")
    public JwtResponse refreshJwtToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
        boolean isValid = jwtUtil.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        if (isValid) {
            String username = refreshTokenRequest.getUsername();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtUtil.generateToken(userDetails);

            JwtResponse jwtResponse = new JwtResponse(null, newAccessToken, refreshTokenRequest.getRefreshToken());

            return jwtResponse;
        } else {
            throw new Exception("Invalid refresh token");
        }
    }
}