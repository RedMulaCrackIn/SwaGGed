package swagged.gestionecommunity.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionecommunity.services.GestioneCommunityService;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/visualizzaCommunity")
public class VisualizzaCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneCommunityService gestioneCommunity;

    public VisualizzaCommunityServlet(GestioneCommunityServiceImpl gestioneCommunity) {
        this.gestioneCommunity = gestioneCommunity;
    }

    public VisualizzaCommunityServlet() {
        this.gestioneCommunity = new GestioneCommunityServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

            String nome = request.getParameter("nome");
        CommunityBean community = null;
        try {
            community = gestioneCommunity.visualizza(nome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("community", community);
            response.sendRedirect(request.getContextPath() + "/community.jsp");
    }
}
