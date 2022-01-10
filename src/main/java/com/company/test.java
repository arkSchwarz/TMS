package com.company;


import java.sql.*;




public class test {




    public static void READ_ALL(Connection conn,String table_name) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM "+ table_name;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);
        if(table_name.equalsIgnoreCase("[User]")) {

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



        /*public*/ static void main(String[] args) {

        Connection conn = null;

        try {

            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");
            if (conn != null) {

            //WRITE_TASK(conn,"anan","baldız","31",2,"3","2022-02-19","bacak", "0");

            //READ_ALL(conn,"[Task]");
            //DELETE_TASK(conn,"1");

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
