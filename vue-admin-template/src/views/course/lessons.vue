<template>
  <div class="app-container">

    <!-- 可用讲师列表  start -->
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
      label="名字"
      width="180" prop="name">
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.row.id)">添加</el-button>
      </template>
    </el-table-column>
  </el-table>
      <!-- 课程列表  end -->
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
import {list,addLessons} from '@/api/course'
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
    this.$route.params.id,
    this.list(this.$route.params.id)
  },
  methods:{
    // 获取所有讲师
     handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    list(id){
       list(id).then(response => {
         this.teachers = response.data.teachers
       })
    },
    handleEdit(id){
        addLessons(this.$route.params.id,id).then(response=>{
          // 提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
            });
            this.$router.push({path:"/course/list"})
        })
    }
  }
}
</script>