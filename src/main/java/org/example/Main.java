package org.example;

import org.example.Model.*;
import org.example.Util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Session session = null;
    public static void main(String[] args) {
        SessionFactory sessionFactory = HiberanteUtil.getFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();

        main();

        session.getTransaction().commit();
        session.close();
    }


    public static void main(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Library Management System");
        boolean loop = true;
        while(loop) {
            System.out.println("Press 1 for Student");
            System.out.println("Press 2 for Librarian");
            System.out.println("Press 3 for Admin");
            System.out.println("Press 4 for Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    studentOperation();
                    break;
                case 2:
                    librarianOperation();
                    break;
                case 3:
                    adminOperation();
                    break;
                case 4:
                    loop = false;
                    System.out.println("Thanks for visit");
                    break;
                default:
                    System.out.println("Invalid Entry !");
            }
        }
    }

    public static void adminOperation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter User Id");
        String userId = scanner.nextLine();
        System.out.println("Enter Password");
        String password = scanner.nextLine();
        AdminUtil adminUtil = new AdminUtil(session);
        if(!adminUtil.adminLogin(userId, password)){
            System.out.println("Invalid Entry !");
            return;
        }
        boolean loop = true;
        while(loop) {
            System.out.println("Press 1 for Add Librarian");
            System.out.println("Press 2 for Remove Librarian");
            System.out.println("Press 3 for Update Librarian");
            System.out.println("Press 4 for Read All Librarian");
            System.out.println("Press 5 for Search Librarian");
            System.out.println("Press 6 for Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: adminUtil.addLibrarian();
                    break;
                case 2: adminUtil.deleteLibrarianData();
                    break;
                case 3: adminUtil.updateLibrarianData();
                    break;
                case 4: adminUtil.readLibrarianData();
                    break;
                case 5: adminUtil.SearchLibrarianData();
                    break;
                case 6: loop = false;
                    break;
                default:
                    System.out.println("Invalid Entry !");
            }
        }
    }

    public static void studentOperation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Student Info");
        System.out.println("Enter Student Roll Number");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Student Password");
        String password = scanner.nextLine();
        StudentUtil studentUtil = new StudentUtil(session);
        if(!studentUtil.studentLogin(id, password)){
            System.out.println("Invlid Student Roll Number");
            return;
        }
        boolean loop = true;
        while(loop) {
            System.out.println("Press 1 for Read Student Info");
            System.out.println("Press 2 for Issue Book");
            System.out.println("Press 3 for Return Book");
            System.out.println("Press 4 for Search Book");
            System.out.println("Press 5 for Read Book");
            System.out.println("Press 6 for Check Account");
            System.out.println("Press 7 for Back <-");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: studentUtil.readStudent();
                    break;
                case 2: studentUtil.issueBook();
                    break;
                case 3: studentUtil.returnBook();
                    break;
                case 4: studentUtil.searchBook();
                    break;
                case 5: studentUtil.readStudentBook();
                    break;
                case 6: studentUtil.checkAccount();
                    break;
                case 7: loop = false;
                    break;
                default:
                    System.out.println("Invalid Entry !");
            }
        }

    }

    public static void librarianOperation(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Librarian Info");
        System.out.println("Enter Librarian Id ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Librarian password ");
        String password = scanner.nextLine();
        LibrarianUtil librarianUtil = new LibrarianUtil(session);
        if(!librarianUtil.librarianLogin(id, password)){
            System.out.println("Invalid Librarian Id");
            return;
        }
        boolean loop = true;
        while(loop) {
            System.out.println("Press 1 for Read Librarian Data");
            System.out.println("Press 2 for Add Book");
            System.out.println("Press 3 for Update Book");
            System.out.println("Press 4 for Delete Book");
            System.out.println("Press 5 for Read Book");
            System.out.println("Press 6 for Search Book");
            System.out.println("Press 7 for Display Student Data");
            System.out.println("Press 8 for Add Student");
            System.out.println("Press 9 for Delete Student");
            System.out.println("Press 10 for Update Student");
            System.out.println("Press 11 for Back <-");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1: librarianUtil.readLibrarianData();
                    break;
                case 2: librarianUtil.addBook();
                    break;
                case 3: librarianUtil.updateBook();
                    break;
                case 4: librarianUtil.deleteBook();
                    break;
                case 5: librarianUtil.readBook();
                    break;
                case 6: librarianUtil.searchBook();
                    break;
                case 7: librarianUtil.displayStudentData();
                    break;
                case 8: librarianUtil.addStudentData();
                    break;
                case 9: librarianUtil.deleteStudentData();
                    break;
                case 10: librarianUtil.updateStudentData();
                    break;
                case 11: loop = false;
                    break;
                default:
                    System.out.println("Invalid Entry !");
            }
        }
    }

/*
    public static void insertStudent(Session session){
        Student samad = new Student(101, "Samad", 11);
        Student rohit = new Student(102, "Rohit", 10);
        Student bhupendra = new Student(105, "Bhipendra", 10);
        Student aman = new Student(106, "Aman", 9);
        Student abhishek = new Student(108, "Abhishek", 12);


        Book b1 = new Book("The Lost Tribe", "John Smyth", 300, 12);
        Book b2 = new Book("How to Sew Buttons", "Eleanor Mellors", 500, 10);
        Book b3 = new Book("The Terrible Night","John Smyth", 350, 8);
        Book b4 = new Book("Mindy''s Mittens",  "Jane Do", 400, 11);
        Book b5 = new Book("Pizza and Donuts Diet","Eleanor Mellors", 480, 15);
        session.save(b1);
        session.save(b2);
        session.save(b3);
        session.save(b4);
        session.save(b5);

        Account a1 = new Account(3, 4, 1);
        Account a2 = new Account(0, 2, 0);
        Account a3 = new Account(2, 5, 2);
        Account a4 = new Account(1, 1, 0);
        Account a5 = new Account(0, 1, 0);

        samad.addAccount(a1);
        rohit.addAccount(a2);
        bhupendra.addAccount(a3);
        aman.addAccount(a4);
        abhishek.addAccount(a5);
        
        samad.addBook(b1, MyDate.toDate(LocalDate.of(2023, 4,3)));
        rohit.addBook(b2, MyDate.toDate(LocalDate.of(2023, 4,3)));
        bhupendra.addBook(b3, MyDate.toDate(LocalDate.of(2023, 3, 29)));
        aman.addBook(b4, MyDate.toDate(LocalDate.of(2023, 4,1)));
        abhishek.addBook(b5, MyDate.toDate(LocalDate.of(2023, 3,28)));

        session.save(a1);
        session.save(a2);
        session.save(a3);
        session.save(a4);
        session.save(a5);

        session.save(samad);
        session.save(rohit);
        session.save(bhupendra);
        session.save(aman);
        session.save(abhishek);

    }

 */

}