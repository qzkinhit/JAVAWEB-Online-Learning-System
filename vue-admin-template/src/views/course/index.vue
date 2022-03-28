<template>
  <div id="course-list">
    <el-form
      ref="form"
      label-width="300px"
      @keyup.enter.native="searchCourse(input)"
    >
      <el-input
        class="input-item"
        placeholder="请输入内容"
        v-model="input"
        style="width: 800px"
        clearable
      >
      </el-input>
      <el-button size="small" class="input-button" @click="searchCourse(input)"
        >查询</el-button
      >
      <div>
        <el-button size="small" class="input-button" @click="fetchData()"
          >全部</el-button
        >
        <el-button size="small" class="input-button" @click="searchCourse2()"
          >计算机编程</el-button
        >
        <el-button size="small" class="input-button" @click="searchCourse3()"
          >计算机应用</el-button
        >
        <el-button size="small" class="input-button" @click="searchCourse4()"
          >数学基础</el-button
        >
        <el-button size="small" class="input-button" @click="searchCourse5()"
          >其它</el-button
        >
      </div>
    </el-form>

    <el-row :gutter="60">
      <el-col
        :span="8"
        v-for="course in courses.slice(
          (currentPage - 1) * pagesize,
          currentPage * pagesize
        )"
        :key="course.id"
      >
        <el-card direction="vertical" id="box-card" shadow="hover">
          <template #header>
            <div @click="getDetail(course.id)" class="box-card-header">
              <img
                style="width: 200px; height: 200px; border: none"
                :src="course.logo"
              />
            </div>
            <div class="card-header" @click="getDetail(course.id)">
              <span
                ><h1>{{ course.name }}</h1></span
              >
            </div>
          </template>
          <div class="text-item">
            {{ course.title }}
          </div>
          <div>
            <div class="text-item">
              <el-button
                @click="handleEdit(course.id)"
                style="margin-block: 0px"
                type="primary"
                >添加</el-button
              >
              <el-button
                @click="handleEdit2(course.id)"
                style="margin-block: 0px"
                type="primary"
                >收藏</el-button
              >
            </div>
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
        :total="courses.length"
        @current-change="handleCurrentChange"
        @size-change="handleSizeChange"
      >
      </el-pagination>
    </el-row>
  </div>
</template>

<style scoped>
#course-list {
  max-width: 1200px;
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
  addPersonal,
  addCollection,
  getContain,
} from "@/api/course";
import { getToken } from "@/utils/auth";
export default {
  data() {
    return {
      courses: [],
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
        this.courses = response.data.items;
      });
    }, 
    getDetail(id) {
      this.$router.push("/course/detail/" + id);
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    searchCourse(input) {
      search(input).then((res) => {
        this.courses = res.data.items;
      });
    },
    searchCourse2() {
      getContain(1).then((res) => {
        this.courses = res.data.courses;
      });
    },
    searchCourse3() {
      getContain(2).then((res) => {
        this.courses = res.data.courses;
      });
    },
    searchCourse4() {
      getContain(3).then((res) => {
        this.courses = res.data.courses;
      });
    },
    searchCourse5() {
      getContain(4).then((res) => {
        this.courses = res.data.courses;
      });
    },
    handleEdit(id) {
      addPersonal(id, getToken()).then((response) => {
        // 提示信息
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        this.$router.push({ path: "/course/index" });
      });
    },
    handleEdit2(id) {
      addCollection(id, getToken()).then((response) => {
        // 提示信息
        this.$message({
          type: "success",
          message: "添加成功!",
        });
        this.$router.push({ path: "/course/index" });
      });
    },
  },
};
</script>