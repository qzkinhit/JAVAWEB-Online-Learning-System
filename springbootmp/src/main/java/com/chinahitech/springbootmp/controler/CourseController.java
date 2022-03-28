package com.chinahitech.springbootmp.controler;

import com.chinahitech.springbootmp.bean.*;
import com.chinahitech.springbootmp.mapper.*;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private StuMapper studentMapper;
    @Autowired
    private PersonalMapper personalMapper;
    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CollectionMapper collectionMapper;

    @GetMapping("all")
    public R findAll()
    {
        List<EduCourse> courses = courseMapper.selectList(null);
        return R.ok().data("items",courses);
    }

    @GetMapping("delete")
    public R deleteByID(@RequestParam String id)
    {
        System.out.println(id);
        int i = courseMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("add")
    public R addCourse(@RequestBody EduCourse course)
    {
        int i = courseMapper.insert(course);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("get")
    public R getCourseByID(@RequestParam String id){
        EduCourse course = courseMapper.selectById(id);
        return R.ok().data("course",course);
    }

    @PostMapping("update")
    public R updateCourse(@RequestBody EduCourse course){
        int i = courseMapper.updateById(course);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("students")
    public R getStudent(@RequestParam int id)
    {
        List<EduPersonal> eduPersonals = personalMapper.selectList(null);
        List<Stu> students = new ArrayList<>();
        for(EduPersonal a:eduPersonals)
        {
            if(a.getCid()==id)
            {
                Stu student = studentMapper.selectById(a.getSid());
                students.add(student);
            }
        }
        return R.ok().data("students",students);
    }

    @PostMapping("teachers")
    public R getTeacher(@RequestParam int id)
    {
        List<EduLesson> eduLessons = lessonMapper.selectList(null);
        List<EduTeacher> teachers = new ArrayList<>();
        for(EduLesson a:eduLessons)
        {
            if(a.getCid()==id)
            {
                EduTeacher teacher = teacherMapper.selectById(a.getTid());
                teachers.add(teacher);
            }
        }
        return R.ok().data("teachers",teachers);
    }

    @PostMapping("lessons")
    public R addLesson(@RequestParam int cid,@RequestParam int tid)
    {
        EduLesson eduLesson = new EduLesson();
        eduLesson.setCid(cid);
        eduLesson.setTid(tid);
        int i = lessonMapper.insert(eduLesson);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("canuse")
    public R listRest(@RequestParam int id)
    {
        int flag=0;
        List<EduLesson> lessons = lessonMapper.selectList(null);
        List<EduLesson> lessons1 = new ArrayList<>();
        List<EduTeacher> teachers = new ArrayList<>();
        List<EduTeacher> all = teacherMapper.selectList(null);
        for(EduLesson a:lessons)
        {
            if(a.getCid()==id)
            {
                lessons1.add(a);
            }
        }
        for(EduTeacher a:all)
        {
            flag=0;
            for(EduLesson b:lessons1)
            {
                if(a.getId()==b.getTid())
                {
                    flag=1;
                }
            }
            if(flag==0)
                teachers.add(a);
        }
        return R.ok().data("teachers",teachers);
    }

    @GetMapping("search")
    public R search(@RequestParam String name)
    {
        System.out.println(name);
        List<EduCourse> courses = courseMapper.selectList(null);
        List<EduCourse> myCourse = new ArrayList<>();
        for(EduCourse a:courses) {
            if (a.getName().contains(name)) {
                myCourse.add(a);
            }
        }
            return R.ok().data("items",myCourse);
    }

    @PostMapping("mycourse")
    public R getCourse(@RequestParam int id)
    {
        List<EduPersonal> eduPersonals = personalMapper.selectList(null);
        List<EduCourse> courses = new ArrayList<>();
        for(EduPersonal a:eduPersonals)
        {
            if(a.getSid()==id)
            {
                EduCourse course = courseMapper.selectById(a.getCid());
                courses.add(course);
            }
        }
        return R.ok().data("courses",courses);
    }

    @PostMapping("mycollection")
    public R getCollection(@RequestParam int id)
    {
        List<EduCollection> eduCollections = collectionMapper.selectList(null);
        List<EduCourse> courses = new ArrayList<>();
        for(EduCollection a:eduCollections)
        {
            if(a.getSid()==id)
            {
                EduCourse course = courseMapper.selectById(a.getCid());
                courses.add(course);
            }
        }
        return R.ok().data("courses",courses);
    }

    @PostMapping("contain")
    public R getContain(@RequestParam int con)
    {
        List<EduCourse> courses = courseMapper.selectList(null);
        List<EduCourse> mycourse = new ArrayList<>();
        for(EduCourse a:courses)
        {
            if(a.getContain()==con)
            {
                mycourse.add(a);
            }
        }
        return R.ok().data("courses",mycourse);
    }

    @PostMapping("close")
    public R deleteCollection(@RequestParam int cid,@RequestParam int sid)
    {

        int id =-1;
        List<EduCollection> eduCollections = collectionMapper.selectList(null);
        for(EduCollection a:eduCollections)
        {
            if(a.getCid()==cid && a.getSid()==sid)
                id = a.getId();
        }
        int i = collectionMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("like")
    public R like(@RequestParam int id){
        EduCourse course = courseMapper.selectById(id);
        int count = course.getZan()+1;
        course.setZan(count);
        int i = courseMapper.updateById(course);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("watch")
    public R watch(@RequestParam int id){
        EduCourse course = courseMapper.selectById(id);
        int count = course.getThrough()+1;
        course.setThrough(count);
        int i = courseMapper.updateById(course);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("personals")
    public R addPersonal(@RequestParam int sid,@RequestParam int cid)
    {
        EduPersonal eduPersonal = new EduPersonal();
        eduPersonal.setCid(cid);
        eduPersonal.setSid(sid);
        int i = personalMapper.insert(eduPersonal);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("addit")
    public R addCollection(@RequestParam int sid,@RequestParam int cid)
    {
        EduCollection eduCollection = new EduCollection();
        eduCollection.setCid(cid);
        eduCollection.setSid(sid);
        int i = collectionMapper.insert(eduCollection);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("remove")
    public R deleteLesson(@RequestParam int cid,@RequestParam int tid)
    {
        int id =-1;
        List<EduLesson> eduLessons = lessonMapper.selectList(null);
        for(EduLesson a:eduLessons)
        {
            if(a.getCid()==cid && a.getTid()==tid)
                id = a.getId();
        }
        int i = lessonMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("back")
    public R deletePersonal(@RequestParam int cid,@RequestParam int sid)
    {
        int id =-1;
        List<EduPersonal> eduPersonals = personalMapper.selectList(null);
        for(EduPersonal a:eduPersonals)
        {
            if(a.getCid()==cid && a.getSid()==sid)
                id = a.getId();
        }
        int i = personalMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("collection")
    public R collection(@RequestParam int id)
    {
        List<EduCollection> collections = collectionMapper.selectList(null);
        List<Stu> students = new ArrayList<>();
        for(EduCollection a:collections)
        {
            if(a.getCid()==id)
            {
                Stu student = studentMapper.selectById(a.getSid());
                students.add(student);
            }
        }
        return R.ok().data("students",students);
    }

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
