package swagged.model.dao;

import swagged.model.bean.UtenteBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {
    private static final String TABLE_NAME = "utente";

    public synchronized boolean save(UtenteBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (email, username, password, immagine, bandito, admin) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getEmail());
            statement.setString(2, bean.getUsername());
            statement.setString(3, bean.getPassword());
            statement.setString(4, "noPfp.jpg");
            statement.setBoolean(5, bean.isBandito());
            statement.setBoolean(6, bean.isAdmin());

            result = statement.executeUpdate();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return result != 0;
    }

    public synchronized boolean delete(String key) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE email = ?";
        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, key);

            result = statement.executeUpdate();
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return result != 0;
    }

    public synchronized boolean update(UtenteBean bean, String email) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET username = ?, password = ?, immagine = ?, bandito = ?, admin = ? WHERE email = ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getUsername());
            statement.setString(2, bean.getPassword());
            statement.setString(3, bean.getImmagine());
            statement.setBoolean(4, bean.isBandito());
            statement.setBoolean(5, bean.isAdmin());
            statement.setString(6, email);

            result = statement.executeUpdate();

            con.commit();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return result != 0;
    }

    public synchronized List<UtenteBean> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        List<UtenteBean> utenti = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UtenteBean utente = new UtenteBean();

                utente.setEmail(result.getString("email"));
                utente.setUsername(result.getString("username"));
                utente.setPassword(result.getString("password"));
                utente.setImmagine(result.getString("immagine"));
                utente.setBandito(result.getBoolean("bandito"));
                utente.setAdmin(result.getBoolean("admin"));

                utenti.add(utente);
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return utenti;
    }

    public synchronized UtenteBean getByEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        UtenteBean utente = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE email = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                utente = new UtenteBean();
                utente.setEmail(result.getString("email"));
                utente.setUsername(result.getString("username"));
                utente.setPassword(result.getString("password"));
                utente.setImmagine(result.getString("immagine"));
                utente.setBandito(result.getBoolean("bandito"));
                utente.setAdmin(result.getBoolean("admin"));
            }
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return utente;
    }

    public synchronized UtenteBean getByUsername(String username) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        UtenteBean utente = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if(result.next()) {
                utente = new UtenteBean();
                utente.setEmail(result.getString("email"));
                utente.setUsername(result.getString("username"));
                utente.setPassword(result.getString("password"));
                utente.setImmagine(result.getString("immagine"));
                utente.setBandito(result.getBoolean("bandito"));
                utente.setAdmin(result.getBoolean("admin"));
            }
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }

        return utente;
    }

    public synchronized List<UtenteBean> getByUsernameSubstring(String substring) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<UtenteBean> utenti = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE username LIKE ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            // Aggiungi i caratteri jolly per la ricerca parziale
            statement.setString(1, "%" + substring + "%");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                UtenteBean utente = new UtenteBean();
                utente.setEmail(result.getString("email"));
                utente.setUsername(result.getString("username"));
                utente.setPassword(result.getString("password"));
                utente.setImmagine(result.getString("immagine"));
                utente.setBandito(result.getBoolean("bandito"));
                utente.setAdmin(result.getBoolean("admin"));
                utenti.add(utente);
            }
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }

        return utenti;
    }
}