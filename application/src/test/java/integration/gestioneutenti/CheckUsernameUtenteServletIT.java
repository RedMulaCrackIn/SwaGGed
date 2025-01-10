package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.CheckUsernameUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CheckUsernameUtenteServletIT {

    private CheckUsernameUtenteServlet checkUsernameUtenteServlet;

    @Mock
    private GestioneUtentiService gestioneUtenti;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkUsernameUtenteServlet = new CheckUsernameUtenteServlet(gestioneUtenti);
        responseWriter = new StringWriter();
    }

    @Test
    void testUsernameNonDisponibile() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "user1";

        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.checkUsername(username)).thenReturn(true);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkUsernameUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkUsername(username);
        assertEquals("non disponibile", responseWriter.toString().trim());
    }

    @Test
    void testUsernameDisponibile() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "newUser";

        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.checkUsername(username)).thenReturn(false);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkUsernameUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkUsername(username);
        assertEquals("disponibile", responseWriter.toString().trim());
    }

    @Test
    void testErroreSQL() throws ServletException, IOException, SQLException {
        // Arrange
        String username = "user1";

        when(request.getParameter("username")).thenReturn(username);
        when(gestioneUtenti.checkUsername(username)).thenThrow(new SQLException("Errore nel database"));
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkUsernameUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkUsername(username);
        assertEquals("", responseWriter.toString().trim()); // Nessuna risposta scritta in caso di errore
    }
}
