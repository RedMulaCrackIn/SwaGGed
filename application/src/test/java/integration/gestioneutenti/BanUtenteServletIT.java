package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.BanUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class BanUtenteServletIT {

    private BanUtenteServlet banUtenteServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private GestioneUtentiService gestioneUtenti;

    @Mock
    private UtenteDAO utenteDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        banUtenteServlet = new BanUtenteServlet(gestioneUtenti); // Iniezione del servizio
    }

    @Test
    void testBanUtente() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        UtenteBean utenteMock = new UtenteBean();
        utenteMock.setEmail(emailToBan);
        utenteMock.setUsername("user1");
        UtenteBean moderatore = new UtenteBean();
        moderatore.setAdmin(true);

        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(utenteDAO.getByEmail(emailToBan)).thenReturn(utenteMock);

        // Act
        banUtenteServlet.doGet(request, response);

        // Assert
        verify(gestioneUtenti).ban(moderatore, emailToBan);
        verify(response).sendRedirect(request.getContextPath() + "/visualizzaUtente?username=user1");
    }

    @Test
    void testBanUtenteConEccezioneSQL() throws ServletException, IOException, SQLException {
        // Arrange
        String emailToBan = "email1@email.com";
        UtenteBean moderatore = new UtenteBean();
        moderatore.setAdmin(true);

        when(request.getParameter("utenteEmail")).thenReturn(emailToBan);
        when(gestioneUtenti.ban(moderatore, emailToBan)).thenThrow(new SQLException("Errore nel ban dell'utente"));

        // Act & Assert
        try {
            banUtenteServlet.doGet(request, response);
        } catch (RuntimeException e) {
            verify(gestioneUtenti).ban(moderatore, emailToBan);
            verifyNoInteractions(response); // Non deve redirigere in caso di errore
        }
    }
}
