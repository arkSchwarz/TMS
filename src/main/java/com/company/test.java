package com.company;

import java.sql.*;


public class test {



    public static void main(String[] args) {

        Connection conn = null;

        try {

            String url = "jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;";

            conn = DriverManager.getConnection(url);
            if (conn != null) {


                Statement m_Statement = conn.createStatement();
                String query = "SELECT ALL * FROM [User] WHERE user_id='3'";

                ResultSet m_ResultSet = m_Statement.executeQuery(query);

                while (m_ResultSet.next()) {
                    System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                            ","+m_ResultSet.getString(4) + "," + m_ResultSet.getString(5));
                }





                /* WRITE
                String sql = "INSERT INTO [User] (type, mail, password, name_surname) VALUES (?, ?, ?, ?)";

                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, "Employee");
                statement.setString(2, "david28@hotmail.com");
                statement.setString(3, "9696");
                statement.setString(4, "David Beckhem");

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                }

                 */

            /* UPTADE
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

        */

                /* DELETE
                String sql = "DELETE FROM [User] WHERE user_id='1'";

                PreparedStatement statement = conn.prepareStatement(sql);
                //statement.setString(4, "David M. Beckhem");

                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("A user was deleted successfully!");
                }

                 */


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
