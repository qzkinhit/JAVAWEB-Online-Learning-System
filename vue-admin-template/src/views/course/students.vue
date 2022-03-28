<template>
  <div class="app-container">

    <!-- 学生名单  start -->
    <el-table
    :data="students.slice((currentPage - 1) * pagesize, currentPage * pagesize)"
    style="width: 100%">
    <el-table-column
      label="序号"
      width="180">
      <template slot-scope="scope">
        {{scope.$index+1}}
      </template>
    </el-table-column>
    <el-table-column
      label="名字"
      width="180" prop="sname">
    </el-table-column>
  </el-table>
   <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pagesize"
      :total="students.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
  </div>
</template>
<script>
import {getStudents} from '@/api/course'
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
      students:[]
    }
  },
  created(){
    this.$route.params.id,
    this.getStudents(this.$route.params.id)
  },
  methods:{
       handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    // 获取所有学生
    getStudents(id){
       getStudents(id).then(response => {
         this.students = response.data.students
       })
    }
  }
}
</script>