package com.chinahitech.springbootmp.controler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@RequestMapping("article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("all")
    public R findAll()
    {
        List<EduArticle> articles = articleMapper.selectList(null);
        return R.ok().data("items",articles);
    }

    @GetMapping("delete")
    public R deleteByID(@RequestParam int id)
    {
        System.out.println(id);
        int i = articleMapper.deleteById(id);
        if(i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @PostMapping("add")
    public R addArticle(@RequestBody EduArticle article)
    {
        int i = articleMapper.insert(article);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @GetMapping("get")
    public R getArticleID(@RequestParam int id){
        EduArticle article = articleMapper.selectById(id);
        return R.ok().data("article",article);
    }

    @PostMapping("update")
    public R updateArticle(@RequestBody EduArticle article){
        int i = articleMapper.updateById(article);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
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

    @PostMapping("search")
    public R searchQuestion(@RequestBody String[] words){
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        for (String w : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", w).or().like("summary", w).or().like("keyword", w).like("author", w));
        }
        List<EduArticle> list = articleMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }

    @GetMapping("like")
    public R like(@RequestParam int id){
        EduArticle article = articleMapper.selectById(id);
        int count = article.getPraiseNum()+1;
        article.setPraiseNum(count);
        int i = articleMapper.updateById(article);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("watch")
    public R watch(@RequestParam int id){
        EduArticle article = articleMapper.selectById(id);
        int count = article.getClickNum()+1;
        article.setClickNum(count);
        int i = articleMapper.updateById(article);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("reply")
    public R reply(@RequestParam int id){
        EduArticle article = articleMapper.selectById(id);
        int count = article.getCommentNum()+1;
        article.setCommentNum(count);
        int i = articleMapper.updateById(article);
        if (i > 0){
            return R.ok();
        }else {
            return R.error();
        }
    }
    @GetMapping("sort")
    public R getBySort(@RequestParam int sort){
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sort", sort);
        List<EduArticle> list = articleMapper.selectList(queryWrapper);
        return R.ok().data("items",list);
    }
    @GetMapping("query")
    public R searchArticle(@RequestParam int type, @RequestParam String w){
        QueryWrapper<EduArticle> queryWrapper = new QueryWrapper<>();
        String[] words = w.split(" ");
        if(type != -1){
            queryWrapper.eq("sort", type);
        }
        for (String word : words) {
            queryWrapper = queryWrapper.and(i -> i.like("title", w).or().like("summary", w).or().like("keyword", w).like("author", w));
        }
        List<EduArticle> list = articleMapper.selectList(queryWrapper);
        return R.ok().data("items", list);
    }
}
