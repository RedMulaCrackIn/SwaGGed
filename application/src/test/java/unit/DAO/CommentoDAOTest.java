package unit.DAO;

import org.junit.jupiter.api.*;
import org.mockito.*;
import swagged.model.bean.CommentoBean;
import swagged.model.dao.CommentoDAO;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentoDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private CommentoDAO commentoDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        commentoDAO = new CommentoDAO();

        // Mock DriverManagerConnectionPool to return mockConnection
        mockStatic(DriverManagerConnectionPool.class);
        when(DriverManagerConnectionPool.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void testSave() throws SQLException {
        CommentoBean commento = new CommentoBean();
        commento.setCorpo("Test commento");
        commento.setUtenteEmail("testuser@email.com");
        commento.setPostId(1);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean result = commentoDAO.save(commento);

        assertTrue(result, "Save should return true when the insert is successful");
        verify(mockStatement, times(1)).setString(1, "Test commento");
        verify(mockStatement, times(1)).setString(2, "testuser@email.com");
        verify(mockStatement, times(1)).setInt(3, 1);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean result = commentoDAO.delete(1);

        assertTrue(result, "Delete should return true when the delete is successful");
        verify(mockStatement, times(1)).setInt(1, 1);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void testUpdate() throws SQLException {
        CommentoBean commento = new CommentoBean();
        commento.setId(1);
        commento.setCorpo("Updated comment");
        commento.setUtenteEmail("testuser@email.com");
        commento.setPostId(1);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean result = commentoDAO.update(commento);

        assertTrue(result, "Update should return true when the update is successful");
        verify(mockStatement, times(1)).setString(1, "Updated comment");
        verify(mockStatement, times(1)).setString(2, "testuser@email.com");
        verify(mockStatement, times(1)).setInt(3, 1);
        verify(mockStatement, times(1)).setInt(4, 1);
        verify(mockStatement, times(1)).executeUpdate();
    }

    @Test
    void testGetById() throws SQLException {
        // Mocking the connection and statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Simuliamo il comportamento del ResultSet
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);  // La prima chiamata restituisce true, la seconda false
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("corpo")).thenReturn("Test comment");
        when(mockResultSet.getString("utenteEmail")).thenReturn("testuser@email.com");
        when(mockResultSet.getInt("postId")).thenReturn(1);

        // Chiamata al metodo da testare
        CommentoBean result = commentoDAO.getById(1);

        // Verifica del risultato
        assertNotNull(result, "getById should return a CommentoBean");
        assertEquals(1, result.getId());
        assertEquals("Test comment", result.getCorpo());
        assertEquals("testuser@email.com", result.getUtenteEmail());
        assertEquals(1, result.getPostId());
    }

    @Test
    void testGetAll() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false); // Mock 2 results
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("corpo")).thenReturn("Comment 1", "Comment 2");
        when(mockResultSet.getString("utenteEmail")).thenReturn("user1@email.com", "user2@email.com");
        when(mockResultSet.getInt("postId")).thenReturn(1, 2);

        List<CommentoBean> result = commentoDAO.getAll();

        assertNotNull(result, "getAll should return a non-null list");
        assertEquals(2, result.size(), "getAll should return 2 comments");
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @AfterEach
    void tearDown() {
        Mockito.clearAllCaches();
    }
}
