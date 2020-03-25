package se.utb.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class AdminController {
    @GetMapping("/admin")
    public String getAdminPage(){
        return "admin-page";
    }
}
