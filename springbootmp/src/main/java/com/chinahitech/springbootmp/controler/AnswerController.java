package com.chinahitech.springbootmp.controler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinahitech.springbootmp.bean.*;
import com.chinahitech.springbootmp.mapper.*;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/answer")
@CrossOrigin
public class AnswerController {

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private StuMapper stuMapper;

    @GetMapping("all")
    public R findAll(){
        List<EduAnswer> Answer = answerMapper.selectList(null);
        return R.ok().data("items",Answer);
    }

    @GetMapping("allw")
    public R findAllw(){
        List<EduAnswer> answers = answerMapper.selectList(null);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduAnswer a : answers) {
            if(stuMapper.selectById(a.getCusId()) != null){
                stus.add(stuMapper.selectById(a.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items",stus);
    }

    @GetMapping("delete")
    public R deleteByID(@RequestParam int id){
        System.out.println(id);
        int i = answerMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("add")
    public R addAnswer(@RequestBody EduAnswer answer){
        int i = answerMapper.insert(answer);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("get")
    public R getAnswerByID(@RequestParam int id){
        EduAnswer answer = answerMapper.selectById(id);
        return R.ok().data("answer",answer);
    }

    @PostMapping("update")
    public R updateAnswer(@RequestBody EduAnswer answer){
        int i = answerMapper.updateById(answer);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("getansbyq")
    public R getAnswerByQuestion(@RequestParam int qid){
        QueryWrapper<EduAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", qid);
        List<EduAnswer> list = answerMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }

    @GetMapping("getansbyqw")
    public R getAnswerByQuestionw(@RequestParam int qid){
        QueryWrapper<EduAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("question_id", qid);
        List<EduAnswer> answers = answerMapper.selectList(queryWrapper);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduAnswer a : answers) {
            if(stuMapper.selectById(a.getCusId()) != null){
                stus.add(stuMapper.selectById(a.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items",stus);
    }

    @GetMapping("getansbycus")
    public R getAnswerByCus(@RequestParam int cid){
        QueryWrapper<EduAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cus_id", cid);
        List<EduAnswer> list = answerMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }

    @GetMapping("getansbycusw")
    public R getAnswerByCusw(@RequestParam int cid){
        QueryWrapper<EduAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cus_id", cid);
        List<EduAnswer> answers = answerMapper.selectList(queryWrapper);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduAnswer a : answers) {
            if(stuMapper.selectById(a.getCusId()) != null){
                stus.add(stuMapper.selectById(a.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items",stus);
    }

    @GetMapping("like")
    public R like(@RequestParam int id){
        EduAnswer answer = answerMapper.selectById(id);
        int count = answer.getPraiseCount()+1;
        answer.setPraiseCount(count);
        int i = answerMapper.updateById(answer);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
}
