package integration.gestionepost;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestionepost.controller.VisualizzaPostServlet;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class VisualizzaPostServletIT {

    private VisualizzaPostServlet visualizzaPostServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private GestionePostServiceImpl gestionePostService;

    @BeforeEach
    void setUp() {
        gestionePostService = mock(GestionePostServiceImpl.class);
        visualizzaPostServlet = new VisualizzaPostServlet(gestionePostService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testVisualizzaPostSuccessfully() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;
        PostBean post = new PostBean();
        post.setId(postId);
        post.setTitolo("Test Post");
        post.setCorpo("Contenuto del post");

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(gestionePostService.visualizza(postId)).thenReturn(post);

        // Chiamata alla servlet
        visualizzaPostServlet.doGet(request, response);

        // Verifica che il metodo visualizza sia stato chiamato con il postId corretto
        verify(gestionePostService, times(1)).visualizza(postId);

        // Verifica che il post venga messo nella sessione
        verify(session, times(1)).setAttribute("post", post);

        // Verifica che venga eseguito il redirect alla pagina di visualizzazione del post
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/post.jsp");
    }

    @Test
    void testVisualizzaPostWithError() throws SQLException, ServletException, IOException {
        // Dati di test
        int postId = 1;

        when(request.getParameter("id")).thenReturn(String.valueOf(postId));
        when(gestionePostService.visualizza(postId)).thenThrow(new SQLException("Errore nel recupero del post"));

        // Verifica che venga sollevata una RuntimeException
        assertThrows(RuntimeException.class, () -> {
            visualizzaPostServlet.doGet(request, response);
        });

        // Verifica che non venga eseguito il redirect alla pagina di post
        verify(response, times(0)).sendRedirect(request.getContextPath() + "/post.jsp");
    }
}
