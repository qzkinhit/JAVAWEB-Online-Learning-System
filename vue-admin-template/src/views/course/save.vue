<template>
  <div class="app-container">
  <el-form ref="form" :model="course" label-width="80px">
  <el-form-item label="课程名称">
    <el-input v-model="course.name"></el-input>
  </el-form-item>
  <el-form-item label="课程类型">
   <el-input v-model="course.title"></el-input>
  </el-form-item>
  <el-form-item label="课程链接">
    <el-input v-model="course.link"></el-input>
  </el-form-item>
  <el-form-item label="结束时间">
    <el-input v-model="course.endTime"></el-input>
  </el-form-item>

  <!-- 讲师头像 -->
  <el-form-item label="logo">
    <el-upload
    class="avatar-uploader"
    action="http://localhost:8080/course/upload"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img v-if="course.logo" :src="course.logo" class="avatar">
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
import {add,get,update} from '@/api/course'
export default {
    data() {
      return {
        course: {
          name:'',
          title:'',
          link:'',
          endTime:'',
          logo:''
        },
        disable:false
      }
    },
    created(){
      if(this.$route.params && this.$route.params.id){
        this.getInfo(this.$route.params.id)
      }
    },
    methods: {
      onSubmit() {
      
        if(this.course.id){
          this.updateInfo(this.course)
        }else{
          this.addInfo(this.course)
         
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.course = response.data.course
        })
      },
      addInfo(course){
        add(course).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/course/list"})
        })
      },
      updateInfo(course){
        update(course).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/course/list"})
        })
      },
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.course.logo = "http://localhost:8080"+res;
        // console.log("http://localhost:8080"+res)
      },
      beforeAvatarUpload(file) {
        /*
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      */
     }
    }
  }
</script>


<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>