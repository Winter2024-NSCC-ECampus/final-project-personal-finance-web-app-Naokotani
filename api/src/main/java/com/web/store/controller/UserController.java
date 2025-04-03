package com.web.store.controller;

import com.web.store.config.JwtProvider;
import com.web.store.dto.session.AuthResponse;
import com.web.store.dto.session.UserResponse;
import com.web.store.mapper.UserMapper;
import com.web.store.model.User;
import com.web.store.repository.UserRepository;
import com.web.store.service.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl customUserDetails;
    private final UserMapper userMapper;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserServiceImpl customUserDetails, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetails = customUserDetails;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)  {
        String email = user.getEmail();
        String password = user.getPassword();

        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setLandmark(user.getLandmark());
        createdUser.setFirstName(user.getFirstName());
        createdUser.setLastName(user.getLastName());
        createdUser.setAddress(user.getAddress());
        createdUser.setRole("user");
        createdUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(createdUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = JwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Login success");
        authResponse.setRole(authorities);
        authResponse.setJwt(token);
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user =  userRepository.findByEmail(email);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No user found");
        }
        UserResponse res = userMapper.UserToUserDto(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if(userDetails == null) {
            System.out.println("Sign in details - null" + userDetails);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())) {
            System.out.println("Sign in userDetails - password mismatch"+userDetails);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
