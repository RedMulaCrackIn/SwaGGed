package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.CheckEmailUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CheckEmailUtenteServletIT {

    private CheckEmailUtenteServlet checkEmailUtenteServlet;

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
        checkEmailUtenteServlet = new CheckEmailUtenteServlet(gestioneUtenti);
        responseWriter = new StringWriter();
    }

    @Test
    void testEmailNonDisponibile() throws ServletException, IOException, SQLException {
        // Arrange
        String email = "email1@email.com";

        when(request.getParameter("email")).thenReturn(email);
        when(gestioneUtenti.checkEmail(email)).thenReturn(true);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkEmailUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkEmail(email);
        assertEquals("non disponibile", responseWriter.toString().trim());
    }

    @Test
    void testEmailDisponibile() throws ServletException, IOException, SQLException {
        // Arrange
        String email = "nuovaemail@email.com";

        when(request.getParameter("email")).thenReturn(email);
        when(gestioneUtenti.checkEmail(email)).thenReturn(false);
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkEmailUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkEmail(email);
        assertEquals("disponibile", responseWriter.toString().trim());
    }

    @Test
    void testErroreSQL() throws ServletException, IOException, SQLException {
        // Arrange
        String email = "email1@email.com";

        when(request.getParameter("email")).thenReturn(email);
        when(gestioneUtenti.checkEmail(email)).thenThrow(new SQLException("Errore nel database"));
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Act
        checkEmailUtenteServlet.doPost(request, response);

        // Assert
        verify(gestioneUtenti).checkEmail(email);
        assertEquals("", responseWriter.toString().trim()); // Nessuna risposta scritta in caso di errore
    }
}
