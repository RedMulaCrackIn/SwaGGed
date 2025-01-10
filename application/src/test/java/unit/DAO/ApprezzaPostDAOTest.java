package unit.DAO;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.ApprezzaPostBean;
import swagged.model.dao.ApprezzaPostDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.*;
import java.util.*;

public class ApprezzaPostDAOTest {

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private Connection connection;

    private ApprezzaPostDAO apprezzaPostDAO;
    private ApprezzaPostBean apprezzaPostBean;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this); // Inizializza i mock

        // Mock del metodo statico DriverManagerConnectionPool.getConnection()
        try (MockedStatic<DriverManagerConnectionPool> mockedStatic = mockStatic(DriverManagerConnectionPool.class)) {
            mockedStatic.when(DriverManagerConnectionPool::getConnection).thenReturn(connection);

            // Crea un'istanza di ApprezzaPostDAO
            apprezzaPostDAO = new ApprezzaPostDAO();
        }

        // Imposta un ApprezzaPostBean di esempio
        apprezzaPostBean = new ApprezzaPostBean();
        apprezzaPostBean.setUtenteEmail("test@example.com");
        apprezzaPostBean.setPostId(1);
    }

    @Test
    public void testSave() throws SQLException {
        try (MockedStatic<DriverManagerConnectionPool> mockedStatic = mockStatic(DriverManagerConnectionPool.class)) {
            // Mocka la connessione e la preparazione della query
            Connection mockConnection = mock(Connection.class);
            mockedStatic.when(DriverManagerConnectionPool::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1); // Simula successo

            // Verifica che il metodo save() funzioni correttamente
            boolean result = apprezzaPostDAO.save(apprezzaPostBean);
            assertTrue(result);
            verify(preparedStatement, times(1)).executeUpdate();
        }
    }

    @Test
    public void testDelete() throws SQLException {
        try (MockedStatic<DriverManagerConnectionPool> mockedStatic = mockStatic(DriverManagerConnectionPool.class)) {
            // Mocka la connessione e la preparazione della query
            Connection mockConnection = mock(Connection.class);
            mockedStatic.when(DriverManagerConnectionPool::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1); // Simula successo

            // Verifica che il metodo delete() funzioni correttamente
            boolean result = apprezzaPostDAO.delete("test@example.com", 1);
            assertTrue(result);
            verify(preparedStatement, times(1)).executeUpdate();
        }
    }



    @Test
    public void testGetByKey_NotFound() throws SQLException {
        // Mock del comportamento del PreparedStatement e ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false); // Simula che non ci siano risultati

        // Verifica che il metodo getByKey restituisca null quando non trova dati
        ApprezzaPostBean result = apprezzaPostDAO.getByKey("test@example.com", 1);
        assertNull(result);
    }
    @Test
    public void testGetByKey_Success() throws SQLException {
        // Mock del PreparedStatement e del ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mock del ResultSet per restituire una riga
        when(resultSet.next()).thenReturn(true).thenReturn(false); // Una riga, poi fine del result set

        // Mock delle colonne nel ResultSet
        when(resultSet.getString("utenteEmail")).thenReturn("test@example.com");
        when(resultSet.getInt("postId")).thenReturn(1);

        // Chiamata al metodo da testare
        ApprezzaPostBean result = apprezzaPostDAO.getByKey("test@example.com", 1);

        // Verifica del risultato
        assertNotNull(result);  // Assicura che il risultato non sia null
        assertEquals("test@example.com", result.getUtenteEmail());
        assertEquals(1, result.getPostId());
    }

    @Test
    public void testGetByEmail() throws SQLException {
        // Mock del PreparedStatement e del ResultSet
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mock del ResultSet per restituire due righe
        when(resultSet.next()).thenReturn(true)  // Prima riga
                .thenReturn(true)  // Seconda riga
                .thenReturn(false); // Fine delle righe

        // Mock delle colonne nel ResultSet per le due righe
        when(resultSet.getString("utenteEmail")).thenReturn("test@example.com")
                .thenReturn("test2@example.com");
        when(resultSet.getInt("postId")).thenReturn(1)
                .thenReturn(2);

        // Chiamata al metodo da testare
        List<ApprezzaPostBean> result = apprezzaPostDAO.getByEmail("test@example.com");

        // Verifica del risultato
        assertNotNull(result);  // Assicura che il risultato non sia null
        assertEquals(2, result.size());  // Assicura che ci siano 2 risultati

        // Verifica del primo post
        ApprezzaPostBean post1 = result.get(0);
        assertEquals("test@example.com", post1.getUtenteEmail());
        assertEquals(1, post1.getPostId());

        // Verifica del secondo post
        ApprezzaPostBean post2 = result.get(1);
        assertEquals("test2@example.com", post2.getUtenteEmail());
        assertEquals(2, post2.getPostId());
    }


}
