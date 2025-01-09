package swagged.gestioneutenti.services;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.Part;
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

    @Override
    public List<UtenteBean> cerca(String substring) throws SQLException {
        if (substring == null || substring.isEmpty())
            return null;

        List<UtenteBean> utentiCercati = utenteDAO.getByUsernameSubstring(substring);
        return utentiCercati;
    }

    @Override
    public boolean modificaImmagine(UtenteBean utente, Part filePart, GenericServlet servlet) throws SQLException {
        if (filePart == null || utente == null)
            return false;

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String applicationPath = servlet.getServletContext().getRealPath("");

        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String sanitizedFileName = fileName.replaceAll("\\s+", "_");
        String filePath = uploadFilePath + File.separator + sanitizedFileName;
        File file = new File(filePath);
        while (file.exists()) {
            String uniqueID = UUID.randomUUID().toString();
            sanitizedFileName = uniqueID + "_" + sanitizedFileName;
            filePath = uploadFilePath + File.separator + sanitizedFileName;
            file = new File(filePath);
        }
        try {
            Files.copy(filePart.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
        String relativeFileName = sanitizedFileName;

        utente.setImmagine(relativeFileName);

        return utenteDAO.update(utente, utente.getEmail());
    }

    @Override
    public boolean checkEmail(String email) throws SQLException {
        if (email == null || email.isEmpty())
            return false;

        UtenteBean utente = utenteDAO.getByEmail(email);
        return utente != null;
    }
}
