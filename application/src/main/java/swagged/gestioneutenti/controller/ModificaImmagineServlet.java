package swagged.gestioneutenti.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/modificaImmagineUtente")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ModificaImmagineServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    private static final GestioneUtentiServiceImpl gestioneUtenti = new GestioneUtentiServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String path = null;
        UtenteDAO utenteDAO = new UtenteDAO();

        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        Part filePart = request.getPart("immagine");
        try {
            gestioneUtenti.modificaImmagine(utente, filePart, this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("utente", utente);
        response.sendRedirect(request.getContextPath() + "/homepage.jsp");
    }
}