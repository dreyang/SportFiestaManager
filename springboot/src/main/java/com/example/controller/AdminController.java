package com.example.controller;

import com.example.common.AutoLog;
import com.example.common.CaptureConfig;
import com.example.common.Result;
import com.example.entity.Admin;
import com.example.entity.Params;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminService adminService;

    @PostMapping("/login")
    @AutoLog("登录该系统")
    public Result login(@RequestBody Admin admin, @RequestParam String key, HttpServletRequest request){
        if (!admin.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            CaptchaUtil.clear(request);
            return Result.error("验证码不正确");
        }

        Admin loginUser = adminService.login(admin);
        CaptureConfig.CAPTURE_MAP.remove(key);
        return Result.success(loginUser);
    }
    @PostMapping("/register")
    @AutoLog("注册该系统")
    public Result register(@RequestBody Admin admin){
       adminService.add(admin);
        return Result.success();
    }

    @PostMapping
    @AutoLog("修改用户信息")
    public Result save(@RequestBody Admin admin){
        if (admin.getId() == null){
            adminService.add(admin);
        }else{
            adminService.update(admin);
        }

        return Result.success();
    }


    @GetMapping
    public Result findAll(){
        List<Admin> list = adminService.findAll();
        return Result.success(list);

    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Admin> info =  adminService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除用户信息")
    public Result delete(@PathVariable Integer id){
        adminService.delete(id);
        return Result.success();
    }
}
