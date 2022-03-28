<template>

  <div id="all">
      <el-row>
    <el-button @click="handleClick" style="margin-block: 0px" type="primary"
      >返回</el-button
    >
  </el-row>
   <el-row>
    <div class="title">
      <img
        style="width: 200px; height: 200px; border: none;padding-top:10px"
        :src="teachers.avatar"
      />
    </div>
    <div teachers>
      <el-card shadow="hover" class="teachers" type="primary">
        <h1>{{ teachers.name }}</h1>
        <p>{{ teachers.intro }}</p>
      </el-card>
    </div>
   </el-row>
  </div>
</template>

<style scoped>
.teachers {
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
import { get } from "@/api/teacher";
export default {
  data() {
    return {
      
        teachers: {
          name:'',
          intro:'',
          sort:1,
          level:'',
          career:'',
          avatar:''
        },
        disable:false
    };
  },
  created() {
    this.fetchQ(this.$route.params.id);
  },
  methods: {
    fetchQ(id) {
      get(id).then((res) => {
        this.teachers = res.data.teacher;
        console.log(this.teachers);
      });
    },

    handleClick() {
      this.$router.go(-1);
    },
  },
};
</script>