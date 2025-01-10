package swagged.gestioneutenti.services;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.Part;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class GestioneUtentiServiceImpl implements GestioneUtentiService {
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

        String relativeFileName = null;
        if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
            if (!isImageFile(filePart)) {
                return false;
            }

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
            relativeFileName = sanitizedFileName;
        }

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

    @Override
    public boolean checkUsername(String username) throws SQLException {
        if (username == null || username.isEmpty())
            return false;

        UtenteBean utente = utenteDAO.getByUsername(username);
        return utente != null;
    }

    public UtenteBean visualizza(String username) throws SQLException {
        if (username == null || username.isEmpty() || utenteDAO.getByUsername(username) == null)
            return null;
        UtenteBean utente = utenteDAO.getByUsername(username);
        PostDAO postDAO = new PostDAO();
        utente.set("postCreati", postDAO.getByEmail(utente.getEmail()));

        return utente;
    }

    @Override
    public UtenteBean login(String username, String password) throws SQLException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }

        // Encode the password in Base64
        Base64.Encoder encoder = Base64.getEncoder();
        String password64 = encoder.encodeToString(password.getBytes());

        // Check login credentials
        UtenteDAO utenteDAO = new UtenteDAO();
        UtenteBean utente = utenteDAO.getByUsername(username);

        if (utente != null && utente.getPassword().equals(password64)) {
            PostDAO postDAO = new PostDAO();
            ApprezzaPostDAO apprezzaPostDAO = new ApprezzaPostDAO();
            CommentoDAO commentoDAO = new CommentoDAO();
            CommunityDAO communityDAO = new CommunityDAO();
            IscrivitiCommunityDAO iscrivitiCommunityDAO = new IscrivitiCommunityDAO();

            // Populate user details
            utente.set("postCreati", postDAO.getByEmail(utente.getEmail()));
            utente.set("postApprezzati", apprezzaPostDAO.getByEmail(utente.getEmail()));
            utente.set("commentiCreati", commentoDAO.getByUtenteEmail(utente.getEmail()));
            utente.set("communityCreate", communityDAO.getByEmail(utente.getEmail()));
            utente.set("communityIscritto", iscrivitiCommunityDAO.getByEmail(utente.getEmail()));

            return utente;
        }

        return null;
    }

    @Override
    public UtenteBean registrazione(String email, String username, String password, String passwordCheck) throws SQLException {
        if (email == null || email.isEmpty() || username == null || username.isEmpty() ||
                password == null || password.isEmpty() || passwordCheck == null || passwordCheck.isEmpty()) {
            return null;
        }

        // Encode passwords in Base64
        Base64.Encoder encoder = Base64.getEncoder();
        String pwd64 = encoder.encodeToString(password.getBytes());
        String pwdchk64 = encoder.encodeToString(passwordCheck.getBytes());

        if (!pwd64.equals(pwdchk64)) {
            return null; // Passwords do not match
        }

        UtenteDAO dbUtenti = new UtenteDAO();
        List<UtenteBean> listaUtenti = dbUtenti.getAll();

        for (UtenteBean utenteEsistente : listaUtenti) {
            if (utenteEsistente.getUsername().equalsIgnoreCase(username) ||
                    utenteEsistente.getEmail().equalsIgnoreCase(email)) {
                return null; // Username or email already exists
            }
        }

        // Create and save the new user
        UtenteBean nuovoUtente = new UtenteBean();
        nuovoUtente.setUsername(username);
        nuovoUtente.setPassword(pwd64);
        nuovoUtente.setEmail(email);
        nuovoUtente.setAdmin(false);
        nuovoUtente.setImmagine("noPfp.jpg");

        dbUtenti.save(nuovoUtente);
        return nuovoUtente;
    }

    private boolean isImageFile(Part filePart) {
        String contentType = filePart.getContentType();
        return contentType != null && (contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/gif") || contentType.equals("image/jpg"));
    }
}


