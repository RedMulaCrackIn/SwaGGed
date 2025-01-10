package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/visualizzaUtente")
public class VisualizzaUtenteServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private GestioneUtentiService gestioneUtenti; // non statico

    // Costruttore per iniezione di dipendenze (usato nel test)
    public VisualizzaUtenteServlet(GestioneUtentiService gestioneUtenti) {
        this.gestioneUtenti = gestioneUtenti;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter("username");
        UtenteBean profilo = null;
        try {
            profilo = gestioneUtenti.visualizza(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("profilo", profilo);
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        if(utente == null)
            response.sendRedirect(request.getContextPath() + "/utente.jsp");
        else if(profilo.getUsername().equals(utente.getUsername())) {
            response.sendRedirect(request.getContextPath() + "/common/profilo.jsp");
        }else
            response.sendRedirect(request.getContextPath() + "/utente.jsp");
    }

}
