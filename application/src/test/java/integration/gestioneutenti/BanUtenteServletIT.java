package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.BanUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BanUtenteServletIT {

    @InjectMocks
    private BanUtenteServlet banUtenteServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private GestioneUtentiService gestioneUtentiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        banUtenteServlet = new BanUtenteServlet(gestioneUtentiService);
    }

    @Test
    void testBanUtenteSuccess() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        UtenteBean moderatore = new UtenteBean();
        moderatore.setAdmin(true);

        UtenteBean bannato = new UtenteBean();
        bannato.setEmail(emailToBan);

        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(moderatore);
        when(gestioneUtentiService.ban(moderatore, emailToBan)).thenReturn(true);

        // Act
        banUtenteServlet.doGet(request, response);

        // Assert
        verify(gestioneUtentiService).ban(moderatore, emailToBan);
        verify(response).sendRedirect(contains("/visualizzaUtente?username=user1"));
    }

    @Test
    void testBanUtenteFailInvalidPermissions() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        UtenteBean nonAdmin = new UtenteBean();
        nonAdmin.setAdmin(false);

        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(nonAdmin);

        // Act
        banUtenteServlet.doGet(request, response);

        // Assert
        verify(response).sendRedirect(contains("/visualizzaUtente?username=user1"));
    }

    @Test
    void testBanUtenteFailUserNotFound() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "nonexistent@example.com";
        UtenteBean moderatore = new UtenteBean();
        moderatore.setAdmin(true);

        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(moderatore);
        when(gestioneUtentiService.ban(moderatore, emailToBan)).thenReturn(false);

        // Act
        banUtenteServlet.doGet(request, response);

        // Assert
        verify(gestioneUtentiService).ban(moderatore, emailToBan);
    }
}
