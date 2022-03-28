<template>
  <div class="app-container">
  <el-form ref="form" :model="teacher" label-width="80px">
  <el-form-item label="讲师名称">
    <el-input v-model="teacher.name"></el-input>
  </el-form-item>
  <el-form-item label="讲师排序">
      <el-input-number v-model="teacher.sort" controls-position="right" :min="0" :max="10"></el-input-number>
  </el-form-item>
  <el-form-item label="讲师头衔">
    <el-select v-model="teacher.level" placeholder="请选择">
      <el-option label="高级讲师" value="1"></el-option>
      <el-option label="首席讲师" value="2"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="讲师资历">
    <el-input v-model="teacher.career"></el-input>
  </el-form-item>
  <el-form-item label="讲师简介">
    <el-input type="textarea" v-model="teacher.intro"></el-input>
  </el-form-item>

  <!-- 讲师头像 -->
  <el-form-item label="头像">
    <el-upload
    class="avatar-uploader"
    action="http://localhost:8080/teacher/upload"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img v-if="teacher.avatar" :src="teacher.avatar" class="avatar">
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
import {add,get, update} from '@/api/teacher'
export default {
    data() {
      return {
        
        teacher: {
          name:'',
          intro:'',
          sort:1,
          level:'',
          career:'',
          avatar:''
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

        if(this.teacher.id){
          this.updateInfo(this.teacher)
        }else{
          
          this.addInfo(this.teacher)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.teacher = response.data.teacher
        })
      },
      addInfo(teacher){
        
        add(teacher).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/teacher/list"})
        })
      },
      updateInfo(teacher){
        update(teacher).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/teacher/list"})
        })
      },
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.teacher.avatar = "http://localhost:8080"+res;
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