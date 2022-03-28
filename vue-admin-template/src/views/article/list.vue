<template>
  <div class="app-container">

    <!-- 讲师列表  start -->
    <el-table
    :data="articles.slice((currentPage - 1) * pagesize, currentPage * pagesize)"
    style="width: 100%">
    <el-table-column
      label="序号"
        fixed="left"
      width="60">
      <template slot-scope="scope">
        {{scope.$index+1}}
      </template>
    </el-table-column>
    <el-table-column
      label="标题"
      width="180" prop="title">
    </el-table-column>
       <el-table-column label="创建时间" width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{
            scope.row.createTime | moment
          }}</span>
        </template>
      </el-table-column>
  
       <el-table-column label="最后编辑时间" width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{
            scope.row.publishTime | moment
          }}</span>
        </template>
      </el-table-column>
     <el-table-column
      label="标签"
      width="180" prop="type">
    </el-table-column>
    <el-table-column
      label="作者"
      width="180" prop="author">
    </el-table-column>
    <el-table-column
      label="文章大类"
      width="180"> 
       <template slot-scope="scope">
        {{scope.row.sort == 1 ? "数学" : scope.row.sort == 2? "计算机": scope.row.sort == 3? "语文": scope.row.sort == 4? "英语": scope.row.sort == 5? 
      "物理": scope.row.sort == 6? "化学": scope.row.sort == 7? "地理": scope.row.sort == 8? "历史": scope.row.sort == 9? "地理":"其它"}}
         </template>
    </el-table-column>
     <el-table-column
      label="文章关键词"
      width="180" prop="keyword">
    </el-table-column>
   <el-table-column
      label="内容"
      width="180" prop="link">
    </el-table-column>
    <el-table-column label="操作"
     fixed="right"
      width="150">
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
      :page-sizes="[7, 12, 15, 18]"
      :page-size="pagesize"
      :total="articles.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
  </div>
</template>
<script>
import {getAll,deleteByID} from '@/api/article'
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
      pagesize: 7,
      currentPage: 1,
      articles:[]
    }
  },
  created(){
    this.getArticles()
  },
  methods:{
    // 获取所有文章
      handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    getArticles(){
       getAll().then(response => {
         this.articles = response.data.items
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
            this.getArticles()
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          })
        })
    },
    handleEdit(id){
      // 路由跳转  /teacher/save/xxxxxxx"
      this.$router.push("/article/save/"+id)
    }
  }
}
</script>