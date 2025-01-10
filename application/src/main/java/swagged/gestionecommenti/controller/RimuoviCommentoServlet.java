package swagged.gestionecommenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionecommenti.services.GestioneCommentiService;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rimuoviCommento")
public class RimuoviCommentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneCommentiService gestioneCommenti;

    public RimuoviCommentoServlet(GestioneCommentiServiceImpl gestioneCommenti) {
        this.gestioneCommenti = gestioneCommenti;
    }

    public RimuoviCommentoServlet() {
        this.gestioneCommenti = new GestioneCommentiServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int postId = Integer.parseInt(request.getParameter("postId"));
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");

        try {
            boolean success = gestioneCommenti.remove(id, postId, utente);
            if (success) {
                // Commento rimosso con successo
                response.sendRedirect(request.getContextPath() + "/visualizzaPost?id=" + postId);
            } else {
                // Gestire il caso in cui la rimozione non è andata a buon fine
                request.setAttribute("errorMessage", "Errore nella rimozione del commento.");
                request.getRequestDispatcher("/errore.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}