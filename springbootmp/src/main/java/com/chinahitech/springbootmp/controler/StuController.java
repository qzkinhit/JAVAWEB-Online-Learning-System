package com.chinahitech.springbootmp.controler;

import com.alibaba.fastjson.JSON;
import com.chinahitech.springbootmp.bean.Stu;
import com.chinahitech.springbootmp.mapper.StuMapper;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuMapper stuMapper;
    @GetMapping("all")
    public String getEmps(){

        List list = stuMapper.selectList(null);
        R r = R.ok().data("items",list);

        return JSON.toJSONString(r);
    }
    @GetMapping("del")
    public R del(@RequestParam int id){
        System.out.println(id);
        int i = stuMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("add")
    public R addTeacher(@RequestBody Stu stu){
        System.out.println(stu);
        int i = stuMapper.insert(stu);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("reg")
    public int regStu(@RequestBody Stu stu) {
        System.out.println(stu);
        int i = stuMapper.insert(stu);
        if (i > 0) {
            return stu.getId();
        } else {
            return -1;
        }
    }
    @GetMapping("get")
    public R getStuById(@RequestParam String id){
        Stu stu = stuMapper.selectById(id);
        return R.ok().data("stu", stu);
    }
    @GetMapping("getname")
    public R getStuname(@RequestParam String id){
        Stu stu = stuMapper.selectById(id);
        return R.ok().data("stu", stu);
    }

    @PostMapping("update")
    public R updateStu(@RequestBody Stu stu){
        int i = stuMapper.updateById(stu);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    //    上传头像
    @PostMapping("upload")
    public String upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString().replace("-","") + originalFilename;
        String dirPath = System.getProperty("user.dir");
        String path = "/" + "uploadImg" + "/"+ newFileName;
        File destFile = new File(dirPath + path);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }try {
            file.transferTo(destFile);
            // 将相对路径返回给前端
            return path;
        } catch (IOException e) {
            return null;
        }
    }


}
