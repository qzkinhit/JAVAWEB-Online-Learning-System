<template>
  <div id="ques-list">
    <el-row style="padding: 30px">
      <el-col :span="3">
        <el-select v-model="querytype" placeholder="请选择">
          <el-option label="全部" value="-1"></el-option>
          <el-option label="数学" value="1"></el-option>
          <el-option label="计算机" value="2"></el-option>
          <el-option label="语文" value="3"></el-option>
          <el-option label="英语" value="4"></el-option>
          <el-option label="物理" value="5"></el-option>
          <el-option label="化学" value="6"></el-option>
          <el-option label="地理" value="7"></el-option>
          <el-option label="历史" value="8"></el-option>
          <el-option label="政治" value="9"></el-option>
          <el-option label="其它" value="0"></el-option>
        </el-select>
      </el-col>
      <el-col :span="16" :offset="1">
        <el-form
          ref="form"
          @keydown.enter.native="handleClick(querytype, input)">
          <el-input
            placeholder="请输入内容"
            v-model="input"
            style="width: 600px"
            clearable>
          </el-input>
        </el-form>
      </el-col>
      <el-col :span="3" :offset="1">
        <el-button
          size="normal"
          class="input-button"
          @click="handleClick(querytype, input)"
          >查询</el-button>
      </el-col>
    </el-row>
    <div>
      <el-card
        direction="vertical"
        id="box-card"
        shadow="hover"
        class="question"
        v-for="(question, index) in questions.slice((currentPage - 1) * pagesize,currentPage * pagesize)"
        :key="question.id">
        <template #header>
          <div class="card-header" @click="getDetail(question.id)">
            <el-avatar
              shape="square"
              size="small"
              :src="writers[(currentPage - 1) * pagesize + index].avatar"
              style="vertical-align: middle"
            ></el-avatar>
            <span class="name">{{ writers[(currentPage - 1) * pagesize + index].sname }}</span>
            <span class="name">{{ writers[(currentPage - 1) * pagesize + index].sid }}</span>
            <span><h1>{{ question.title }}</h1></span>
          </div>
        </template>
        <span class="text-item">{{ question.content }}</span>
        <el-row>
          <el-col :span="6" :offset="18"
            ><p class="time">提问于:{{ question.addTime | moment}}</p></el-col
          >
        </el-row>
      </el-card>
    </div>
    <el-pagination
      background
      layout="prev, pager, next, sizes, total, jumper"
      :page-sizes="[5, 10, 15]"
      :page-size="pagesize"
      :total="questions.length"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange">
    </el-pagination>
  </div>
</template>

<style scoped>
#ques-list {
  max-width: 960px;
  margin: 0 auto;
}
#box-card {
  width: 900px;
  margin-block: 30px;
}
.card-header {
  font-family: "PingFang SC";
  font-weight: 1900;
  padding: 10px;
}
.text-item {
  font-family: "PingFang SC";
  font-size: 20px;
  font-weight: 400;
  padding: 30px;
}
.time {
  font-family: "PingFang SC";
  color: dimgrey;
  font-size: 10px;
  padding-left: 30px;
}
.writer {
  padding: 10px;
}
.name {
  font-size: 15px;
  color: dimgrey;
  font-family: "PingFang SC";
  font-weight: bold;
  padding-left: 10px;
  vertical-align: middle;
}
</style>

<script>
import { getAll, likeQuestion, search, query, getAllw, searchw, queryw,} from "@/api/question";
import { get, getStuById } from "@/api/stu";
export default {
  data() {
    return {
      questions: [],
      writers: [],
      pagesize: 5,
      currentPage: 1,
      input: "",
      querytype: "-1",
    };
  },
  created() {
    this.fetchData()
  },
  methods: {
    likeQuestion(qid) {
      likeQuestion(qid).then((res) => {
        this.$message({
          type: "success",
          message: "点赞成功!",
        });
        this.fetchQ(this.$route.params.id);
      });
    },
    fetchData() {
      getAll().then((response) => {
        this.questions = response.data.items;
      });
      getAllw().then((response) => {
        this.writers = response.data.items;
      });
    },
    getDetail(id) {
      this.$router.push("/question/detail/" + id);
    },
    handleSizeChange(psize) {
      this.pagesize = psize;
    },
    handleCurrentChange(cpage) {
      this.currentPage = cpage;
    },
    searchQuestions(input) {
      search(input.split(" ")).then((res) => {
        this.questions = res.data.items;
      });
      searchw(input.split(" ")).then((res) => {
        this.writers = res.data.items;
      });
    },
    handleClick(type, w) {
      if (type != "") {
        query(type, w).then((res) => {
          this.questions = res.data.items;
        });
        queryw(type, w).then((res) => {
          this.writers = res.data.items;
        });
      } else {
        query(-1, w).then((res) => {
          this.questions = res.data.items;
        });
        queryw(-1, w).then((res) => {
          this.writers = res.data.items;
        });
      }
    },
  },
};

</script>
