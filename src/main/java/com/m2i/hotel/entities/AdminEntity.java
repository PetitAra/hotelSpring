package com.m2i.hotel.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin", schema = "hotel", catalog = "")
public class AdminEntity {
    private int id;
    private String username;
    private String password;
    private String role;
    private String photouser;


    public AdminEntity() {
    }

    public AdminEntity(int id, String username, String password, String role, String photouser) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.photouser = photouser;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotouser() {
        return photouser;
    }

    public void setPhotouser(String photouser) {
        this.photouser = photouser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return id == that.id && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(role, that.role) && Objects.equals(photouser, that.photouser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role, photouser);
    }
}
