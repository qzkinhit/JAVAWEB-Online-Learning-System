<template>
  <div id="articles-list">
    <el-row style="padding:30px">
      <el-col  :span="3">
          <el-select v-model="querytype" placeholder="请选择">
            <el-option label="全部"   value=-1></el-option>
            <el-option label="数学"   value=1></el-option>
            <el-option label="计算机" value=2></el-option>
            <el-option label="语文"   value=3></el-option>
            <el-option label="英语"   value=4></el-option>
            <el-option label="物理"   value=5></el-option>
            <el-option label="化学"   value=6></el-option>
            <el-option label="地理"   value=7></el-option>
            <el-option label="历史"   value=8></el-option>
            <el-option label="政治"   value=9></el-option>
            <el-option label="其它"   value=0></el-option>
          </el-select>
        </el-col>
      <el-col :span="16" :offset="1">
        <el-form ref="form" @keydown.enter.native="handleClick(querytype, input)">
          <el-input
          placeholder="请输入内容"
          v-model="input"
          style="width: 600px"
          clearable>
        </el-input>
        </el-form>
      </el-col>
      <el-col :span="3" :offset="1">
        <el-button size="normal" class = "input-button" @click="handleClick(querytype, input)">查询</el-button>
      </el-col>
    </el-row>
    <el-card direction="vertical" id="box-card" shadow="hover" @click="getDetail(article.id)"
    v-for="article in articles.slice((currentPage - 1) * pagesize, currentPage * pagesize)" :key="article.publishTime">
      <template #header>
        <div class="card-header" @click="getDetail(article.id)">
          <span><h1>{{article.title}}</h1></span>
          <span class="time">关键词：{{article.keyword}}</span>
          <i style="padding: 30px" class="el-icon-collection-tag" size="mini"><span class="type">{{article.type}}</span></i>
        </div>
      </template>
      <el-row :gutter="20">
      <el-col :span="8">
      <div @click="getDetail(article.id)">
        <el-image class="el-image" fit="cover" :src="article.imageUrl"></el-image>
      </div>
      </el-col>
      <el-col :span="16">
        <p class="text-item">{{article.summary}}</p>
        <p class="time">本文创建于{{article.createTime | moment}}，发布于{{article.publishTime | moment}}。</p>
        <p class="time">作者为：{{article.author}}，发布在{{article.source}}。</p>
      </el-col></el-row>
      <div class="subscribe">
        <el-button @click="likeArticle(article.id)"  style="margin-top: 30px;float:bottom" icon="el-icon-thumb">{{article.praiseNum}}</el-button>
        <i id="browse" style="padding: 30px" class="el-icon-view" size="large">{{article.clickNum}}</i>
        </div>
    </el-card>
    <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[5, 10, 15]"
      :page-size="pagesize"
      :total="articles.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
  </div>
</template>

<style scoped>
#articles-list {
    max-width: 960px;
    margin: 0 auto;
}
#box-card {
    width: 900px;
    margin-block: 30px;
}
.card-header {
    font-family: 'PingFang SC';
    font-weight: 1900;
    padding: 30px;
}
.text-item {
    font-family: 'PingFang SC';
    font-size: 120%;
    font-weight: 400;
    padding-left: 30px;
    padding-right: 30px;
    padding-bottom: 10px;
    padding-top: 10px;
}
.input-item {
    padding: 30px;
    width: 300px;
}
.subscribe {
    float:inline-end;
    padding-left: 30px;
}
.el-image {
    width: 320px;
    height: 240px;
    padding: 30px;
}
.time {
    font-family: "PingFang SC";
    color:dimgrey;
    font-size: 10px;
    padding-left: 30px;
}
.el-icon-collection-tag {
    color:dimgrey;
    padding: 5px;
    vertical-align: middle;
}
.type {
    font-family: "PingFang SC";
    color:dimgrey;
    font-size: 10px;
    padding: 5px;
    vertical-align: middle;
}
</style>

<script>
import {getAll, get, search, likeArticle, watchArticle, query} from '@/api/article'
export default {
    data(){
      return{
        articles:[],
        pagesize: 5,
        currentPage: 1,
        input:'',
        querytype: ''
      }
    },
    created(){
        this.fetchData()
    },
    methods:{
        fetchData(){
            getAll().then(response=>{
                this.articles = response.data.items
            })
        },
        getDetail(id){
            watchArticle(id)
            // this.$router.push("/article/detail/"+id)
            get(id).then(res => {
                window.location.href = res.data.article.link
            })
            
        },
        handleSizeChange(psize) {
          this.pagesize = psize;
        },
        handleCurrentChange(cpage) {
          this.currentPage = cpage;
        },
        searchArticles(input){
          search(input.split(' ')).then(res => {
            this.articles = res.data.items
          })
        },
        likeArticle(aid){
            likeArticle(aid).then(res => {
                this.$message({
                    type: 'success',
                    message: '点赞成功!'
                })
                this.fetchData()
            })
        },
        clickArticle(aid){
            watchArticle(aid)
        },
        handleClick(type, w){
          if(type != ''){
            query(type, w).then(res => {
            this.articles = res.data.items
          })}else {
            query(-1, w).then(res => {
            this.articles = res.data.items
          })}          
        },
    },
}
</script>
