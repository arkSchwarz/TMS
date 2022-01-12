package com.company;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private static final Scanner scanner_int = new Scanner(System.in);
    private static final Scanner scanner_string = new Scanner(System.in);

    public static int logIn_signInMenu() {
        System.out.println("\n---------- WELCOME ----------");
        System.out.println("\t[1] Log in");
        System.out.println("\t[2] Sign in");
        System.out.println("\t[3] Quit");

        System.out.print("\nChoice: ");
        return scanner_int.nextInt();
    }

    public static ArrayList<String> logInMenu() {
        System.out.println("\n---------- Log In ----------");
        System.out.println("\tLeave input fields empty to go parent menu\n");
        System.out.print("Mail: ");
        String username = scanner_string.nextLine();
        System.out.print("Password: ");
        String password = scanner_string.nextLine();

        if (username.length() == 0 || password.length() == 0)
            return null;

        ArrayList<String> username_password = new ArrayList<>();
        username_password.add(username);
        username_password.add(password);
        return username_password;
    }

    public static ArrayList<String> signInMenu() throws SQLException {
        System.out.println("\n---------- Sign In ----------");
        System.out.println("\tLeave input fields empty to go parent menu\n");

        System.out.print("User Type ('E' for employee, 'M' for manager): ");
        String type = scanner_string.nextLine();
        System.out.print("Mail: ");
        String mail = scanner_string.nextLine();
        System.out.print("Password: ");
        String password = scanner_string.nextLine();

        String project_name = "", group_name = "";

        if (type.equalsIgnoreCase("E")) {
            ArrayList<String> project_names = DB.distinct_all_project_names();
            if (project_names.size() == 0) {
                System.out.println("You can not create a employee user now. \n" +
                        "Because there is no created project to work on it. \n" +
                        "Wait for your manager to create a project for you. Bye bye...");
                return null;
            }
            System.out.println("\nChose a project to work on it\n");
            for (int i = 0; i < project_names.size(); i++) {
                System.out.println("\t\t[" + i + "] " + project_names.get(i));
            }
            System.out.print("Choice: ");
            int choice_project = scanner_int.nextInt();
            try {
                project_name = project_names.get(choice_project);
            } catch (Exception e) {
                System.out.println("Wrong choice. You are going to up menu!");
                return null;
            }

            System.out.println("\nChose a group to work in it\n");
            ArrayList<String> group_names = DB.distinct_all_group_names_according_to_project(project_name);
            for (int i = 0; i < group_names.size(); i++) {
                System.out.println("\t\t[" + i + "] " + group_names.get(i));
            }
            System.out.print("Choice: ");
            int choice_group = scanner_int.nextInt();
            try {
                group_name = group_names.get(choice_group);
            } catch (Exception e) {
                System.out.println("Wrong choice. You are going to up menu!");
                return null;
            }


        }

        if (type.equalsIgnoreCase("M")){
            System.out.println("\n\t[1] Create a new group on an existing project");
            System.out.println("\t[2] Create a new project");
            System.out.print("Choice: ");
            int choice = scanner_int.nextInt();

            if (choice == 1){
                ArrayList<String> project_names = DB.distinct_all_project_names();
                if (project_names.size() == 0) {
                    System.out.println("There is no project yet. You must create a new one.");
                    return null;
                }
                System.out.println("\nChose a project to work on it\n");
                for (int i = 0; i < project_names.size(); i++) {
                    System.out.println("\t\t[" + i + "] " + project_names.get(i));
                }
                System.out.print("Choice: ");
                int choice_project = scanner_int.nextInt();
                try {
                    project_name = project_names.get(choice_project);
                } catch (Exception e) {
                    System.out.println("Wrong choice. You are going to up menu!");
                    return null;
                }
            }

            if (choice== 2){
                System.out.print("Enter a new project name to be manager of it: ");
                project_name = scanner_string.nextLine();

                if (project_name.length() == 0){
                    System.out.println("Project name length should be at least 1");
                    return null;
                }

                if (DB.is_exist_project(project_name)){
                    System.out.println("There is already a project with this name. Try another name please.");
                    return null;
                }
            }

            System.out.print("Enter a new group name to be manager of it: ");
            group_name = scanner_string.nextLine();

            if (group_name.length() == 0){
                System.out.println("Group name length should be at least 1");
                return null;
            }

            if (DB.is_group_exist_in_project(project_name, group_name)){
                System.out.println("There is already a group with this name in same project. Try another name please.");
                return null;
            }

        }

        if (type.length() == 0 || mail.length() == 0  || password.length() == 0)
            return null;

        ArrayList<String> user_infos = new ArrayList<>();
        user_infos.add(type);
        user_infos.add(mail);
        user_infos.add(password);
        user_infos.add(project_name);
        user_infos.add(group_name);

        return user_infos;

    }

    public static int showMainMenu_forManager() {
        System.out.println("\n---------- Main Menu ----------");
        System.out.println("[1] Create a Task");
        System.out.println("[2] Update a Task");
        System.out.println("[3] Delete a Task");
        System.out.println("[4] Show All Tasks That I Created");
        System.out.println("[5] Create Note");
        System.out.println("[6] Delete Note");
        System.out.println("[7] Update Note");
        System.out.println("[8] Show All Notes That I Created");
        System.out.println("[9] Quit");

        System.out.print("\nChoice: ");
        return scanner_int.nextInt();

    }

    public static int showMainMenu_forEmployee() {
        System.out.println("\n---------- Main Menu ----------");
        System.out.println("[1] Show My Tasks");
        System.out.println("[2] Update Status of My Tasks");
        System.out.println("[3] Take a Task");
        System.out.println("[4] Show All Tasks of My Group");
        System.out.println("[5] Show All Notes of My Group");
        System.out.println("[6] Quit");

        System.out.print("\nChoice: ");
        return scanner_int.nextInt();
    }
}
