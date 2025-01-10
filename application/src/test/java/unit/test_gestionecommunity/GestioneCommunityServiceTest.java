package unit.test_gestionecommunity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import swagged.gestionecommunity.services.GestioneCommunityServiceImpl;
import swagged.model.bean.CommunityBean;
import swagged.model.bean.IscrivitiCommunityBean;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.CommunityDAO;
import swagged.model.dao.IscrivitiCommunityDAO;
import swagged.model.dao.PostDAO;
import swagged.model.dao.UtenteDAO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GestioneCommunityServiceTest {
    private GestioneCommunityServiceImpl gestioneCommunityService;
    private CommunityDAO communityDAOMock;
    private IscrivitiCommunityDAO iscrivitiCommunityDAOMock;
    private UtenteDAO utenteDAOMock;
    private PostDAO postDAOMock;

    @BeforeEach
    void setUp() {
        // Creazione dei mock per le dipendenze
        communityDAOMock = mock(CommunityDAO.class);
        iscrivitiCommunityDAOMock = mock(IscrivitiCommunityDAO.class);
        utenteDAOMock = mock(UtenteDAO.class);
        postDAOMock = mock(PostDAO.class);

        // Creazione dell'istanza della classe da testare
        gestioneCommunityService = new GestioneCommunityServiceImpl(communityDAOMock, iscrivitiCommunityDAOMock, utenteDAOMock, postDAOMock);
    }


    private CommunityBean createMockCommunity(String nome, String descrizione, String utenteEmail, int iscritti) {
        CommunityBean community = new CommunityBean();
        community.setNome(nome);
        community.setDescrizione(descrizione);
        community.setIscritti(iscritti);
        community.setUtenteEmail(utenteEmail);
        community.setPost(Collections.emptyList());  // Puoi personalizzare la lista dei post
        return community;
    }

    @Nested
    class CreateCommunityTests {

        @Test
        void testCreateCommunitySuccess() throws SQLException {
            // Arrange
            String nome = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";
            UtenteBean utente = mock(UtenteBean.class);
            when(utente.getEmail()).thenReturn(utenteEmail);

            CommunityBean community = createMockCommunity(nome, descrizione, utenteEmail, 0);

            when(communityDAOMock.save(any(CommunityBean.class))).thenReturn(true);  // Simula successo nella creazione

            // Act
            CommunityBean result = gestioneCommunityService.create(nome, descrizione, utente);

            // Assert
            assertNotNull(result, "La community dovrebbe essere creata correttamente.");
            assertEquals(nome, result.getNome(), "Il nome della community non corrisponde.");
            assertEquals(descrizione, result.getDescrizione(), "La descrizione della community non corrisponde.");
            assertEquals(0, result.getIscritti(), "Il numero di iscritti dovrebbe essere inizialmente 0.");
            verify(communityDAOMock).save(result);  // Verifica che il metodo save sia stato chiamato
        }

        @Test
        void testCreateCommunityWithInvalidNome() throws SQLException {
            // Arrange
            String nome = "";
            String descrizione = "A community for tech enthusiasts";
            UtenteBean utente = mock(UtenteBean.class);

            // Act
            CommunityBean result = gestioneCommunityService.create(nome, descrizione, utente);

            // Assert
            assertNull(result, "La creazione della community dovrebbe fallire se il nome è vuoto.");
        }

        @Test
        void testCreateCommunityWithNullUtente() throws SQLException {
            // Arrange
            String nome = "Tech Community";
            String descrizione = "A community for tech enthusiasts";

            // Act
            CommunityBean result = gestioneCommunityService.create(nome, descrizione, null);

            // Assert
            assertNull(result, "La creazione della community dovrebbe fallire se l'utente è null.");
        }

        @Test
        void testCreateCommunityFailureOnSave() throws SQLException {
            // Arrange
            String nome = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";
            UtenteBean utente = mock(UtenteBean.class);
            when(utente.getEmail()).thenReturn(utenteEmail);

            CommunityBean community = createMockCommunity(nome, descrizione, utenteEmail, 0);

            when(communityDAOMock.save(any(CommunityBean.class))).thenReturn(false);  // Simula fallimento nella creazione

            // Act
            CommunityBean result = gestioneCommunityService.create(nome, descrizione, utente);

            // Assert
            assertNull(result, "La creazione della community dovrebbe fallire se il salvataggio fallisce.");
        }
    }

    @Nested
    class RemoveCommunityTests {

        @Test
        void testRemoveCommunitySuccess() throws SQLException {
            // Arrange
            String nome = "Community1";
            String descrizione = null;
            String utenteEmail = "email1@email.com";
            UtenteBean utente = mock(UtenteBean.class);
            when(utente.getEmail()).thenReturn(utenteEmail);

            // Crea una mock CommunityBean
            CommunityBean community = createMockCommunity(nome, descrizione, utenteEmail, 0);

            // Crea un mock di IscrivitiCommunityBean
            IscrivitiCommunityBean iscrivitiCommunityBean = new IscrivitiCommunityBean();
            iscrivitiCommunityBean.setUtenteEmail(utenteEmail);
            iscrivitiCommunityBean.setCommunityNome(nome);

            // Mock del metodo getByKey per restituire il bean giusto
            when(iscrivitiCommunityDAOMock.getByKey(utenteEmail, nome)).thenReturn(iscrivitiCommunityBean);

            // Simula il successo nella rimozione della community
            when(communityDAOMock.delete(nome)).thenReturn(true);

            // Act
            boolean result = gestioneCommunityService.remove(community, utente);

            // Assert
            assertTrue(result, "La community dovrebbe essere rimossa correttamente.");
            verify(communityDAOMock).delete(nome);  // Verifica che il metodo delete sia stato chiamato
            verify(utente).remove("communityCreate", community);  // Verifica che la community venga rimossa dalla lista di creazione
            verify(utente).remove("communityIscritto", iscrivitiCommunityBean);  // Verifica che l'iscrizione venga rimossa correttamente
        }

        @Test
        void testRemoveCommunityWithNullCommunity() throws SQLException {
            // Arrange
            UtenteBean utente = mock(UtenteBean.class);

            // Act
            boolean result = gestioneCommunityService.remove(null, utente);

            // Assert
            assertFalse(result, "La rimozione dovrebbe fallire se la community è null.");
        }

        @Test
        void testRemoveCommunityWithNullUtente() throws SQLException {
            // Arrange
            CommunityBean community = mock(CommunityBean.class);

            // Act
            boolean result = gestioneCommunityService.remove(community, null);

            // Assert
            assertFalse(result, "La rimozione dovrebbe fallire se l'utente è null.");
        }

        @Test
        void testRemoveCommunityFailureOnDelete() throws SQLException {
            // Arrange
            String nome = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";
            UtenteBean utente = mock(UtenteBean.class);
            when(utente.getEmail()).thenReturn(utenteEmail);

            CommunityBean community = createMockCommunity(nome, descrizione, utenteEmail, 0);

            IscrivitiCommunityBean iscrivitiCommunityBean = new IscrivitiCommunityBean();
            when(iscrivitiCommunityDAOMock.getByKey(utenteEmail, nome)).thenReturn(iscrivitiCommunityBean);
            when(communityDAOMock.delete(nome)).thenReturn(false);  // Simula fallimento nella rimozione

            // Act
            boolean result = gestioneCommunityService.remove(community, utente);

            // Assert
            assertFalse(result, "La rimozione della community dovrebbe fallire se il metodo delete non ha successo.");
        }
    }

    @Nested
    class VisualizzaCommunityTests {

        @Test
        void testVisualizzaCommunitySuccess() throws SQLException {
            String nomeCommunity = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";

            // Crea un mock di CommunityBean
            CommunityBean mockCommunity = createMockCommunity(nomeCommunity, descrizione, utenteEmail, 10);

            // Mock del metodo communityDAO.getByNome
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(mockCommunity);
            when(postDAOMock.getByCommunityNome(nomeCommunity)).thenReturn(Collections.emptyList());

            // Act
            CommunityBean result = gestioneCommunityService.visualizza(nomeCommunity);

            // Assert
            assertNotNull(result);
            assertEquals(nomeCommunity, result.getNome());
            assertEquals(descrizione, result.getDescrizione());
            verify(postDAOMock).getByCommunityNome(nomeCommunity);
        }

        @Test
        void testVisualizzaCommunityNotFound() throws SQLException {
            String nomeCommunity = "NonExistentCommunity";

            // Mock del metodo communityDAO.getByNome per restituire null
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(null);

            // Act
            CommunityBean result = gestioneCommunityService.visualizza(nomeCommunity);

            // Assert
            assertNull(result);
        }
    }

    @Nested
    class IscrizioneTests {

        @Test
        void testIscrizioneCommunitySuccess() throws SQLException {
            String nomeCommunity = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";

            // Crea un mock di UtenteBean
            UtenteBean mockUtente = mock(UtenteBean.class);
            when(mockUtente.getEmail()).thenReturn(utenteEmail);

            // Crea il mock della Community
            CommunityBean mockCommunity = createMockCommunity(nomeCommunity, descrizione, utenteEmail, 10);
            IscrivitiCommunityBean mockIscriviti = new IscrivitiCommunityBean();
            mockIscriviti.setUtenteEmail(utenteEmail);
            mockIscriviti.setCommunityNome(nomeCommunity);

            // Mock dei metodi per simulare l'iscrizione
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(mockCommunity);
            when(iscrivitiCommunityDAOMock.getByKey(utenteEmail, nomeCommunity)).thenReturn(null);
            when(iscrivitiCommunityDAOMock.save(mockIscriviti)).thenReturn(true);
            when(communityDAOMock.update(mockCommunity)).thenReturn(true);

            // Act
            CommunityBean result = gestioneCommunityService.iscrizione(mockUtente, nomeCommunity);

            // Assert
            assertNotNull(result);
            assertEquals(nomeCommunity, result.getNome());
            verify(iscrivitiCommunityDAOMock).save(mockIscriviti);
            verify(mockUtente).add("communityIscritto", mockIscriviti);
            verify(communityDAOMock).update(mockCommunity);
        }

        @Test
        void testIscrizioneAlreadyExists() throws SQLException {
            String nomeCommunity = "Tech Community";
            String descrizione = "A community for tech enthusiasts";
            String utenteEmail = "user@example.com";

            // Crea un mock di UtenteBean
            UtenteBean mockUtente = mock(UtenteBean.class);
            when(mockUtente.getEmail()).thenReturn(utenteEmail);

            // Crea il mock della Community
            CommunityBean mockCommunity = createMockCommunity(nomeCommunity, descrizione, utenteEmail, 10);
            IscrivitiCommunityBean mockIscriviti = new IscrivitiCommunityBean();
            mockIscriviti.setUtenteEmail(utenteEmail);
            mockIscriviti.setCommunityNome(nomeCommunity);

            // Mock dei metodi per simulare l'iscrizione esistente
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(mockCommunity);
            when(iscrivitiCommunityDAOMock.getByKey(utenteEmail, nomeCommunity)).thenReturn(mockIscriviti);
            when(iscrivitiCommunityDAOMock.delete(utenteEmail, nomeCommunity)).thenReturn(true);
            when(communityDAOMock.update(mockCommunity)).thenReturn(true);

            // Act
            CommunityBean result = gestioneCommunityService.iscrizione(mockUtente, nomeCommunity);

            // Assert
            assertNotNull(result);
            assertEquals(nomeCommunity, result.getNome());
            verify(iscrivitiCommunityDAOMock).delete(utenteEmail, nomeCommunity);
            verify(mockUtente).remove("communityIscritto", mockIscriviti);
            verify(communityDAOMock).update(mockCommunity);
        }

        @Test
        void testIscrizioneCommunityNotFound() throws SQLException {
            String nomeCommunity = "NonExistentCommunity";
            String utenteEmail = "user@example.com";

            // Crea un mock di UtenteBean
            UtenteBean mockUtente = mock(UtenteBean.class);
            when(mockUtente.getEmail()).thenReturn(utenteEmail);

            // Mock del metodo communityDAO.getByNome per restituire null
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(null);

            // Act
            CommunityBean result = gestioneCommunityService.iscrizione(mockUtente, nomeCommunity);

            // Assert
            assertNull(result);
        }
    }

    @Nested
    class CercaCommunityTests {

        @Test
        void testCercaCommunitySuccess() throws SQLException {
            String substring = "Tech";
            CommunityBean mockCommunity = createMockCommunity("Tech Community", "A community for tech enthusiasts", "user@example.com", 10);

            // Mock del metodo communityDAO.getByNameSubstring
            when(communityDAOMock.getByNameSubstring(substring)).thenReturn(Collections.singletonList(mockCommunity));

            // Act
            List<CommunityBean> result = gestioneCommunityService.cerca(substring);

            // Assert
            assertNotNull(result);
            assertFalse(result.isEmpty());
            assertEquals(1, result.size());
            assertEquals("Tech Community", result.get(0).getNome());
            verify(communityDAOMock).getByNameSubstring(substring);
        }

        @Test
        void testCercaCommunityEmptySubstring() throws SQLException {
            // Act
            List<CommunityBean> result = gestioneCommunityService.cerca("");

            // Assert
            assertNull(result);
        }

        @Test
        void testCercaCommunityNotFound() throws SQLException {
            String substring = "NonExistent";

            // Mock del metodo communityDAO.getByNameSubstring per restituire una lista vuota
            when(communityDAOMock.getByNameSubstring(substring)).thenReturn(Collections.emptyList());

            // Act
            List<CommunityBean> result = gestioneCommunityService.cerca(substring);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    class CheckNomeCommunityTests {

        @Test
        void testCheckNomeCommunityExists() throws SQLException {
            String nomeCommunity = "Tech Community";
            CommunityBean mockCommunity = createMockCommunity(nomeCommunity, "A community for tech enthusiasts", "user@example.com", 10);

            // Mock del metodo communityDAO.getByNome per restituire una community
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(mockCommunity);

            // Act
            boolean result = gestioneCommunityService.checkNome(nomeCommunity);

            // Assert
            assertTrue(result);
            verify(communityDAOMock).getByNome(nomeCommunity);
        }

        @Test
        void testCheckNomeCommunityNotFound() throws SQLException {
            String nomeCommunity = "NonExistentCommunity";

            // Mock del metodo communityDAO.getByNome per restituire null
            when(communityDAOMock.getByNome(nomeCommunity)).thenReturn(null);

            // Act
            boolean result = gestioneCommunityService.checkNome(nomeCommunity);

            // Assert
            assertFalse(result);
            verify(communityDAOMock).getByNome(nomeCommunity);
        }

        @Test
        void testCheckNomeCommunityEmptyName() throws SQLException {
            // Act
            boolean result = gestioneCommunityService.checkNome("");

            // Assert
            assertFalse(result);
        }
    }
}
