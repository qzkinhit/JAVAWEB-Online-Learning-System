<template>
  <div class="app-container">
  <el-form ref="form" :model="article" label-width="80px">
  <el-form-item label="标签">
    <el-input v-model="article.type"></el-input>
  </el-form-item>
  <el-form-item label="标题">
    <el-input v-model="article.title"></el-input>
  </el-form-item>
  <el-form-item label="作者">
    <el-input v-model="article.author"></el-input>
  </el-form-item>
  <el-form-item label="来源">
    <el-input v-model="article.source"></el-input>
  </el-form-item>
  <el-form-item label="链接">
    <el-input v-model="article.link"></el-input>
  </el-form-item>
   <el-form-item label="关键词" >
    <el-input type="textarea" v-model="article.keyword" :autosize="{ minRows: 1 }"></el-input>
  </el-form-item>
  <el-form-item label="内容" >
    <el-input type="textarea" v-model="article.summary" :autosize="{ minRows: 4 }"></el-input>
  </el-form-item>
  <el-form-item label="文章大类">
    <el-select v-model="article.sort" placeholder="请选择">
          <el-option label="其它" value=0></el-option>
      <el-option label="数学" value=1></el-option>
      <el-option label="计算机" value=2></el-option>
      <el-option label="语文" value=3></el-option>
      <el-option label="英语" value=4></el-option>
      <el-option label="物理" value=5></el-option>
      <el-option label="化学" value=6></el-option>
      <el-option label="地理" value=7></el-option>
      <el-option label="历史" value=8></el-option>
      <el-option label="政治" value=9></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="图片">
    <el-upload
    class="avatar-uploader"
    action="http://localhost:8080/article/upload"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img v-if="article.imageUrl" :src="article.imageUrl" class="avatar">
    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
  </el-upload>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" :disabled="disable" @click="onSubmit">保存</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import {add,get, update} from '@/api/article'
import { getStuById } from '@/api/stu'
import { getToken } from '@/utils/auth'
export default {
    data() {
      return {
        article: {
                title: "",
                summary: "",
                keyword: "",
                imageUrl: "https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg",
                source: "本站",
                author : "none",
                link: "https://",
                type: "",
                sort: "",
                publishTime:""
        },
        me: {
                sname: "",
                sid: "",
        },
        disable:false
      }
    },
    created(){
      if(this.$route.params && this.$route.params.id){
        this.getInfo(this.$route.params.id)
      }
      this.getme(getToken())
    },
    methods: {
      onSubmit() {
        if(this.article.id){
          this.updateInfo(this.article)
         
        }else{
          this.addInfo(this.article)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.article = response.data.article
          this.article.publishTime=new Date();
        })
      },
      addInfo(article){
        add(article).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '上传成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/article/index"})
        })
      },
      updateInfo(article){
        update(article).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/article/index"})
        })
      },
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.article.imageUrl = "http://localhost:8080"+res;
        // console.log("http://localhost:8080"+res)
      },
       beforeAvatarUpload(file) {
        
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      
     },
     getme(id){
          getStuById(id).then(res => {
              this.me = res.data.stu
              this.stuId = res.data.stu.sid
              console.log(this.me)
              this.article.author = res.data.stu.sname
          })
      }
    }
  }
</script>


