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

@WebServlet("/creaCommento")
public class CreaCommentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneCommentiService gestioneCommenti;

    public CreaCommentoServlet(GestioneCommentiServiceImpl gestioneCommenti) {
        this.gestioneCommenti = gestioneCommenti;
    }

    public CreaCommentoServlet() {
        this.gestioneCommenti = new GestioneCommentiServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        String email = utente.getEmail();
        int postId = Integer.parseInt(request.getParameter("postId"));
        String corpo = request.getParameter("corpo");
        try {
            gestioneCommenti.create(postId, corpo, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/visualizzaPost?id=" + postId);
    }
}
