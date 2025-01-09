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
import java.util.List;

@WebServlet("/cercaCommunity")
public class CercaCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommunityService gestioneCommunity = new GestioneCommunityServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String substring = request.getParameter("substring");
        List<CommunityBean> risultati = null;
        try {
            risultati = gestioneCommunity.cerca(substring);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("risultati", risultati);
        response.sendRedirect(request.getContextPath() + "/ricerca.jsp");

    }
}
