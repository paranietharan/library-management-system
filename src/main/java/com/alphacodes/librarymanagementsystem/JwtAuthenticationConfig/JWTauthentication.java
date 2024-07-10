package com.alphacodes.librarymanagementsystem.JwtAuthenticationConfig;

import com.alphacodes.librarymanagementsystem.Model.User;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import java.util.Date;

@Service
public class JWTauthentication {

    public String generateToken(User user){
        byte[] secretKeyBytes= Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded();
        String secretKey= Base64.getEncoder().encodeToString(secretKeyBytes);
        assert user != null;
        String token= Jwts.builder()
                .claim("username",user.getEmailAddress())
                .claim("userId",user.getUserID())
                .claim("role",user.getRole())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();

        return token;

}}

