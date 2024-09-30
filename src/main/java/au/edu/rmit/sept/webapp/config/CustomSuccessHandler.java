package au.edu.rmit.sept.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    @Qualifier("appDetailsService")
    private UserDetailsService appUserService;

    @Autowired
    @Qualifier("adminUserDetailsService")
    private UserDetailsService adminUserDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // Custom logic on successful authentication
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String redirectUrl = determineRedirectUrl(roles);
        response.sendRedirect(redirectUrl);
        request.getSession().setAttribute("role", roles);
    }

    private String determineRedirectUrl(Set<String> roles) {
        if (roles.contains("ROLE_ADMIN")) {
            return "/adminhome";
        } else if (roles.contains("ROLE_VET")) {
            return "/vethome";
        } else if (roles.contains("ROLE_USER")) {
            return "/userhome";
        } else {
            return "/home";
        }
    }
}