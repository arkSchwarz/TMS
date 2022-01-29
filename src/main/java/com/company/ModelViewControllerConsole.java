package com.company;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Enes Güler 18050111005
 * Yakup Batuhan Ördek 18050111041
 * */

public class ModelViewControllerConsole {


    public static void show_tasks_table(ArrayList<ArrayList<String>> tasks) {
        System.out.printf("\n%-10s %-10s %-15s %-15s %-40s\n", "[task_id]", "[priority]", "[status]", "[deadline]", "[description]");
        // System.out.println("\n[task_id]\t\t" +
        //        "[priority]\t\t[status]\t\t[deadline]\t\t[description]");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (ArrayList<String> task : tasks) {
            System.out.printf("%-10s %-10s %-15s %-15s %-40s\n", task.get(0), task.get(4), task.get(5), task.get(6), task.get(7));
            // System.out.println(task.get(0) + "\t\t" +
            //        task.get(4) + "\t\t" + task.get(5) + "\t\t" + task.get(6) + "\t\t" + task.get(7));
        }
    }

    public static void show_notes_table(ArrayList<ArrayList<String>> notes) {
        System.out.printf("%-10s %-40s\n", "[note_id]", "[note_text]");
        //System.out.println("\n[note_id]\t\t[note_text]");
        System.out.println("-------------------------------------------------------------");
        for (ArrayList<String> note : notes) {
            System.out.printf("%-10s %-40s\n", note.get(0), note.get(3));
            //System.out.println(note.get(0) + "\t\t" + note.get(3));
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner scanner_int = new Scanner(System.in);
        Scanner scanner_string = new Scanner(System.in);

        int loginsignin_choice;
        do {
            loginsignin_choice = MainMenuView.logIn_signInMenu();
            switch (loginsignin_choice) {
                case 1: // Log in
                    while (true) {
                        ArrayList<String> username_password = MainMenuView.logInMenu();

                        if (username_password == null) // Bir üst menüye çıkılmak isteniyor
                            break;
                        ArrayList<String> username_infos = DB.check_mail_password(username_password);

                        if (username_infos == null) // Böyle bir kullanıcı yok
                            System.out.println("Mail or password is wrong. Try again...");
                        else {// Kullanıcı log in oldu
                            int user_id = Integer.parseInt(username_infos.get(0));
                            String type = username_infos.get(1);
                            String mail = username_infos.get(2);
                            String password = username_infos.get(3);
                            String project_name = username_infos.get(4);
                            String group_name = username_infos.get(5);


                            if (type.equalsIgnoreCase("E")) {
                                int choice = 0;
                                while (choice != 6) {
                                    choice = MainMenuView.showMainMenu_forEmployee();

                                    switch (choice) {
                                        case 1: // Show My Tasks
                                            ArrayList<ArrayList<String>> tasks = DB.show_all_tasks_filtered_by_employee_id(user_id);
                                            show_tasks_table(tasks);

                                            break;
                                        case 2: // Update Status of My Tasks
                                            System.out.println("Chose a task to make its status 'Done'\n");
                                            ArrayList<ArrayList<String>> tasks_2 = DB.show_all_tasks_filtered_by_employee_id(user_id);
                                            show_tasks_table(tasks_2);

                                            System.out.println("Choice: ");
                                            int task_choice = scanner_int.nextInt();
                                            boolean is_ok = DB.check_is_proper_task_id_and_employee_id(task_choice, user_id);

                                            if (!is_ok) {
                                                System.out.println("You can not update choice task. Because it is not your task!");
                                                break;
                                            }
                                            DB.update_task_status_to_done(task_choice);
                                            break;
                                        case 3: // Take a Task
                                            System.out.println("Chose a task to take\n");
                                            ArrayList<ArrayList<String>> tasks_3 = DB.show_all_tasks_filtered_by_group_name_and_project_name(project_name, group_name);
                                            show_tasks_table(tasks_3);

                                            System.out.println("Choice: ");
                                            int task_choice_2 = scanner_int.nextInt();

                                            boolean is_ok_2 = DB.check_is_task_takeable(task_choice_2, project_name, group_name);

                                            if (!is_ok_2) {
                                                System.out.println("You can not take choice task. Because it is already taken by someone else! " +
                                                        "Or you are not in that group/project!");
                                                break;
                                            } else {
                                                DB.take_a_task(task_choice_2, user_id);
                                            }

                                            break;
                                        case 4: // Show All Tasks of My Group
                                            ArrayList<ArrayList<String>> tasks_4 = DB.show_all_tasks_filtered_by_group_name_and_project_name(project_name, group_name);
                                            show_tasks_table(tasks_4);
                                            break;
                                        case 5: // Show All Notes of My Group
                                            ArrayList<ArrayList<String>> notes = DB.all_notes_filtered_by_project_name_and_group_name(project_name, group_name);
                                            show_notes_table(notes);
                                            break;
                                        case 6: // Quit
                                            System.out.println("Bye bye...");
                                            break;
                                        default:
                                            System.out.println("You type incorrect choice. Try again...");
                                    }
                                }


                            }

                            if (type.equalsIgnoreCase("M")) {
                                int choice = 0;
                                while (choice != 9) {
                                    choice = MainMenuView.showMainMenu_forManager();

                                    switch (choice) {
                                        case 1: // Create a Task
                                            System.out.print("Enter priority of task: ");
                                            int priority = scanner_int.nextInt();

                                            System.out.print("Enter deadline of task as YYYY-MM-DD: ");
                                            String deadline = scanner_string.nextLine();

                                            System.out.print("Enter description of task: ");
                                            String description = scanner_string.nextLine();

                                            DB.create_task(project_name, group_name, user_id, priority, "not done", deadline, description, -1);

                                            break;
                                        case 2: // Update a Task
                                            System.out.println("\nChose a task to update it\n");
                                            ArrayList<ArrayList<String>> tasks = DB.all_tasks_filtered_by_manager_id(user_id);
                                            show_tasks_table(tasks);

                                            System.out.print("Choice:");
                                            int choice_task = scanner_int.nextInt();

                                            System.out.print("Enter new priority of task (to keep same put '-' character): ");
                                            String new_priority = scanner_string.nextLine();

                                            System.out.print("Enter new deadline of task as YYYY-MM-DD (to keep same put '-' character):");
                                            String new_deadline = scanner_string.nextLine();

                                            System.out.print("Enter new description of task (to keep same put '-' character):");
                                            String new_description = scanner_string.nextLine();

                                            if (!DB.update_task(choice_task, user_id, new_priority, new_deadline, new_description))
                                                System.out.println("You can not update choiced task. Or there is no task with that id!");


                                            break;
                                        case 3: // Delete a Task
                                            System.out.println("\nChose a task to delete it\n");
                                            ArrayList<ArrayList<String>> tasks_2 = DB.all_tasks_filtered_by_manager_id(user_id);
                                            show_tasks_table(tasks_2);

                                            System.out.print("Choice:");
                                            int choice_task_2 = scanner_int.nextInt();

                                            if (!DB.detete_task(user_id, choice_task_2))
                                                System.out.println("You can not delete choiced task. Or there is no task with that id!");


                                            break;
                                        case 4: // Show All Tasks That I Created
                                            ArrayList<ArrayList<String>> tasks_3 = DB.all_tasks_filtered_by_manager_id(user_id);
                                            show_tasks_table(tasks_3);

                                            break;
                                        case 5: // Create Note
                                            System.out.print("Enter note text: ");
                                            String note_text = scanner_string.nextLine();

                                            DB.create_note(group_name, project_name, note_text);

                                            break;
                                        case 6: // Delete Note
                                            System.out.println("\nChoose a note to delete\n");
                                            ArrayList<ArrayList<String>> notes = DB.all_notes_filtered_by_project_name_and_group_name(project_name, group_name);
                                            show_notes_table(notes);

                                            System.out.println("Choice: ");
                                            int choice_note = scanner_int.nextInt();

                                            if (!DB.delete_note(group_name, project_name, choice_note))
                                                System.out.println("You can not delete choiced note. Or there is no note with that id!");
                                            break;
                                        case 7: // Update Note
                                            System.out.println("\nChoose a note to update\n");
                                            ArrayList<ArrayList<String>> notes_2 = DB.all_notes_filtered_by_project_name_and_group_name(project_name, group_name);
                                            show_notes_table(notes_2);

                                            System.out.println("Choice: ");
                                            int choice_note_2 = scanner_int.nextInt();

                                            System.out.print("Enter new text of note (to keep same put '-' character):");
                                            String new_text = scanner_string.nextLine();

                                            if (!DB.update_note(group_name, project_name, choice_note_2, new_text))
                                                System.out.println("You can not delete choiced note. Or there is no note with that id!");
                                            break;
                                        case 8: // Show All Notes That I Created
                                            ArrayList<ArrayList<String>> notes_3 = DB.all_notes_filtered_by_project_name_and_group_name(project_name, group_name);
                                            show_notes_table(notes_3);
                                            break;
                                        case 9: // Quit
                                            System.out.println("Bye bye...");
                                            break;
                                        default:
                                            System.out.println("You type incorrect choice. Try again...");
                                    }
                                }
                            }


                        }
                    }


                    break;

                case 2: // Sign in
                    ArrayList<String> register_infos = MainMenuView.signInMenu();
                    if (register_infos == null) // bir üst menüye çıkılmak isteniyor
                        break;
                    DB.create_user(register_infos);
                    break;

                case 3:
                    System.out.println("Bye bye...");
                    break;
                default:
                    System.out.println("You type incorrect choice. Try again...");
            }
        } while (loginsignin_choice != 3);


    }
}
