package integration.gestionecommenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommenti.controller.RimuoviCommentoServlet;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RimuoviCommentoServletIT {

    private RimuoviCommentoServlet rimuoviCommentoServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommentiServiceImpl gestioneCommentiService;

    @BeforeEach
    void setUp() {
        gestioneCommentiService = mock(GestioneCommentiServiceImpl.class);
        rimuoviCommentoServlet = new RimuoviCommentoServlet(gestioneCommentiService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testRimuoviCommentoSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        int commentoId = 1;
        int postId = 1;
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        // Simulazione comportamento del servizio
        when(request.getParameter("id")).thenReturn(String.valueOf(commentoId));
        when(request.getParameter("postId")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommentiService.remove(commentoId, postId, mockUtente)).thenReturn(true);

        // Chiamata alla servlet
        rimuoviCommentoServlet.doGet(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommentiService, times(1)).remove(commentoId, postId, mockUtente);

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaPost?id=" + postId);
    }

    /*@Test
    void testRimuoviCommentoFailure() throws SQLException, ServletException, IOException {
        // Simulazione fallimento nella rimozione del commento
        int commentoId = 1;
        int postId = 1;
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(commentoId));
        when(request.getParameter("postId")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommentiService.remove(commentoId, postId, mockUtente)).thenReturn(false);

        // Chiamata alla servlet
        rimuoviCommentoServlet.doGet(request, response);

        // Verifica che la servlet abbia gestito il fallimento
        verify(request, times(1)).setAttribute("errorMessage", "Errore nella rimozione del commento.");
        verify(request, times(1)).getRequestDispatcher("/errore.jsp");
    }*/

    @Test
    void testRimuoviCommentoSQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        int commentoId = 1;
        int postId = 1;
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(commentoId));
        when(request.getParameter("postId")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommentiService.remove(commentoId, postId, mockUtente)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            rimuoviCommentoServlet.doGet(request, response);
        });
    }
}
