package integration.gestionepost;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionepost.controller.RimuoviPostServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RimuoviPostServletIT {

    private RimuoviPostServlet rimuoviPostServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestionePostServiceImpl gestionePostService;

    @BeforeEach
    void setUp() {
        gestionePostService = mock(GestionePostServiceImpl.class);
        rimuoviPostServlet = new RimuoviPostServlet(gestionePostService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testRimuoviPostSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);

        // Simula un successo nella rimozione del post
        when(gestionePostService.remove(postId, utente)).thenReturn(true);

        // Chiamata alla servlet
        rimuoviPostServlet.doGet(request, response);

        // Verifica che il metodo remove sia stato chiamato con i parametri corretti
        verify(gestionePostService, times(1)).remove(postId, utente);

        // Verifica che la sessione venga aggiornata correttamente
        verify(session, times(1)).setAttribute("utente", utente);

        // Verifica che venga eseguito il redirect alla homepage
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

    @Test
    void testRimuoviPostUnsuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);

        // Simula un fallimento nella rimozione del post
        when(gestionePostService.remove(postId, utente)).thenReturn(false);

        // Chiamata alla servlet
        rimuoviPostServlet.doGet(request, response);

        // Verifica che il metodo remove sia stato chiamato con i parametri corretti
        verify(gestionePostService, times(1)).remove(postId, utente);

    }

    @Test
    void testRimuoviPostWithError() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);

        // Simula un errore durante la rimozione del post (errore database)
        when(gestionePostService.remove(postId, utente)).thenThrow(new SQLException("Errore database"));

        // Verifica che venga sollevata un'eccezione durante l'esecuzione
        assertThrows(RuntimeException.class, () -> {
            rimuoviPostServlet.doGet(request, response);
        });
    }
}
