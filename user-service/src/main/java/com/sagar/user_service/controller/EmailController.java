package com.sagar.user_service.controller;

import com.sagar.user_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

//    @PostMapping("/send")
//    public String sendEmail(@RequestParam String to) throws IOException {
//
//        emailService.sendEmail(to, "Test Email from Spring Boot", "Hello Sagar! This email was sent using SendGrid.");
//
//        return "Email sent successfully";
//    }
}