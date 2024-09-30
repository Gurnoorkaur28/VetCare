package au.edu.rmit.sept.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.model.Admin;
import au.edu.rmit.sept.webapp.model.User;
import au.edu.rmit.sept.webapp.model.Vet;
import au.edu.rmit.sept.webapp.repository.AdminRepository;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import au.edu.rmit.sept.webapp.repository.VetRepository;

import java.util.Collections;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // For normal users

    @Autowired
    private VetRepository vetRepository; // For vet_users

    @Autowired
    private AdminRepository adminRepository; // For admins

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check for normal users
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        if (optionalUser.isPresent()) {
            return buildUserDetails(optionalUser.get());
        }

        // Check for vet users
        Optional<Vet> optionalVet = vetRepository.findByUsername(username);
        if (optionalVet.isPresent()) {
            return buildUserDetails(optionalVet.get());
        }

        // Check for admin users

        // User not found in any repository
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

    private UserDetails buildUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
                .accountExpired(false) // Set to false or implement logic if needed
                .accountLocked(false) // Set to false or implement logic if needed
                .credentialsExpired(false) // Set to false or implement logic if needed
                .disabled(false) // Set to false or implement logic if needed
                .build();
    }

    private UserDetails buildUserDetails(Vet vet) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(vet.getUsername())
                .password(vet.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(vet.getRole())))
                .accountExpired(false) // Set to false or implement logic if needed
                .accountLocked(false) // Set to false or implement logic if needed
                .credentialsExpired(false) // Set to false or implement logic if needed
                .disabled(false) // Set to false or implement logic if needed
                .build();
    }

}
