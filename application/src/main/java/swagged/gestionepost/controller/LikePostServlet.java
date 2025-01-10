package swagged.gestionepost.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionepost.services.GestionePostService;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/likePost")
public class LikePostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestionePostService gestionePost;

    public LikePostServlet(GestionePostServiceImpl gestionePost) {
        this.gestionePost = gestionePost;
    }

    public LikePostServlet() {
        this.gestionePost = new GestionePostServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            PostBean post = gestionePost.like(utente, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");

    }
}