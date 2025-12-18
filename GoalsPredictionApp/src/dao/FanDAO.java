package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Fan;

public class FanDAO {

    // CREATE
    public void addFan(Fan fan) throws Exception {
        String sql = "INSERT INTO fans(name, team, player_one_goals, player_two_goals, player_three_goals) VALUES (?,?,?,?,?)";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, fan.getName());
        ps.setString(2, fan.getTeam());
        ps.setInt(3, fan.getP1());
        ps.setInt(4, fan.getP2());
        ps.setInt(5, fan.getP3());

        ps.executeUpdate();
        con.close();
    }

    // READ
    public List<Fan> getAllFans() throws Exception {
        List<Fan> list = new ArrayList<>();
        String sql = "SELECT * FROM fans";

        Connection con = DatabaseConnection.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new Fan(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("team"),
                rs.getInt("player_one_goals"),
                rs.getInt("player_two_goals"),
                rs.getInt("player_three_goals")
            ));
        }
        con.close();
        return list;
    }

    // UPDATE
    public void updateFan(Fan fan) throws Exception {
        String sql = "UPDATE fans SET name=?, team=?, player_one_goals=?, player_two_goals=?, player_three_goals=? WHERE id=?";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, fan.getName());
        ps.setString(2, fan.getTeam());
        ps.setInt(3, fan.getP1());
        ps.setInt(4, fan.getP2());
        ps.setInt(5, fan.getP3());
        ps.setInt(6, fan.getId());

        ps.executeUpdate();
        con.close();
    }

    // DELETE
    public void deleteFan(int id) throws Exception {
        String sql = "DELETE FROM fans WHERE id=?";
        Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }
}
