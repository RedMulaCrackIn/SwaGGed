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

@WebServlet("/banUtente")
public class BanUtenteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneUtentiService gestioneUtenti; // non statico

    // Costruttore per iniezione di dipendenze (usato nel test)
    public BanUtenteServlet(GestioneUtentiService gestioneUtenti) {
        this.gestioneUtenti = gestioneUtenti;
    }

    public BanUtenteServlet(){
        this.gestioneUtenti = new GestioneUtentiServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteDAO utenteDAO = new UtenteDAO();

        String emailToBan = request.getParameter("utenteEmail"); // The email of the user to be banned
        UtenteBean bannato = null;
        UtenteBean moderatore = (UtenteBean) request.getSession().getAttribute("utente");
        try {
            bannato = utenteDAO.getByEmail(emailToBan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            gestioneUtenti.ban(moderatore, emailToBan);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(bannato != null)
            response.sendRedirect(request.getContextPath() + "/visualizzaUtente?username=" + bannato.getUsername());
        else
            response.sendRedirect(request.getContextPath() + "/error404.jsp");
    }
}