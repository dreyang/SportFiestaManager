package com.example.dao;

import com.example.entity.Bisai;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface BisaiDao extends Mapper<Bisai> {


    List<Bisai> findBySearch(@Param("params")Params params);
    @Select("select bisai.*, gonggao.name as typeName from bisai left join gonggao on bisai.typeId = gonggao.id")
    List<Bisai> findAll();
}
