package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.RegistrazioneServlet;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class RegistrazioneServletTest {

    private RegistrazioneServlet registrazioneServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private GestioneUtentiServiceImpl gestioneUtenti;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registrazioneServlet = new RegistrazioneServlet();
        gestioneUtenti = mock(GestioneUtentiServiceImpl.class);
        registrazioneServlet.setGestioneUtentiService(gestioneUtenti);
    }


    @Test
    void testRegistrazioneSuccesso() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getParameter("mode")).thenReturn("register");
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getParameter("passwordCheck")).thenReturn("password123");
        when(request.getParameter("email")).thenReturn("testuser@example.com");
        when(request.getSession()).thenReturn(session);

        UtenteBean utente = new UtenteBean();
        utente.setUsername("testuser");
        utente.setEmail("testuser@example.com");

        GestioneUtentiServiceImpl gestioneUtentiMock = mock(GestioneUtentiServiceImpl.class);
        when(gestioneUtentiMock.registrazione("testuser@example.com", "testuser", "password123", "password123"))
                .thenReturn(utente);

        registrazioneServlet = new RegistrazioneServlet();
        registrazioneServlet.setGestioneUtentiService(gestioneUtentiMock);

        // Act
        registrazioneServlet.doPost(request, response);

        // Assert
        verify(session).setAttribute("result", "Registrato con successo!");
        verify(session).setAttribute(eq("utente"), argThat(u ->
                u instanceof UtenteBean &&
                        "testuser".equals(((UtenteBean) u).getUsername()) &&
                        "testuser@example.com".equals(((UtenteBean) u).getEmail())
        ));
        verify(response).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

    @Test
    void testRegistrazioneFallita() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getParameter("mode")).thenReturn("register");
        when(request.getParameter("username")).thenReturn("testuser");
        when(request.getParameter("password")).thenReturn("password123");
        when(request.getParameter("passwordCheck")).thenReturn("differentpassword");
        when(request.getParameter("email")).thenReturn("testuser@example.com");
        when(request.getSession()).thenReturn(session);

        GestioneUtentiServiceImpl gestioneUtentiMock = mock(GestioneUtentiServiceImpl.class);
        when(gestioneUtentiMock.registrazione("testuser@example.com", "testuser", "password123", "differentpassword"))
                .thenReturn(null);

        registrazioneServlet = new RegistrazioneServlet();
        // Act
        registrazioneServlet.doPost(request, response);

        // Assert
        verify(session).setAttribute("error", "Registrazione non completata. Riprovare.");
        verify(response).sendRedirect(request.getContextPath() + "/registrazione.jsp");
    }
}
