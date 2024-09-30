package au.edu.rmit.sept.webapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTests {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Model model;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginPage() {
        String viewName = loginController.login();
        assertEquals("login", viewName);
    }

    @Test
    public void testUserHome() {
        String viewName = loginController.userhome();
        assertEquals("userhome", viewName);
    }

    @Test
    public void testHandleException() {
        Exception ex = new Exception("Test exception");
        String result = loginController.handleException(model, ex);

        assertEquals("error", result);
        verify(model).addAttribute("errorMessage", "An unexpected error occurred: Test exception");
    }
}
