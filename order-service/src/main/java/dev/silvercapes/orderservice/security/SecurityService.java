package dev.silvercapes.orderservice.security;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public String getLoginUsername(){
        return "user";
    }
}
