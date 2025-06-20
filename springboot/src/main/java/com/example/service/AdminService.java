package com.example.service;

import com.example.common.JwtTokenUtils;
import com.example.dao.AdminDao;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;

    public List<Admin> findAll(){
        return adminDao.selectAll();
    }

    public PageInfo<Admin> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<Admin> list = adminDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        //用户名一定要有，否则不让新增，因为后续需要用户名登录
        if (admin.getName() == null || "".equals(admin.getName()) ){
            throw new CustomException("用户名不能为空");}

        Admin user = adminDao.findByName(admin.getName());
        if (user != null){
            //说明已经有这个月用户名了，就要提示前台不允许新增了
            throw new CustomException("该用户名已存在,请更换用户名");
            }
        //如果注册时没输入密码，默认给一个密码123456
        if (admin.getPassword()== null){
            admin.setPassword("123456");
        }
        adminDao.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    public void delete(Integer id) {
        adminDao.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
        //1.非空判断
        if (admin.getName() == null || "".equals(admin.getName()) ){
            throw new CustomException("用户名不能为空");}
        if (admin.getPassword() == null || "".equals(admin.getPassword()) ){
            throw new CustomException("密码不能为空");}
        //2.从数据库里面根据这个用户名和密码去查询对应的管理员信息，而且输入的用户名和密码都对
        Admin user = adminDao.findByNameAndPassword(admin.getName(),admin.getPassword());
            if (user == null){
                //说明没有查询出来，说明输入用户名密码错误提示不许登录
                throw new CustomException("用户名或密码输入错误");
            }
            //如果查询出来了，有
        //生成登录用户对应的token，然后跟着user一起返回到前台（使用jwt鉴权功能：保护数据的安全：详细查看common/jwttokenutils文件）
        String token = JwtTokenUtils.genToken(user.getId().toString(),user.getPassword());
        user.setToken(token);
            return user;
    }

    public Admin findById(Integer id) {
        return adminDao.selectByPrimaryKey(id);
    }
}
