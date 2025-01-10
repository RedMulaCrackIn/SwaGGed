package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private GestioneUtentiService gestioneUtenti; // non statico

    // Costruttore per iniezione di dipendenze (usato nel test)
    public LoginServlet(GestioneUtentiService gestioneUtenti) {
        this.gestioneUtenti = gestioneUtenti;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UtenteBean utente = gestioneUtenti.login(username, password);

            if (utente != null) {
                request.getSession().setAttribute("logged", true);
                request.getSession().setAttribute("utente", utente);
                response.sendRedirect(request.getContextPath() + "/homepage.jsp");
            } else {
                request.getSession().setAttribute("logged", false);
                request.getSession().setAttribute("error", "Username e/o password invalidi.");
                response.sendRedirect(request.getContextPath() + "/login.jsp?action=error");
            }
        } catch (SQLException e) {
            throw new ServletException("Errore durante il login", e);
        }
    }
}
