package com.example.dao;

import com.example.entity.Baoming;
import com.example.entity.Bisai;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface BaomingDao extends Mapper<Baoming> {


    List<Baoming> findBySearch(@Param("params")Params params);
    @Select("select * from baoming where bisaiId = #{bisaiId} and userId = #{userId} limit 1")
    Baoming find(@Param("bisaiId") Integer bisaiId, @Param("userId")Integer userId);
}
