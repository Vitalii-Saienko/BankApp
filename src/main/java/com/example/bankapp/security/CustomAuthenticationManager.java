//package com.example.bankapp.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    private DaoAuthenticationProvider authenticationProvider;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return authenticationProvider.authenticate(authentication);
//    }
//}
