<template>
  <div id="all">
    <el-button @click="handleClick" style="margin-block: 0px" type="primary"
      >返回</el-button
    >
    <div class="title" @click="getLink()">
      <img
        style="width: 200px; height: 200px; border: none"
        :src="course.logo"
      />
    </div>
    <div course @click="getLink()">
      <el-card shadow="hover" class="course" type="primary">
        <h1>{{ course.name }}</h1>
        <p>{{ course.title }}</p>
        <el-space wrap size="large">
          <el-button
            size="small"
            @click="likeCourse(course.id)"
            style="margin-block: 10px"
            icon="el-icon-thumb"
            >{{ course.zan }}</el-button
          >
          <i
            id="browse"
            style="padding: 30px"
            class="el-icon-view"
            size="large"
            >{{ course.through }}</i
          >
        </el-space>
      </el-card>
    </div>

    <div comment>
      <h1>任课教师</h1>
      <el-table :data="teachers" style="width: 100%">
        <el-table-column label="名字" width="180" prop="name">
        </el-table-column>
        <el-table-column label="职称" width="180" prop="career">
        </el-table-column>
        <el-table-column label="照片" width="180">
          <template slot-scope="scope" >
            <img
            @click="getDetail(scope.row.id)"
              style="width: 100px; height: 100px; border: none"
              :src="scope.row.avatar"
            />
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div></div>
  </div>
</template>

<style scoped>
.course {
  padding: 10px;
  max-width: 960px;
  margin: 0 auto;
  margin-block: 30px;
}
.comment {
  padding: 10px;
  max-width: 900px;
  margin: 0 auto;
  margin-block: 30px;
}
#all {
  padding: 10px;
  max-width: 960px;
  margin: 0 auto;
  margin-block: 30px;
}
</style>

<script>
import { get, likeCourse, watchCourse, getTeachers } from "@/api/course";
export default {
  data() {
    return {
      course: "",
      teachers: [],
    };
  },
  created() {
    this.watchCourse(this.$route.params.id),
      this.getTeachers(this.$route.params.id);
  },
  methods: {
     getLink(){
                window.location.href = this.course.link
            
        },
    getDetail(id) {
      this.$router.push("/teacher/detail/" + id);
    },
    fetchQ(id) {
      get(id).then((res) => {
        this.course = res.data.course;
        console.log(this.course);
      });
    },
    getTeachers(id) {
      getTeachers(id).then((response) => {
        this.teachers = response.data.teachers;
      });
    },
    handleClick() {
      this.$router.go(-1);
    },
    likeCourse(qid) {
      likeCourse(qid).then((res) => {
        this.fetchQ(this.$route.params.id);
      });
    },
    watchCourse(id) {
      watchCourse(this.$route.params.id).then((res) => {
        this.fetchQ(this.$route.params.id);
      });
    },
  },
};
</script>