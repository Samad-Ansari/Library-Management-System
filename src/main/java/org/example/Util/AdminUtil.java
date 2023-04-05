package org.example.Util;

import org.example.Model.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdminUtil {
    private Session session;
    private Admin admin;
    private Scanner scanner = new Scanner(System.in);

    public AdminUtil(Session session){
        this.session = session;
    }

    public boolean adminLogin(String userId, String password){
        Query query = session.createQuery("from Admin where userId= :userId and password = :password");
        query.setString("userId", userId);
        query.setString("password", password);
        this.admin = (Admin) query.uniqueResult();
        if(this.admin == null){
            return false;
        }
        return true;
    }

    public void addLibrarian(){
        System.out.println("Enter Emp Id");
        int empid = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter name");
        String name = scanner.nextLine();
        Librarian librarian = new Librarian(name, empid);
        session.save(librarian);
    }

    public void readLibrarianData(){
        Query query = session.createQuery("from Librarian");
        List<Librarian> librarianList = query.list();
        for(Librarian librarian: librarianList){
            System.out.println(librarian);
        }
    }

    public void SearchLibrarianData(){
        System.out.println("Enter Librarian id ");
        int id = scanner.nextInt();
        Query query = session.createQuery("from Librarian where empId = :id");
        query.setLong("id", id);
        Librarian librarian = (Librarian) query.uniqueResult();
        System.out.println(librarian);
    }

    public void deleteLibrarianData(){
        System.out.println("Enter Librarian id ");
        int id = scanner.nextInt();
        Query query = session.createQuery("delete from Librarian where empId = :id");
        query.setLong("id", id);
        query.executeUpdate();
    }

    public void updateLibrarianData(){
        scanner = new Scanner(System.in);
        System.out.println("Enter New Name ");
        String value = scanner.nextLine();
        System.out.println("Enter empId ");
        int empId = scanner.nextInt();
        Query query = session.createQuery("update Librarian set name = :value where  empId = :empId");
        query.setString("value", value);
        query.setLong("empId", empId);
        int result = query.executeUpdate();;
        if(result > 0){
            System.out.println("\nUpdate SuccessfulL !");
        } else {
            System.out.println("\nUpdate Unsuccessfull !");
        }
        query.executeUpdate();
    }

}
