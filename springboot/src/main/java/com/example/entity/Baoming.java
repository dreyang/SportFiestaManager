package com.example.entity;

import javax.persistence.*;

@Table(name = "baoming")
public class Baoming {

    @Id
    //主键id自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //C注解：方便与数据字段映射
    @Column(name = "bisaiId")
    private  Integer bisaiId;
    @Column(name = "userId")
    private  Integer userId;
    @Column(name = "status")
    private  String status;
    @Column(name = "time")
    private  String time;
    @Transient
    private String bisaiName;
    @Transient
    private String userName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBisaiId() {
        return bisaiId;
    }

    public void setBisaiId(Integer bisaiId) {
        this.bisaiId = bisaiId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBisaiName() {
        return bisaiName;
    }

    public void setBisaiName(String bisaiName) {
        this.bisaiName = bisaiName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
