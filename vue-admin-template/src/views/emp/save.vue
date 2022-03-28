<template>
  <div class="app-container">
  <el-form ref="form" :model="emp" label-width="80px">
  <el-form-item label="员工姓名">
    <el-input v-model="emp.ename"></el-input>
  </el-form-item>
 
  <el-form-item label="入职时间">
 
  <div class="block">
    <el-date-picker
      v-model="emp.hiredate"
      type="datetime"
      placeholder="选择日期时间"
      align="right"
      :picker-options="pickerOptions">
    </el-date-picker>
  </div>
  </el-form-item>
  <el-form-item label="工作职务">
    <el-input v-model="emp.job"></el-input>
  </el-form-item>
  <el-form-item label="薪水">
    <el-input v-model="emp.sal"></el-input>
  </el-form-item>

  <el-form-item>
    <el-button type="primary" :disabled="disable" @click="onSubmit">保存</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import {add,addEmps,get, update} from '@/api/emp'
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
        value1: '',
        value2: '',
        value3: '',
        emp: {
          ename:'',
          hiredate:'1981-04-01T16:00:00.000+00:00',
          job:'Manger',
          sal:1200
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
        if(this.emp.id){
          this.updateInfo(this.emp)
        }else{
          this.addInfo(this.emp)
        }
      },
      getInfo(id){
        get(id).then(response=>{
          this.emp = response.data.emp
        })
      },
      addInfo(emp){
        console.log("add")
        add(emp).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
            });
            // 跳转到列表页
            this.$router.push({path:"/emp/list"})
        })
      },
      updateInfo(emp){
        update(emp).then(reponse=>{
          this.$message({
            type: 'success',
            message: '修改!'
          });
          // 跳转到列表页
          this.$router.push({path:"/emp/list"})
        })
      },
    }
  }
</script>
