package com.company;

import java.sql.*;


public class test {



    public void UPTADE() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;");

        String sql = "UPDATE [User] SET type=?, mail=?, password=?, name_surname =? WHERE user_id='1'";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, "Employee");
        statement.setString(2, "david28@hotmail.com");
        statement.setString(3, "6666");
        statement.setString(4, "David M. Beckhem");

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }

    }

    public void DELETE() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;");

        String sql = "DELETE FROM [User] WHERE user_id='1'";

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }

    }

     static void main(String[] args) {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;");
            if (conn != null) {



            }
        }


        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
