package swagged.model.dao;

import swagged.model.bean.PostBean;
import swagged.utils.DriverManagerConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

}
