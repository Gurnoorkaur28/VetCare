package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    @Qualifier("userService")
    private UserService userService; // Assumes UserService handles vet_users, vet, and admin

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login page
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            // Load user details from the corresponding table
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                // Authenticate the user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Check the user's role and redirect accordingly
                String role = userDetails.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .findFirst()
                        .orElse(null);

                logger.info("User role: " + role);

                if ("ROLE_ADMIN".equals(role)) {
                    return "redirect:/adminhome"; // Redirect to admin home
                } else if ("ROLE_VET".equals(role)) {
                    return "redirect:/vethome"; // Redirect to vet home
                } else if ("ROLE_USER".equals(role)) {
                    return "redirect:/userhome"; // Redirect to general user home
                } else {
                    model.addAttribute("error", "Unknown role");
                    return "login";
                }
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "login"; // Show the error on the login page
            }
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Show the error on the login page
        }
    }

    @GetMapping("/userhome")
    public String userhome() {
        return "userhome"; // General user home
    }

    @GetMapping("/adminhome")
    public String adminhome() {
        return "adminhome"; // Admin user home
    }

    @GetMapping("/vethome")
    public String vethome() {
        return "vethome"; // Vet user home
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Example of handling exceptions at the controller level
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("errorMessage", "An unexpected error occurred: " + ex.getMessage());
        return "error"; // Return error page
    }
}
