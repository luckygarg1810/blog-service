package com.luckygarg.blog_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luckygarg.blog_service.DTO.LoginRequest;
import com.luckygarg.blog_service.DTO.LoginResponse;
import com.luckygarg.blog_service.DTO.RegisterRequest;
import com.luckygarg.blog_service.Entity.User;
import com.luckygarg.blog_service.Repository.UserRepository;
import com.luckygarg.blog_service.Service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private final AuthService authService;
	   private final UserRepository userRepository;
	  
    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
        	 User user = userRepository.findByUsername(request.getUsername())
            		 .orElseThrow(() -> new RuntimeException("Invalid username or password"));
            String token = authService.login(request);
            return ResponseEntity.ok(new LoginResponse(token, user.getId()));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
    
    
}
