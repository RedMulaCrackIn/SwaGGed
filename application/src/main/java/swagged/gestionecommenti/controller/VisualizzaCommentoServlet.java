package swagged.gestionecommenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestionecommenti.services.GestioneCommentiService;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/visualizzaCommento")
public class VisualizzaCommentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestioneCommentiService gestioneCommenti = new GestioneCommentiServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));
        try {
            gestioneCommenti.visualizza(postId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}