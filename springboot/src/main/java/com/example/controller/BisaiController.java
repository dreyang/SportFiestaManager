package com.example.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.AutoLog;
import com.example.common.Result;

import com.example.entity.Bisai;
import com.example.entity.Gonggao;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.service.BisaiService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
@RequestMapping("/bisai")
public class BisaiController {
    @Resource
    private BisaiService bisaiService;



    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Bisai> info =  bisaiService.findBySearch(params);
        return Result.success(info);
    }
    @PostMapping
    @AutoLog("修改比赛信息")
    public Result save(@RequestBody Bisai bisai){
        if (bisai.getId() == null){
            bisaiService.add(bisai);
        }else{
            bisaiService.update(bisai);
        }

        return Result.success();
    }
    @DeleteMapping("/{id}")
    @AutoLog("删除比赛信息")
    public Result delete(@PathVariable Integer id){
        bisaiService.delete(id);
        return Result.success();
    }

    @GetMapping("/echarts/bie")
    public Result bie(){
        //查询出所有比赛
        List<Bisai> list = bisaiService.findAll();
        Map<String, Long> collect =
                list.stream().filter(x -> ObjectUtil.isNotEmpty(x.getTypeName()))
                        .collect(Collectors.groupingBy(Bisai::getTypeName,Collectors.counting()));
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(collect)){
            for (String key : collect.keySet()){
                Map<String, Object> map = new HashMap<>();
                map.put("name",key);
                map.put("value",collect.get(key));
                mapList.add(map);
            }
        }
        return Result.success(mapList);
    }
    @GetMapping("/exp_1")
    @AutoLog("批量导入比赛信息")
    public Result export(HttpServletResponse response) throws IOException {
        //要一行一行的组装数据，装到list里面，每条数据对应
        // 数据库里的字段，隐射到java里的实体类
        //如何知道某一类对应某个表头？需要映射数据，需要一个Map<key,value>
        //然后把这个map塞到list里
        //so！
        //1、从数据库中查询出所有数据
        List<Bisai> all = bisaiService.findAll();
        if (CollectionUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        //2、定义一个list和map，存储处理之后的数据，用于塞到list里
        List<Map<String,Object>> list = new ArrayList<>(all.size());
        //3、遍历每一条数据，然后封装到map<key,value>里，把这个map塞到list里
        for (Bisai bisai :all){
            Map<String, Object> row = new HashMap<>();
            row.put("比赛名称",bisai.getName());
            row.put("比赛日期",bisai.getRiqi());
            row.put("参赛地点",bisai.getDidian());
            row.put("比赛人数",bisai.getRenshu());
            row.put("剩余容量",bisai.getNum());
            row.put("比赛分类",bisai.getTypeId());

            list.add(row);
        }
        //4、创建一个excelwriter，把list数据用这个writer写出来(生成出来)
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(list);
        //5、把这个Excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=bisai.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out, true);
        wr.close();
        IoUtil.close(System.out);


        return Result.success();
    }
}
