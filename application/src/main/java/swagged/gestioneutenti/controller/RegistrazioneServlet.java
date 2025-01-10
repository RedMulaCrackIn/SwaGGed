package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {
    private GestioneUtentiServiceImpl gestioneUtenti;
    public void setGestioneUtentiService(GestioneUtentiServiceImpl gestioneUtenti) {
        this.gestioneUtenti = gestioneUtenti;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteDAO dbUtenti = new UtenteDAO();
        GestioneUtentiServiceImpl gestioneUtenti = new GestioneUtentiServiceImpl();
        String mode = request.getParameter("mode");
        String path = null;

        if(mode.equals("register")){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String passwordCheck = request.getParameter("passwordCheck");
            String email = request.getParameter("email");

            try {
                UtenteBean utente = gestioneUtenti.registrazione(email, username, password, passwordCheck);

                if (utente != null) {
                    request.getSession().setAttribute("result", "Registrato con successo!");
                    request.getSession().setAttribute("utente", utente);
                    response.sendRedirect(request.getContextPath() + "/homepage.jsp");
                } else {
                    request.getSession().setAttribute("error", "Registrazione non completata. Riprovare.");
                    response.sendRedirect(request.getContextPath() + "/registrazione.jsp");
                }
            } catch (SQLException e) {
                throw new ServletException("Errore durante la registrazione", e);
            }
        }
    }
}
