package com.orgvoteapp.controller;

import com.orgvoteapp.model.Voter;
import com.orgvoteapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private UserRepository userRepo;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest dto){
        Optional<Voter> ou = userRepo.findByUsername(dto.getUsername());
        if(ou.isEmpty()) return new LoginResponse(false, "user not found");
        Voter u = ou.get();
        if(!u.getPassword().equals(dto.getPassword())) return new LoginResponse(false, "invalid credentials");
        return new LoginResponse(true, "ok", u.getRole(), u.getUsername());
    }

    public static class LoginRequest {
        private String username;
        private String password;
        public String getUsername(){return username;}
        public void setUsername(String u){this.username=u;}
        public String getPassword(){return password;}
        public void setPassword(String p){this.password=p;}
    }

    public static class LoginResponse {
        private boolean success;
        private String message;
        private String role;
        private String username;
        public LoginResponse(boolean s, String m){this.success=s;this.message=m;}
        public LoginResponse(boolean s, String m, String role, String username){
            this.success=s;this.message=m;this.role=role;this.username=username;
        }
        public boolean isSuccess(){return success;}
        public String getMessage(){return message;}
        public String getRole(){return role;}
        public String getUsername(){return username;}
    }
}
