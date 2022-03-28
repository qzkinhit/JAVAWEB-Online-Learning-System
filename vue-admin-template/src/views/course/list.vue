<template>
  <div class="app-container">

    <!-- 课程列表  start -->
    <el-table
    :data="courses.slice((currentPage - 1) * pagesize, currentPage * pagesize)"
    style="width: 100%">
    <el-table-column
      label="序号"
      width="180">
      <template slot-scope="scope">
        {{scope.$index+1}}
      </template>
    </el-table-column>
    <el-table-column
      label="名称"
      width="180" prop="name">
    </el-table-column>
        <el-table-column
      label="类型"
      width="180" prop="title">
    </el-table-column>
   <el-table-column label="结束时间" width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{
            scope.row.endTime | moment
          }}</span>
        </template>
      </el-table-column>

    <el-table-column  fixed="right"
      label="操作"
      width="150">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.row.id)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.row.id)">删除</el-button>
        <el-button
          size="mini"
          @click="handleCheck(scope.row.id)">学生名单</el-button>
          <el-button
          size="mini"
          @click="handleCheck4(scope.row.id)">收藏学生</el-button>
        <el-button
          size="mini"
           type="danger"
          @click="handleCheck2(scope.row.id)">删除任课讲师</el-button>
        <el-button
          size="mini"
          @click="handleCheck3(scope.row.id)">添加任课讲师</el-button>
      </template>
    </el-table-column>
  </el-table>
      <!-- 课程列表  end -->
        <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pagesize"
      :total="courses.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
  </div>
</template>
<script>
import {getAll,deleteByID} from '@/api/course'
export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: "success",
        draft: "gray",
        deleted: "danger",
      };
      return statusMap[status];
    },
  },
  data() {
    return {
      pagesize: 10,
      currentPage: 1,
      courses:[]
    }
  },
  created(){
    this.getCourses()
  },
  methods:{
       handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    // 获取所有讲师
    getCourses(){
       getAll().then(response => {
         this.courses = response.data.items
       })
    },
    // 根据id删除讲师
    handleDelete(id){
      // 弹出提示框
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
          // 如果点击了确定则调用then
        }).then(() => {
          // 调用api中删除讲师的方法
          deleteByID(id).then(response=>{
            // 删除成功后，重新加载讲师列表
            this.getCourses()
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          })
        })
    },
    handleEdit(id){
      // 路由跳转  /teacher/save/xxxxxxx"
      this.$router.push("/course/save/"+id)
    },
    handleCheck(id){
      this.$router.push("/course/students/"+id)
    },
    handleCheck2(id){
      this.$router.push("/course/teachers/"+id)
    },
    handleCheck3(id){
      this.$router.push("/course/lessons/"+id)
    },
    handleCheck4(id){
      this.$router.push("/course/collections/"+id)
    }
  }
}
</script>