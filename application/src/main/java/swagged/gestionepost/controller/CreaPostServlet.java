package swagged.gestionepost.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import swagged.gestionepost.services.GestionePostService;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/creaPost")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class CreaPostServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final GestionePostService gestionePost = new GestionePostServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String titolo = request.getParameter("titolo");
        String corpo = request.getParameter("corpo");
        Part filePart = null;
        try {
            filePart = request.getPart("immagine"); // Può essere null se l'immagine non è caricata
        } catch (IllegalStateException | IOException | ServletException e) {
            e.printStackTrace(); // Logga l'errore ma non interrompere l'esecuzione
        }
        UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
        String communityNome = request.getParameter("communityNome");
        PostBean post = null;
        try {
            post = gestionePost.create(titolo, corpo, filePart, utente, communityNome, this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.getSession().setAttribute("utente", utente);
        if (post != null) {
            response.sendRedirect(request.getContextPath() + "/visualizzaCommunity?nome=" + post.getCommunityNome());
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp"); // Gestione degli errori
        }
    }
}