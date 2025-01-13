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

@WebServlet("/checkNomeCommunity")
public class CheckNomeCommunityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestioneCommunityService gestioneCommunity;

    public CheckNomeCommunityServlet(GestioneCommunityServiceImpl gestioneCommunity) {
        this.gestioneCommunity = gestioneCommunity;
    }

    public CheckNomeCommunityServlet() {
        this.gestioneCommunity = new GestioneCommunityServiceImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        String nome = request.getParameter("nome");

        try {
            if(gestioneCommunity.checkNome(nome)) {
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
