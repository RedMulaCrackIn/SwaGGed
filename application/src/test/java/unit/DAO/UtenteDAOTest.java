package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.UtenteBean;
import swagged.model.dao.UtenteDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UtenteDAOTest {

    @Mock
    private Connection connection;  // Mock della connessione

    @Mock
    private PreparedStatement preparedStatement;  // Mock della PreparedStatement

    @Mock
    private ResultSet resultSet;  // Mock del ResultSet

    @Mock
    private UtenteBean utenteBean;  // Mock del UtenteBean

    private UtenteDAO utenteDAO;
    private MockedStatic<DriverManagerConnectionPool> mockedDriverManagerConnectionPool;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Inizializzazione dei mock
        utenteDAO = new UtenteDAO();  // Istanza della DAO

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
        // Configura il comportamento del mock UtenteBean
        when(utenteBean.getEmail()).thenReturn("user@example.com");
        when(utenteBean.getUsername()).thenReturn("TestUser");
        when(utenteBean.getPassword()).thenReturn("password123");
        when(utenteBean.getImmagine()).thenReturn("noPfp.jpg");
        when(utenteBean.isBandito()).thenReturn(false);
        when(utenteBean.isAdmin()).thenReturn(true);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = utenteDAO.save(utenteBean);

        assertTrue(result, "Il salvataggio dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete() throws SQLException {
        String email = "user@example.com";

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo

        boolean result = utenteDAO.delete(email);

        assertTrue(result, "La cancellazione dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
    @Test
    void testUpdate() throws SQLException {
        String email = "user@example.com";

        // Configura il comportamento del mock UtenteBean
        when(utenteBean.getUsername()).thenReturn("UpdatedUser");
        when(utenteBean.getPassword()).thenReturn("updatedPassword123");
        when(utenteBean.getImmagine()).thenReturn("updatedPfp.jpg");
        when(utenteBean.isBandito()).thenReturn(true);
        when(utenteBean.isAdmin()).thenReturn(false);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo dell'aggiornamento

        // Chiamata al metodo update
        boolean result = utenteDAO.update(utenteBean, email);

        // Verifica che il risultato sia true (operazione riuscita)
        assertTrue(result, "L'aggiornamento dovrebbe restituire true");

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

        // Mock dei metodi di ResultSet per restituire i valori attesi
        when(resultSet.getString("email")).thenReturn(email);
        when(resultSet.getString("username")).thenReturn("TestUser");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getString("immagine")).thenReturn("noPfp.jpg");
        when(resultSet.getBoolean("bandito")).thenReturn(false);
        when(resultSet.getBoolean("admin")).thenReturn(true);

        UtenteBean resultUser = utenteDAO.getByEmail(email);

        // Verifica che l'utente restituito non sia nullo e che l'email corrisponda
        assertNotNull(resultUser, "L'utente restituito non dovrebbe essere nullo");
        assertEquals(email, resultUser.getEmail(), "L'email dell'utente dovrebbe corrispondere");

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
        when(resultSet.getString("email")).thenReturn("user@example.com");
        when(resultSet.getString("username")).thenReturn("TestUser");
        when(resultSet.getString("password")).thenReturn("password123");
        when(resultSet.getString("immagine")).thenReturn("noPfp.jpg");
        when(resultSet.getBoolean("bandito")).thenReturn(false);
        when(resultSet.getBoolean("admin")).thenReturn(true);

        List<UtenteBean> resultList = utenteDAO.getAll();

        assertNotNull(resultList, "La lista degli utenti non dovrebbe essere nulla");
        assertEquals(1, resultList.size(), "La lista dovrebbe contenere un elemento");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
}
