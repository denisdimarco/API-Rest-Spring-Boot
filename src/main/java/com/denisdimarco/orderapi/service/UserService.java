package com.denisdimarco.orderapi.service;


import com.denisdimarco.orderapi.converter.UserConverter;
import com.denisdimarco.orderapi.dto.LoginRequestDTO;
import com.denisdimarco.orderapi.dto.LoginResponseDTO;
import com.denisdimarco.orderapi.entity.User;
import com.denisdimarco.orderapi.exception.GeneralServiceException;
import com.denisdimarco.orderapi.exception.NoDataFoundException;
import com.denisdimarco.orderapi.exception.ValidateServiceException;
import com.denisdimarco.orderapi.repository.UserRepository;
import com.denisdimarco.orderapi.validator.UserValidator;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserService {

    @Value("${jwt.password}")
    private String jwtSecret;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        try {
            UserValidator.signup(user);

            User existentUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if (existentUser != null) throw new ValidateServiceException("Username already exist");

            return userRepository.save(user);

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ValidateServiceException("Incorrect User or Password"));

            if (!user.getPassword().equals(request.getPassword()))
                throw new ValidateServiceException("Incorrect User or Password");


            String token = createToken(user);


            return new LoginResponseDTO(userConverter.fromEntity(user), token);

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public String createToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + (1000 * 60 * 60));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected by the application");
        } catch (MalformedJwtException e) {
            log.error("JWT was not correctly constructed and should be rejected.");
        } catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        } catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected.");
        }
        return false;
    }
}
