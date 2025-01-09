package swagged.gestionepost.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionepost.services.GestionePostService;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/visualizzaPost")
public class VisualizzaPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestionePostService gestionePost = new GestionePostServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PostBean post = null;
        try {
            post = gestionePost.visualizza(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("post", post);
        response.sendRedirect(request.getContextPath() + "/post.jsp");

    }
}