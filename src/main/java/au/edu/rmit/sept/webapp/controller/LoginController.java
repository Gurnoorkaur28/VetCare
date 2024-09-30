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
    private UserService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the login page
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                // Set authentication context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, password, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Log the roles of the authenticated user
                userDetails.getAuthorities().forEach(authority -> logger.info("Role: " + authority.getAuthority()));

                return "redirect:/userhome"; // Redirect to userhome after successful login
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
        return "userhome";
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