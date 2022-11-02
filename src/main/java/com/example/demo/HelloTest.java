package com.example.demo;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTest {
    @GetMapping("/hello")
    public Map<String, Object> hello(){
        return Map.of("code", 1, "content", "hello");
    }
}
