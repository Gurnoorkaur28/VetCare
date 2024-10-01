package au.edu.rmit.sept.webapp.controller;

import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.model.Pet;
import au.edu.rmit.sept.webapp.model.enums.UserRole;
import au.edu.rmit.sept.webapp.service.UserService;
import au.edu.rmit.sept.webapp.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.regex.Pattern;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9]).{8,}$");
    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    // Display the signup form
    @GetMapping("/signup-client")
    public String showSignupForm(Model model) {
        return "signup-client"; // Use the same form for both client and receptionist
    }

    // Handle the signup form submission
    @PostMapping("/signup-client")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam UserRole role, // Role selection between CLIENT and RECEPTIONIST
            @RequestParam(required = false) String petName, // Optional
            @RequestParam(required = false) String petType, // Optional
            @RequestParam(required = false, defaultValue = "-1") int petAge, // Optional
            @RequestParam(required = false) String petBio, // Optional
            RedirectAttributes redirectAttributes) {

        try {
            // Validate email structure
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                redirectAttributes.addFlashAttribute("message", "Invalid email format");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }

            // Ensure username length does not exceed 20 characters
            if (username.length() > 20) {
                redirectAttributes.addFlashAttribute("message", "Username must not exceed 20 characters");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }

            // Check if username or email is taken
            if (userService.isUsernameTaken(username)) {
                redirectAttributes.addFlashAttribute("message", "Username is already taken");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }
            if (userService.isEmailTaken(email)) {
                redirectAttributes.addFlashAttribute("message", "Email is already taken");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }

            // Validate password strength
            if (!PASSWORD_PATTERN.matcher(password).matches()) {
                redirectAttributes.addFlashAttribute("message",
                        "Password must be at least 8 characters long and contain at least one number");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }

            // Register the user with the selected role
            userService.registerUser(username, email, password, role);

            // Find the newly registered user
            User user = userService.findUserByEmail(email);
            if (user == null) {
                redirectAttributes.addFlashAttribute("message", "User not found");
                redirectAttributes.addFlashAttribute("success", false);
                return "redirect:/signup-client";
            }

            // If role is client or receptionist and pet details are provided, register the pet
            if (role == UserRole.CLIENT || role == UserRole.RECEPTIONIST) {
                if (petName != null && petType != null && petAge != -1) {
                    Pet newPet = new Pet(petName, petType, petAge, petBio, user);
                    user.getPets().add(newPet);
                    petService.addPet(newPet);
                }
            }

            redirectAttributes.addFlashAttribute("message", "User registered successfully!");
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/login-client";

        } catch (Exception e) {
            logger.error("Registration failed", e);
            redirectAttributes.addFlashAttribute("message", "Registration failed: " + e.getMessage());
            redirectAttributes.addFlashAttribute("success", false);
            return "redirect:/signup-client";
        }
    }
}
