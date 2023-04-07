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

    public void readLibrarianData(){
        System.out.println(librarian);
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
        Query query = session.createQuery("from Book where id=:id");
        query.setLong("id", id);
        Book book = (Book) query.uniqueResult();
        List<StudentBook> items = book.getStudentBooks();
        for(StudentBook item: items){
            if(item.getBook().getId() == id){
                session.delete(item);
            }
        }

        session.delete(book);

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

    public void searchbyRoll(){
        System.out.println("Enter Student Roll Number ");
        int roll = scanner.nextInt();
        Query query = session.createQuery("from Student where rollNumber = :roll");
        query.setLong("roll", roll);
        Student student = (Student)query.uniqueResult();
        System.out.println(student);
    }

    public void reportLostBook(){
        System.out.println("Enter Student Roll Number ");
        int roll = scanner.nextInt();
        Query query = session.createQuery("from Student where rollNumber = :roll");
        query.setLong("roll", roll);
        Student student = (Student)query.uniqueResult();
        System.out.println("Enter Book Id");
        int id = scanner.nextInt();
        query = session.createQuery("from Book where id=:id");
        query.setLong("id",id);
        Book book = (Book)query.uniqueResult();
        student.getAccount().setNoLostBook(student.getAccount().getNoLostBook()+1);
        student.getAccount().setFineAmount(student.getAccount().getFineAmount() + book.getPrice());

    }

    public void displayStudentData(){
        Query query = session.createQuery("from Student");
        List<Student> studentList = query.list();
        for(Student student: studentList){
            System.out.println(student);
            student.checkAccount();
            for(StudentBook studentBook : student.getStudentBooks()){
                System.out.println(studentBook.getBook());
                System.out.println("Issue Date " + studentBook.getIssue_date() + ", Return Date " + studentBook.getReturn_date());
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
//
//        query = session.createQuery("from StudentBook where student_id=:id");
//        query.setLong("id", student.getId());
        List<StudentBook> items = student.getStudentBooks();
        for(StudentBook item: items){
            if(item.getStudent().getId() == student.getId()){
                session.delete(item);

            }
        }

        session.delete(student);
    }

    public void updateStudentData(){
        System.out.print("Enter target data (name/class) ");
        String target = scanner.nextLine();
        System.out.print("Enter value for " + target + " ");
        String value = scanner.nextLine();
        System.out.println("Enter roll Number ");
        int roll = scanner.nextInt();
        Query query = null;
        if(target.equalsIgnoreCase("class")) {
            target = "classNumber";
        }
        query = session.createQuery("update Student set "+ target + " = :newValue where  rollNumber = :roll");
        query.setLong("roll", roll);
        if(target == "name")
            query.setString("newValue", value);
        else
            query.setLong("newValue", Integer.parseInt(value));
        int result = query.executeUpdate();;
        if(result > 0){
            System.out.println("\nUpdate SuccessfulL !");
        } else {
            System.out.println("\nUpdate Unsuccessfull !");
        }
    }

}
