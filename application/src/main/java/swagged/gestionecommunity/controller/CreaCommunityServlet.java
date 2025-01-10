package swagged.gestionecommunity.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionecommunity.services.GestioneCommunityService;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/creaCommunity")
public class CreaCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneCommunityService gestioneCommunity;

    public CreaCommunityServlet(GestioneCommunityServiceImpl gestioneCommunity) {
        this.gestioneCommunity = gestioneCommunity;
    }

    public CreaCommunityServlet() {
        this.gestioneCommunity = new GestioneCommunityServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String nome = request.getParameter("communityNomeCreazione");
        String descrizione = request.getParameter("descrizione");
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        CommunityBean community = null;
        try {
            community = gestioneCommunity.create(nome, descrizione, utente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + community.getNome());
    }
}
