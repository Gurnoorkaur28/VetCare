package au.edu.rmit.sept.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import au.edu.rmit.sept.webapp.service.PetRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import au.edu.rmit.sept.webapp.model.PetRecord;

@Controller
public class VetHomeController {

    @Autowired
    private PetRecordService petRecordService;

    @GetMapping("/vetcaresystemhome")
    public String showHome() {
        return "vetcaresystemhome";
    }

    @PostMapping("/vetcaresystemhome/selectrole")
    public String handleRoleSelection(@RequestParam("role") String role) {
        switch (role) {
            case "CLIENT":
                return "redirect:/login-client";
            case "RECEPTIONIST":
                return "redirect:/login-receptionist";
            case "VETERINARIAN":
                return "redirect:/login-veterinarian";
            default:
                return "redirect:/vetcaresystemhome";
        }
    }
}
