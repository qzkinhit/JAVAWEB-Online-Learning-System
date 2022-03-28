<template>
  <div class="app-container">

    <!-- 课程列表  start -->
    <el-table
    :data="courses.slice((currentPage - 1) * pagesize, currentPage * pagesize)"
    style="width: 100%">
    <el-table-column
      width="180">
      <template slot-scope="scope">
        <img style="width: 100px; height: 100px; border:none;" :src="scope.row.logo">
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
      width="300">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.row.id)">取消收藏</el-button>
      
        <el-button
          size="mini"
          type="danger"
          @click="getDetail(scope.row.id)">查看细节</el-button>
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
import {getCollection,deleteCollection} from '@/api/course'
import {getToken} from '@/utils/auth'
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
    getDetail(id) {
      this.$router.push("/course/detail/" + id);
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    // 获取所有讲师
    getCourses(){
       getCollection(getToken()).then(response => {
         this.courses = response.data.courses
       })
    },
    // 根据id删除讲师
    handleDelete(id){
      // 弹出提示框
   
          // 调用api中删除讲师的方法
          deleteCollection(id,getToken()).then(response=>{
            // 删除成功后，重新加载讲师列表
            this.getCourses()
            this.$message({
              type: 'success',
              message: '取消成功!'
            });
          })
        
    }
  }
}
</script>