package com.example.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.AutoLog;
import com.example.common.Result;

import com.example.entity.Gonggao;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.GonggaoService;

import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/gonggao")
public class GonggaoController {
    @Resource
    private GonggaoService gonggaoService;

    @GetMapping
    public Result findAll(){
        return Result.success(gonggaoService.findAll());
    }

    @PostMapping
    public Result save(@RequestBody Gonggao gonggao){
        if (gonggao.getId() == null){
            gonggaoService.add(gonggao);
        }else{
            gonggaoService.update(gonggao);
        }

        return Result.success();
    }
    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Gonggao> info =  gonggaoService.findBySearch(params);
        return Result.success(info);


    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        gonggaoService.delete(id);
        return Result.success();
    }
    @PutMapping("/delBatch")
    @AutoLog("批量删除赛事信息")
    public Result delBatch(@RequestBody List<Gonggao> list){
        for (Gonggao gonggao : list){
            gonggaoService.delete(gonggao.getId());
        }
        return Result.success();
    }
    @GetMapping("/export")
    @AutoLog("批量导入赛事信息")
    public Result export(HttpServletResponse response) throws IOException {
        //要一行一行的组装数据，装到list里面，每条数据对应
        // 数据库里的字段，隐射到java里的实体类
        //如何知道某一类对应某个表头？需要映射数据，需要一个Map<key,value>
        //然后把这个map塞到list里
        //so！
        //1、从数据库中查询出所有数据
        List<Gonggao> all = gonggaoService.findAll();
        if (CollectionUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        //2、定义一个list和map，存储处理之后的数据，用于塞到list里
        List<Map<String,Object>> list = new ArrayList<>(all.size());
        //3、遍历每一条数据，然后封装到map<key,value>里，把这个map塞到list里
        for (Gonggao gonggao :all){
            Map<String, Object> row = new HashMap<>();
            row.put("分类名称",gonggao.getName());
            row.put("注意事项",gonggao.getZhuyi());
            row.put("公告信息",gonggao.getXinxi());

            list.add(row);
        }
        //4、创建一个excelwriter，把list数据用这个writer写出来(生成出来)
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(list);
        //5、把这个Excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=gonggao.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        wr.close();
        IoUtil.close(System.out);


        return Result.success();
    }
    @PostMapping("/upload")
    @AutoLog("下载赛事信息")
    public Result upload(MultipartFile file) throws IOException {
        List<Gonggao> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Gonggao.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            for (Gonggao gonggao : infoList) {
                try {
                    gonggaoService.add(gonggao);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }
}