package org.example.Model;

import javax.persistence.*;

@Entity()
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    int id;
    private int noBorrowedBook;
    private int noReturnedBook;
    private int noLostBook;
    private int fineAmount;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    public Account(){}

    public Account(int noBorrowedBook, int noReturnedBook, int noLostBook) {
        this.noBorrowedBook = noBorrowedBook;
        this.noReturnedBook = noReturnedBook;
        this.noLostBook = noLostBook;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoBorrowedBook() {
        return noBorrowedBook;
    }

    public void setNoBorrowedBook(int noBorrowedBook) {
        this.noBorrowedBook = noBorrowedBook;
    }

    public int getNoReturnedBook() {
        return noReturnedBook;
    }

    public void setNoReturnedBook(int noReturnedBook) {
        this.noReturnedBook = noReturnedBook;
    }

    public int getNoLostBook() {
        return noLostBook;
    }

    public void setNoLostBook(int noLostBook) {
        this.noLostBook = noLostBook;
    }

    public void calculatefine(){
        fineAmount = 100;
    }

    @Override
    public String toString() {
        return "Account { " +
                "id = " + id +
                ", noBorrowedBook = " + noBorrowedBook +
                ", noReturnedBook = " + noReturnedBook +
                ", noLostBook = " + noLostBook +
                ", fineAmount = " + fineAmount +
                " }";
    }
}
