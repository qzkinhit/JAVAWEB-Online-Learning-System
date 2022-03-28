<template>
  <div class="app-container">

  <el-form ref="form" :model="stu" label-width="80px">

  <el-form-item label="学生姓名">
    <el-input v-model="stu.sname"></el-input>
  </el-form-item>

   <el-form-item label="学号">
    <el-input v-model="stu.sid"></el-input>
  </el-form-item>

   <el-form-item label="性别">
    <el-input v-model="stu.gender"></el-input>
  </el-form-item>

   <el-form-item label="年龄">
    <el-input v-model="stu.age"></el-input>
  </el-form-item>
 
  <el-form-item label="生日">
  <div class="block">
    <el-date-picker
      v-model="stu.birthday"
      type="datetime"
      placeholder="选择日期时间"
      align="right"
      :picker-options="pickerOptions">
    </el-date-picker>
  </div>
    </el-form-item>
   <el-form-item label="密码">
    <el-input v-model="stu.pwd"></el-input>
  </el-form-item>
   <el-form-item label="邮箱">
    <el-input v-model="stu.email"></el-input>
  </el-form-item>
   <el-form-item label="电话">
    <el-input v-model="stu.phone"></el-input>
  </el-form-item>
 <!-- 学生头像 -->
  <el-form-item label="头像">
    <el-upload
    class="avatar-uploader"
    action="http://localhost:8080/stu/upload"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload">
    <img style="width: 200px; height: 200px; border: none" v-if="stu.avatar" :src="stu.avatar" class="avatar">
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
import {add,addStu,get, update} from '@/api/stu'
export default {
    data() {
      return {
        pickerOptions: {
          shortcuts: [ {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }]
        },
        stu: {
          sname:'',
          birthday:'1981-04-01T16:00:00.000+00:00',
          gender:'男',
          age:'',
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
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.stu.avatar = "http://localhost:8080"+res;
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
      onSubmit() {
        if(this.stu.id){
          this.updateInfo(this.stu)
        }else{
          this.addInfo(this.stu)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.stu = response.data.stu
        })
        
      },
      addInfo(stu){
        console.log("add")
        
        add(stu).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/stu/list"})
        })
      },
      updateInfo(stu){
        update(stu).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/stu/list"})
        })
      },
    }
  }
</script>
