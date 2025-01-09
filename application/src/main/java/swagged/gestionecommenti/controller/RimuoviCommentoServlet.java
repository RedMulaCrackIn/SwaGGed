package swagged.gestionecommenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionecommenti.services.GestioneCommentiService;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rimuoviCommento")
public class RimuoviCommentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommentiService gestioneCommenti = new GestioneCommentiServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int postId = Integer.parseInt(request.getParameter("postId"));
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");

        try {
            gestioneCommenti.remove(id, postId, utente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");

    }
}