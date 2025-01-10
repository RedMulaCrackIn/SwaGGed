package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import swagged.gestioneutenti.controller.LoginServlet;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginServletTest {

    @InjectMocks
    private LoginServlet loginServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private GestioneUtentiServiceImpl gestioneUtentiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gestioneUtentiService = mock(GestioneUtentiServiceImpl.class);
        loginServlet = new LoginServlet(gestioneUtentiService);
    }

    @Test
    void testLoginValidCredentials() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "user1";
        String password = "pass";
        UtenteBean utente = new UtenteBean();
        utente.setUsername("user1");
        utente.setPassword("cGFzcw=="); // Base64 encoded 'pass'

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(gestioneUtentiService.login(username, password)).thenReturn(utente);
        when(request.getSession()).thenReturn(session);

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(anyString());  // Should redirect to homepage.jsp
        verify(session).setAttribute("logged", true);
        verify(session).setAttribute("utente", utente);
    }

    @Test
    void testLoginInvalidCredentials() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "user1";
        String password = "wrongpass";

        when(request.getParameter("username")).thenReturn(username);
        when(request.getParameter("password")).thenReturn(password);
        when(gestioneUtentiService.login(username, password)).thenReturn(null);
        when(request.getSession()).thenReturn(session);

        // Act
        loginServlet.doPost(request, response);

        // Assert
        verify(response).sendRedirect(anyString());  // Should redirect to login.jsp with error
        verify(session).setAttribute("logged", false);
        verify(session).setAttribute("error", "Username e/o password invalidi.");
    }
}
