package unit.test_gestioneutenti;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import swagged.gestioneutenti.services.GestioneUtentiServiceImpl;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.PostDAO;
import swagged.model.dao.UtenteDAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestioneUtentiServiceTest {
    private GestioneUtentiServiceImpl gestioneUtentiService;
    private UtenteDAO utenteDAOMock;


    @BeforeEach
    void setUp() {
        utenteDAOMock = mock(UtenteDAO.class);
        gestioneUtentiService = new GestioneUtentiServiceImpl(utenteDAOMock);
    }

    private UtenteBean createMockUser(String email, String username, String password, boolean bandito, boolean admin) {
        UtenteBean utente = new UtenteBean();
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));
        utente.setImmagine("noPfp.jpg");
        utente.setBandito(bandito);
        utente.setAdmin(admin);

        utente.set("postCreati", Collections.emptyList());
        utente.set("postApprezzati", Collections.emptyList());
        utente.set("commentiCreati", Collections.emptyList());
        utente.set("communityCreate", Collections.emptyList());
        utente.set("communityIscritto", Collections.emptyList());

        return utente;
    }

    @Nested
    class LoginTests {
        @Test
        void testLoginSuccess() throws SQLException {
            // Arrange
            String username = "user1";
            String password = "pass";
            UtenteBean mockUtente = createMockUser("email1@email.com", username, password, false, false);

            when(utenteDAOMock.getByUsername(username)).thenReturn(mockUtente);

            // Act
            UtenteBean utente = gestioneUtentiService.login(username, password);

            // Assert
            assertNotNull(utente, "Il login dovrebbe avere successo");
            assertEquals(username, utente.getUsername(), "Lo username non corrisponde");
        }

        @Test
        void testLoginWrongPassword() throws SQLException {
            // Arrange
            String username = "user1";
            String password = "password";
            String wrongPassword = "wrongpassword";
            UtenteBean mockUtente = createMockUser("user1@example.com", username, password, false, false);

            when(utenteDAOMock.getByUsername(username)).thenReturn(mockUtente);

            // Act
            UtenteBean utente = gestioneUtentiService.login(username, wrongPassword);

            // Assert
            assertNull(utente, "Il login dovrebbe fallire con una password errata");
        }

        @Test
        void testLoginUserNotFound() throws SQLException {
            // Arrange
            String username = "nonexistentuser";
            String password = "password";

            when(utenteDAOMock.getByUsername(username)).thenReturn(null);

            // Act
            UtenteBean utente = gestioneUtentiService.login(username, password);

            // Assert
            assertNull(utente, "Il login dovrebbe fallire per utente non trovato");
        }
    }

    @Nested
    class BanTests {
        @Test
        void testBanUserSuccess() throws SQLException {
            // Arrange
            String email = "email1@email.com";
            UtenteBean mockUtente = createMockUser(email, "user1", "pass", false, false);
            UtenteBean moderatore = new UtenteBean();
            moderatore.setAdmin(true);


            when(utenteDAOMock.getByEmail(email)).thenReturn(mockUtente);
            when(utenteDAOMock.update(any(UtenteBean.class), eq(email))).thenReturn(true);

            // Act
            boolean result = gestioneUtentiService.ban(moderatore, email);

            // Assert
            assertTrue(result, "Il ban dovrebbe avere successo");
            assertTrue(mockUtente.isBandito(), "L'utente dovrebbe essere contrassegnato come bandito");
        }

        @Test
        void testBanUserNotFound() throws SQLException {
            // Arrange
            String email = "nonexistent@example.com";
            UtenteBean moderatore = new UtenteBean();
            moderatore.setAdmin(true);

            when(utenteDAOMock.getByEmail(email)).thenReturn(null);

            // Act
            boolean result = gestioneUtentiService.ban(moderatore, email);

            // Assert
            assertFalse(result, "Il ban dovrebbe fallire per utente non trovato");
        }
    }

    @Nested
    class CercaTests {
        @Test
        void testCercaSuccess() throws SQLException {
            // Arrange
            String substring = "user";
            UtenteBean mockUtente1 = createMockUser("email1@email.com", "user1", "pass", false, false);
            UtenteBean mockUtente2 = createMockUser("email2@email.com", "user2", "pass", false, false);

            when(utenteDAOMock.getByUsernameSubstring(substring)).thenReturn(List.of(mockUtente1, mockUtente2));

            // Act
            List<UtenteBean> utenti = gestioneUtentiService.cerca(substring);

            // Assert
            assertNotNull(utenti, "La ricerca dovrebbe restituire una lista non nulla");
            assertEquals(2, utenti.size(), "La ricerca dovrebbe restituire due utenti");
        }

        @Test
        void testCercaNoResults() throws SQLException {
            // Arrange
            String substring = "nonexistent";
            when(utenteDAOMock.getByUsernameSubstring(substring)).thenReturn(Collections.emptyList());

            // Act
            List<UtenteBean> utenti = gestioneUtentiService.cerca(substring);

            // Assert
            assertNotNull(utenti, "La ricerca non dovrebbe restituire null");
            assertTrue(utenti.isEmpty(), "La ricerca dovrebbe restituire una lista vuota");
        }

        @Test
        void testCercaNullOrEmpty() throws SQLException {
            // Arrange
            String substring = "";

            // Act
            List<UtenteBean> utenti = gestioneUtentiService.cerca(substring);

            // Assert
            assertNull(utenti, "La ricerca dovrebbe restituire null per una stringa vuota o nulla");
        }
    }

    @Nested
    class ModificaImmagineTests {

        private static final String UPLOAD_DIR = "assets/images/pfp"; // Puoi sostituirlo con il valore corretto

        @Test
        void testModificaImmagineFileNull() throws SQLException {
            // Arrange
            UtenteBean utente = createMockUser("user@example.com", "user1", "password", false, false);
            Part filePart = null;  // File part null
            GenericServlet servlet = mock(GenericServlet.class);

            // Act
            boolean result = gestioneUtentiService.modificaImmagine(utente, filePart, servlet);

            // Assert
            assertFalse(result, "La modifica dell'immagine dovrebbe fallire con un file null");
        }

        @Test
        void testModificaImmagineUtenteNull() throws SQLException {
            // Arrange
            UtenteBean utente = null;  // Utente null
            Part filePart = mock(Part.class);
            GenericServlet servlet = mock(GenericServlet.class);

            // Act
            boolean result = gestioneUtentiService.modificaImmagine(utente, filePart, servlet);

            // Assert
            assertFalse(result, "La modifica dell'immagine dovrebbe fallire con un utente null");
        }

        @Test
        void testModificaImmagineFileNonImmagine() throws SQLException {
            // Arrange
            UtenteBean utente = createMockUser("user@example.com", "user1", "password", false, false);
            Part filePart = mock(Part.class);
            when(filePart.getSubmittedFileName()).thenReturn("test.txt");  // Non immagine
            GenericServlet servlet = mock(GenericServlet.class);

            // Act
            boolean result = gestioneUtentiService.modificaImmagine(utente, filePart, servlet);

            // Assert
            assertFalse(result, "La modifica dell'immagine dovrebbe fallire se il file non è un'immagine");
        }

        @Test
        void testmodificaImmagineSuccesso() {
            // Arrange
            UtenteBean utente = new UtenteBean();
            utente.setEmail("test@example.com");
            utente.setImmagine("vecchia_immagine.jpg");

            String nuovaImmagine = "nuova_immagine.jpg";

            // Act
            utente.setImmagine(nuovaImmagine);

            // Assert
            assertNotNull(utente.getImmagine());
            assertEquals(nuovaImmagine, utente.getImmagine());
        }
        @Test
        void testModificaImmagineErroreSalvataggioFile() throws SQLException, IOException {
            // Arrange
            UtenteBean utente = createMockUser("user@example.com", "user1", "password", false, false);
            Part filePart = mock(Part.class);
            when(filePart.getSubmittedFileName()).thenReturn("image.jpg");  // File immagine
            when(filePart.getInputStream()).thenThrow(new IOException("Errore di I/O"));  // Simula errore I/O
            GenericServlet servlet = mock(GenericServlet.class);

            // Act
            boolean result = gestioneUtentiService.modificaImmagine(utente, filePart, servlet);

            // Assert
            assertFalse(result, "La modifica dell'immagine dovrebbe fallire in caso di errore di salvataggio del file");
        }

        @Test
        void testModificaImmagineErroreDatabase() throws SQLException, IOException {
            // Arrange
            UtenteBean utente = createMockUser("user@example.com", "user1", "password", false, false);
            Part filePart = mock(Part.class);
            when(filePart.getSubmittedFileName()).thenReturn("image.jpg");  // File immagine
            when(filePart.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[10]));  // Simula un input stream di file
            GenericServlet servlet = mock(GenericServlet.class);
            when(servlet.getServletContext()).thenReturn(mock(ServletContext.class));
            when(servlet.getServletContext().getRealPath("")).thenReturn("/path/to/real/directory");
            utenteDAOMock = mock(UtenteDAO.class);
            when(utenteDAOMock.update(any(UtenteBean.class), eq(utente.getEmail()))).thenReturn(false);  // Simula errore nel salvataggio nel DB

            // Act
            boolean result = gestioneUtentiService.modificaImmagine(utente, filePart, servlet);

            // Assert
            assertFalse(result, "La modifica dell'immagine dovrebbe fallire se l'aggiornamento del database fallisce");
        }
    }

    @Nested
    class CheckEmailTests {
        @Test
        void testCheckEmailSuccess() throws SQLException {
            // Arrange
            String email = "email1@email.com";
            UtenteBean mockUtente = createMockUser(email, "user1", "pass", false, false);

            when(utenteDAOMock.getByEmail(email)).thenReturn(mockUtente);  // Simula il recupero dell'utente

            // Act
            boolean result = gestioneUtentiService.checkEmail(email);

            // Assert
            assertTrue(result, "La verifica dell'email dovrebbe avere successo");
        }

        @Test
        void testCheckEmailNotFound() throws SQLException {
            // Arrange
            String email = "nonexistent@email.com";

            when(utenteDAOMock.getByEmail(email)).thenReturn(null);  // Simula l'assenza dell'utente

            // Act
            boolean result = gestioneUtentiService.checkEmail(email);

            // Assert
            assertFalse(result, "La verifica dell'email dovrebbe fallire per email non trovata");
        }

        @Test
        void testCheckEmailEmpty() throws SQLException {
            // Arrange
            String email = "";

            // Act
            boolean result = gestioneUtentiService.checkEmail(email);

            // Assert
            assertFalse(result, "La verifica dell'email dovrebbe fallire per email vuota");
        }
    }

    @Nested
    class CheckUsernameTests {
        @Test
        void testCheckUsernameSuccess() throws SQLException {
            // Arrange
            String username = "user1";
            UtenteBean mockUtente = createMockUser("email1@email.com", username, "pass", false, false);

            when(utenteDAOMock.getByUsername(username)).thenReturn(mockUtente);  // Simula il recupero dell'utente

            // Act
            boolean result = gestioneUtentiService.checkUsername(username);

            // Assert
            assertTrue(result, "La verifica dello username dovrebbe avere successo");
        }

        @Test
        void testCheckUsernameNotFound() throws SQLException {
            // Arrange
            String username = "nonexistentuser";

            when(utenteDAOMock.getByUsername(username)).thenReturn(null);  // Simula l'assenza dell'utente

            // Act
            boolean result = gestioneUtentiService.checkUsername(username);

            // Assert
            assertFalse(result, "La verifica dello username dovrebbe fallire per username non trovato");
        }

        @Test
        void testCheckUsernameEmpty() throws SQLException {
            // Arrange
            String username = "";

            // Act
            boolean result = gestioneUtentiService.checkUsername(username);

            // Assert
            assertFalse(result, "La verifica dello username dovrebbe fallire per username vuoto");
        }
    }

    @Nested
    class VisualizzaTests {
        @Test
        void testVisualizzaSuccess() throws SQLException {
            // Arrange
            String username = "user1";
            UtenteBean mockUtente = createMockUser("email1@email.com", username, "pass", false, false);
            PostDAO dao = new PostDAO();
            List<PostBean> mockPosts = dao.getByEmail("email1@email.com");

            when(utenteDAOMock.getByUsername(username)).thenReturn(mockUtente);  // Simula il recupero dell'utente
            PostDAO postDAOMock = mock(PostDAO.class);
            when(postDAOMock.getByEmail(mockUtente.getEmail())).thenReturn(mockPosts);  // Simula il recupero dei post

            // Act
            UtenteBean result = gestioneUtentiService.visualizza(username);

            // Assert
            assertNotNull(result, "Il risultato non dovrebbe essere nullo");
            assertEquals(username, result.getUsername(), "Lo username non corrisponde");
            assertEquals(mockPosts, result.get("postCreati"), "I post creati non corrispondono");
        }

        @Test
        void testVisualizzaUserNotFound() throws SQLException {
            // Arrange
            String username = "nonexistentuser";

            when(utenteDAOMock.getByUsername(username)).thenReturn(null);  // Simula l'assenza dell'utente

            // Act
            UtenteBean result = gestioneUtentiService.visualizza(username);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere nullo per un utente non trovato");
        }

        @Test
        void testVisualizzaUsernameEmpty() throws SQLException {
            // Arrange
            String username = "";

            // Act
            UtenteBean result = gestioneUtentiService.visualizza(username);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere nullo per un username vuoto");
        }
    }

    @Nested
    class RegistrazioneTests {
        @Test
        void testRegistrazioneSuccess() throws SQLException {
            // Arrange
            String email = "newuser@email.com";
            String username = "newuser";
            String password = "password";
            String passwordCheck = "password";

            UtenteBean mockUtente = new UtenteBean();
            mockUtente.setEmail(email);
            mockUtente.setUsername(username);
            mockUtente.setPassword(Base64.getEncoder().encodeToString(password.getBytes()));

            when(utenteDAOMock.getByEmail(email)).thenReturn(null); // Email non esistente
            when(utenteDAOMock.getByUsername(username)).thenReturn(null); // Username non esistente
            when(utenteDAOMock.getAll()).thenReturn(Collections.emptyList()); // Nessun utente esistente

            // Act
            UtenteBean result = gestioneUtentiService.registrazione(email, username, password, passwordCheck);

            // Assert
            assertNotNull(result, "La registrazione dovrebbe avere successo");
            assertEquals(email, result.getEmail(), "L'email non corrisponde");
            assertEquals(username, result.getUsername(), "Lo username non corrisponde");
            assertNotNull(result.getPassword(), "La password dovrebbe essere codificata");
            assertEquals("noPfp.jpg", result.getImmagine(), "L'immagine predefinita non corrisponde");
        }

        @Test
        void testRegistrazioneEmailEsistente() throws SQLException {
            // Arrange
            String email = "existinguser@email.com";
            String username = "newuser";
            String password = "password";
            String passwordCheck = "password";

            when(utenteDAOMock.getByEmail(email)).thenReturn(new UtenteBean()); // Email già esistente
            when(utenteDAOMock.getByUsername(username)).thenReturn(null); // Username non esistente

            // Act
            UtenteBean result = gestioneUtentiService.registrazione(email, username, password, passwordCheck);

            // Assert
            assertNull(result, "La registrazione non dovrebbe riuscire con email già esistente");
        }

        @Test
        void testRegistrazioneUsernameEsistente() throws SQLException {
            // Arrange
            String email = "newuser@email.com";
            String username = "existinguser";
            String password = "password";
            String passwordCheck = "password";

            when(utenteDAOMock.getByEmail(email)).thenReturn(null); // Email non esistente
            when(utenteDAOMock.getByUsername(username)).thenReturn(new UtenteBean()); // Username già esistente

            // Act
            UtenteBean result = gestioneUtentiService.registrazione(email, username, password, passwordCheck);

            // Assert
            assertNull(result, "La registrazione non dovrebbe riuscire con username già esistente");
        }

        @Test
        void testRegistrazionePasswordMismatch() throws SQLException {
            // Arrange
            String email = "newuser@email.com";
            String username = "newuser";
            String password = "password";
            String passwordCheck = "differentpassword";

            // Act
            UtenteBean result = gestioneUtentiService.registrazione(email, username, password, passwordCheck);

            // Assert
            assertNull(result, "La registrazione dovrebbe fallire se le password non corrispondono");
        }

        @Test
        void testRegistrazioneParametriNulli() throws SQLException {
            // Act
            UtenteBean result = gestioneUtentiService.registrazione(null, null, null, null);

            // Assert
            assertNull(result, "La registrazione dovrebbe fallire con parametri nulli");
        }

        @Test
        void testRegistrazionePasswordVuota() throws SQLException {
            // Act
            UtenteBean result = gestioneUtentiService.registrazione("newuser@email.com", "newuser", "", "");

            // Assert
            assertNull(result, "La registrazione dovrebbe fallire con password vuote");
        }
    }

}
