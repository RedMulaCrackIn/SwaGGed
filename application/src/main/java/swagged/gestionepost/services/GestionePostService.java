package swagged.gestionepost.services;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.Part;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;

import java.sql.SQLException;
import java.util.List;

public interface GestionePostService {
    PostBean create(String titolo, String corpo, Part filePart, UtenteBean utente, String comunityNome, GenericServlet servlet) throws SQLException;
    boolean remove(int id, UtenteBean utente) throws SQLException;
    List<PostBean> cerca(String substring) throws SQLException;
    PostBean like(UtenteBean utente, int postId) throws SQLException;
    PostBean visualizza(int id) throws SQLException;

}
