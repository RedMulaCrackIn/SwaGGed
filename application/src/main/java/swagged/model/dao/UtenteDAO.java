package swagged.model.dao;

import swagged.model.bean.UtenteBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}