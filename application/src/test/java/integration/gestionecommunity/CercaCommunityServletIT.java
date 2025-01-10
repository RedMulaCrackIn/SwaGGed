package integration.gestionecommunity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommunity.controller.CercaCommunityServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CercaCommunityServletIT {

    private CercaCommunityServlet cercaCommunityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommunityServiceImpl gestioneCommunityService;

    @BeforeEach
    void setUp() {
        gestioneCommunityService = mock(GestioneCommunityServiceImpl.class);
        cercaCommunityServlet = new CercaCommunityServlet(gestioneCommunityService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testCercaCommunitySuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String substring = "Community1";
        List<CommunityBean> mockResults = new ArrayList<>();
        CommunityBean community = new CommunityBean();
        community.setNome("Community1");
        mockResults.add(community);

        // Simulazione comportamento del servizio
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestioneCommunityService.cerca(substring)).thenReturn(mockResults);

        // Chiamata alla servlet
        cercaCommunityServlet.doGet(request, response);

        // Verifica l'interazione con il servizio
        verify(gestioneCommunityService, times(1)).cerca(substring);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("risultati", mockResults);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/ricerca.jsp");
    }

    @Test
    void testCercaCommunityEmptySubstring() throws SQLException, ServletException, IOException {
        // Test con substring vuoto
        String substring = "";
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestioneCommunityService.cerca(substring)).thenReturn(null);

        cercaCommunityServlet.doGet(request, response);

        verify(gestioneCommunityService, times(1)).cerca(substring);
        verify(session, times(1)).setAttribute("risultati", null);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/ricerca.jsp");
    }

    @Test
    void testCercaCommunitySQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String substring = "Community1";
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestioneCommunityService.cerca(substring)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            cercaCommunityServlet.doGet(request, response);
        });
    }
}
