package com.chinahitech.springbootmp.controler;

import com.alibaba.fastjson.JSON;
import com.chinahitech.springbootmp.bean.Emp;
import com.chinahitech.springbootmp.mapper.EmpMapper;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpMapper empMapper;
    @GetMapping("all")
    public String getEmps(){

        List list = empMapper.selectList(null);
        R r = R.ok().data("items",list);

        return JSON.toJSONString(r);
    }
    @GetMapping("del")
    public R del(@RequestParam int id){
        System.out.println(id);
        int i = empMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("add")
    public R addTeacher(@RequestBody Emp emp){
        System.out.println(emp);
        int i = empMapper.insert(emp);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("modify")
    public String modify(@RequestParam int id,@RequestParam int newid,@RequestParam String ename,@RequestParam int age,@RequestParam String job,@RequestParam int sal){
        Emp e = empMapper.selectById(id);
        e.setId(newid);
        e.setEname(ename);
        e.setJob(job);
        e.setSal(sal);
        int i = empMapper.insert(e);
        return "" + i;
    }
    @GetMapping("get")
    public R getTeacherByID(@RequestParam String id){
        Emp emp = empMapper.selectById(id);
        return R.ok().data("emp",emp);
    }

    @PostMapping("update")
    public R updateTeacher(@RequestBody Emp emp){
        int i = empMapper.updateById(emp);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }


}
