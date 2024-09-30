package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminlogin")
    public String adminLogin() {
        return "adminlogin"; // Ensure this matches the name of your HTML file
    }

    @GetMapping("/adminhome")
    public String adminHome() {
        return "adminhome"; // Ensure this matches the name of your admin home HTML file
    }
}