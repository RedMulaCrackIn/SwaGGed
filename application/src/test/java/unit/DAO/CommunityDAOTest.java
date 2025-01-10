package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.CommunityBean;
import swagged.model.dao.CommunityDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommunityDAOTest {

    @Mock
    private Connection connection;  // Mock della connessione

    @Mock
    private PreparedStatement preparedStatement;  // Mock della PreparedStatement

    @Mock
    private ResultSet resultSet;  // Mock del ResultSet

    @Mock
    private CommunityBean communityBean;  // Mock della CommunityBean

    private CommunityDAO communityDAO;
    private MockedStatic<DriverManagerConnectionPool> mockedDriverManagerConnectionPool;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Inizializzazione dei mock
        communityDAO = new CommunityDAO();  // Istanza della DAO

        // Mock dei metodi statici
        mockedDriverManagerConnectionPool = mockStatic(DriverManagerConnectionPool.class);

        // Mocking del comportamento di getConnection() e releaseConnection()
        when(DriverManagerConnectionPool.getConnection()).thenReturn(connection);
        doNothing().when(DriverManagerConnectionPool.class);
        DriverManagerConnectionPool.releaseConnection(connection);  // Mock per releaseConnection
    }

    @AfterEach
    void tearDown() {
        // Rilascia il mock statico
        mockedDriverManagerConnectionPool.close();
    }

    @Test
    void testSave() throws SQLException {
        // Configura il comportamento del mock CommunityBean
        when(communityBean.getNome()).thenReturn("Community1");
        when(communityBean.getDescrizione()).thenReturn("Descrizione");
        when(communityBean.getIscritti()).thenReturn(100);
        when(communityBean.getUtenteEmail()).thenReturn("user@example.com");

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = communityDAO.save(communityBean);

        assertTrue(result, "Il salvataggio dovrebbe restituire true");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete() throws SQLException {
        String nome = "Community1";

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = communityDAO.delete(nome);

        assertTrue(result, "La cancellazione dovrebbe restituire true");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testUpdate() throws SQLException {
        // Configura il comportamento del mock CommunityBean
        when(communityBean.getDescrizione()).thenReturn("Descrizione aggiornata");
        when(communityBean.getIscritti()).thenReturn(120);
        when(communityBean.getUtenteEmail()).thenReturn("user@example.com");
        when(communityBean.getNome()).thenReturn("Community1");

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = communityDAO.update(communityBean);

        assertTrue(result, "L'aggiornamento dovrebbe restituire true");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByNome() throws SQLException {
        String nome = "Community1";

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("nome")).thenReturn(nome);
        when(resultSet.getString("descrizione")).thenReturn("Descrizione");
        when(resultSet.getInt("iscritti")).thenReturn(100);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");

        CommunityBean community = communityDAO.getByNome(nome);

        assertNotNull(community, "La community dovrebbe essere trovata");
        assertEquals(nome, community.getNome(), "Il nome della community dovrebbe essere corretto");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetAll() throws SQLException {
        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Un elemento nella lista

        when(resultSet.getString("nome")).thenReturn("Community1");
        when(resultSet.getString("descrizione")).thenReturn("Descrizione");
        when(resultSet.getInt("iscritti")).thenReturn(100);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");

        List<CommunityBean> communities = communityDAO.getAll();

        assertNotNull(communities, "La lista di community non dovrebbe essere nulla");
        assertEquals(1, communities.size(), "La lista dovrebbe contenere un elemento");
        // Verifica l'interazione con i metodi statici
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

        when(resultSet.getString("nome")).thenReturn("Community1");
        when(resultSet.getString("descrizione")).thenReturn("Descrizione");
        when(resultSet.getInt("iscritti")).thenReturn(100);
        when(resultSet.getString("utenteEmail")).thenReturn(email);

        List<CommunityBean> communities = communityDAO.getByEmail(email);

        assertNotNull(communities, "La lista di community non dovrebbe essere nulla");
        assertEquals(1, communities.size(), "La lista dovrebbe contenere un elemento");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByNameSubstring() throws SQLException {
        String substring = "Com";

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Un elemento nella lista

        when(resultSet.getString("nome")).thenReturn("Community1");
        when(resultSet.getString("descrizione")).thenReturn("Descrizione");
        when(resultSet.getInt("iscritti")).thenReturn(100);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");

        List<CommunityBean> communities = communityDAO.getByNameSubstring(substring);

        assertNotNull(communities, "La lista di community non dovrebbe essere nulla");
        assertEquals(1, communities.size(), "La lista dovrebbe contenere un elemento");
        // Verifica l'interazione con i metodi statici
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
}
