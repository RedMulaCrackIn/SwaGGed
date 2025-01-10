package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiService;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkUsernameUtente")
public class CheckUsernameUtenteServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private GestioneUtentiService gestioneUtenti; // non statico

    // Costruttore per iniezione di dipendenze (usato nel test)
    public CheckUsernameUtenteServlet(GestioneUtentiService gestioneUtenti) {
        this.gestioneUtenti = gestioneUtenti;
    }

    public CheckUsernameUtenteServlet() {
        this.gestioneUtenti = new GestioneUtentiServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String username = request.getParameter("username");

        try {
            if(gestioneUtenti.checkUsername(username)) {
                response.getWriter().print("non disponibile");
            } else {
                response.getWriter().print("disponibile");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
