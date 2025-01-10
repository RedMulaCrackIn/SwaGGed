package integration.gestionecommunity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommunity.controller.CreaCommunityServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreaCommunityServletIT {

    private CreaCommunityServlet creaCommunityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommunityServiceImpl gestioneCommunityService;

    @BeforeEach
    void setUp() {
        gestioneCommunityService = mock(GestioneCommunityServiceImpl.class);
        creaCommunityServlet = new CreaCommunityServlet(gestioneCommunityService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testCreaCommunitySuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String nomeCommunity = "NewCommunity";
        String descrizioneCommunity = "Descrizione della nuova community";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        CommunityBean mockCommunity = new CommunityBean();
        mockCommunity.setNome(nomeCommunity);

        // Simulazione comportamento del servizio
        when(request.getParameter("communityNomeCreazione")).thenReturn(nomeCommunity);
        when(request.getParameter("descrizione")).thenReturn(descrizioneCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommunityService.create(nomeCommunity, descrizioneCommunity, mockUtente)).thenReturn(mockCommunity);

        // Chiamata alla servlet
        creaCommunityServlet.doPost(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommunityService, times(1)).create(nomeCommunity, descrizioneCommunity, mockUtente);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("utente", mockUtente);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + nomeCommunity);
    }

    @Test
    void testCreaCommunitySQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String nomeCommunity = "NewCommunity";
        String descrizioneCommunity = "Descrizione della nuova community";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("utente@example.com");

        when(request.getParameter("communityNomeCreazione")).thenReturn(nomeCommunity);
        when(request.getParameter("descrizione")).thenReturn(descrizioneCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(gestioneCommunityService.create(nomeCommunity, descrizioneCommunity, mockUtente)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            creaCommunityServlet.doPost(request, response);
        });
    }
}
