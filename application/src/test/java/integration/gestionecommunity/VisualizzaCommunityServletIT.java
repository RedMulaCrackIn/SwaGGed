package integration.gestionecommunity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommunity.controller.VisualizzaCommunityServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VisualizzaCommunityServletIT {

    private VisualizzaCommunityServlet visualizzaCommunityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommunityServiceImpl gestioneCommunityService;

    @BeforeEach
    void setUp() {
        gestioneCommunityService = mock(GestioneCommunityServiceImpl.class);
        visualizzaCommunityServlet = new VisualizzaCommunityServlet(gestioneCommunityService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testVisualizzaCommunitySuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String nomeCommunity = "Community1";
        CommunityBean mockCommunity = new CommunityBean();
        mockCommunity.setNome(nomeCommunity);

        // Simulazione comportamento del servizio
        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(gestioneCommunityService.visualizza(nomeCommunity)).thenReturn(mockCommunity);

        // Chiamata alla servlet
        visualizzaCommunityServlet.doGet(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommunityService, times(1)).visualizza(nomeCommunity);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("community", mockCommunity);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/community.jsp");
    }

    @Test
    void testVisualizzaCommunitySQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String nomeCommunity = "Community1";
        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(gestioneCommunityService.visualizza(nomeCommunity)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            visualizzaCommunityServlet.doGet(request, response);
        });
    }
}
