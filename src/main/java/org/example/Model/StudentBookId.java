package org.example.Model;


import javax.persistence.*;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentBookId implements Serializable {
    @Column(name = "student_id")
    public int studentId;

    @Column(name = "book_id")
    private int bookId;

    StudentBookId(){}

    StudentBookId(int studentId, int bookId){
        this.studentId = studentId;
        this.bookId = bookId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        StudentBookId that = (StudentBookId) o;
        return Objects.equals(studentId, that.studentId) &&
                Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, bookId);
    }



}
