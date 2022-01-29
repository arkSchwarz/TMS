package com.company;


import java.sql.*;
import java.util.ArrayList;

/*This is just a test class*/

public class test {


    public static String RETURN_SPECIFIC_TASK(Connection conn,String task_id) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [TASK] WHERE task_id="+task_id;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);
        String a = "";

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5)+"," + m_ResultSet.getString(6)
                    +"," + m_ResultSet.getString(7)+"," + m_ResultSet.getString(8)+ "," + m_ResultSet.getString(9));


        }

        return a;
    }

    public static void update_task_status_to_done(Connection conn, int task_id) throws SQLException {
        // task_id'si verilen id'ye eşit olan task'ın status'unu "Done" olarak değiştir
        String taskid = String.valueOf(task_id);
        System.out.println(taskid);

        String sql = "UPDATE [Task] SET status='Done' WHERE task_id ="+taskid;



        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);


    }

    public static boolean update_task(int task_id, int manager_id, int new_priority, String new_deadline, String new_description) throws SQLException {
        //Eğer verilen task_id'ye sahip bir task varsa ve manager_id'si manager_id'ye eşitse update et ve true döndür
        // diğer durumlarda false döndür

        String newpriority = String.valueOf(new_priority);
        String taskid = String.valueOf(task_id);
        String managerid = String.valueOf(manager_id);


        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "UPDATE [Task] SET priority=?, deadline=?, description=? WHERE task_id="+taskid+" AND manager_id="+managerid;



        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, newpriority);
        preparedStatement.setString(2, new_deadline);
        preparedStatement.setString(3, new_description);

        int rowsUpdated = preparedStatement.executeUpdate();
        return rowsUpdated > 0;
    }



    public static void WRITE_TASK(Connection conn,String project_name,String group_name,String manager_id,int priority, String status, String deadline, String description, String employee_id) throws SQLException{

        String sql = "INSERT INTO [Task] (project_name, group_name, manager_id, priority, status, deadline, description, employee_id) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";


        Date date = java.sql.Date.valueOf(deadline);

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, project_name);
        statement.setString(2, group_name);
        statement.setString(3, manager_id);
        statement.setFloat(4, priority);
        statement.setString(5, status);
        statement.setDate(6, date);
        statement.setString(7, description);
        statement.setString(8, employee_id);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new task was inserted successfully!");
        }

    }

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



        /*public*/ static void main(String[] args) throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        try {


            if (conn != null) {

                //WRITE_TASK(conn,"proje1","group1","1",2,"Not Done","2022-02-19","desc", "0");
                //update_task_status_to_done(conn,1);
                //WRITE_USER(conn,"E","batuhan@gmail.com","1234","batuhan");
                READ_ALL(conn,"[Task]");
                //DELETE_TASK(conn,"1");
                //RETURN_SPECIFIC_USER(conn,"1");
                //System.out.println(RETURN_SPECIFIC_TASK(conn,"1"));
                //update_task(1,1,6,"2022-06-01","blab-la");

                //System.out.println(show_all_tasks_filtered_by_employee_id(conn,0));

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
