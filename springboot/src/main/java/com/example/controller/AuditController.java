package com.example.controller;


import com.example.common.AutoLog;
import com.example.common.Result;
import com.example.entity.Audit;

import com.example.entity.Params;

import com.example.service.AuditService;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;


@CrossOrigin
@RestController
@RequestMapping("/audit")
public class AuditController {
    @Resource
    private AuditService auditService;


    @PostMapping
    @AutoLog("管理员进行请假审核")
    public Result save(@RequestBody Audit audit){
        if (audit.getId() == null){
            auditService.add(audit);
        }else{
            auditService.update(audit);
        }

        return Result.success();
    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Audit> info =  auditService.findBySearch(params);
        return Result.success(info);


    }
    @DeleteMapping("/{id}")
    @AutoLog("删除请假信息")
    public Result delete(@PathVariable Integer id){
        auditService.delete(id);
        return Result.success();
    }


}