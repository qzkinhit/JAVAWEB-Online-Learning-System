<template>
  <div class="app-container">

  <el-form ref="form" :model="stu" label-width="80px">

  <el-form-item label="id">
    <el-input v-model="stu.id"></el-input>
  </el-form-item>
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
          id:"",
          sname:'',
          birthday:'1981-04-01T16:00:00.000+00:00',
          gender:'男',
          age:'',
          avatar:'http://localhost:8080/uploadImg/7d2b27fd19ed45ee85d28145bc18bc70QQ图片20210805011645.jpg'
        },
        disable:false
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
        
          this.addInfo(this.stu)
      },
     updateInfo(stu){
        update(stu).then(reponse=>{
         
        })
      },
      addInfo(stu){
        console.log(stu)
        add(stu).then(response=>{
          // 提示信息
          this.$alert('你的id是'+this.stu.id+"\n你的密码是"+this.stu.pwd, '注册成功', {
          confirmButtonText: '确定',
          callback: action => {
            this.$message({
              type: 'success',
              message: '注册成功!'
            })
          }
        });
           console.log(stu)
            // 跳转到列表页
            this.$router.push({path:"/login"})
            
        })
      }
    }
}
  
</script>
