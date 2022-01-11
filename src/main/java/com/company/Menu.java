package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void logIn_signInMenu(){
        System.out.println("\n---------- WELCOME ----------");
        System.out.println("[1] Log in");
        System.out.println("[2] Sign in");
    }

    public static ArrayList<String> logInMenu(){
        System.out.println("\n---------- Log In ----------");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        ArrayList<String> username_password = new ArrayList<>();
        username_password.add(username);
        username_password.add(password);
        return username_password;
    }

    public static ArrayList<String> signInMenu(){
        System.out.println("\n---------- Sign In ----------");
        System.out.print("Mail: ");
        String mail = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("User Type ('E' for employee, 'M' for manager): ");
        String type = scanner.nextLine();

        ArrayList<String> username_password = new ArrayList<>();
        username_password.add(username);
        username_password.add(password);
        username_password.add(mail);
        username_password.add(type);
        return username_password;

    }

    public static void showMainMenu_forManager(){
        System.out.println("\n---------- Main Menu ----------");
        System.out.println("[1] Create a Task");
        System.out.println("[2] Update a Task");
        System.out.println("[3] Delete a Task");
        System.out.println("[4] Show All Tasks");
        System.out.println("[5] Show All Tasks That I Created");
        System.out.println("[6] Filter Tasks by Project Name / Group Name");
        System.out.println("[7] Create Note");
        System.out.println("[8] Delete Note");
        System.out.println("[9] Update Note");
        System.out.println("[10] Show All Notes");
        System.out.println("[11] Show All Notes That I Created");
        System.out.println("[12] Filter Notes by Project Name / Group Name");
        System.out.println("[13] Update Your User Information");


    }

    public static void showMainMenu_forEmployee(){
        System.out.println("\n---------- Main Menu ----------");
        System.out.println("[1] Show My Tasks");
        System.out.println("[2] Update Status of My Tasks");
        System.out.println("[3] Take a Task");
        System.out.println("[4] Show All Tasks of My Group");
        System.out.println("[5] Show All Notes");

    }

}
