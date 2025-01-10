package integration.gestionepost;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionepost.controller.CreaPostServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreaPostServletIT {

    private CreaPostServlet creaPostServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestionePostServiceImpl gestionePostService;

    @BeforeEach
    void setUp() {
        gestionePostService = mock(GestionePostServiceImpl.class);
        creaPostServlet = new CreaPostServlet(gestionePostService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testCreaPostSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String titolo = "Test Post";
        String corpo = "Questo è il contenuto del post";
        String communityNome = "Comunità Test";

        // Simuliamo l'oggetto Part (l'immagine)
        Part filePart = mock(Part.class);
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        // Crea il PostBean mockato
        PostBean post = new PostBean();
        post.setTitolo(titolo);
        post.setCorpo(corpo);
        post.setCommunityNome(communityNome);

        // Comportamento del servizio
        when(request.getParameter("titolo")).thenReturn(titolo);
        when(request.getParameter("corpo")).thenReturn(corpo);
        when(request.getParameter("communityNome")).thenReturn(communityNome);
        when(request.getPart("immagine")).thenReturn(filePart);
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(gestionePostService.create(titolo, corpo, filePart, utente, communityNome, creaPostServlet)).thenReturn(post);

        // Chiamata alla servlet
        creaPostServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestionePostService, times(1)).create(titolo, corpo, filePart, utente, communityNome, creaPostServlet);

        // Verifica la gestione della sessione
        verify(session, times(1)).setAttribute("utente", utente);

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + communityNome);
    }

    @Test
    void testCreaPostWithNoImage() throws SQLException, ServletException, IOException {
        // Test senza immagine
        String titolo = "Test Post Senza Immagine";
        String corpo = "Questo post non ha immagine";
        String communityNome = "Comunità Test";

        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        PostBean post = new PostBean();
        post.setTitolo(titolo);
        post.setCorpo(corpo);
        post.setCommunityNome(communityNome);

        // Comportamento del servizio
        when(request.getParameter("titolo")).thenReturn(titolo);
        when(request.getParameter("corpo")).thenReturn(corpo);
        when(request.getParameter("communityNome")).thenReturn(communityNome);
        when(request.getPart("immagine")).thenReturn(null);  // Nessuna immagine caricata
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(gestionePostService.create(titolo, corpo, null, utente, communityNome, creaPostServlet)).thenReturn(post);

        // Chiamata alla servlet
        creaPostServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestionePostService, times(1)).create(titolo, corpo, null, utente, communityNome, creaPostServlet);

        // Verifica la gestione della sessione
        verify(session, times(1)).setAttribute("utente", utente);

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + communityNome);
    }

    @Test
    void testCreaPostError() throws SQLException, ServletException, IOException {
        // Simuliamo un errore durante la creazione del post
        String titolo = "Errore Post";
        String corpo = "Questo post genererà un errore";
        String communityNome = "Comunità Test";

        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        when(request.getParameter("titolo")).thenReturn(titolo);
        when(request.getParameter("corpo")).thenReturn(corpo);
        when(request.getParameter("communityNome")).thenReturn(communityNome);
        when(request.getPart("immagine")).thenReturn(null);  // Nessuna immagine caricata
        when(request.getSession().getAttribute("utente")).thenReturn(utente);
        when(gestionePostService.create(titolo, corpo, null, utente, communityNome, creaPostServlet))
                .thenThrow(new SQLException("Errore database"));

        // Verifica che venga lanciata un'eccezione
        assertThrows(RuntimeException.class, () -> {
            creaPostServlet.doPost(request, response);
        });
    }
}
