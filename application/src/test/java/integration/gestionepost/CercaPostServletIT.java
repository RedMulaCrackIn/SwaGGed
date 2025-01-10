package integration.gestionepost;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionepost.controller.CercaPostServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CercaPostServletIT {

    private CercaPostServlet cercaPostServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestionePostServiceImpl gestionePostService;

    @BeforeEach
    void setUp() {
        gestionePostService = mock(GestionePostServiceImpl.class);
        cercaPostServlet = new CercaPostServlet(gestionePostService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testCercaPostSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        String substring = "Post1";
        List<PostBean> mockResults = new ArrayList<>();
        PostBean post = new PostBean();
        post.setTitolo("Post1");
        mockResults.add(post);

        // Simulazione comportamento del servizio
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestionePostService.cerca(substring)).thenReturn(mockResults);

        // Chiamata alla servlet
        cercaPostServlet.doGet(request, response);

        // Verifica l'interazione con il servizio
        verify(gestionePostService, times(1)).cerca(substring);

        // Verifica la gestione della sessione e il redirect
        verify(session, times(1)).setAttribute("risultati", mockResults);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/ricerca.jsp");
    }

    @Test
    void testCercaPostEmptySubstring() throws SQLException, ServletException, IOException {
        // Test con substring vuoto
        String substring = "";
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestionePostService.cerca(substring)).thenReturn(null);

        cercaPostServlet.doGet(request, response);

        verify(gestionePostService, times(1)).cerca(substring);
        verify(session, times(1)).setAttribute("risultati", null);
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/ricerca.jsp");
    }

    @Test
    void testCercaPostSQLException() throws SQLException, ServletException, IOException {
        // Simulazione errore SQL
        String substring = "Post1";
        when(request.getParameter("substring")).thenReturn(substring);
        when(gestionePostService.cerca(substring)).thenThrow(new SQLException("Database error"));

        // Verifica che venga lanciata un'eccezione RuntimeException (gestita nella servlet)
        assertThrows(RuntimeException.class, () -> {
            cercaPostServlet.doGet(request, response);
        });
    }
}
