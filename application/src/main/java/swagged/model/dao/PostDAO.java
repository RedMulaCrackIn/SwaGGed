package swagged.model.dao;

import swagged.model.bean.PostBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    private static final String TABLE_NAME = "post";

    public synchronized boolean save(PostBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (titolo, corpo, immagine, likes, dataCreazione, numeroCommenti, utenteEmail, communityNome) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getTitolo());
            statement.setString(2, bean.getCorpo());
            statement.setString(3, bean.getImmagine());
            statement.setInt(4, bean.getLikes());
            statement.setDate(5, bean.getDataCreazione()); // Assume that dataCreazione is set in the bean
            statement.setInt(6, bean.getNumeroCommenti());
            statement.setString(7, bean.getUtenteEmail());
            statement.setString(8, bean.getCommunityNome());

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
            con.commit(); // Commit the transaction
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return result != 0;
    }

    public synchronized boolean update(PostBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "UPDATE " + TABLE_NAME + " SET titolo = ?, corpo = ?, immagine = ?, likes = ?, dataCreazione = ?, numeroCommenti = ?, utenteEmail = ?, communityNome = ? WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            // Set the parameters based on the PostBean object
            statement.setString(1, bean.getTitolo());
            statement.setString(2, bean.getCorpo());
            statement.setString(3, bean.getImmagine());
            statement.setInt(4, bean.getLikes());
            statement.setDate(5, bean.getDataCreazione());
            statement.setInt(6, bean.getNumeroCommenti());
            statement.setString(7, bean.getUtenteEmail());
            statement.setString(8, bean.getCommunityNome());
            statement.setInt(9, bean.getId());

            // Execute the update and capture the result
            result = statement.executeUpdate();

            con.commit(); // Commit the transaction
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

    public synchronized PostBean getById(int id) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        PostBean post = new PostBean();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                post.setId(result.getInt("id"));
                post.setTitolo(result.getString("titolo"));
                post.setCorpo(result.getString("corpo"));
                post.setImmagine(result.getString("immagine"));
                post.setLikes(result.getInt("likes")); // Retrieve likes after segnalazioni
                post.setDataCreazione(result.getDate("dataCreazione"));
                post.setNumeroCommenti(result.getInt("numeroCommenti"));
                post.setUtenteEmail(result.getString("utenteEmail"));
                post.setCommunityNome(result.getString("communityNome"));
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return post;
    }

    public synchronized List<PostBean> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<PostBean> posts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                PostBean post = new PostBean();
                post.setId(result.getInt("id"));
                post.setTitolo(result.getString("titolo"));
                post.setCorpo(result.getString("corpo"));
                post.setImmagine(result.getString("immagine"));
                post.setLikes(result.getInt("likes")); // Include likes after segnalazioni
                post.setDataCreazione(result.getDate("dataCreazione"));
                post.setNumeroCommenti(result.getInt("numeroCommenti"));
                post.setUtenteEmail(result.getString("utenteEmail"));
                post.setCommunityNome(result.getString("communityNome"));
                posts.add(post);
            }
        } finally {
            if (statement != null) statement.close();
            DriverManagerConnectionPool.releaseConnection(con);
        }
        return posts;
    }


}
