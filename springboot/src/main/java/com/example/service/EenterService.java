package com.example.service;


import cn.hutool.core.date.DateUtil;
import com.example.dao.EenterDao;

import com.example.entity.Admin;
import com.example.entity.Eenter;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EenterService {

    @Resource
    private EenterDao eenterDao;


    public void add(Eenter eenter) {
        eenterDao.insertSelective(eenter);
    }

    public PageInfo<Eenter> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Eenter> list = eenterDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void update(Eenter eenter) {
        eenterDao.updateByPrimaryKeySelective(eenter);
    }

    public void delete(Integer id) {
        eenterDao.deleteByPrimaryKey(id);
    }
}
