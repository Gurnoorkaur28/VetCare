package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.Vet;
import au.edu.rmit.sept.webapp.service.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VetController {

    @Autowired
    private VetService vetService;

    @GetMapping("/vetlogin")
    public String vetLogin() {
        return "vetlogin"; // Return the vet login page
    }

    @PostMapping("/vetlogin")
    public String handleVetLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Vet vet = vetService.findByEmail(email);
        if (vet == null || !vet.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password");
            return "vetlogin";
        }
        return "redirect:/vethome";
    }

    @GetMapping("/vethome")
    public String vetHome() {
        return "vethome";
    }

    @GetMapping("/vets")
    public String listVets(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets";
    }

    @GetMapping("/vet/{id}")
    public String getVet(@PathVariable Long id, Model model) {
        model.addAttribute("vet", vetService.findById(id));
        return "vet";
    }

    @PostMapping("/vet")
    public String saveVet(@ModelAttribute Vet vet) {
        vetService.save(vet);
        return "redirect:/vets";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "error"; // Return error page
    }
}