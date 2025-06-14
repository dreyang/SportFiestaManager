package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.dao.AdminDao;
import com.example.dao.BaomingDao;
import com.example.dao.BisaiDao;

import com.example.entity.Admin;
import com.example.entity.Baoming;
import com.example.entity.Bisai;

import com.example.entity.Params;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class BaomingService {
    @Resource
    private BaomingDao baomingDao;
    @Resource
    private BisaiDao bisaiDao;
    @Resource
    private AdminDao adminDao;

    public PageInfo<Baoming> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Baoming> list = baomingDao.findBySearch(params);
        if (CollectionUtil.isEmpty(list)){
            return PageInfo.of(new ArrayList<>());
        }
        for (Baoming baoming : list){
            if (ObjectUtil.isNotEmpty(baoming.getBisaiId())){
                Bisai bisai = bisaiDao.selectByPrimaryKey(baoming.getBisaiId());
                if (ObjectUtil.isNotEmpty(bisai)){
                    baoming.setBisaiName(bisai.getName());
                }
            }
            if (ObjectUtil.isNotEmpty(baoming.getUserId())){
                Admin admin = adminDao.selectByPrimaryKey(baoming.getUserId());
                if (ObjectUtil.isNotEmpty(admin)){
                    baoming.setUserName(admin.getName());
                }

            }
        }
        return PageInfo.of(list);
    }

    public void add(Baoming baoming) {
        baomingDao.insertSelective(baoming);
    }

    public void update(Baoming baoming) {
        baomingDao.updateByPrimaryKeySelective(baoming);
    }

    public void delete(Integer id) {
        baomingDao.deleteByPrimaryKey(id);
    }

    public Baoming find(Integer bisaiId, Integer userId) {
        return baomingDao.find(bisaiId,userId);
    }
}
