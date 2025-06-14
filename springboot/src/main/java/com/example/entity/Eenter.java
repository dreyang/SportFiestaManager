package com.example.entity;

import javax.persistence.*;

@Table(name = "eenter")
public class Eenter {

    @Id
    //主键id自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "grade")
    private  String grade;
    @Column(name = "xiangmu")
    private  String xiangmu;

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    @Column(name = "mingci")
    private  String mingci;
    @Column(name = "typeid")
    private Integer typeid;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getXiangmu() {
        return xiangmu;
    }

    public void setXiangmu(String xiangmu) {
        this.xiangmu = xiangmu;
    }

    public String getMingci() {
        return mingci;
    }

    public void setMingci(String mingci) {
        this.mingci = mingci;
    }
}
