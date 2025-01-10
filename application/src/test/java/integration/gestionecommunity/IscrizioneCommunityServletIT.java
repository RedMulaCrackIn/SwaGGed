package integration.gestionecommunity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommunity.controller.IscrizioneCommunityServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IscrizioneCommunityServletIT {

    private IscrizioneCommunityServlet iscrizioneCommunityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommunityServiceImpl gestioneCommunityService;

    @BeforeEach
    void setUp() {
        gestioneCommunityService = mock(GestioneCommunityServiceImpl.class);
        iscrizioneCommunityServlet = new IscrizioneCommunityServlet(gestioneCommunityService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testIscrizioneCommunitySuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String nomeCommunity = "ExistingCommunity";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        CommunityBean mockCommunity = new CommunityBean();
        mockCommunity.setNome(nomeCommunity);

        // Simulazione comportamento del servizio
        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommunityService.iscrizione(mockUtente, nomeCommunity)).thenReturn(mockCommunity);

        // Chiamata alla servlet
        iscrizioneCommunityServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommunityService, times(1)).iscrizione(mockUtente, nomeCommunity);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("utente", mockUtente);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + nomeCommunity);
    }

    @Test
    void testIscrizioneCommunitySQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String nomeCommunity = "ExistingCommunity";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommunityService.iscrizione(mockUtente, nomeCommunity)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita dalla servlet)
        assertThrows(RuntimeException.class, () -> {
            iscrizioneCommunityServlet.doPost(request, response);
        });
    }
}
