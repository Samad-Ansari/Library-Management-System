package org.example.Model;

import javax.persistence.*;

@Entity()
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true, nullable = false)
    private String userId;
    @Column(unique = true, nullable = false)
    private String password;

    Admin(){}

    public Admin(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id = " + id +
                ", userId = '" + userId + '\'' +
                ", password = '" + password + '\'' +
                '}';
    }
}
