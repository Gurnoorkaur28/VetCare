package au.edu.rmit.sept.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.SecurityUtil;
import au.edu.rmit.sept.webapp.model.Resource;
import au.edu.rmit.sept.webapp.service.ResourceService;

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/resources")
    public String viewResources(Model model) {
        if (!SecurityUtil.hasRole("CLIENT")) {
            return "403";  // Redirect to access denied page if not CLIENT
        }
        List<Resource> resources = resourceService.getAllResources();
        model.addAttribute("resources", resources);
        return "resources/list";
    }

    @GetMapping("/resources/new")
    public String showNewResourceForm(Model model) {
        if (!SecurityUtil.hasRole("CLIENT")) {
            return "403";  // Redirect to access denied page if not CLIENT
        }
        model.addAttribute("resource", new Resource());
        return "resources/new";
    }

    @PostMapping("/resources/save")
    public String saveResource(@ModelAttribute("resource") Resource resource) {
        if (!SecurityUtil.hasRole("CLIENT")) {
            return "403";  // Redirect to access denied page if not CLIENT
        }
        resourceService.addResource(resource);
        return "redirect:/resources";
    }

    @GetMapping("/resources/delete/{id}")
    public String deleteResource(@PathVariable("id") Long id) {
        if (!SecurityUtil.hasRole("CLIENT")) {
            return "403";  // Redirect to access denied page if not CLIENT
        }
        resourceService.deleteResourceById(id);
        return "redirect:/resources";
    }

    // New method for viewing individual resource details
    @GetMapping("/resources/{id}")
    public String viewResource(@PathVariable Long id, Model model) {
        if (!SecurityUtil.hasRole("CLIENT")) {
            return "403";  // Redirect to access denied page if not CLIENT
        }
        Resource resource = resourceService.getResourceById(id);
        model.addAttribute("resource", resource);
        return "resources/view";
    }
}
