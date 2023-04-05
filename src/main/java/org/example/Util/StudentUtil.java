package org.example.Util;

import org.example.Model.Account;
import org.example.Model.Book;
import org.example.Model.Student;
import org.example.Model.StudentBook;
import org.hibernate.Query;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StudentUtil {
    private Session session;
    private Scanner scanner = new Scanner(System.in);
    private Student student = null;

    public StudentUtil(Session session){
        this.session = session;
    }

    public boolean studentLogin(int rollNumber, String password){
        Query query = session.createQuery("from Student where rollNumber= :roll and password = :password");
        query.setLong("roll", rollNumber);
        query.setString("password", password);
        this.student = (Student) query.uniqueResult();
        if(this.student == null){
            return false;
        }
        return true;
    }

    public void returnBook(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Return Book id");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter issue date (dd/mm/yyyy)");
        String returndate  = scanner.nextLine();
        String[] dates = returndate.split("/");
        List<StudentBook> studentBooks = student.getBooks();
        for(StudentBook studentBook: studentBooks){
            if(studentBook.getBook().getId() == bookId){
                studentBook.setReturn_date(MyDate.toDate(LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]))));
                studentBook.getBook().setStock(studentBook.getBook().getStock()+1);
            }
        }

        student.getAccount().setNoReturnedBook(student.getAccount().getNoReturnedBook()+1);
    }

    public void readStudent(){
        System.out.println(student);
        readStudentBook();
        checkAccount();
    }

    public void readStudentBook(){
        System.out.println("Student Books");
        List<StudentBook> studentBooks = student.getBooks();
        for(StudentBook studentBook: studentBooks){
            System.out.println(studentBook.getBook());
        }

    }

    public void checkAccount(){
        System.out.println("Student Account");
        Account account = student.getAccount();
        System.out.println(account);
    }

    public void issueBook(){
        System.out.print("Enter book id ");
        int id = scanner.nextInt();
        Query query = session.createQuery("from Book where id=:id");
        query.setLong("id", id);
        Book book = (Book) query.uniqueResult();
        book.setStock(book.getStock() - 1);
        scanner.nextLine();
        System.out.println("Enter issue date (dd/mm/yyyy)");
        String issuedate  = scanner.nextLine();
        String[] dates = issuedate.split("/");
        Date date = MyDate.toDate(LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0])));
        student.addBook(book, date);
        student.getAccount().setNoBorrowedBook(student.getAccount().getNoBorrowedBook()+1);
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

}
