package com.example.entity;

import cn.hutool.core.annotation.Alias;

import javax.persistence.*;

@Table(name = "gonggao")
public class Gonggao {

    @Id
    //主键id自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //C注解：方便与数据字段映射
    @Alias("分类名称")
    @Column(name = "name")
    private  String name;
    @Alias("注意事项")
    @Column(name = "zhuyi")
    private  String zhuyi;
    @Alias("公告信息")
    @Column(name = "xinxi")
    private  String xinxi;

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

    public String getZhuyi() {
        return zhuyi;
    }

    public void setZhuyi(String zhuyi) {
        this.zhuyi = zhuyi;
    }

    public String getXinxi() {
        return xinxi;
    }

    public void setXinxi(String xinxi) {
        this.xinxi = xinxi;
    }
}
