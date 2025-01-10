package integration.gestionecommunity;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionecommunity.controller.RimuoviCommunityServlet;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.CommunityDAO;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RimuoviCommunityServletIT {

    private RimuoviCommunityServlet rimuoviCommunityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestioneCommunityServiceImpl gestioneCommunityService;
    private CommunityDAO communityDAO;

    @BeforeEach
    void setUp() {
        gestioneCommunityService = mock(GestioneCommunityServiceImpl.class);
        communityDAO = mock(CommunityDAO.class);
        rimuoviCommunityServlet = new RimuoviCommunityServlet(gestioneCommunityService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testRimuoviCommunitySuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String nomeCommunity = "Community1";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("email1@email.com"); // Utente che è il proprietario della community

        // Mock della comunità presente nel database
        CommunityBean mockCommunity = new CommunityBean();
        mockCommunity.setNome(nomeCommunity);
        mockCommunity.setDescrizione("Descrizione");
        mockCommunity.setIscritti(3);
        mockCommunity.setUtenteEmail("email1@email.com");

        // Simulazione comportamento del DAO per ottenere la community dal DB
        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        when(communityDAO.getByNome(nomeCommunity)).thenReturn(mockCommunity);

        // Simulazione comportamento del servizio per rimuovere la community
        when(gestioneCommunityService.remove(mockCommunity, mockUtente)).thenReturn(true);

        // Chiamata alla servlet
        rimuoviCommunityServlet.doGet(request, response);

        // Verifica che il metodo remove sia stato invocato
        verify(gestioneCommunityService, times(1)).remove(mockCommunity, mockUtente);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("utente", mockUtente);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }

    /*@Test
    void testRimuoviCommunitySQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL nel DAO
        String nomeCommunity = "Community1";
        UtenteBean mockUtente = new UtenteBean();
        mockUtente.setEmail("email1@email.com");

        when(request.getParameter("nome")).thenReturn(nomeCommunity);
        when(request.getSession().getAttribute("utente")).thenReturn(mockUtente);
        // Simuliamo che il DAO lanci un SQLException
        when(communityDAO.getByNome(nomeCommunity)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita dalla servlet)
        assertThrows(RuntimeException.class, () -> {
            rimuoviCommunityServlet.doGet(request, response);
        });
    }
*/
}
