package swagged.model.dao;

import swagged.model.bean.CommunityBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public synchronized boolean update(CommunityBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET descrizione = ?, iscritti = ?, utenteEmail = ? WHERE nome = ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getDescrizione());
            statement.setInt(2, bean.getIscritti());
            statement.setString(3, bean.getUtenteEmail());
            statement.setString(4, bean.getNome());

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized CommunityBean getByNome(String nome) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        CommunityBean community = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, nome);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                community = new CommunityBean();
                community.setNome(result.getString("nome"));
                community.setDescrizione(result.getString("descrizione"));
                community.setIscritti(result.getInt("iscritti"));
                community.setUtenteEmail(result.getString("utenteEmail"));
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return community;
    }

    public synchronized List<CommunityBean> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommunityBean> communities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                CommunityBean community = new CommunityBean();
                community.setNome(result.getString("nome"));
                community.setDescrizione(result.getString("descrizione"));
                community.setIscritti(result.getInt("iscritti"));
                community.setUtenteEmail(result.getString("utenteEmail"));

                communities.add(community);
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return communities;
    }

    public synchronized List<CommunityBean> getByEmail(String email) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommunityBean> communities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CommunityBean community = new CommunityBean();
                community.setNome(result.getString("nome"));
                community.setDescrizione(result.getString("descrizione"));
                community.setIscritti(result.getInt("iscritti"));
                community.setUtenteEmail(result.getString("utenteEmail"));

                communities.add(community);
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return communities;
    }

    public synchronized List<CommunityBean> getByNameSubstring(String substring) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommunityBean> communities = new ArrayList<>();

        // Use the SQL LIKE operator for substring matching
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome LIKE ?;";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            // Prepare the substring for the SQL query, adding wildcards for partial matches
            statement.setString(1, "%" + substring + "%");

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CommunityBean community = new CommunityBean();
                community.setNome(result.getString("nome"));
                community.setDescrizione(result.getString("descrizione"));
                community.setIscritti(result.getInt("iscritti"));
                community.setUtenteEmail(result.getString("utenteEmail"));

                communities.add(community);
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return communities;
    }

}
