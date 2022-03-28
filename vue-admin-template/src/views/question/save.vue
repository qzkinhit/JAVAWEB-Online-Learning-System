<template>
  <div class="app-container">
  <el-form ref="form" :model="question" label-width="80px">
  <el-form-item label="提问者id">
    <el-input v-model="question.cusId"></el-input>
  </el-form-item>
  <el-form-item label="标题">
    <el-input v-model="question.title"></el-input>
  </el-form-item>
  <el-form-item label="内容" >
    <el-input type="textarea" v-model="question.content" :autosize="{ minRows: 4 }"></el-input>
  </el-form-item>
  <el-form-item label="类型">
    <el-select v-model="question.type" placeholder="请选择">
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
   <el-form-item label="状态">
    <el-select v-model="question.status" placeholder="请选择">
          
      <el-option label="已解决" value=0></el-option>
      <el-option label="未解决" value=1></el-option>
    </el-select>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" :disabled="disable" @click="onSubmit">保存</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import {add,get, update} from '@/api/question'
export default {
    data() {
      return {
        question: {
				cusId: "", 
				title: "",
				content: "",
				type: "",
        addTime:""
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
        if(this.question.id){
          this.updateInfo(this.question)
        }else{
          this.addInfo(this.question)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.question = response.data.question
           this.question.addTime=new Date();
        })
      },
      addInfo(question){
        add(question).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '上传成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/question/list"})
        })
      },
      updateInfo(question){
        update(question).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/question/list"})
        })
      },
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.question.avatar = "http://localhost:8080"+res;
        // console.log("http://localhost:8080"+res)
      }
    }
  }
</script>


