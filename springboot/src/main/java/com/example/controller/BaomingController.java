package com.example.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.dao.BisaiDao;
import com.example.entity.Baoming;
import com.example.entity.Bisai;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.BaomingService;
import com.example.service.BisaiService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/baoming")
public class BaomingController {
    @Resource
    private BaomingService baomingService;
    @Resource
    private BisaiDao bisaiDao;


    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Baoming> info =  baomingService.findBySearch(params);
        return Result.success(info);
    }
    @PostMapping
    @AutoLog("报名比赛")
    public Result save(@RequestBody Baoming baoming){
        Bisai bisai = bisaiDao.selectByPrimaryKey(baoming.getBisaiId());
        //判断是否重复报名
        Baoming bao = baomingService.find(baoming.getBisaiId(),baoming.getUserId());
        if (ObjectUtil.isNotEmpty(bao)){
            throw new CustomException("您已报名，请勿重复报名!");
        }



        //比赛剩余人数是否为0，只有大于0的时候才可以被报名

        if (bisai.getNum() == 0){
            return Result.error("该项目报名人数已满");
        }
        //往报名表里插入一条数据
       baoming.setTime(DateUtil.now());
       baomingService.add(baoming);

       //添加之后对应的可报名人数减一
        bisai.setNum(bisai.getNum() - 1);
        bisaiDao.updateByPrimaryKeySelective(bisai);
        return Result.success();
    }
    @PostMapping("/shuju")
    @AutoLog("管理员进行报名审核")
    public Result shuju(@RequestBody Baoming baoming){
        if (baoming.getId() == null){
            baomingService.add(baoming);
        }else{
            baomingService.update(baoming);
        }
        return Result.success();
    }
    @DeleteMapping("/{id}")
    @AutoLog("删除报名信息")
    public Result delete(@PathVariable Integer id){
        baomingService.delete(id);
        return Result.success();
    }

}
