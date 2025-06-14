package com.example.service;

import com.example.dao.BisaiDao;
import com.example.entity.Admin;
import com.example.entity.Bisai;
import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BisaiService {
    @Resource
    private BisaiDao bisaiDao;


    public PageInfo<Bisai> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Bisai> list = bisaiDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Bisai bisai) {
        bisaiDao.insertSelective(bisai);
    }

    public void update(Bisai bisai) {
        bisaiDao.updateByPrimaryKeySelective(bisai);
    }

    public void delete(Integer id) {
        bisaiDao.deleteByPrimaryKey(id);
    }

    public List<Bisai> findAll() {
        return bisaiDao.findAll();
    }
}
