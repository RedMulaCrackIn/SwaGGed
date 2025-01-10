package unit.test_gestionecommenti;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swagged.gestionecommenti.services.GestioneCommentiService;
import swagged.gestionecommenti.services.GestioneCommentiServiceImpl;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommentoBean;
import swagged.model.bean.PostBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class GestioneCommentiServiceTest {
    private GestioneCommentiServiceImpl gestioneCommentiService;
    private CommentoDAO commentoDAOMock;
    private UtenteDAO utenteDAOMock;
    private PostDAO postDAOMock;

    @BeforeEach
    void setUp() {
        // Creazione dei mock per le dipendenze
        commentoDAOMock = mock(CommentoDAO.class);
        utenteDAOMock = mock(UtenteDAO.class);
        postDAOMock = mock(PostDAO.class);

        // Creazione dell'istanza della classe da testare
        gestioneCommentiService = new GestioneCommentiServiceImpl(commentoDAOMock, utenteDAOMock, postDAOMock);
    }

    private CommentoBean createMockCommento(int id, String corpo, String utenteEmail, int postId) {
        CommentoBean commento = new CommentoBean();
        commento.setId(id);  // Imposta l'ID del commento
        commento.setCorpo(corpo);  // Imposta il corpo del commento
        commento.setUtenteEmail(utenteEmail);  // Imposta l'email dell'utente che ha scritto il commento
        commento.setPostId(postId);  // Imposta l'ID del post a cui il commento è associato
        return commento;
    }

    @Nested
    class CreateCommentTests {
        @Test
        void testCreateCommentoSuccess() throws SQLException {
            int postId = 1;
            String corpo = "This is a comment";
            String utenteEmail = "user@example.com";

            UtenteBean mockUtente = new UtenteBean();
            mockUtente.setEmail(utenteEmail);
            when(utenteDAOMock.getByEmail(utenteEmail)).thenReturn(mockUtente);

            PostBean mockPost = new PostBean();
            mockPost.setId(postId);
            when(postDAOMock.getById(postId)).thenReturn(mockPost);

            // Mocka il salvataggio del commento
            when(commentoDAOMock.save(any(CommentoBean.class))).thenReturn(true);

            // Testa la creazione del commento
            CommentoBean createdCommento = gestioneCommentiService.create(postId, corpo, utenteEmail);
            assertNotNull(createdCommento);
            assertEquals(corpo, createdCommento.getCorpo());
            assertEquals(utenteEmail, createdCommento.getUtenteEmail());
        }

        @Test
        void testCreateCommentoFailDueToInvalidPostId() throws SQLException {
            int postId = 999;
            String corpo = "This is a comment";
            String utenteEmail = "user@example.com";

            when(postDAOMock.getById(postId)).thenReturn(null);

            // Testa il fallimento se il post non esiste
            CommentoBean createdCommento = gestioneCommentiService.create(postId, corpo, utenteEmail);
            assertNull(createdCommento);
        }

        @Test
        void testCreateCommentoFailDueToInvalidUtenteEmail() throws SQLException {
            int postId = 1;
            String corpo = "This is a comment";
            String utenteEmail = "invalid@example.com";

            when(utenteDAOMock.getByEmail(utenteEmail)).thenReturn(null);

            // Testa il fallimento se l'utente non esiste
            CommentoBean createdCommento = gestioneCommentiService.create(postId, corpo, utenteEmail);
            assertNull(createdCommento);
        }
    }

    @Nested
    class RemoveCommentTests {
        @Test
        void testRemoveCommentoSuccess() throws SQLException {
            int commentoId = 1;
            int postId = 1;
            UtenteBean mockUtente = new UtenteBean();
            mockUtente.setEmail("user@example.com");

            CommentoBean mockCommento = createMockCommento(commentoId, "This is a comment", "user@example.com", postId);
            PostBean mockPost = new PostBean();
            mockPost.setId(postId);

            when(commentoDAOMock.getById(commentoId)).thenReturn(mockCommento);
            when(postDAOMock.getById(postId)).thenReturn(mockPost);

            // Mocka il metodo delete del DAO
            when(commentoDAOMock.delete(commentoId)).thenReturn(true);

            // Testa la rimozione del commento
            boolean result = gestioneCommentiService.remove(commentoId, postId, mockUtente);
            assertTrue(result);
        }

        @Test
        void testRemoveCommentoFailDueToNonExistingCommento() throws SQLException {
            int commentoId = 1;
            int postId = 1;
            UtenteBean mockUtente = new UtenteBean();
            mockUtente.setEmail("user@example.com");

            when(commentoDAOMock.getById(commentoId)).thenReturn(null);

            // Testa il fallimento se il commento non esiste
            boolean result = gestioneCommentiService.remove(commentoId, postId, mockUtente);
            assertFalse(result);
        }
    }

    @Nested
    class VisualizzaCommentTests {
        @Test
        void testVisualizzaCommentiSuccess() throws SQLException {
            int postId = 1;
            CommentoBean mockCommento1 = createMockCommento(1, "First comment", "user@example.com", postId);
            CommentoBean mockCommento2 = createMockCommento(2, "Second comment", "user@example.com", postId);

            when(postDAOMock.getById(postId)).thenReturn(new PostBean());
            when(commentoDAOMock.getByPostId(postId)).thenReturn(Arrays.asList(mockCommento1, mockCommento2));

            // Testa il recupero dei commenti associati a un post
            List<CommentoBean> commenti = gestioneCommentiService.visualizza(postId);
            assertNotNull(commenti);
            assertEquals(2, commenti.size());
            assertTrue(commenti.contains(mockCommento1));
            assertTrue(commenti.contains(mockCommento2));
        }

        @Test
        void testVisualizzaCommentiFailDueToNonExistingPost() throws SQLException {
            int postId = 999;

            when(postDAOMock.getById(postId)).thenReturn(null);

            // Testa il fallimento se il post non esiste
            List<CommentoBean> commenti = gestioneCommentiService.visualizza(postId);
            assertNull(commenti);
        }
    }
}