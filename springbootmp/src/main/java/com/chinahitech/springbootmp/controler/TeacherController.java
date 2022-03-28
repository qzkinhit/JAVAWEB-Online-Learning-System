package com.chinahitech.springbootmp.controler;
import com.chinahitech.springbootmp.bean.EduCourse;
import com.chinahitech.springbootmp.bean.EduTeacher;
import com.chinahitech.springbootmp.mapper.TeacherMapper;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherMapper teacherMapper;

    @GetMapping("all")
    public R findAll(){

        List<EduTeacher> teachers = teacherMapper.selectList(null);
        return R.ok().data("items",teachers);
    }

    @GetMapping("delete")
    public R deleteByID(@RequestParam int id){
        System.out.println(id);
        int i = teacherMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("add")
    public R addTeacher(@RequestBody EduTeacher teacher){

        int i = teacherMapper.insert(teacher);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("get")
    public R getTeacherByID(@RequestParam int id){
        EduTeacher teacher = teacherMapper.selectById(id);
        return R.ok().data("teacher",teacher);
    }

    @PostMapping("update")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        int i = teacherMapper.updateById(teacher);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("search")
    public R search(@RequestParam String name)
    {
        System.out.println(name);
        List<EduTeacher> teachers = teacherMapper.selectList(null);
        List<EduTeacher> myTeacher = new ArrayList<>();
        for(EduTeacher a:teachers) {
            if (a.getName().contains(name)) {
                myTeacher.add(a);
            }
        }
        return R.ok().data("items",myTeacher);
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
