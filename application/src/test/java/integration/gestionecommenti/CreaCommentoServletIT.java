package integration.gestionecommenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommenti.controller.CreaCommentoServlet;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreaCommentoServletIT {

    private CreaCommentoServlet creaCommentoServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommentiServiceImpl gestioneCommentiService;

    @BeforeEach
    void setUp() {
        gestioneCommentiService = mock(GestioneCommentiServiceImpl.class);
        creaCommentoServlet = new CreaCommentoServlet(gestioneCommentiService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testCreaCommentoSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String corpoCommento = "Questo è un nuovo commento";
        int postId = 1;
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        // Simulazione comportamento del servizio
        when(request.getParameter("postId")).thenReturn(String.valueOf(postId));
        when(request.getParameter("corpo")).thenReturn(corpoCommento);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommentiService.create(postId, corpoCommento, mockUtente.getEmail())).thenReturn(null);

        // Chiamata alla servlet
        creaCommentoServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommentiService, times(1)).create(postId, corpoCommento, mockUtente.getEmail());

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaPost?id=" + postId);
    }

    @Test
    void testCreaCommentoSQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String corpoCommento = "Questo è un nuovo commento";
        int postId = 1;
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        when(request.getParameter("postId")).thenReturn(String.valueOf(postId));
        when(request.getParameter("corpo")).thenReturn(corpoCommento);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommentiService.create(postId, corpoCommento, mockUtente.getEmail())).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            creaCommentoServlet.doPost(request, response);
        });
    }
}
