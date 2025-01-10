package integration.gestioneutenti;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import swagged.gestioneutenti.controller.LogoutServlet;

import java.io.IOException;

import static org.mockito.Mockito.*;

class LogoutServletTest {

    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logoutServlet = new LogoutServlet();
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoGet() throws IOException, ServletException {
        // Act
        logoutServlet.doGet(request, response);

        // Assert
        verify(session, times(1)).removeAttribute("logged");
        verify(session, times(1)).removeAttribute("utente");
        verify(session, times(1)).invalidate();
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/homepage.jsp");
    }
}
