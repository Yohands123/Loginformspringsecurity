package com.example.Loginform.controller;

import com.example.Loginform.model.JwtRequest;
import com.example.Loginform.model.JwtResponse;
import com.example.Loginform.service.UserService;
import com.example.Loginform.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController

public class Employeecontroller {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtility jwtUtility;

    @GetMapping("/")
    public String home(){
        return "welcome to sharath home";
    }
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest)throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
         final UserDetails userDetails=userService.loadUserByUsername(jwtRequest.getUsername());
         final String token =jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
