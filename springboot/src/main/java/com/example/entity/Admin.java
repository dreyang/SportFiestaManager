package com.example.entity;

import javax.persistence.*;

@Table(name = "admin")
public class Admin {

    @Id
    //主键id自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //C注解：方便与数据字段映射
    @Column(name = "name")
    private  String name;
    @Column(name = "password")
    private  String password;
    @Column(name = "sex")
    private  String sex;
    @Column(name = "age")
    private  Integer age;
    @Column(name = "phone")
    private  String phone;
    @Column(name = "role")
    private  String role;
    @Transient //T注解：告诉后台，这不是跟数据库映射的字段，防止报错
    private String token;
    @Transient
    private String verCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
