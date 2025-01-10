package integration.gestionepost;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionepost.controller.LikePostServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LikePostServletIT {

    private LikePostServlet likePostServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestionePostServiceImpl gestionePostService;

    @BeforeEach
    void setUp() {
        gestionePostService = mock(GestionePostServiceImpl.class);
        likePostServlet = new LikePostServlet(gestionePostService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testLikePostSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        // Simula il comportamento del servizio
        PostBean post = new PostBean();
        post.setId(postId);

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(gestionePostService.like(utente, postId)).thenReturn(post);

        // Chiamata alla servlet
        likePostServlet.doGet(request, response);

        // Verifica l'interazione con il servizio
        verify(gestionePostService, times(1)).like(utente, postId);

        // Verifica la gestione della sessione
        verify(session, times(1)).setAttribute("utente", utente);

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

    @Test
    void testLikePostWithError() throws SQLException, ServletException, IOException {
        // Simula un errore durante l'operazione di like
        int postId = 1;
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(gestionePostService.like(utente, postId)).thenThrow(new SQLException("Errore database"));

        // Verifica che venga lanciata un'eccezione
        assertThrows(RuntimeException.class, () -> {
            likePostServlet.doGet(request, response);
        });
    }
}
