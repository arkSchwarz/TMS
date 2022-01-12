package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DB {


    public static ArrayList<String> check_mail_password(ArrayList<String> mail_password) throws SQLException {
        // username_password'in 0. indexi mail, 1. indexi password
        // DB'de sorgula böyle biri varmı yok mu diye. Eğer varsa bütün colonları arraylist içinde dönder.
        // Yoksa null dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        ArrayList<String> projects = new ArrayList<String>();

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [User] WHERE mail="+"'"+mail_password.get(0)+"'"+"AND password="+"'"+mail_password.get(1)+"'";

        ResultSet m_ResultSet = m_Statement.executeQuery(query);


        if(m_ResultSet.next())
            do {
                projects.add(m_ResultSet.getString(1));
                projects.add(m_ResultSet.getString(2));
                projects.add(m_ResultSet.getString(3));
                projects.add(m_ResultSet.getString(4));
                projects.add(m_ResultSet.getString(5));
                projects.add(m_ResultSet.getString(6));

            }while(m_ResultSet.next());
        else{
            return null;
        }

        return projects;
    }

    public static String RETURN_SPECIFIC_USER(String user_id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");
        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [User] WHERE user_id="+"'"+user_id+"'";

        ResultSet m_ResultSet = m_Statement.executeQuery(query);
        String a = "";

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5) + " " + m_ResultSet.getString(6));
        }

        return a;
    }

    public static void create_user(ArrayList<String> register_infos) throws SQLException {
        // Verdiğim kolon bilgileriyle yeni bir user yarat
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "INSERT INTO [User] (type, mail, password, project_name, group_name) VALUES (?, ?, ?, ?, ?)";

        String query = "SELECT * FROM [User] WHERE project_name="+"'"+register_infos.get(3)+"'"+" AND group_name="+"'"+register_infos.get(4)+"'";




        Statement m_Statement = conn.createStatement();

        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        statement.setString(1, register_infos.get(0));
        statement.setString(2, register_infos.get(1));
        statement.setString(3, register_infos.get(2));
        statement.setString(4, register_infos.get(3));
        statement.setString(5, register_infos.get(4));


        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }

        int manager_id = -1;

        if(m_ResultSet.next()){
            manager_id = m_ResultSet.getInt(1);
        }


        if (register_infos.get(0).equalsIgnoreCase("M")){

            String project_name = register_infos.get(3);
            String group_name = register_infos.get(4);

            DB.create_project_group(project_name, group_name, manager_id);
        }


    }

    public static ArrayList<String> distinct_all_project_names() throws SQLException {
        // bütün distinct project_name'lerin listesini dönder
        // eğer hiç proje yoksa içi boş bir arraylist dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");
        ArrayList<String> projects = new ArrayList<String>();

        Statement m_Statement = conn.createStatement();
        String query = "SELECT DISTINCT project_name FROM [Project_Group]";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        if(m_ResultSet.next()){
            do{
                projects.add(m_ResultSet.getString(1));
            }while(m_ResultSet.next());
        }


        return projects;
    }

    public static ArrayList<String> distinct_all_group_names_according_to_project(String project_name) throws SQLException {
        // Project_Group tablosu üzerinde WHERE project_name == project_name olan bütün group_name'leri
        // arraylist olarak distinc bir şekilde dönder. Eğer group yoksa içi boş arraylist dönder

        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        ArrayList<String> projects = new ArrayList<String>();

        Statement m_Statement = conn.createStatement();
        String query = "SELECT DISTINCT project_name FROM [Project_Group] WHERE project_name="+"'"+project_name+"'";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            projects.add(m_ResultSet.getString(1));
        }

        return projects;
    }

    public static boolean is_exist_project(String project_name) throws SQLException {
        // Verilen isimde bir proje daha önceden tabloda var mı diye kontrol et
        // Eğer varsa true, yoksa false dönder

        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        ArrayList<String> projects = new ArrayList<String>();

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [Project_Group] WHERE project_name="+"'"+project_name+"'";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        return m_ResultSet.next();
    }

    public static boolean is_group_exist_in_project(String project_name, String group_name) throws SQLException {
        // Verilen isimde bir proje ve grup daha önceden tabloda var mı diye kontrol et
        // Eğer varsa true, yoksa false dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [Project_Group] WHERE project_name="+"'"+project_name+"'"+ "AND group_name="+"'"+group_name+"'";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        return m_ResultSet.next();

    }

    public static ArrayList<ArrayList<String>> show_all_tasks_filtered_by_employee_id(int employee_id) throws SQLException {
        // task tablosunda verilen employee_id'ye sahip olan bütün taskları 2d arraylist olarak dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        Statement m_Statement = conn.createStatement();
        String employeeid = String.valueOf(employee_id);
        String query = "SELECT * FROM [Task] WHERE employee_id=" +"'"+employeeid+"'";

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        ArrayList<ArrayList<String>> tasks = new ArrayList<ArrayList<String>>();


        int i=0;
        while (m_ResultSet.next()) {
            tasks.add(new ArrayList<String>());
            tasks.get(i).add(0,m_ResultSet.getString(1));
            tasks.get(i).add(1,m_ResultSet.getString(2));
            tasks.get(i).add(2,m_ResultSet.getString(3));
            tasks.get(i).add(3,m_ResultSet.getString(4));
            tasks.get(i).add(4,m_ResultSet.getString(5));
            tasks.get(i).add(5,m_ResultSet.getString(6));
            tasks.get(i).add(6,m_ResultSet.getString(7));
            tasks.get(i).add(7,m_ResultSet.getString(8));
            tasks.get(i).add(8,m_ResultSet.getString(9));

            i++;
        }

        return tasks;

    }

    public static ArrayList<ArrayList<String>> show_all_tasks_filtered_by_group_name_and_project_name(String project_name, String group_name) throws SQLException {
        // task tablosunda verilen group_name'e ve project_name'e sahip olan bütün taskları 2d arraylist olarak dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Task] WHERE project_name="+"'"+project_name+"'"+ "AND group_name="+"'"+group_name+"'";


        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        ArrayList<ArrayList<String>> tasks = new ArrayList<ArrayList<String>>();


        int i=0;
        while (m_ResultSet.next()) {
            tasks.add(new ArrayList<String>());
            tasks.get(i).add(0,m_ResultSet.getString(1));
            tasks.get(i).add(1,m_ResultSet.getString(2));
            tasks.get(i).add(2,m_ResultSet.getString(3));
            tasks.get(i).add(3,m_ResultSet.getString(4));
            tasks.get(i).add(4,m_ResultSet.getString(5));
            tasks.get(i).add(5,m_ResultSet.getString(6));
            tasks.get(i).add(6,m_ResultSet.getString(7));
            tasks.get(i).add(7,m_ResultSet.getString(8));
            tasks.get(i).add(8,m_ResultSet.getString(9));

            i++;
        }

        return tasks;


    }

    public static boolean check_is_proper_task_id_and_employee_id( int task_id, int employee_id) throws SQLException {
        // eğer verilen task_id'ye sahip olan task'ın employee_id'si uyuşuyorsa return true
        // uyuşmuyorsa return false
        // eğer verilen task_id task tablosunda yoksa return false
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String taskid = String.valueOf(task_id);
        String employeeid = String.valueOf(employee_id);

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Task] WHERE employe_id="+"'"+employeeid+"'"+" AND task_id="+"'"+taskid+"'";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        return m_ResultSet.next();

    }


    //Çalışıyor ama konsola 1 basıyor :D
    public static void update_task_status_to_done(int task_id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");
        String taskid = String.valueOf(task_id);
        System.out.println(taskid);

        String sql = "UPDATE [Task] SET status='Done' WHERE task_id ="+"'"+taskid+"'";



        Statement statement = conn.createStatement();
        statement.executeUpdate(sql);



    }

    public static boolean check_is_task_takeable(int task_id, String project_name, String group_name) throws SQLException {
        // Eğer verilen task_id'ye sahip olan taskın project_name ve group_name'i aynıyse ve
        // henüz bir employee tarafından alınmadıysa return true
        // diğer durumlarda return false
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String taskid = String.valueOf(task_id);
        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Task] WHERE task_id="+taskid+" AND project_name=" +"'"+project_name +"'"+" AND group_name="+"'"+group_name +"'"+" AND employee_id=-1";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        return m_ResultSet.next();


    }

    public static ArrayList<ArrayList<String>> all_notes_filtered_by_project_name_and_group_name(String project_name, String group_name) throws SQLException {
        // verilen project_name ve group_name'e sahip olan bütün notları 2d arraylist olarak dönder
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Note_] WHERE project_name="+"'"+project_name+"'"+ "AND group_name="+"'"+group_name+"'";


        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        ArrayList<ArrayList<String>> notes = new ArrayList<ArrayList<String>>();


        int i=0;
        while (m_ResultSet.next()) {
            notes.add(new ArrayList<String>());
            notes.get(i).add(0,m_ResultSet.getString(1));
            notes.get(i).add(1,m_ResultSet.getString(2));
            notes.get(i).add(2,m_ResultSet.getString(3));
            notes.get(i).add(3,m_ResultSet.getString(4));

            i++;
        }

        return notes;




    }

    public static void create_task(String project_name, String group_name, int user_id, int priority, String status, String deadline, String description, int employee_id) throws SQLException {
        // Input şu şekilde gelecek : DB.create_task(project_name, group_name, user_id, priority, "not done",deadline, description, -1);
        // employee_id yerine -1 gönderdim.
        // Kafana göre takıl

        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String userid = String.valueOf(user_id);
        String employeeid = String.valueOf(employee_id);

        String sql = "INSERT INTO [Task] (project_name, group_name, manager_id, priority, status, deadline, description, employee_id) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";


        Date date = java.sql.Date.valueOf(deadline);

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, project_name);
        statement.setString(2, group_name);
        statement.setString(3, userid);
        statement.setFloat(4, priority);
        statement.setString(5, status);
        statement.setDate(6, date);
        statement.setString(7, description);
        statement.setString(8, employeeid);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new task was inserted successfully!");
        }
    }

    public static ArrayList<ArrayList<String>> all_tasks_filtered_by_manager_id(int manager_id) throws SQLException {
        // task'lar tablosunda manager_id'sine göre filtreme yap ve sonuçları 2d arraylist olarak returnla
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        Statement m_Statement = conn.createStatement();
        String managerid = String.valueOf(manager_id);

        String query = "SELECT * FROM [Task] WHERE manager_id=" +"'"+managerid+"'";


        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        ArrayList<ArrayList<String>> tasks = new ArrayList<ArrayList<String>>();

        int i = 0;
        while (m_ResultSet.next()) {
            tasks.add(new ArrayList<String>());
            tasks.get(i).add(0, m_ResultSet.getString(1));
            tasks.get(i).add(1, m_ResultSet.getString(2));
            tasks.get(i).add(2, m_ResultSet.getString(3));
            tasks.get(i).add(3, m_ResultSet.getString(4));
            tasks.get(i).add(4, m_ResultSet.getString(5));
            tasks.get(i).add(5, m_ResultSet.getString(6));
            tasks.get(i).add(6, m_ResultSet.getString(7));
            tasks.get(i).add(7, m_ResultSet.getString(8));
            tasks.get(i).add(8, m_ResultSet.getString(9));

            i++;
        }

        return tasks;





    }

    public static boolean detete_task(int manager_id, int task_id) throws SQLException {
        // Eğer verilen task_id'ye sahip bir task varsa ve manager_id'si manager_id'ye eşitse sil ve true döndür
        // diğer durumlarda false dönder (manager id farklıysa veya böyle bir task hiç yoksa)
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");
        String taskid = String.valueOf(task_id);
        String managerid = String.valueOf(manager_id);


        String sql = "DELETE FROM [Task] WHERE task_id="+"'"+taskid+"'"+"AND manager_id="+"'"+managerid+"'";

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            return true;
        }

        return false;

    }


    public static boolean update_task(int task_id, int manager_id, int new_priority, String new_deadline, String new_description) throws SQLException {
        //Eğer verilen task_id'ye sahip bir task varsa ve manager_id'si manager_id'ye eşitse update et ve true döndür
        // diğer durumlarda false döndür

        String newpriority = String.valueOf(new_priority);
        String taskid = String.valueOf(task_id);
        String managerid = String.valueOf(manager_id);


        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "UPDATE [Task] SET priority=?, deadline=?, description=? WHERE task_id="+"'"+taskid+"'"+" AND manager_id="+"'"+managerid+"'";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, newpriority);
        preparedStatement.setString(2, new_deadline);
        preparedStatement.setString(3, new_description);

        int rowsUpdated = preparedStatement.executeUpdate();
        return rowsUpdated > 0;
    }


    public static void create_note(String group_name, String project_name, String note_text) throws SQLException {
        // Her ne kadar yaratmak Alah'a mahsus olsa da sen de çok güzel bir şekilde
        // verilen bilgilere bağlı olarak note yaratabilirsin diye düşünüyorum
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "INSERT INTO [Note_] (group_name, project_name, note_text) VALUES (?, ?, ?)";
        //String sql = "INSERT INTO [Note_] (" + group_name+ ", " + project_name + ", " + note_text + ") VALUES (?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, group_name);
        statement.setString(2, project_name);
        statement.setString(3, note_text);


        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new note was inserted successfully!");
        }
    }


    public static boolean delete_note(String group_name, String project_name, int note_id) throws SQLException {
        //Eğer verilen note_id'ye sahip bir note varsa ve grup'la proje'de uyuşuyorsa sil ve true döndür
        // diğer durumlarda false döndür

        String noteid = String.valueOf(note_id);


        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String query = "DELETE FROM [Note_] WHERE note_id=" +"'"+ noteid +"'"+ " AND project_name=" +"'"+ project_name +"'"+ " AND group_name=" +"'"+ group_name+"'";

        PreparedStatement statement = conn.prepareStatement(query);


        int rowsDeleted = statement.executeUpdate();

        if (rowsDeleted > 0) {
            return true;

        }

        return false;
    }



    public static boolean update_note(String group_name, String project_name, int note_id, String new_text) throws SQLException {

        //Eğer verilen note_id'ye sahip bir note varsa ve grup'la proje'de uyuşuyorsa new_text ile update et ve true döndür
        // diğer durumlarda false döndür

        String noteid = String.valueOf(note_id);



        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "UPDATE [Note_] SET note_text=?,WHERE project_name="+"'"+project_name+"'"+" AND note_id="+"'"+noteid+"'";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, noteid);


        int rowsUpdated = preparedStatement.executeUpdate();
        return rowsUpdated > 0;
    }


    public static void create_project_group(String group_name, String project_name, int manager_id) throws SQLException {
        // Her ne kadar yaratmak Alah'a mahsus olsa da sen de çok güzel bir şekilde
        // verilen bilgilere bağlı olarak note yaratabilirsin diye düşünüyorum
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String sql = "INSERT INTO [Project_Group] (project_name, group_name, manager_id) VALUES (?, ?, ?)";

        String manageid = String.valueOf(manager_id);

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, group_name);
        statement.setString(2, project_name);
        statement.setString(3, manageid);


        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new Project was inserted successfully!");
        }
    }

    public static void take_a_task(int task_id, int employee_id) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:49702;databaseName=TaskManagement;integratedSecurity=true;");

        String taskid = String.valueOf(task_id);


        String query = "UPDATE [Task] SET employee_id=? WHERE task_id="+"'"+taskid+"'";

        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, employee_id);


        int rowsUpdated = preparedStatement.executeUpdate();



    }














    public static void READ_ALL(Connection conn,String table_name) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM "+ table_name;
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        if(table_name.equalsIgnoreCase("[User]")) {

            while (m_ResultSet.next()) {
                System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                        "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5) + " " + m_ResultSet.getString(6));
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




    public static void READ_SPECIFIC_TASK(Connection conn,String project_name,String group_name) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM [Task] WHERE project_name="+project_name+ "AND"+"group_name="+group_name;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5)+"," + m_ResultSet.getString(6)
                    +"," + m_ResultSet.getString(7)+"," + m_ResultSet.getString(8)+ "," + m_ResultSet.getString(9));


        }

    }

    public static void READ_SPECIFIC_USER(Connection conn,String user_id) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [User] WHERE user_id="+user_id;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3) +
                    "," + m_ResultSet.getString(4) + "," + m_ResultSet.getString(5) + " " + m_ResultSet.getString(6));
        }

    }

    public static void READ_SPECIFIC_NOTES(Connection conn,String note_id) throws SQLException {

        Statement m_Statement = conn.createStatement();
        String query = "SELECT ALL * FROM [Note_] WHERE note_id="+note_id;

        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", " + m_ResultSet.getString(3));
        }

    }




    public static void WRITE_USER(Connection conn,String type,String mail,String password,String project_name, String group_name) throws SQLException {

        String sql = "INSERT INTO [User] (type, mail, password, project_name, group_name) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, type);
        statement.setString(2, mail);
        statement.setString(3, password);
        statement.setString(4, project_name);
        statement.setString(5, group_name);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new user was inserted successfully!");
        }

    }
                                                                                                                                        /*yyyy-mm-dd*/
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

    public static void WRITE_NOTE(Connection conn,String group_id, String note_text) throws SQLException {

        String sql = "INSERT INTO [Note_] (group_id, note_text) VALUES (?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, group_id);
        statement.setString(2, note_text);


        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A new note was inserted successfully!");
        }

    }


    public static void UPTADE_USER(Connection conn,String type,String mail,String password,String project_name, String group_name) throws SQLException {

        String sql = "UPDATE [User] SET type=?, mail=?, password=?, name_surname =?";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, type);
        statement.setString(2, mail);
        statement.setString(3, password);
        statement.setString(4, project_name);
        statement.setString(5, group_name);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }

    }

    public static void UPTADE_TASK(Connection conn,String project_name,String group_name,String manager_id,int priority, String status, String deadline, String description, String employee_id) throws SQLException{

        String sql = "UPDATE [Task] SET project_name=?, group_name=?,  manager_id=?, priority=?, status=?, deadline=?, description=?, employee_id=?";

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

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing task was updated successfully!");
        }

    }

    public static void UPTADE_NOTE(Connection conn,String group_id, String note_text) throws SQLException {

        String sql = "UPDATE [Note_] SET group_id=?, note_text=?";

        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, group_id);
        statement.setString(2, note_text);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("An existing user was updated successfully!");
        }

    }



    public static void DELETE_USER(Connection conn, String user_id) throws SQLException {

        String sql = "DELETE FROM [User] WHERE user_id="+user_id;

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }

    }

    public static void DELETE_TASK(Connection conn, String task_id) throws SQLException {
        String sql = "DELETE FROM [Task] WHERE task_id="+task_id;

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }

    }

    public static void DELETE_NOTES(Connection conn, String note_id) throws SQLException {
        String sql = "DELETE FROM [Notes] WHERE note_id="+note_id;

        PreparedStatement statement = conn.prepareStatement(sql);


        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A user was deleted successfully!");
        }

    }


}
