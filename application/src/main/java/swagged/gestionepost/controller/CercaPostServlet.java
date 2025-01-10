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
import java.util.List;

@WebServlet("/cercaPost")
public class CercaPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestionePostService gestionePost;

    public CercaPostServlet(GestionePostServiceImpl gestionePost) {
        this.gestionePost = gestionePost;
    }

    public CercaPostServlet() {
        this.gestionePost = new GestionePostServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String substring = request.getParameter("substring");
        List<PostBean> risultati = null;
        try {
            risultati = gestionePost.cerca(substring);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("risultati", risultati);
        response.sendRedirect(request.getContextPath() + "/ricerca.jsp");

    }
}