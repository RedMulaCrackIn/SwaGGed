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
import swagged.gestioneutenti.controller.CercaUtenteServlet;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CercaUtenteServletIT {

    private CercaUtenteServlet cercaUtenteServlet;

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
        cercaUtenteServlet = new CercaUtenteServlet(gestioneUtenti);
    }

    @Test
    void testRicercaConRisultati() throws ServletException, IOException, SQLException {
        // Arrange
        String substring = "user";
        List<UtenteBean> risultatiMock = new ArrayList<>();
        risultatiMock.add(createUtente("email1@email.com", "user1"));
        risultatiMock.add(createUtente("email2@email.com", "user2"));

        when(request.getParameter("substring")).thenReturn(substring);
        when(request.getSession()).thenReturn(session);
        when(gestioneUtenti.cerca(substring)).thenReturn(risultatiMock);

        // Act
        cercaUtenteServlet.doGet(request, response);

        // Assert
        verify(session).setAttribute("risultati", risultatiMock);
        verify(response).sendRedirect(request.getContextPath() + "/ricerca.jsp");

        assertEquals(2, risultatiMock.size());
        assertEquals("user1", risultatiMock.get(0).getUsername());
        assertEquals("user2", risultatiMock.get(1).getUsername());
    }

    @Test
    void testRicercaSenzaRisultati() throws ServletException, IOException, SQLException {
        // Arrange
        String substring = "nonexistent";
        List<UtenteBean> risultatiMock = new ArrayList<>();

        when(request.getParameter("substring")).thenReturn(substring);
        when(request.getSession()).thenReturn(session);
        when(gestioneUtenti.cerca(substring)).thenReturn(risultatiMock);

        // Act
        cercaUtenteServlet.doGet(request, response);

        // Assert
        verify(session).setAttribute("risultati", risultatiMock);
        verify(response).sendRedirect(request.getContextPath() + "/ricerca.jsp");

        assertEquals(0, risultatiMock.size());
    }

    private UtenteBean createUtente(String email, String username) {
        UtenteBean utente = new UtenteBean();
        utente.setEmail(email);
        utente.setUsername(username);
        return utente;
    }
}
