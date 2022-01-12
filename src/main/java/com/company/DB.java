package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DB {

    public static ArrayList<String> check_mail_password(ArrayList<String> mail_password) {
        // username_password'in 0. indexi username, 1. indexi password
        // DB'de sorgula böyle biri varmı yok mu diye. Eğer varsa bütün colonları arraylist içinde dönder.
        // Yoksa null dönder
        if (mail_password.get(0).equalsIgnoreCase(""))
            return null;
        return new ArrayList<String>();
    }

    public static void create_user(ArrayList<String> register_infos) {
        // Verdiğim kolon bilgileriyle yeni bir user yarat
    }

    public static ArrayList<String> distinct_all_project_names() {
        // bütün distinct project_name'lerin listesini dönder
        // eğer hiç proje yoksa içi boş bir arraylist dönder
        return new ArrayList<String>();
    }

    public static ArrayList<String> distinct_all_group_names_according_to_project(String project_name) {
        // Project_Group tablosu üzerinde WHERE project_name == project_name olan bütün group_name'leri
        // arraylist olarak distinc bir şekilde dönder. Eğer group yoksa içi boş arraylist dönder
        return new ArrayList<String>();
    }

    public static boolean is_exist_project(String project_name) {
        // Verilen isimde bir proje daha önceden tabloda var mı diye kontrol et
        // Eğer varsa true, yoksa false dönder
        return false;
    }

    public static boolean is_group_exist_in_project(String project_name, String group_name) {
        // Verilen isimde bir proje ve grup daha önceden tabloda var mı diye kontrol et
        // Eğer varsa true, yoksa false dönder
        return false;
    }


    public static ArrayList<ArrayList<String>> show_all_tasks_filtered_by_employee_id(int employee_id) {
        // task tablosunda verilen employee_id'ye sahip olan bütün taskları 2d arraylist olarak dönder
        return new ArrayList<ArrayList<String>>();

    }

    public static ArrayList<ArrayList<String>> show_all_tasks_filtered_by_group_name_and_project_name(String project_name, String group_name) {
        // task tablosunda verilen group_name'e ve project_name'e sahip olan bütün taskları 2d arraylist olarak dönder
        return new ArrayList<ArrayList<String>>();

    }


    public static boolean check_is_proper_task_id_and_employee_id(int task_id, int employee_id) {
        // eğer verilen task_id'ye sahip olan task'ın employee_id'si uyuşuyorsa return true
        // uyuşmuyorsa return false
        // eğer verilen task_id task tablosunda yoksa return false
        return true;
    }

    public static void update_task_status_to_done(int task_id) {
        // task_id'si verilen id'ye eşit olan task'ın status'unu "Done" olarak değiştir
    }


    public static boolean check_is_task_takeable(int task_id, String project_name, String group_name) {
        // Eğer verilen task_id'ye sahip olan taskın project_name ve group_name'i aynıyse ve
        // henüz bir employee tarafından alınmadıysa return true
        // diğer durumlarda return false
        return true;
    }

    public static ArrayList<ArrayList<String>> all_notes_filtered_by_project_name_and_group_name(String project_name, String group_name) {
        // verilen project_name ve group_name'e sahip olan bütün notları 2d arraylist olarak dönder
        return new ArrayList<ArrayList<String>>();
    }

    public static void create_task(String project_name, String group_name, int user_id, int priority, String status, String deadline, String description, int employee_id) {
        // Input şu şekilde gelecek : DB.create_task(project_name, group_name, user_id, priority, "not done",deadline, description, -1);
        // employee_id yerine -1 gönderdim.
        // Kafana göre takıl
    }

    public static ArrayList<ArrayList<String>> all_tasks_filtered_by_manager_id(int manager_id) {
        // task'lar tablosunda manager_id'sine göre filtreme yap ve sonuçları 2d arraylist olarak returnla
        return new ArrayList<ArrayList<String>>();
    }

    public static boolean detete_task(int manager_id, int task_id) {
        // Eğer verilen task_id'ye sahip bir task varsa ve manager_id'si manager_id'ye eşitse sil ve true döndür
        // diğer durumlarda false dönder (manager id farklıysa veya böyle bir task hiç yoksa)
        return true;

    }

    public static boolean update_task(int task_id, int manager_id, int new_priority, String new_deadline, String new_description) {
        //Eğer verilen task_id'ye sahip bir task varsa ve manager_id'si manager_id'ye eşitse update et ve true döndür
        // diğer durumlarda false döndür
        return true;
    }


    public static void create_note(String group_name, String project_name, String note_text) {
        // Her ne kadar yaratmak Alah'a mahsus olsa da sen de çok güzel bir şekilde
        // verilen bilgilere bağlı olarak note yaratabilirsin diye düşünüyorum
    }

    public static boolean delete_note(String group_name, String project_name, int note_id) {
        //Eğer verilen note_id'ye sahip bir note varsa ve grup'la proje'de uyuşuyorsa sil ve true döndür
        // diğer durumlarda false döndür
        return true;
    }

    public static boolean update_note(String group_name, String project_name, int note_id, String new_text) {
        //Eğer verilen note_id'ye sahip bir note varsa ve grup'la proje'de uyuşuyorsa new_text ile update et ve true döndür
        // diğer durumlarda false döndür
        return true;
    }

}
