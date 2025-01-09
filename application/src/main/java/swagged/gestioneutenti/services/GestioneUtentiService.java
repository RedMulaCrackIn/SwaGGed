package swagged.gestioneutenti.services;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.Part;
import swagged.model.bean.UtenteBean;


import java.sql.SQLException;
import java.util.List;

public interface GestioneUtentiService {
    boolean ban (String email) throws SQLException;
    List<UtenteBean> cerca(String substring) throws SQLException;
    boolean modificaImmagine(UtenteBean utente, Part filePart, GenericServlet servlet) throws SQLException;
    boolean checkEmail(String email) throws SQLException;
    boolean checkUsername(String username) throws SQLException;
    UtenteBean visualizza(String username) throws SQLException;
    UtenteBean login(String username, String password) throws SQLException;
    UtenteBean registrazione(String email, String username, String password, String passwordCheck) throws SQLException;
}