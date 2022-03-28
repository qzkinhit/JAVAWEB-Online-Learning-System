<template>
  <div class="app-container">
  <el-form ref="form" :model="answer" label-width="80px">
  <el-form-item label="内容" placeholder="请输入内容" >
    <el-input type="textarea" v-model="answer.content" rows="5"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" :disabled="disable" @click="onSubmit">保存</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import {add,get, update} from '@/api/answer'
export default {
    data() {
      return {
        answer: {
				cusId: "",
				questionId: "",
				isBest:"",
				content: "",
				praiseCount: 0,
				editTime: ""
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
        if(this.answer.id){
          this.updateInfo(this.answer)
        }else{
          this.addInfo(this.answer)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.answer = response.data.answer
           this.answer.editTime=new Date();
        })
      },
      addInfo(answer){
        add(answer).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '上传成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/question/detail"+answer.questionId})
        })
      },
      updateInfo(answer){
        update(answer).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/question/detail"+answer.questionId})
        })
      },
      handleAvatarSuccess(res) {
        console.log("--------------------------------------")
        this.answer.avatar = "http://localhost:8080"+res;
        // console.log("http://localhost:8080"+res)
      }
    }
  }
</script>


