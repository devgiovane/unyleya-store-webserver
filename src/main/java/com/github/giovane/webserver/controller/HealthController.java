package com.github.giovane.webserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/health")
public final class HealthController {

    @PostMapping("")
    public @ResponseBody ResponseEntity<String> check() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
