package au.edu.rmit.sept.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VetController {

    @GetMapping("/vetlogin")
    public String vetLogin() {
        return "vetlogin"; // Ensure this matches the name of your HTML file
    }

    @GetMapping("/vethome")
    public String vetHome() {
        return "vethome"; // Ensure this matches the name of your vet home HTML file
    }
}