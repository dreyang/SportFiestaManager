package com.example.dao;

import com.example.entity.Eenter;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface EenterDao extends Mapper<Eenter> {


    List<Eenter> findBySearch(@Param("params") Params params);
}
