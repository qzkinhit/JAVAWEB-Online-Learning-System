package com.chinahitech.springbootmp.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

public class EduCourse {
    private int id;
    private String name;
    @TableLogic
    private int isAvaliable;

    private String link;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    private String title;
    private String logo;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private Date endTime;

    private int zan;

    private int through;

    private int contain;

    public int getContain() {
        return contain;
    }

    public void setContain(int contain) {
        this.contain = contain;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public int getThrough() {
        return through;
    }

    public void setThrough(int through) {
        this.through = through;
    }

    public int getIsAvaliable() {
        return isAvaliable;
    }

    public void setIsAvaliable(int isAvaliable) {
        this.isAvaliable = isAvaliable;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getgmtCreate() {
        return gmtCreate;
    }

    public void setgmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return gmtModified;
    }

    public void setUpdateTime(Date updateTime) {
        this.gmtModified = updateTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
