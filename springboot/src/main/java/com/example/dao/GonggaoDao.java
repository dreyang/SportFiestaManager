package com.example.dao;


import com.example.entity.Gonggao;

import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface GonggaoDao extends Mapper<Gonggao> {


    List<Gonggao> findBySearch(@Param("params") Params params);
}
