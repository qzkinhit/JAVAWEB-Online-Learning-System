<template>
  <div id="teacher-list">
    <el-form
      ref="form"

      label-width="300px"
      @keyup.enter.native="searchTeacher(input)"
    >
      <el-input
        class="input-item"
        placeholder="请输入内容"
        v-model="input"
        style="width: 800px"
        clearable
      >
      </el-input>
      <el-button size="small" class="input-button" @click="searchTeacher(input)"
        >查询</el-button
      >

    </el-form>

      <el-row>
  
      <el-col :span="8"  v-for="teacher in teachers.slice(
            (currentPage - 1) * pagesize,
            currentPage * pagesize
          )"
          :key="teacher.id">
        <el-card
          direction="vertical"
          id="box-card"
          shadow="hover"
        >
          <template #header>
            <div @click="getDetail(teacher.id)" class="box-card-header">
              <img
                style="width: 200px; height: 200px; border: none"
                :src="teacher.avatar"
              />
            </div>
            <div class="card-header" @click="getDetail(teacher.id)">
              <span
                ><h1>{{ teacher.name }}</h1></span
              >
            </div>
          </template>
          <div class="text-item" style="width: 300px;">
            {{ teacher.intro }}
          </div>
        </el-card>
      </el-col>
      
       </el-row>
   <el-row>
    <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[3, 6, 9, 12]"
      :page-size="pagesize"
      :total="teachers.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
    >
    </el-pagination>
     </el-row>
  </div>
</template>

<style scoped>
#teacher-list {
  max-width:  1000px;
  margin: 20px auto;
}
#box-card {
  width: 400px;
  margin-block: 30px;
}
.card-header {
  font-family: "Microsoft YaHei";
  font-weight: 1200;
  padding: 10px;
}
.text-item {
  font-family: "Microsoft YaHei";
  font-size: 120%;
  font-weight: 400;
  padding: 30px;
}
.input-item {
  padding: 30px;
  width: 300px;
}
</style>

<script>
import {
  getAll,
  search,
} from "@/api/teacher";
export default {
  data() {
    return {
      teachers: [],
      pagesize: 3,
      currentPage: 1,
      input: "",
    };
  },
  created() {
    this.fetchData(), this.$route.params.id;
  },
  methods: {
    fetchData() {
      getAll().then((response) => {
        this.teachers = response.data.items;
      });
    },
    getDetail(id) {
      this.$router.push("/teacher/detail/" + id);
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    searchTeacher(input) {
      search(input).then(res => {
        this.teachers = res.data.items;
        console.log(this.teachers);
      });
    },
   
  },
};
</script>