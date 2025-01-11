package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swagged.model.bean.UtenteBean;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.gestioneutenti.controller.ModificaImmagineServlet;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ModificaImmagineServletIT {

    @Mock
    private GestioneUtentiService gestioneUtentiService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private Part filePart;

    @InjectMocks
    private ModificaImmagineServlet modificaImmagineServlet;

    private UtenteBean utente;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creiamo un utente di esempio
        utente = new UtenteBean();
        utente.setEmail("testuser@email.com");
        utente.setUsername("TestUser");

        // Mock della sessione
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("utente")).thenReturn(utente);

        // Mock di getContextPath per evitare il NullPointerException
        when(request.getContextPath()).thenReturn("/"); // Impostiamo un contesto valido
    }

    @Test
    public void testModificaImmagineSuccessfully() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getPart("immagine")).thenReturn(filePart); // Simula il file caricato
        when(gestioneUtentiService.modificaImmagine(utente, filePart, modificaImmagineServlet)).thenReturn(true); // Successo

        // Act
        modificaImmagineServlet.doPost(request, response);

        // Assert
        verify(gestioneUtentiService).modificaImmagine(utente, filePart, modificaImmagineServlet);
        verify(response).sendRedirect(Mockito.contains("/homepage.jsp")); // Verifica che il redirect avvenga correttamente
    }

    @Test
    public void testModificaImmagineFailure() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getPart("immagine")).thenReturn(filePart); // Simula il file caricato
        when(gestioneUtentiService.modificaImmagine(utente, filePart, modificaImmagineServlet)).thenReturn(false); // Fallimento

        // Act
        modificaImmagineServlet.doPost(request, response);

        // Assert
        verify(gestioneUtentiService).modificaImmagine(utente, filePart, modificaImmagineServlet);
        verify(response, never()).sendRedirect(Mockito.contains("/homepage.jsp")); // Verifica che non venga effettuato il redirect in caso di fallimento
    }

    @Test
    public void testModificaImmagineSQLException() throws ServletException, IOException, SQLException {
        // Arrange
        when(request.getPart("immagine")).thenReturn(filePart); // Simula il file caricato
        doThrow(new SQLException("Database error")).when(gestioneUtentiService).modificaImmagine(utente, filePart, modificaImmagineServlet);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> modificaImmagineServlet.doPost(request, response));
    }
}
