package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.PostBean;
import swagged.model.dao.PostDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PostDAOTest {

    @Mock
    private Connection connection;  // Mock della connessione

    @Mock
    private PreparedStatement preparedStatement;  // Mock della PreparedStatement

    @Mock
    private ResultSet resultSet;  // Mock del ResultSet

    @Mock
    private PostBean postBean;  // Mock del PostBean

    private PostDAO postDAO;
    private MockedStatic<DriverManagerConnectionPool> mockedDriverManagerConnectionPool;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Inizializzazione dei mock
        postDAO = new PostDAO();  // Istanza della DAO

        // Mock dei metodi statici
        mockedDriverManagerConnectionPool = mockStatic(DriverManagerConnectionPool.class);

        // Mocking del comportamento di getConnection() e releaseConnection()
        when(DriverManagerConnectionPool.getConnection()).thenReturn(connection);
        doNothing().when(DriverManagerConnectionPool.class);
        DriverManagerConnectionPool.releaseConnection(connection);
    }

    @AfterEach
    void tearDown() {
        // Rilascia il mock statico
        mockedDriverManagerConnectionPool.close();
    }

    @Test
    void testSave() throws SQLException {
        // Configura il comportamento del mock PostBean
        when(postBean.getTitolo()).thenReturn("Test Titolo");
        when(postBean.getCorpo()).thenReturn("Test Corpo");
        when(postBean.getImmagine()).thenReturn("Test Immagine");
        when(postBean.getLikes()).thenReturn(10);
        when(postBean.getDataCreazione()).thenReturn(Date.valueOf("2025-01-10"));
        when(postBean.getNumeroCommenti()).thenReturn(0);
        when(postBean.getUtenteEmail()).thenReturn("user@example.com");
        when(postBean.getCommunityNome()).thenReturn("Community1");

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = postDAO.save(postBean);

        assertTrue(result, "Il salvataggio dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete() throws SQLException {
        int postId = 1;

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = postDAO.delete(postId);

        assertTrue(result, "La cancellazione dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testUpdate() throws SQLException {
        // Configura il comportamento del mock PostBean
        when(postBean.getTitolo()).thenReturn("Updated Titolo");
        when(postBean.getCorpo()).thenReturn("Updated Corpo");
        when(postBean.getImmagine()).thenReturn("Updated Immagine");
        when(postBean.getLikes()).thenReturn(20);
        when(postBean.getDataCreazione()).thenReturn(Date.valueOf("2025-01-10"));
        when(postBean.getNumeroCommenti()).thenReturn(5);
        when(postBean.getUtenteEmail()).thenReturn("user@example.com");
        when(postBean.getCommunityNome()).thenReturn("Community1");
        when(postBean.getId()).thenReturn(1);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = postDAO.update(postBean);

        assertTrue(result, "L'aggiornamento dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetById() throws SQLException {
        int postId = 1;

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mock di ResultSet: restituisce true la prima volta, poi false per simulare la fine dei risultati
        when(resultSet.next()).thenReturn(true, false);  // Primo "true" per leggere un record, poi "false" per fermarsi

        // Mock dei metodi di ResultSet per restituire i valori attesi
        when(resultSet.getInt("id")).thenReturn(postId);
        when(resultSet.getString("titolo")).thenReturn("Test Titolo");
        when(resultSet.getString("corpo")).thenReturn("Test Corpo");
        when(resultSet.getString("immagine")).thenReturn("Test Immagine");
        when(resultSet.getInt("likes")).thenReturn(10);
        when(resultSet.getDate("dataCreazione")).thenReturn(Date.valueOf("2025-01-10"));
        when(resultSet.getInt("numeroCommenti")).thenReturn(0);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");
        when(resultSet.getString("communityNome")).thenReturn("Community1");

        // Chiamata al metodo da testare
        PostBean resultPost = postDAO.getById(postId);

        // Verifica che il risultato non sia nullo e che l'ID corrisponda
        assertNotNull(resultPost, "Il post restituito non dovrebbe essere nullo");
        assertEquals(postId, resultPost.getId(), "L'ID del post dovrebbe corrispondere");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetAll() throws SQLException {
        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Un elemento nella lista
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("titolo")).thenReturn("Test Titolo");
        when(resultSet.getString("corpo")).thenReturn("Test Corpo");
        when(resultSet.getString("immagine")).thenReturn("Test Immagine");
        when(resultSet.getInt("likes")).thenReturn(10);
        when(resultSet.getDate("dataCreazione")).thenReturn(Date.valueOf("2025-01-10"));
        when(resultSet.getInt("numeroCommenti")).thenReturn(0);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");
        when(resultSet.getString("communityNome")).thenReturn("Community1");

        List<PostBean> resultList = postDAO.getAll();

        assertNotNull(resultList, "La lista dei post non dovrebbe essere nulla");
        assertEquals(1, resultList.size(), "La lista dovrebbe contenere un elemento");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByEmail() throws SQLException {
        String email = "user@example.com";

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Un elemento nella lista
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("titolo")).thenReturn("Test Titolo");
        when(resultSet.getString("corpo")).thenReturn("Test Corpo");
        when(resultSet.getString("immagine")).thenReturn("Test Immagine");
        when(resultSet.getInt("likes")).thenReturn(10);
        when(resultSet.getDate("dataCreazione")).thenReturn(Date.valueOf("2025-01-10"));
        when(resultSet.getInt("numeroCommenti")).thenReturn(0);
        when(resultSet.getString("utenteEmail")).thenReturn(email);
        when(resultSet.getString("communityNome")).thenReturn("Community1");

        List<PostBean> resultList = postDAO.getByEmail(email);

        assertNotNull(resultList, "La lista dei post non dovrebbe essere nulla");
        assertEquals(1, resultList.size(), "La lista dovrebbe contenere un elemento");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
}
