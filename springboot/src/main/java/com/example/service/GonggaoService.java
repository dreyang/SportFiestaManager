package com.example.service;


import com.example.dao.GonggaoDao;
import com.example.entity.Bisai;
import com.example.entity.Gonggao;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GonggaoService {

    @Resource
    private GonggaoDao gonggaoDao;

    public void add(Gonggao gonggao) {
        gonggaoDao.insertSelective(gonggao);
    }

    public void update(Gonggao gonggao) {
        gonggaoDao.updateByPrimaryKeySelective(gonggao);
    }

    public PageInfo<Gonggao> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Gonggao> list = gonggaoDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void delete(Integer id) {
        gonggaoDao.deleteByPrimaryKey(id);
    }

    public List<Gonggao> findAll() {
        return gonggaoDao.selectAll();
    }
}
