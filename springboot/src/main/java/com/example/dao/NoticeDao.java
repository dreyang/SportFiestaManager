package com.example.dao;


import com.example.entity.Notice;
import com.example.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface NoticeDao extends Mapper<Notice> {


    List<Notice> findBySearch(@Param("params") Params params);

    @Select("select * from notice order by time desc limit 4")
    List<Notice> findTop3();
}
