package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.VetResource;
import au.edu.rmit.sept.webapp.service.VetResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/resources")
public class VetResourceController {

    private final VetResourceService vetResourceService;

    @Autowired
    public VetResourceController(VetResourceService vetResourceService) {
        this.vetResourceService = vetResourceService;
    }

    // Display all educational resources
    @GetMapping
    public String showResources(Model model) {
        List<VetResource> resources = vetResourceService.getAllResources();
        model.addAttribute("resources", resources);
        return "resources/list"; // Points to the Thymeleaf template for displaying resources
    }

    // Form to add a new resource (optional)
    @PostMapping("/add")
    public String addResource(VetResource vetResource) {
        vetResourceService.addResource(vetResource);
        return "redirect:/resources";
    }
}
