package org.example.Util;

import org.example.Model.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StudentUtil {
    private Session session;
    private Scanner scanner = new Scanner(System.in);
    private Student student = null;
    private final int FINEPERLOST = 5;
    private final int BOOKISSUEDAYS  = 20;

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
        LocalDate localDate = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0]));
        Date returnDate = MyDate.toDate(localDate);
        List<StudentBook> studentBooks = student.getStudentBooks();
        for(StudentBook studentBook: studentBooks){
            if(studentBook.getBook().getId() == bookId){
                studentBook.getBook().setStock(studentBook.getBook().getStock()+1);

                // compare issue date and return date
                int days = (int)ChronoUnit.DAYS.between(MyDate.toLocalDate(studentBook.getIssue_date()), localDate);
                if(days > BOOKISSUEDAYS){
                    System.out.println("You have return book " + (days-BOOKISSUEDAYS) + " days late ");
                    int fine = ((days-BOOKISSUEDAYS) * FINEPERLOST);
                    System.out.println("Fine : " + fine);
                    student.getAccount().setFineAmount(student.getAccount().getFineAmount() + ((days-BOOKISSUEDAYS) * FINEPERLOST));
                }

                studentBook.setReturn_date(returnDate);
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
        List<StudentBook> studentBooks = student.getStudentBooks();
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
        scanner.nextLine();
        System.out.println("Enter issue date (dd/mm/yyyy)");
        String issuedate  = scanner.nextLine();
        String[] dates = issuedate.split("/");
        Date date = MyDate.toDate(LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]), Integer.parseInt(dates[0])));
        for(StudentBook studentBook : student.getStudentBooks()){
            if(studentBook.getBook().getId() == id && studentBook.getReturn_date() == null){
                System.out.println("You have already Issued this book");
                return;
            }
        }
        book.setStock(book.getStock() - 1);
        StudentBook sb = new StudentBook(student, book, date);
        student.getStudentBooks().add(sb);
        book.getStudentBooks().add(sb);
        student.getAccount().setNoBorrowedBook(student.getAccount().getNoBorrowedBook()+1);
        session.save(sb);
        System.out.println("Book Issued !");
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
