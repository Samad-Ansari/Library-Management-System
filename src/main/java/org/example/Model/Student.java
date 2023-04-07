package org.example.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private int rollNumber;
    @Column(unique = true, nullable = false)
    private String password;
    private String name;
    private int classNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(mappedBy = "student")
    private List<StudentBook> studentBooks = new ArrayList<>();

    public List<StudentBook> getStudentBooks() {
        return studentBooks;
    }

    public void setStudentBooks(List<StudentBook> studentBooks) {
        this.studentBooks = studentBooks;
    }

    Student(){}
    public Student(int rollNumber, String name, int classNumber, String password) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.classNumber = classNumber;
        this.password = password;
    }

    public void addAccount(Account account){
        this.account = account;
        this.account.setStudent(this);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void checkAccount(){
        System.out.println(account);
    }

    @Override
    public String toString() {
        return "Student { " +
                " id = " + id +
                ", rollNumber = " + rollNumber +
                ", name = '" + name + '\'' +
                ", classNumber = " + classNumber +
                " }";
    }
}
