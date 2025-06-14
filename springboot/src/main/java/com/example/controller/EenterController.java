package com.example.controller;


import cn.hutool.core.util.ObjectUtil;
import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Eenter;
import com.example.entity.Notice;
import com.example.entity.Params;
import com.example.service.EenterService;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/eenter")
public class EenterController {
  @Resource
    private EenterService eenterService;

    @PostMapping
    public Result update(@RequestBody Eenter eenter){
        if (ObjectUtil.isEmpty(eenter.getId())){


        eenterService.add(eenter);
        }else{
        eenterService.update(eenter);
        }

        return  Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Eenter> info =  eenterService.findBySearch(params);
        return Result.success(info);
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
         eenterService.delete(id);
         return Result.success();
    }


}