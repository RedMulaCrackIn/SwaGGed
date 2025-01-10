package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestioneutenti.controller.ModificaImmagineServlet;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ModificaImmagineServletIT {

    private ModificaImmagineServlet modificaImmagineServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneUtentiServiceImpl gestioneUtentiService;

    @BeforeEach
    void setUp() {
        gestioneUtentiService = mock(GestioneUtentiServiceImpl.class);
        modificaImmagineServlet = new ModificaImmagineServlet(gestioneUtentiService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testModificaImmagineSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        UtenteBean utente = new UtenteBean();
        utente.setEmail("email1@email.com");

        // Simuliamo l'oggetto Part (l'immagine)
        Part filePart = mock(Part.class);

        // Comportamento del servizio
        when(request.getPart("immagine")).thenReturn(filePart);
        when(request.getSession().getAttribute("utente")).thenReturn(utente);

        // Simula il comportamento del servizio di modifica immagine
        when(gestioneUtentiService.modificaImmagine(utente, filePart, modificaImmagineServlet)).thenReturn(true);

        // Verifica che il mock restituisca true
        assertTrue(gestioneUtentiService.modificaImmagine(utente, filePart, modificaImmagineServlet));

        // Chiamata alla servlet
        modificaImmagineServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneUtentiService, times(1)).modificaImmagine(utente, filePart, modificaImmagineServlet);

        // Verifica il redirect
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }


    @Test
    void testModificaImmagineWithError() throws SQLException, ServletException, IOException {
        // Dati di test
        UtenteBean utente = new UtenteBean();
        utente.setEmail("utente@test.com");

        // Simuliamo l'oggetto Part (l'immagine)
        Part filePart = mock(Part.class);

        // Simula un errore nel servizio di modifica immagine
        doThrow(new SQLException("Errore durante la modifica dell'immagine")).when(gestioneUtentiService)
                .modificaImmagine(utente, filePart, modificaImmagineServlet);

        when(request.getPart("immagine")).thenReturn(filePart);
        when(request.getSession().getAttribute("utente")).thenReturn(utente);

        // Verifica che venga lanciata un'eccezione
        assertThrows(RuntimeException.class, () -> {
            modificaImmagineServlet.doPost(request, response);
        });
    }
}
