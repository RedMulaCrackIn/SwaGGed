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
import swagged.model.dao.CommunityDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rimuoviCommunity")
public class RimuoviCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommunityService gestioneCommunity = new GestioneCommunityServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        String nome = request.getParameter("nome");
        CommunityDAO communityDAO = new CommunityDAO();
        CommunityBean community = null;
        try {
            community = communityDAO.getByNome(nome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        try {
            gestioneCommunity.remove(community, utente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");
    }
}
