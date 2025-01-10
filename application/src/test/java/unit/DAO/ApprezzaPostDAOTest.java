package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.ApprezzaPostBean;
import swagged.model.dao.ApprezzaPostDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApprezzaPostDAOTest {

    @Mock
    private Connection connection;  // Mock della connessione

    @Mock
    private PreparedStatement preparedStatement;  // Mock del PreparedStatement

    @Mock
    private ResultSet resultSet;  // Mock del ResultSet

    @Mock
    private ApprezzaPostBean apprezzaPostBean;  // Mock del ApprezzaPostBean

    private ApprezzaPostDAO apprezzaPostDAO;
    private MockedStatic<DriverManagerConnectionPool> mockedDriverManagerConnectionPool;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);  // Inizializzazione dei mock
        apprezzaPostDAO = new ApprezzaPostDAO();  // Istanza della DAO

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
    void testSave_Success() throws SQLException {
        // Configura il comportamento del mock ApprezzaPostBean
        when(apprezzaPostBean.getUtenteEmail()).thenReturn("user@example.com");
        when(apprezzaPostBean.getPostId()).thenReturn(1);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo dell'inserimento

        // Chiamata al metodo save
        boolean result = apprezzaPostDAO.save(apprezzaPostBean);

        // Verifica che il risultato sia true (operazione riuscita)
        assertTrue(result, "Il salvataggio dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testSave_Failure() throws SQLException {
        // Configura il comportamento del mock ApprezzaPostBean
        when(apprezzaPostBean.getUtenteEmail()).thenReturn("user@example.com");
        when(apprezzaPostBean.getPostId()).thenReturn(1);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);  // Simula fallimento dell'inserimento

        // Chiamata al metodo save
        boolean result = apprezzaPostDAO.save(apprezzaPostBean);

        // Verifica che il risultato sia false (operazione fallita)
        assertFalse(result, "Il salvataggio dovrebbe restituire false quando non vengono inserite righe");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete_Success() throws SQLException {
        // Configura il comportamento dei parametri
        String email = "user@example.com";
        int postId = 1;

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Simula successo della cancellazione

        // Chiamata al metodo delete
        boolean result = apprezzaPostDAO.delete(email, postId);

        // Verifica che il risultato sia true (operazione riuscita)
        assertTrue(result, "La cancellazione dovrebbe restituire true");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testDelete_Failure() throws SQLException {
        // Configura il comportamento dei parametri
        String email = "user@example.com";
        int postId = 1;

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(0);  // Simula fallimento della cancellazione

        // Chiamata al metodo delete
        boolean result = apprezzaPostDAO.delete(email, postId);

        // Verifica che il risultato sia false (operazione fallita)
        assertFalse(result, "La cancellazione dovrebbe restituire false quando non vengono eliminate righe");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByKey_Success() throws SQLException {
        // Configura il comportamento del mock ResultSet
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");
        when(resultSet.getInt("postId")).thenReturn(1);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Chiamata al metodo getByKey
        ApprezzaPostBean result = apprezzaPostDAO.getByKey("user@example.com", 1);

        // Verifica che l'oggetto restituito sia corretto
        assertNotNull(result, "getByKey dovrebbe restituire un oggetto valido");
        assertEquals("user@example.com", result.getUtenteEmail(), "L'email dovrebbe corrispondere");
        assertEquals(1, result.getPostId(), "Il postId dovrebbe corrispondere");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByKey_NotFound() throws SQLException {
        // Configura il comportamento del mock ResultSet
        when(resultSet.next()).thenReturn(false);  // Nessun risultato trovato

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Chiamata al metodo getByKey
        ApprezzaPostBean result = apprezzaPostDAO.getByKey("user@example.com", 1);

        // Verifica che il risultato sia null
        assertNull(result, "getByKey dovrebbe restituire null se non trova il record");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }

    @Test
    void testGetByEmail_Success() throws SQLException {
        // Configura il comportamento del mock ResultSet
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("utenteEmail")).thenReturn("user@example.com");
        when(resultSet.getInt("postId")).thenReturn(1);

        // Mock di PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Chiamata al metodo getByEmail
        List<ApprezzaPostBean> result = apprezzaPostDAO.getByEmail("user@example.com");

        // Verifica che la lista restituita contenga il post
        assertNotNull(result, "getByEmail dovrebbe restituire una lista valida");
        assertEquals(1, result.size(), "La lista dovrebbe contenere 1 elemento");

        // Verifica che i metodi statici siano stati chiamati correttamente
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.getConnection(), times(1));
        mockedDriverManagerConnectionPool.verify(() -> DriverManagerConnectionPool.releaseConnection(connection), times(1));
    }
}
