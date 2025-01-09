package swagged.model.dao;

import swagged.model.bean.CommunityBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommunityDAO {
    private static final String TABLE_NAME = "community";

    public synchronized boolean save(CommunityBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (nome, descrizione, iscritti, utenteEmail) VALUES (?,?,?,?);";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getNome());
            statement.setString(2, bean.getDescrizione());
            statement.setInt(3, bean.getIscritti());
            statement.setString(4, bean.getUtenteEmail());

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized boolean delete(String nome) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE nome = ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, nome);

            result = statement.executeUpdate();

            con.commit();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return result != 0;
    }

}
