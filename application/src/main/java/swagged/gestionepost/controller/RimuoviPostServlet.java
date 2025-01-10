package swagged.gestionepost.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionepost.services.GestionePostService;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rimuoviPost")
public class RimuoviPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GestionePostService gestionePost;

    public RimuoviPostServlet(GestionePostServiceImpl gestionePost) {
        this.gestionePost = gestionePost;
    }

    public RimuoviPostServlet() {
        this.gestionePost = new GestionePostServiceImpl();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        boolean success = false;
        try {
            success = gestionePost.remove(id, utente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");

    }
}