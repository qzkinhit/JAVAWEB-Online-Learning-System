<template>
<div>
  <el-table :data="courses1" style="width: 100%;padding-top: 15px;">
    <el-table-column label="我收藏的课程" min-width="100">
      <template slot-scope="scope">
        {{ scope.row.name}}
      </template>
    </el-table-column>
    <el-table-column label="结束时间" width="200" align="center">
      <template slot-scope="scope">
        {{ scope.row.endTime|moment }}
      </template>
    </el-table-column>
    <el-table-column  
      label="操作"
      width="150">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.row.id)">退课</el-button>
      </template>
       <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="getDetail(scope.row.id)">查看细节</el-button>
      </template>
    </el-table-column>
  </el-table>
  
  <el-table :data="courses2" style="width: 100%;padding-top: 15px;">
    <el-table-column label="我学习的课程" min-width="100">
      <template slot-scope="scope">
        {{ scope.row.name }}
      </template>
    </el-table-column>
    <el-table-column label="结束时间" width="200" align="center">
      <template slot-scope="scope">
        {{ scope.row.endTime|moment }}
      </template>
    </el-table-column>

    <el-table-column 
      label="操作"
      width="150">
      
       <template slot-scope="scope">
        <el-button
          size="mini"
          type="danger"
          @click="getDetail(scope.row.id)">查看细节</el-button>
      </template>
    </el-table-column>
  </el-table>
  </div>
 

</template>


<script>
import {getCourse,getCollection,deletePersonal} from '@/api/course'
import {getToken} from '@/utils/auth'

export default {
  filters: {
    statusFilter(status) {
      const statusMap = {
        success: 'success',
        pending: 'danger'
      }
      return statusMap[status]
    },
    orderNoFilter(str) {
      return str.substring(0, 30)
    }
  },
  data() {
    return {
      courses1:[],
       courses2:[]
    }
  },
  created() {
    this.fetchData1()
     this.fetchData2()
  },
  methods: {
     getDetail(id) {
      this.$router.push("/course/detail/" + id);
    },
    fetchData1() {
       getCollection(getToken()).then(response => {
         this.courses1 = response.data.courses
       })
    },
    fetchData2() {
     getCourse(getToken()).then(response => {
         this.courses2 = response.data.courses
       })
  }
}
}
</script>
