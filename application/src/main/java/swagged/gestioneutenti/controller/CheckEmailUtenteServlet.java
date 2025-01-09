package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkEmailUtente")
public class CheckEmailUtenteServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final GestioneUtentiServiceImpl gestioneUtenti = new GestioneUtentiServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String path = null;
        UtenteDAO utenteDAO = new UtenteDAO();

        response.setContentType("text/plain");
        String email = request.getParameter("email");

        try {
            if(gestioneUtenti.checkEmail(email)) {
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
