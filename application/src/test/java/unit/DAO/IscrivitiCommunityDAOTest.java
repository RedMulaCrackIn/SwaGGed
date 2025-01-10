package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.IscrivitiCommunityBean;
import swagged.model.dao.IscrivitiCommunityDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class IscrivitiCommunityDAOTest {

    @Mock
    private Connection connection;  // Mock della connessione

    @Mock
    private PreparedStatement preparedStatement;  // Mock della PreparedStatement

    @Mock
    private ResultSet resultSet;  // Mock del ResultSet

    @Mock
    private IscrivitiCommunityBean iscrivitiCommunityBean;  // Mock della IscrivitiCommunityBean

    private IscrivitiCommunityDAO iscrivitiCommunityDAO;
    private MockedStatic<DriverManagerConnectionPool> mockedDriverManagerConnectionPool;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Inizializzazione dei mock
        iscrivitiCommunityDAO = new IscrivitiCommunityDAO();  // Istanza della DAO

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
        // Configura il comportamento del mock IscrivitiCommunityBean
        when(iscrivitiCommunityBean.getUtenteEmail()).thenReturn("user@example.com");
        when(iscrivitiCommunityBean.getCommunityNome()).thenReturn("Community1");

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = iscrivitiCommunityDAO.save(iscrivitiCommunityBean);

        assertTrue(result, "Il salvataggio dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete() throws SQLException {
        String utenteEmail = "user@example.com";
        String communityNome = "Community1";

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = iscrivitiCommunityDAO.delete(utenteEmail, communityNome);

        assertTrue(result, "La cancellazione dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByKey() throws SQLException {
        String utenteEmail = "user@example.com";
        String communityNome = "Community1";

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("utenteEmail")).thenReturn(utenteEmail);
        when(resultSet.getString("communityNome")).thenReturn(communityNome);

        IscrivitiCommunityBean resultBean = iscrivitiCommunityDAO.getByKey(utenteEmail, communityNome);

        assertNotNull(resultBean, "Il bean restituito non dovrebbe essere nullo");
        assertEquals(utenteEmail, resultBean.getUtenteEmail(), "L'email utente dovrebbe corrispondere");
        assertEquals(communityNome, resultBean.getCommunityNome(), "Il nome della community dovrebbe corrispondere");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByEmail() throws SQLException {
        String utenteEmail = "user@example.com";

        // Mock di PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Un elemento nella lista
        when(resultSet.getString("utenteEmail")).thenReturn(utenteEmail);
        when(resultSet.getString("communityNome")).thenReturn("Community1");

        List<IscrivitiCommunityBean> resultList = iscrivitiCommunityDAO.getByEmail(utenteEmail);

        assertNotNull(resultList, "La lista di community non dovrebbe essere nulla");
        assertEquals(1, resultList.size(), "La lista dovrebbe contenere un elemento");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
}
