package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.gestioneutenti.controller.BanUtenteServlet;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class BanUtenteServletIT {

    @Mock
    private GestioneUtentiService gestioneUtentiService;

    @Mock
    private UtenteDAO utenteDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private BanUtenteServlet banUtenteServlet;

    private UtenteBean moderatore;
    private UtenteBean utenteBannato;

    @BeforeEach
    public void setUp() {
        // Inizializzare i mock
        MockitoAnnotations.openMocks(this);

        moderatore = new UtenteBean();
        moderatore.setEmail("moderator@domain.com");
        moderatore.setUsername("moderator");

        utenteBannato = new UtenteBean();
        utenteBannato.setEmail("email1@email.com");
        utenteBannato.setUsername("user1");

        // Mock HttpSession (Usiamo jakarta.servlet.http.HttpSession)
        HttpSession sessionMock = mock(HttpSession.class);
        when(request.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("utente")).thenReturn(moderatore);
    }

    @Test
    public void testBanUtenteSuccess() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(utenteDAO.getByEmail(emailToBan)).thenReturn(utenteBannato);
        when(gestioneUtentiService.ban(moderatore, emailToBan)).thenReturn(true);

        // Act
        banUtenteServlet.doGet(request, response);

        // Assert
        verify(gestioneUtentiService, times(1)).ban(moderatore, emailToBan);
        verify(response).sendRedirect(Mockito.contains("/visualizzaUtente?username=user1"));
    }

    @Test
    public void testBanUtenteFailure() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(utenteDAO.getByEmail(emailToBan)).thenReturn(utenteBannato);
        doThrow(new SQLException("Database error")).when(gestioneUtentiService).ban(moderatore, emailToBan);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> banUtenteServlet.doGet(request, response));
    }

    @Test
    public void testGetByEmailNotFound() throws SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        when(utenteDAO.getByEmail(emailToBan)).thenReturn(null);

        // Act
        UtenteBean result = utenteDAO.getByEmail(emailToBan);

        // Assert
        assertNull(result);
    }

    @Test
    public void testBanUtenteSQLException() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(utenteDAO.getByEmail(emailToBan)).thenReturn(utenteBannato);
        doThrow(new SQLException("Database error")).when(gestioneUtentiService).ban(moderatore, emailToBan);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> banUtenteServlet.doGet(request, response));
    }
}
