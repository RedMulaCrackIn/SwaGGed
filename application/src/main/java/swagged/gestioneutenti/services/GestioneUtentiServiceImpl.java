package swagged.gestioneutenti.services;

import swagged.model.bean.CommunityBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.*;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class GestioneUtentiServiceImpl implements GestioneUtentiService
{
    private UtenteDAO utenteDAO = new UtenteDAO();
    private static final String UPLOAD_DIR = "assets/images/pfp";

    @Override
    public boolean ban(String email) throws SQLException {
        if (email == null || email.isEmpty() || utenteDAO.getByEmail(email) == null)
            return false;

        UtenteBean utente = utenteDAO.getByEmail(email);

        if (utente != null) {
            utente.setBandito(true);

            return utenteDAO.update(utente, email);
        }
        return false;
    }
}
