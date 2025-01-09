package swagged.model.dao;

import swagged.model.bean.IscrivitiCommunityBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class IscrivitiCommunityDAO {

    private static final String TABLE_NAME = "iscrivitiCommunity";

    public synchronized boolean save(IscrivitiCommunityBean bean) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "INSERT INTO " + TABLE_NAME + " (utenteEmail, communityNome) VALUES (?,?)";

        try{
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, bean.getUtenteEmail());
            statement.setString(2, bean.getCommunityNome());

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

    public synchronized boolean delete(String utenteEmail, String communityNome) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        int result = 0;

        String query = "DELETE FROM " + TABLE_NAME + " WHERE utenteEmail = ? AND communityNome = ?";

        try{
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);
            statement.setString(2, communityNome);

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

    public synchronized IscrivitiCommunityBean getByKey(String utenteEmail, String communityNome) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        IscrivitiCommunityBean segueCommunity = null;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ? AND communityNome = ?";

        try{
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);
            statement.setString(2, communityNome);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                segueCommunity = new IscrivitiCommunityBean();
                segueCommunity.setUtenteEmail(resultSet.getString("utenteEmail"));
                segueCommunity.setCommunityNome(resultSet.getString("communityNome"));
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return segueCommunity;
    }

    public synchronized List<IscrivitiCommunityBean> getByEmail(String utenteEmail) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;
        List<IscrivitiCommunityBean> segueCommunities = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE utenteEmail = ?";

        try {
            con = DriverManagerConnectionPool.getConnection();
            statement = con.prepareStatement(query);

            statement.setString(1, utenteEmail);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                IscrivitiCommunityBean segueCommunity = new IscrivitiCommunityBean();
                segueCommunity.setUtenteEmail(resultSet.getString("utenteEmail"));
                segueCommunity.setCommunityNome(resultSet.getString("communityNome"));

                segueCommunities.add(segueCommunity);
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } finally {
                DriverManagerConnectionPool.releaseConnection(con);
            }
        }
        return segueCommunities;
    }
}
