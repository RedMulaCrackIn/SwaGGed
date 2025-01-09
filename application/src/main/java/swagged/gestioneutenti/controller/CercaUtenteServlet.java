package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cercaUtente")
public class CercaUtenteServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final GestioneUtentiServiceImpl gestioneUtenti = new GestioneUtentiServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String path = null;
        UtenteDAO utenteDAO = new UtenteDAO();

        String substring = request.getParameter("substring");
        List<UtenteBean> risultati = null;
        try {
            risultati = gestioneUtenti.cerca(substring);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("risultati", risultati);
        response.sendRedirect(request.getContextPath() + "/ricerca.jsp");

    }

}
