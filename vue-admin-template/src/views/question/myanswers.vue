<template>
    <div id="all">
        <el-button @click="handleClick" type="primary">返回</el-button>
        <div  @click="getDetail(answer.questionId)"
        v-for="answer in answers.slice((currentPage - 1) * pagesize, currentPage * pagesize)" :key="answer.id">
            <el-card shadow="hover" class="question" type="primary">
                <el-avatar shape="square" size="small" :src="me.avatar" style="vertical-align: middle"></el-avatar>
                <span class="name">{{me.sname}}</span>
                <span class="name">{{me.sid}}</span>
                <span class="time">回答于{{answer.addTime | moment}}</span>
                <h1 class="title">{{answer.title}}</h1>
                <p class="content">{{answer.content}}</p>
                <el-space wrap size="large">
                <el-button size="small" @click="likeAnswer(answer.id)" style="margin-block: 10px" icon="el-icon-thumb">{{answer.praiseCount}}</el-button>
                <el-button size="small" @click="handleAEdit(answer.cusId, answer.id)" style="margin-block: 10px" icon="Plus">修改</el-button>
                </el-space>
            </el-card>
            
    </div>
    <el-pagination
            background
            layout="prev, pager, next, sizes, total, jumper"
            :page-sizes="[5, 10, 15]"
            :page-size="pagesize"
            :total="answers.length"
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
        >
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
import { likeAnswer, getAnswerByCus} from '@/api/answer'
import { getStuById } from '@/api/stu'
import { getToken } from '@/utils/auth'

export default {
    data(){
        return{
            answers: [],
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
            getAnswerByCus(this.stuId).then(res => {
                this.answers = res.data.items
            })
        },
        handleClick(){
            this.$router.push("/question/index")
        },
        getDetail(id){
            this.$router.push("/question/detail/"+id)
        },
        likeAnswer(aid){
            likeAnswer(aid).then(res => {
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
        handleAEdit(cusid, aid){
            if(cusid == this.stuId){
                this.$router.push("/answer/new/"+aid)
            }else {
                this.$message({
                    type: 'error',
                    message: '您不是回答者，没有修改权限!'
                })
            }
        },
    },
}
</script>