package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.VisualizzaUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class VisualizzaUtenteServletIT {

    private VisualizzaUtenteServlet visualizzaUtenteServlet;

    @Mock
    private GestioneUtentiService gestioneUtenti;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private UtenteBean profilo;

    @Mock
    private UtenteBean utente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        visualizzaUtenteServlet = new VisualizzaUtenteServlet(gestioneUtenti);
    }

    @Test
    void testVisualizzaUtenteProfiloNonTrovato() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "nonTrovato";
        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.visualizza(username)).thenReturn(null); // Mock per utente non trovato

        // Mocking la sessione, anche se non sarà utilizzata nel caso dell'utente non trovato
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        // Act
        visualizzaUtenteServlet.doGet(request, response);

        // Assert
        // Verifica che la servlet faccia un redirect alla pagina "utente.jsp"
        verify(response).sendRedirect(request.getContextPath() + "/utente.jsp");
    }


    @Test
    void testVisualizzaUtenteProfiloTrovato() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "esempioUtente";
        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.visualizza(username)).thenReturn(profilo);
        when(profilo.getUsername()).thenReturn(username);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn(username);

        // Act
        visualizzaUtenteServlet.doGet(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + "/common/profilo.jsp");
    }

    @Test
    void testVisualizzaUtenteProfiloAltrui() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "utenteAltrui";
        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.visualizza(username)).thenReturn(profilo);
        when(profilo.getUsername()).thenReturn(username);
        when(request.getSession()).thenReturn(mock(HttpSession.class));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(utente.getUsername()).thenReturn("utenteDiverso");

        // Act
        visualizzaUtenteServlet.doGet(request, response);

        // Assert
        verify(response).sendRedirect(request.getContextPath() + "/utente.jsp");
    }
}
