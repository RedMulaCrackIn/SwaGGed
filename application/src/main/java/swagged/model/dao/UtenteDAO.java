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

}