<template>
  <div class="app-container">

    <!-- 讲师列表  start -->
    <el-table
    :data="teachers.slice((currentPage - 1) * pagesize, currentPage * pagesize)"
    style="width: 100%">
    <el-table-column
      label="序号"
      width="180">
      <template slot-scope="scope">
        {{scope.$index+1}}
      </template>
    </el-table-column>
    <el-table-column
      label="姓名"
      width="180" prop="name">
    </el-table-column>
        <el-table-column
      label="头衔"
      width="180">
      <template slot-scope="scope">
        {{scope.row.level == 1 ? "高级讲师" : "首席讲师"}}
      </template>
    </el-table-column>
    <el-table-column
      label="资历"
      width="480" prop="intro">
    </el-table-column>
    
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.row.id)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
      <!-- 讲师列表  end -->
  <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pagesize"
      :total="teachers.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
  </div>
</template>
<script>
import {getAll,deleteByID} from '@/api/teacher'
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
      teachers:[]
    }
  },
  created(){
    this.getTeachers()
  },
  methods:{
    // 获取所有讲师
      handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    getTeachers(){
       getAll().then(response => {
         this.teachers = response.data.items
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
            this.getTeachers()
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          })
        })
    },
    handleEdit(id){
      // 路由跳转  /teacher/save/xxxxxxx"
      this.$router.push("/teacher/save/"+id)
    }
  }
}
</script>