package com.company;

import java.sql.*;

public class DB {




    //Table nameler köşeli parantez içinde yazılmak zorunda
    public void READ_ALL(Connection conn,String table_name) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM "+ table_name;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);
        if(table_name.equalsIgnoreCase("[Users]")) {

            while (m_ResultSet.next()) {
                System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                        "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5));
            }

        }

        if(table_name.equalsIgnoreCase("[Task]")) {

            while (m_ResultSet.next()) {
                System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                        "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5)+"," + m_ResultSet.getString(6)
                    +"," + m_ResultSet.getString(7)+"," + m_ResultSet.getString(8)+ "," + m_ResultSet.getString(9));


            }

        }

        if(table_name.equalsIgnoreCase("[Notes]")) {

            while (m_ResultSet.next()) {
                System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3));
            }

        }
    }

    public void READ_SPECIFIC_TASK(Connection conn,String project_name,String group_name) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Task] WHERE project_name="+project_name+ "AND"+"group_name="+group_name;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5)+"," + m_ResultSet.getString(6)
                    +"," + m_ResultSet.getString(7)+"," + m_ResultSet.getString(8)+ "," + m_ResultSet.getString(9));


        }

    }

    public void READ_SPECIFIC_USER(Connection conn,String user_id) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [User] WHERE user_id="+user_id;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    ","+m_ResultSet.getString(4) + "," + m_ResultSet.getString(5));
        }

    }

    public void READ_SPECIFIC_NOTES(Connection conn,String note_id) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [Note_] WHERE user_id="+note_id;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3));
        }

    }



    public void WRITE_USER(Connection conn,String type,String mail,String password,String Name_Surname) throws SQLException {

        String sql = "INSERT INTO [User] (type, mail, password, name_surname) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, type);
        statement.setString(2, mail);
        statement.setString(3, password);
        statement.setString(4, Name_Surname);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }

    }

    public void WRITE_TASK(Connection conn,String project_name,String group_name,String manager_id,int priority, String status, String daedline, String description, String employee_id) throws SQLException {

        String sql = "INSERT INTO [Task] (project_name, group_name, manager_id, priority, status, deadline, description, employee_id) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(0, project_name);
        statement.setString(1, group_name);
        statement.setString(3, manager_id);
        statement.setFloat(4, priority);
        statement.setString(5, status);
        statement.setString(6, daedline);
        statement.setString(7, description);
        statement.setString(8, employee_id);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }

    }

    public void WRITE_NOTE(Connection conn,String group_id, String note_text) throws SQLException {

        String sql = "INSERT INTO [User] (group_id, note_text) VALUES (?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(0, group_id);
        statement.setString(2, note_text);


        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }

    }

    public void UPTADE_USER(Connection conn) throws SQLException {
         conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;");

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

    public void DELETE_USER(Connection conn) throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=Test;integratedSecurity=true;");

        String sql = "DELETE FROM [User] WHERE user_id='1'";

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }

    }
}
