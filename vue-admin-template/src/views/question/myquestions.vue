<template>
    <div id="all">
        <el-button @click="handleClick" type="primary">返回</el-button>
        <div @click="getDetail(question.id)"
        v-for="question in questions.slice((currentPage - 1) * pagesize, currentPage * pagesize)" :key="question.id">
            <el-card shadow="hover" class="question" type="primary">
                <el-avatar shape="square" size="small" :src="me.avatar" style="vertical-align: middle"></el-avatar>
                <span class="name">{{me.sname}}</span>
                <span class="name">{{me.sid}}</span>
                <span class="time">提问于{{question.addTime|moment}}</span>
                <h1 class="title">{{question.title}}</h1>
                <p class="content">{{question.content}}</p>
                <el-space wrap size="large">
                <el-button size="small" @click="likeQuestion(question.id)" style="margin-block: 10px" icon="el-icon-thumb">{{question.praiseCount}}</el-button>
                <i id="browse" style="padding: 30px" class="el-icon-view" size="large">{{question.browseCount}}</i>
                <el-button size="small" @click="handleQEdit(question.cusId, question.id)" style="margin-block: 10px" icon="Plus">修改</el-button>
                </el-space>
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
.question{
    padding: 10px;
    max-width: 960px;
    margin: 0 auto;
    margin-block: 30px;
}
.comment{
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
.title {
    font-size: 25px;
    font-weight: bolder;
    font-family: "PingFang SC";
}
.content {
    font-size: 15px;
    font-family: "PingFang SC";
    line-height: 25px;
}
.name {
    font-size: 15px;
    color:dimgrey;
    font-family: "PingFang SC";
    font-weight: bold;
    padding-left: 10px;
    vertical-align: middle;
}
.cus {
    vertical-align: middle;
}
.time {
    vertical-align: middle;
    font-family: "PingFang SC";
    color:dimgrey;
    font-size: 10px;
    padding-left: 20px;
}
</style>

<script>
import { likeQuestion, searchmy} from '@/api/question'
import { getStuById } from '@/api/stu'
import { getToken } from '@/utils/auth'

export default {
    data(){
        return{
            questions: [],
            pagesize: 5,
            currentPage: 1,
            me: {
                id: "",
                sname: "",
                sid: "",
                avatar: "",
            },
            stuId: "",
        }
        
    },
    created(){
        this.getme(getToken())
    },
    methods:{
        fetchData(){
            searchmy(this.stuId).then(res => {
                this.questions = res.data.items
            }),
              searchmy(this.stuId).then(res => {
                this.questions = res.data.items
            })
        },
        getDetail(id){
            this.$router.push("/question/detail/"+id)
        },
        handleClick(){
            this.$router.push("/question/index")
        },
        likeQuestion(qid){
            likeQuestion(qid).then(res => {
                this.$message({
                    type: 'success',
                    message: '点赞成功!'
                })
                this.fetchData()
            })
        },
        handleSizeChange(psize) {
          this.pagesize = psize;
        },
        handleCurrentChange(cpage) {
          this.currentPage = cpage;
        },
        getme(id){
          getStuById(id).then(res => {
              this.me = res.data.stu
              this.stuId = res.data.stu.id
              this.fetchData()
          })
        },
        handleQEdit(cusid, qid){
            if(cusid == this.stuId){
                this.$router.push("/question/new/"+qid)
            }else {
                this.$message({
                    type: 'error',
                    message: '您不是提问者，没有修改权限!'
                })
            }
        },
    },
}
</script>