package com.example.entity;

import javax.persistence.*;

@Table(name = "bisai")
public class Bisai {

    @Id
    //主键id自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //C注解：方便与数据字段映射
    @Column(name = "name")
    private  String name;
    @Column(name = "riqi")
    private  String riqi;
    @Column(name = "didian")
    private  String didian;
    @Column(name = "renshu")
    private  Integer renshu;
    @Column(name = "num")
    private  Integer num;
    @Column(name = "img")
    private  String img;
    @Column(name = "typeId")
    private  String typeId;
    @Transient
    private String typeName;


    public Integer getId() {
        return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }

    public String getDidian() {
        return didian;
    }

    public void setDidian(String didian) {
        this.didian = didian;
    }

    public Integer getRenshu() {
        return renshu;
    }

    public void setRenshu(Integer renshu) {
        this.renshu = renshu;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
