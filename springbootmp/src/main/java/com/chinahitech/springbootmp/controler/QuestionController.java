package com.chinahitech.springbootmp.controler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinahitech.springbootmp.bean.EduAnswer;
import com.chinahitech.springbootmp.bean.EduQuestions;
import com.chinahitech.springbootmp.bean.Stu;
import com.chinahitech.springbootmp.mapper.QuestionMapper;
import com.chinahitech.springbootmp.mapper.StuMapper;
import com.chinahitech.springbootmp.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {
    @Autowired
    private  QuestionMapper questionMapper;
    @Autowired
    private  StuMapper stuMapper;

    @GetMapping("all")
    public R findAll(){
        List<EduQuestions> questions = questionMapper.selectList(null);
        return R.ok().data("items",questions);
    }

    @GetMapping("allw")
    public R findAllW(){
        List<EduQuestions> questions = questionMapper.selectList(null);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduQuestions q : questions) {
            if(stuMapper.selectById(q.getCusId()) != null){
                stus.add(stuMapper.selectById(q.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items",stus);
    }

    @GetMapping("delete")
    public R deleteByID(@RequestParam String id){
        System.out.println(id);
        int i = questionMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("add")
    public R addQuestion(@RequestBody EduQuestions question){
        int i = questionMapper.insert(question);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("get")
    public R getQuestionByID(@RequestParam String id){
        EduQuestions question = questionMapper.selectById(id);
        return R.ok().data("question",question);
    }
    @PostMapping("update")
    public R updateQuestion(@RequestBody EduQuestions question){
        int i = questionMapper.updateById(question);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("search")
    public R searchQuestion(@RequestBody String[] words){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        for (String w : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", w).or().like("content", w));
        }
        List<EduQuestions> list = questionMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }
    @PostMapping("searchw")
    public R searchQuestionw(@RequestBody String[] words){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        for (String w : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", w).or().like("content", w));
        }
        List<EduQuestions> questions = questionMapper.selectList(queryWrapper);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduQuestions q : questions) {
            if(stuMapper.selectById(q.getCusId()) != null){
                stus.add(stuMapper.selectById(q.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items", stus);
    }
    @GetMapping("query")
    public R searchQuestion(@RequestParam int type, @RequestParam String w){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        String[] words = w.split(" ");
        if(type != -1){
            queryWrapper.eq("type", type);
        }
        for (String word : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", word).or().like("content", word));
        }
        List<EduQuestions> list = questionMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }
    @GetMapping("queryw")
    public R searchQuestionw(@RequestParam int type, @RequestParam String w){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        String[] words = w.split(" ");
        if(type != -1){
            queryWrapper.eq("type", type);
        }
        for (String word : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", word).or().like("content", word));
        }
        List<EduQuestions> questions = questionMapper.selectList(queryWrapper);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduQuestions q : questions) {
            if(stuMapper.selectById(q.getCusId()) != null){
                stus.add(stuMapper.selectById(q.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items", stus);
    }

    @GetMapping("getquebycus")
    public R getQuestionByCus(@RequestParam int cid){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cus_id", cid);
        List<EduQuestions> list = questionMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }
    @GetMapping("getquebycusw")
    public R getQuestionByCusw(@RequestParam int cid){
        QueryWrapper<EduQuestions> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cus_id", cid);
        List<EduQuestions> questions = questionMapper.selectList(queryWrapper);
        List<Stu> stus = new ArrayList<>();
        Stu zero = new Stu();
        zero.setSname("匿名用户");
        zero.setSid("");
        zero.setAvatar("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
        for (EduQuestions q : questions) {
            if(stuMapper.selectById(q.getCusId()) != null){
                stus.add(stuMapper.selectById(q.getCusId()));
            }else{
                stus.add(zero);
            }
        }
        return R.ok().data("items", stus);
    }
    @GetMapping("like")
    public R like(@RequestParam int id){
        EduQuestions question = questionMapper.selectById(id);
        int count = question.getPraiseCount()+1;
        question.setPraiseCount(count);
        int i = questionMapper.updateById(question);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("watch")
    public R watch(@RequestParam int id){
        EduQuestions question = questionMapper.selectById(id);
        int count = question.getBrowseCount()+1;
        question.setBrowseCount(count);
        int i = questionMapper.updateById(question);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("reply")
    public R reply(@RequestParam int id){
        EduQuestions question = questionMapper.selectById(id);
        int count = question.getReplyCount()+1;
        question.setReplyCount(count);
        int i = questionMapper.updateById(question);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

}
