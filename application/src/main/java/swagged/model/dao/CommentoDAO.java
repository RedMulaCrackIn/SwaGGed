package swagged.model.dao;

import swagged.model.bean.CommentoBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class CommentoDAO {

    private static final String TABLE_NAME = "commento";

    public synchronized boolean save(CommentoBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (corpo, utenteEmail, postId) VALUES (?,?,?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getCorpo());
            statement.setString(2, bean.getUtenteEmail());
            statement.setInt(3, bean.getPostId());

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized boolean delete(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, id);

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized boolean update(CommentoBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET corpo = ?, utenteEmail = ?, postId = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getCorpo());
            statement.setString(2, bean.getUtenteEmail());
            statement.setInt(3, bean.getPostId());
            statement.setInt(4, bean.getId());

            result = statement.executeUpdate();
            con.commit();
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized List<CommentoBean> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommentoBean> comments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CommentoBean commento = new CommentoBean();
                commento.setId(result.getInt("id"));
                commento.setCorpo(result.getString("corpo"));
                commento.setUtenteEmail(result.getString("utenteEmail"));
                commento.setPostId(result.getInt("postId"));

                comments.add(commento);
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return comments;
    }

    public synchronized CommentoBean getById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        CommentoBean commento = new CommentoBean();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                commento.setId(result.getInt("id"));
                commento.setCorpo(result.getString("corpo"));
                commento.setUtenteEmail(result.getString("utenteEmail"));
                commento.setPostId(result.getInt("postId"));
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return commento;
    }

    public synchronized List<CommentoBean> getByPostId(int postId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommentoBean> comments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE postId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, postId);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CommentoBean commento = new CommentoBean();
                commento.setId(result.getInt("id"));
                commento.setCorpo(result.getString("corpo"));
                commento.setUtenteEmail(result.getString("utenteEmail"));
                commento.setPostId(result.getInt("postId"));

                comments.add(commento);
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return comments;
    }

    public synchronized List<CommentoBean> getByUtenteEmail(String utenteEmail) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<CommentoBean> comments = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setString(1, utenteEmail);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CommentoBean commento = new CommentoBean();
                commento.setId(result.getInt("id"));
                commento.setCorpo(result.getString("corpo"));
                commento.setUtenteEmail(result.getString("utenteEmail"));
                commento.setPostId(result.getInt("postId"));

                comments.add(commento);
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return comments;
    }

}
