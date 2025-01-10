package unit.test_gestionepost;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swagged.gestionepost.services.GestionePostServiceImpl;
import swagged.model.bean.*;
import swagged.model.dao.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestionePostServiceTest {

    private GestionePostServiceImpl gestionePostService;
    private PostDAO postDAOMock;
    private UtenteDAO utenteDAOMock;
    private CommunityDAO communityDAOMock;
    private ApprezzaPostDAO apprezzaPostDAOMock;
    private CommentoDAO commentoDAOMock;

    @BeforeEach
    void setUp() {
        postDAOMock = mock(PostDAO.class);
        utenteDAOMock = mock(UtenteDAO.class);
        communityDAOMock = mock(CommunityDAO.class);
        apprezzaPostDAOMock = mock(ApprezzaPostDAO.class);
        commentoDAOMock = mock(CommentoDAO.class);
        gestionePostService = new GestionePostServiceImpl(postDAOMock, utenteDAOMock, communityDAOMock, apprezzaPostDAOMock, commentoDAOMock);
    }

    private PostBean createMockPost(String titolo, String corpo, String utenteEmail, String communityNome) {
        PostBean post = new PostBean();
        post.setId(1);
        post.setTitolo(titolo);
        post.setCorpo(corpo);
        post.setImmagine("noImage.jpg");
        post.setLikes(0);
        post.setDataCreazione(new Date(System.currentTimeMillis()));
        post.setNumeroCommenti(0);
        post.setUtenteEmail(utenteEmail);
        post.setCommunityNome(communityNome);
        post.setCommenti(Collections.emptyList());
        return post;
    }

    @Nested
    class CreatePostTests {

        @Test
        void testCreatePostSuccess() throws SQLException, IOException {
            // Arrange
            String titolo = "Post Title";
            String corpo = "This is the body of the post.";
            String communityNome = "Community1";
            UtenteBean utente = new UtenteBean();
            utente.setEmail("email1@email.com");

            CommunityBean community = new CommunityBean();
            community.setNome(communityNome);

            Part filePart = mock(Part.class);
            when(filePart.getSubmittedFileName()).thenReturn("image.jpg");
            when(filePart.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[10]));  // Simula un input stream di file

            when(communityDAOMock.getByNome(communityNome)).thenReturn(community);
            when(postDAOMock.save(any(PostBean.class))).thenReturn(true);

            // Act
            PostBean result = gestionePostService.create(titolo, corpo, null, utente, communityNome, mock(GenericServlet.class));

            // Assert
            assertNotNull(result, "Il post dovrebbe essere creato con successo.");
            assertEquals(titolo, result.getTitolo(), "Il titolo del post non corrisponde.");
            assertEquals(corpo, result.getCorpo(), "Il corpo del post non corrisponde.");
            assertEquals(communityNome, result.getCommunityNome(), "Il nome della community non corrisponde.");
        }

        @Test
        void testCreatePostWithInvalidCommunity() throws SQLException {
            // Arrange
            String titolo = "Post Title";
            String corpo = "This is the body of the post.";
            String communityNome = "Nonexistent Community";
            UtenteBean utente = new UtenteBean();
            utente.setEmail("user@email.com");

            when(communityDAOMock.getByNome(communityNome)).thenReturn(null);

            // Act
            PostBean result = gestionePostService.create(titolo, corpo, null, utente, communityNome, mock(GenericServlet.class));

            // Assert
            assertNull(result, "La creazione del post dovrebbe fallire se la community non esiste.");
        }

        @Test
        void testCreatePostWithEmptyTitle() throws SQLException {
            // Arrange
            String titolo = "";
            String corpo = "This is the body of the post.";
            String communityNome = "Tech Community";
            UtenteBean utente = new UtenteBean();
            utente.setEmail("user@email.com");

            CommunityBean community = new CommunityBean();
            community.setNome(communityNome);

            when(communityDAOMock.getByNome(communityNome)).thenReturn(community);

            // Act
            PostBean result = gestionePostService.create(titolo, corpo, null, utente, communityNome, mock(GenericServlet.class));

            // Assert
            assertNull(result, "La creazione del post dovrebbe fallire se il titolo è vuoto.");
        }

        @Test
        void testCreatePostWithInvalidFile() throws SQLException, IOException {
            // Arrange
            String titolo = "Post Title";
            String corpo = "This is the body of the post.";
            String communityNome = "Tech Community";
            UtenteBean utente = new UtenteBean();
            utente.setEmail("user@email.com");

            Part filePart = mock(Part.class);
            when(filePart.getSubmittedFileName()).thenReturn("invalidfile.txt");
            when(filePart.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[10]));  // Simula un input stream di file

            CommunityBean community = new CommunityBean();
            community.setNome(communityNome);

            when(communityDAOMock.getByNome(communityNome)).thenReturn(community);

            // Act
            PostBean result = gestionePostService.create(titolo, corpo, filePart, utente, communityNome, mock(GenericServlet.class));

            // Assert
            assertNull(result, "La creazione del post dovrebbe fallire se il file non è un'immagine valida.");
        }
    }

    @Nested
    class RemovePostTests {

        @Test
        void testRemovePostSuccess() throws SQLException {
            // Arrange
            int postId = 2;

            // Mock dell'utente
            UtenteBean utente = mock(UtenteBean.class);
            utente.setEmail("email1@email.com");

            PostBean post = new PostBean();
            post.setId(postId);
            post.setTitolo("Post1");
            post.setCorpo("Corpo");
            post.setImmagine(null);
            post.setLikes(4);
            post.setDataCreazione(new Date(System.currentTimeMillis()));
            post.setNumeroCommenti(3);
            post.setUtenteEmail(utente.getEmail());
            post.setCommunityNome("Community1");

            CommunityBean community = new CommunityBean();
            community.setNome("Community1");

            when(postDAOMock.getById(postId)).thenReturn(post);
            when(communityDAOMock.getByNome("Community1")).thenReturn(community);
            when(postDAOMock.delete(postId)).thenReturn(true);

            // Act
            boolean result = gestionePostService.remove(postId, utente);

            // Assert
            assertTrue(result, "Il post dovrebbe essere rimosso con successo.");
            verify(postDAOMock).getById(postId);  // Verifica che getById sia chiamato una volta
            verify(communityDAOMock).getByNome("Community1");  // Verifica che getByNome sia chiamato una volta
            verify(postDAOMock).delete(postId);  // Verifica che delete sia chiamato una volta
            verify(utente).remove("postCreati", post);  // Verifica che il post venga rimosso dalla lista "postCreati" dell'utente
        }

        @Test
        void testRemovePostWithNonExistingPost() throws SQLException {
            // Arrange
            int postId = 1;
            UtenteBean utente = new UtenteBean();
            utente.setEmail("user@email.com");

            when(postDAOMock.getById(postId)).thenReturn(null);

            // Act
            boolean result = gestionePostService.remove(postId, utente);

            // Assert
            assertFalse(result, "La rimozione del post dovrebbe fallire se il post non esiste.");
        }

        @Test
        void testRemovePostFailureOnDelete() throws SQLException {
            // Arrange
            int postId = 1;
            UtenteBean utente = new UtenteBean();
            utente.setEmail("user@email.com");

            PostBean post = new PostBean();
            post.setId(postId);
            post.setTitolo("Post Title");
            post.setCorpo("This is the body of the post.");
            post.setImmagine("noImage.jpg");
            post.setLikes(0);
            post.setDataCreazione(new Date(System.currentTimeMillis()));
            post.setNumeroCommenti(0);
            post.setUtenteEmail(utente.getEmail());
            post.setCommunityNome("Tech Community");

            CommunityBean community = new CommunityBean();
            community.setNome("Tech Community");

            when(postDAOMock.getById(postId)).thenReturn(post);
            when(communityDAOMock.getByNome("Tech Community")).thenReturn(community);
            when(postDAOMock.delete(postId)).thenReturn(false); // Simuliamo un fallimento nella cancellazione

            // Act
            boolean result = gestionePostService.remove(postId, utente);

            // Assert
            assertFalse(result, "La rimozione del post dovrebbe fallire se il delete non ha successo.");
            verify(postDAOMock).delete(postId);
        }
    }

    @Nested
    class CercaPostTests {

        @Test
        void testCercaEmptySubstring() throws SQLException {
            // Arrange
            String substring = "";

            // Act
            List<PostBean> result = gestionePostService.cerca(substring);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere null quando la sottostringa è vuota.");
        }

        @Test
        void testCercaPostWithValidSubstring() throws SQLException {
            // Arrange
            String substring = "Post";
            PostBean post1 = createMockPost("Post1", "Corpo1", "email1@email.com", "Community1");
            PostBean post2 = createMockPost("Post2", "Corpo2", "email2@email.com", "Community2");

            // Simula il comportamento del DAO
            when(postDAOMock.getByTitleSubstring(substring)).thenReturn(List.of(post1, post2));

            // Act
            List<PostBean> result = gestionePostService.cerca(substring);

            // Assert
            assertNotNull(result, "Il risultato non dovrebbe essere null.");
            assertEquals(2, result.size(), "Dovrebbero essere restituiti 2 post.");
            assertTrue(result.contains(post1), "Il primo post dovrebbe essere presente.");
            assertTrue(result.contains(post2), "Il secondo post dovrebbe essere presente.");
        }

        @Test
        void testCercaPostWithNoMatchingSubstring() throws SQLException {
            // Arrange
            String substring = "NonEsiste";
            List<PostBean> posts = Collections.emptyList();

            // Simula il comportamento del DAO
            when(postDAOMock.getByTitleSubstring(substring)).thenReturn(posts);

            // Act
            List<PostBean> result = gestionePostService.cerca(substring);

            // Assert
            assertNotNull(result, "Il risultato non dovrebbe essere null, ma una lista vuota.");
            assertTrue(result.isEmpty(), "La lista dovrebbe essere vuota.");
        }
    }

    @Nested
    class LikeTests {

        @Test
        void testLikePostSuccess() throws SQLException {
            // Arrange
            UtenteBean utente = new UtenteBean();
            utente.setEmail("utente@email.com");

            int postId = 1;
            PostBean post = createMockPost("Post Titolo", "Corpo del post", "utente@email.com", "Community1");

            // Simula il comportamento del DAO
            when(postDAOMock.getById(postId)).thenReturn(post);
            when(apprezzaPostDAOMock.getByKey(utente.getEmail(), postId)).thenReturn(null); // Non ha ancora messo like

            ApprezzaPostBean apprezzaPostBean = new ApprezzaPostBean();
            apprezzaPostBean.setUtenteEmail(utente.getEmail());
            apprezzaPostBean.setPostId(postId);

            // Simula il salvataggio del like
            when(apprezzaPostDAOMock.save(any(ApprezzaPostBean.class))).thenReturn(true);
            when(postDAOMock.update(any(PostBean.class))).thenReturn(true);

            // Act
            PostBean result = gestionePostService.like(utente, postId);

            // Assert
            assertNotNull(result, "Il post dovrebbe essere aggiornato.");
            assertEquals(1, result.getLikes(), "Il numero di like dovrebbe essere 1.");
            verify(apprezzaPostDAOMock).save(any(ApprezzaPostBean.class));  // Verifica che il like sia stato salvato
            verify(postDAOMock).update(post);  // Verifica che il post sia stato aggiornato
        }

        @Test
        void testDislikePostSuccess() throws SQLException {
            // Arrange
            UtenteBean utente = new UtenteBean();
            utente.setEmail("utente@email.com");

            int postId = 1;
            PostBean post = createMockPost("Post Titolo", "Corpo del post", "utente@email.com", "Community1");

            // Simula il comportamento del DAO
            when(postDAOMock.getById(postId)).thenReturn(post);
            ApprezzaPostBean apprezzaPostBean = new ApprezzaPostBean();
            apprezzaPostBean.setUtenteEmail(utente.getEmail());
            apprezzaPostBean.setPostId(postId);

            post.setLikes(1);

            when(apprezzaPostDAOMock.getByKey(utente.getEmail(), postId)).thenReturn(apprezzaPostBean); // Utente ha già messo like

            // Simula il comportamento di rimozione del like
            when(apprezzaPostDAOMock.delete(utente.getEmail(), postId)).thenReturn(true);
            when(postDAOMock.update(any(PostBean.class))).thenReturn(true);

            // Act
            PostBean result = gestionePostService.like(utente, postId);

            // Assert
            assertNotNull(result, "Il post dovrebbe essere aggiornato.");
            assertEquals(0, result.getLikes(), "Il numero di like dovrebbe essere 0.");
            verify(apprezzaPostDAOMock).delete(utente.getEmail(), postId);  // Verifica che il like sia stato rimosso
            verify(postDAOMock).update(post);  // Verifica che il post sia stato aggiornato
        }

        @Test
        void testLikePostWithInvalidUser() throws SQLException {
            // Arrange
            UtenteBean utente = null;  // Utente null
            int postId = 1;

            // Act
            PostBean result = gestionePostService.like(utente, postId);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere null se l'utente è null.");
        }

        @Test
        void testLikePostWithNonExistingPost() throws SQLException {
            // Arrange
            UtenteBean utente = new UtenteBean();
            utente.setEmail("utente@email.com");

            int postId = 999;  // Post che non esiste

            when(postDAOMock.getById(postId)).thenReturn(null);  // Il post non esiste

            // Act
            PostBean result = gestionePostService.like(utente, postId);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere null se il post non esiste.");
        }

        @Test
        void testLikePostFailureOnUpdate() throws SQLException {
            // Arrange
            UtenteBean utente = new UtenteBean();
            utente.setEmail("utente@email.com");

            int postId = 1;
            PostBean post = createMockPost("Post Titolo", "Corpo del post", "utente@email.com", "Community1");

            // Simula il comportamento del DAO
            when(postDAOMock.getById(postId)).thenReturn(post);
            when(apprezzaPostDAOMock.getByKey(utente.getEmail(), postId)).thenReturn(null); // Non ha ancora messo like

            ApprezzaPostBean apprezzaPostBean = new ApprezzaPostBean();
            apprezzaPostBean.setUtenteEmail(utente.getEmail());
            apprezzaPostBean.setPostId(postId);

            when(apprezzaPostDAOMock.save(any(ApprezzaPostBean.class))).thenReturn(true);
            when(postDAOMock.update(any(PostBean.class))).thenReturn(false);  // Simuliamo un fallimento nell'aggiornamento

            // Act
            PostBean result = gestionePostService.like(utente, postId);

            // Assert
            assertNull(result, "Il risultato dovrebbe essere null se l'aggiornamento del post fallisce.");
        }
    }

    @Nested
    class VisualizzaTests {

        @Test
        void testVisualizzaPostSuccess() throws SQLException {
            // Arrange
            int postId = 1;
            PostBean post = createMockPost("Post Titolo", "Corpo del post", "utente@email.com", "Community1");

            // Simula la presenza dei commenti
            List<CommentoBean> commenti = Collections.singletonList(new CommentoBean());
            when(postDAOMock.getById(postId)).thenReturn(post);
            when(commentoDAOMock.getByPostId(postId)).thenReturn(commenti);

            // Act
            PostBean result = gestionePostService.visualizza(postId);

            // Assert
            assertNotNull(result, "Il post dovrebbe essere restituito.");
            assertEquals(postId, result.getId(), "L'ID del post dovrebbe corrispondere.");
            assertEquals(commenti, result.getCommenti(), "I commenti del post dovrebbero essere correttamente associati.");
            verify(postDAOMock).getById(postId);  // Verifica che il post sia recuperato dal DAO
            verify(commentoDAOMock).getByPostId(postId);  // Verifica che i commenti siano recuperati dal DAO
        }

        @Test
        void testVisualizzaPostNonEsistente() throws SQLException {
            // Arrange
            int postId = 999;  // Un post che non esiste

            when(postDAOMock.getById(postId)).thenReturn(null);  // Il post non esiste

            // Act
            PostBean result = gestionePostService.visualizza(postId);

            // Assert
            assertNull(result, "Se il post non esiste, il risultato dovrebbe essere null.");
            verify(postDAOMock).getById(postId);  // Verifica che il post sia cercato nel DAO
        }
    }
}
