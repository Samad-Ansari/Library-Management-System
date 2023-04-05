package org.example.Util;

import org.example.Model.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class LibrarianUtil {
    private Session session;
    private Librarian librarian;
    private Scanner scanner = new Scanner(System.in);

    public LibrarianUtil(Session session){
        this.session = session;
    }

    public boolean librarianLogin(int empId, String password){
        this.session = session;
        Query query = session.createQuery("from Librarian where empId= :empId and password = :password");
        query.setLong("empId", empId);
        query.setString("password", password);
        this.librarian = (Librarian) query.uniqueResult();
        if(this.librarian == null){
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
        System.out.println(this.librarian);
    }

    public void deleteLibrarianData(){
        System.out.println("Enter Librarian id ");
        int id = scanner.nextInt();
        Query query = session.createQuery("delete from Librarian where id = :id");
        query.setLong("id", id);
        query.executeUpdate();
    }

    public void updateLibrarianData(){
        System.out.println("Enter new Name ");
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
    }

    public void addBook(){
        System.out.println("Enter Book Title");
        String title = scanner.nextLine();
        System.out.println("Enter Book author");
        String author = scanner.nextLine();
        System.out.println("Enter Book price");
        int price = scanner.nextInt();
        System.out.println("Enter Book stock");
        int stock = scanner.nextInt();

        Book b = new Book(title, author, price, stock);
        session.save(b);
    }

    public void deleteBook(){
        System.out.println("Enter Book Id");
        int id = scanner.nextInt();
        Query query = session.createQuery("from StudentBook where book_id=:id");
        query.setLong("id", id);
        List<StudentBook> items = (List<StudentBook>) query.list();
        for(StudentBook item: items){
            if(item.getBook().getId() == id){
                query = session.createQuery("delete from StudentBook where book_id = :id");
                query.setLong("id", item.getBook().getId());
                query.executeUpdate();
            }
        }

        query = session.createQuery("delete from Book where id = :id");
        query.setLong("id", id);
        query.executeUpdate();

    }

    public void updateBook(){
        System.out.println("Enter target data ");
        String target = scanner.nextLine();
        System.out.println("Enter value ");
        String value = scanner.nextLine();
        System.out.println("Enter book id");
        int id = scanner.nextInt();
        Query query = session.createQuery("update from Book set " + target + " = :value where id=:id");
        if(target.equalsIgnoreCase("price") || target.equalsIgnoreCase("stock")){
            query.setLong("value", Integer.parseInt(value));
        }
        else
            query.setString("value", value);
        query.setLong("id", id);
        int result = query.executeUpdate();;
        if(result > 0){
            System.out.println("\nDeleted SuccessfulL !");
        } else {
            System.out.println("\nDeleted Unsuccessfull !");
        }
    }

    public void readBook(){
        Query query = session.createQuery("from Book");
        List<Object> list = query.list();
        for(Object item : list){
            System.out.println(item);
        }
    }

    public void searchBook(){
        Query query = session.createQuery("from Book");
        List<Book> list = (List<Book>) query.list();
        System.out.println("Enter Book name ");
        String target = scanner.nextLine();
        for(Book item : list){
            if(item.getTitle().toLowerCase().contains(target)){
                System.out.println(item);
            }
        }
    }

    public void displayStudentData(){
        Query query = session.createQuery("from Student");
        List<Student> studentList = query.list();
        for(Student student: studentList){
            System.out.println(student);
            student.checkAccount();
            for(StudentBook studentBook : student.getBooks()){
                System.out.println(studentBook.getBook());
            }
        }
    }

    public void addStudentData(){

        System.out.println("Enter user rollNumber");
        int rollNumber = scanner.nextInt();
        System.out.print("Enter user name");
        String inputName = scanner.nextLine();
        System.out.print("Enter Student class");
        int inputClass = scanner.nextInt();
        System.out.print("Enter Student password");
        String password = scanner.nextLine();


        Student student = new Student(rollNumber, inputName, inputClass, password);
        Account account = new Account(0,0,0);
        student.setAccount(account);
        account.setStudent(student);
        session.save(student);

    }

    public void deleteStudentData(){
        System.out.println("Enter Student roll number");
        int roll = scanner.nextInt();

        Query query = session.createQuery("from Student where rollNumber = :roll");
        query.setLong("roll",roll);
        Student student = (Student) query.uniqueResult();

        query = session.createQuery("from StudentBook where student_id=:id");
        query.setLong("id", student.getId());
        List<StudentBook> items = (List<StudentBook>) query.list();
        for(StudentBook item: items){
            if(item.getStudent().getId() == student.getId()){
                query = session.createQuery("delete from StudentBook where student_id = :id");
                query.setLong("id", item.getStudent().getId());
            }
        }

        session.delete(student);

    }

    public void updateStudentData(){
        System.out.print("Enter target data (name/className) ");
        String target = scanner.nextLine();
        System.out.print("Enter value for " + target + " ");
        String value = scanner.nextLine();
        System.out.println("Enter roll Number ");
        int roll = scanner.nextInt();
        Query query = null;
        query = session.createQuery("update Student set "+ target + " = :newValue where  rollNumber = :roll");
        query.setLong("roll", roll);
        query.setString("newValue", value);
        int result = query.executeUpdate();;
        if(result > 0){
            System.out.println("\nUpdate SuccessfulL !");
        } else {
            System.out.println("\nUpdate Unsuccessfull !");
        }
    }

}
