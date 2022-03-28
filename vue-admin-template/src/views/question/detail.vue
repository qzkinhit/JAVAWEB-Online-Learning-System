<template>
<div id="all">
    <el-button @click="handleClick" style="margin-block: 0px" type="primary">返回</el-button>
    <div question>
        <el-card shadow="hover" class="question" type="primary">
            <div class="cus">
                <el-avatar shape="square" size="small" :src="cus.avatar" style="vertical-align: middle"></el-avatar>
                <span class="name">{{cus.sname}}</span>
                <span class="name"></span>
                <span class="time">提问于{{question.addTime | moment}}</span>
            </div>
            <h1 class="title">{{question.title}}</h1>
            <p class="content">{{question.content}}</p>
            <el-space wrap size="large">
            <el-button size="small" @click="likeQuestion(question.id)" style="margin-block: 10px" icon="el-icon-thumb">{{question.praiseCount}}</el-button>
            <i id="browse" style="padding: 30px" class="el-icon-view" size="large">{{question.browseCount}}</i>
            <el-button size="small" @click="handleQEdit(question.cusId, question.id)" style="margin-block: 10px" icon="Plus">修改</el-button>
            </el-space>
        </el-card>
    </div>
    <div>
        <el-input type="textarea" :autosize="{ minRows: 4 }" placeholder="请输入内容" v-model="textarea" maxlength="1000"></el-input>
        <el-button @click="addReply(textarea)" style="margin-block: 10px" type="primary">提交</el-button>
        <el-button @click="cleanReply" style="margin-block: 10px">清空</el-button>
    </div>
    <div comments>
        <el-card shadow="hover" class="comment" 
        v-for="(ans, index) in comments.slice((currentPage - 1) * pagesize, currentPage * pagesize)" :key="ans.addTime">
            <el-avatar shape="square" size="small" :src="writers[(currentPage - 1) * pagesize + index].avatar" style="vertical-align: middle"></el-avatar>
            <span class="name">{{writers[(currentPage - 1) * pagesize + index].sname}}</span>
            <span class="name">{{writers[(currentPage - 1) * pagesize + index].sid}}</span>
            <span class="time">回答于{{ans.editTime | moment}}</span>
            <p class="content">{{ans.content}}</p>
            <el-button @click="likeAnswer(ans.id)"  style="margin-block: 10px" icon="el-icon-thumb">{{ans.praiseCount}}</el-button>
            <el-button size="small" @click="handleAEdit(ans.cusId, ans.id)" style="margin-block: 10px" icon="Plus">修改</el-button>
        </el-card>
        <el-pagination
            background
            layout="prev, pager, next, sizes, total, jumper"
            :page-sizes="[5, 10, 15]"
            :page-size="pagesize"
            :total="comments.length"
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
        >
        </el-pagination>
    </div>
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
import { get, likeQuestion, watchQuestion, replyQuestion} from '@/api/question'
import { getAnswerByQuestion, getAnswerByQuestionw, add, likeAnswer} from '@/api/answer'
import { getStuById } from '@/api/stu'
import { getToken } from '@/utils/auth'

export default {
    data(){
        return{
            question: {},
            cus: {
                sname: "匿名用户",
                sid: "",
                avatar: "",
            },
            comments: [],
            textarea: '',
            reply: {},
            pagesize: 5,
            currentPage: 1,
            me: {
                id: "",
                sname: "",
                sid: "",
                avatar: "",
            },
            stuId: "",
            writers: [],
        }
        
    },
    created(){
        this.watchQuestion(this.$route.params.id)
        this.fetchA(this.$route.params.id)
        this.getme(getToken())
    },
    methods:{

        fetchQ(id){
            get(id).then(res => {
                this.question = res.data.question
                getStuById(this.question.cusId).then(response => {
                    this.cus = response.data.stu
                })
            })
        },
        fetchA(id){
            getAnswerByQuestion(id).then(res => {
                this.comments = res.data.items
            })
            getAnswerByQuestionw(id).then(res => {
                this.writers = res.data.items
            })
        },
        handleClick(){
            this.$router.push("/question/index")
        },
        addReply(textarea){
            this.reply.content = textarea
            this.reply.questionId = this.$route.params.id
            this.reply.cusId = this.stuId
            add(this.reply).then(res => {
                this.$message({
                    type: 'success',
                    message: '评论成功!'
                })
                replyQuestion(this.$route.params.id)
                this.fetchA(this.$route.params.id)
                this.textarea = ""
            })
        },
        likeAnswer(aid){
            likeAnswer(aid).then(res => {
                this.$message({
                    type: 'success',
                    message: '点赞成功!'
                })
                this.fetchA(this.$route.params.id)
            })
        },
        likeQuestion(qid){
            likeQuestion(qid).then(res => {
                this.$message({
                    type: 'success',
                    message: '点赞成功!'
                })
                this.fetchQ(this.$route.params.id)
            })
        },
        cleanReply(){
            this.textarea = ""
        },
        watchQuestion(id){
            watchQuestion(id).then(res => {
                this.fetchQ(id)
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
          })
        },
        getstu(id){
          getStuById(id).then(res => {
              this.temp = res.data.stu
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