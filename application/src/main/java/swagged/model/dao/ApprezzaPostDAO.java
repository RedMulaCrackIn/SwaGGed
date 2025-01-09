package swagged.model.dao;

import swagged.model.bean.ApprezzaPostBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApprezzaPostDAO {
    private static final String TABLE_NAME = "apprezzaPost";

    public synchronized boolean save(ApprezzaPostBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (utenteEmail, postId) VALUES (?, ?)";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getUtenteEmail());
            statement.setInt(2, bean.getPostId());

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

    public synchronized boolean delete(String utenteEmail, int postId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE utenteEmail = ? AND postId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);
            statement.setInt(2, postId);

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

    public synchronized ApprezzaPostBean getByKey(String utenteEmail, int postId) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        ApprezzaPostBean apprezzaPost = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ? AND postId = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);
            statement.setInt(2, postId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                apprezzaPost = new ApprezzaPostBean();
                apprezzaPost.setUtenteEmail(resultSet.getString("utenteEmail"));
                apprezzaPost.setPostId(resultSet.getInt("postId"));
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return apprezzaPost;
    }

    public synchronized List<ApprezzaPostBean> getByEmail(String utenteEmail) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<ApprezzaPostBean> apprezzaPosts = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ApprezzaPostBean apprezzaPost = new ApprezzaPostBean();
                apprezzaPost.setUtenteEmail(resultSet.getString("utenteEmail"));
                apprezzaPost.setPostId(resultSet.getInt("postId"));

                apprezzaPosts.add(apprezzaPost);
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return apprezzaPosts;
    }

}