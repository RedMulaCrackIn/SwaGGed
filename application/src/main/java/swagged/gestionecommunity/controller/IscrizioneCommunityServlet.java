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

@WebServlet("/iscrizioneCommunity")
public class IscrizioneCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommunityService gestioneCommunity = new GestioneCommunityServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        String nome = request.getParameter("nome");
        CommunityBean community = null;
        try {
            community = gestioneCommunity.iscrizione(utente, nome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + community.getNome());

    }
}
