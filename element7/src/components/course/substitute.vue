<template>
  <div id="substitute">
    <div style="float: left;">
      <el-button type="primary"  @click="ChangeScore">课程学生成绩</el-button>
      <el-button type="primary"  @click="flush" >授课</el-button>
      <el-button type="primary"  @click="ChangeCourse">已授课程</el-button>
    </div>
    
    <div style="float: right;">
      <el-input v-model="courseName" placeholder="请输入课程名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary" icon="el-icon-search">搜索</el-button>
    </div>
    <div>
      <el-table :data="tables" style="width: 100%" :row-class-name="tableRowClassName">
        
       <el-table-column prop="courseFk" label="课程名" width="180" v-if="courseStatus==2">
         <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.courseName)"></span></template>
         </el-table-column>
         <el-table-column prop="studentFk" label="学生名" width="180" v-if="courseStatus==2">
           <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.studentName)"></span></template>
           </el-table-column>
        <el-table-column prop="score" label="课程成绩" width="180" v-if="courseStatus==2">
          <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.score)"></span></template>
          </el-table-column>
          <el-table-column prop="finalEvaluation" label="课程最终评价" width="180" v-if="courseStatus==2">
            <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.finalEvaluation)"></span></template>
            </el-table-column>
          <el-table-column prop="courseName" label="课程名" width="180" v-if="courseStatus!=2">
            <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.courseName)"></span></template>
            </el-table-column>
          <el-table-column prop="learnTime" label="课时" width="180" v-if="courseStatus!=2">
             <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.learnTime)"></span></template>
          </el-table-column>
          <el-table-column prop="courseType" label="课程类型" v-if="courseStatus!=2">
             <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.courseType)"></span></template>
          </el-table-column>
          <el-table-column prop="courseDesc" label="上课时间" v-if="courseStatus!=2">
             <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.courseDesc)"></span></template>
          </el-table-column>
          <el-table-column prop="learnTime" label="课时" v-if="courseStatus!=2">
             <template slot-scope="scope"><span class="col-cont" v-html="showData(scope.row.learnTime)"></span></template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120" v-if="courseStatus==0">
						
            <template slot-scope="scope"  >
              <el-button
              @click="PostRow(scope.$index,scope.row)"
                type="text"
                size="small">
                授课
              </el-button>
            </template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120" v-if="courseStatus==2">
            <template slot-scope="scope"  >
              <el-button
              @click="updateRow(scope.$index,scope.row)"
                type="text"
                size="small">
                编辑
              </el-button>
            </template>
          </el-table-column>
      </el-table>
      <el-dialog title="学生详细信息"  :visible.sync="dialogFormVisible">
        <el-form  ref="insertForm" :model="insertForm" :rules="studentRoles">
          <el-form-item prop="score" label="成绩" >
            <el-input v-model="insertForm.score" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item  prop="finalEvaluation" label="评价" >
            <el-input v-model="insertForm.finalEvaluation" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateScore('insertForm')">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    
  </div>
</template>

<script>
  import {updateData} from '../../static/courseTypeHelper.js'
  import qs from 'qs'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'substitute',
    data(){
      var validScore= (rule, value, callback)=>{
        const testScore =/^([1-9][0-9]*)+(.[0-9]{1,2})$/;
             if (value === '') {
               callback(new Error('请输入成绩！'));
             } else {
               if (!testScore.test(value)) {
                 callback(new Error('请输入正确的格式！'));
               }else{
                 callback();
               } 
               
             }
        
      };
      return{
        dialogFormVisible:false,
        courseStatus:0,
        roleId:'',
        termValue:'',
        yearValue:'',
        courseName:'',
        insertForm:{
          studentName:'',
          courseName:'',
          score:'',
          finalEvaluation:''
        },
        studentRoles:{
          score:[{ validator: validScore, trigger: 'blur'}],
          finalEvaluation:[{required: true, message: '请输入评价',  trigger: 'blur'}]
        },
        tableData: []
        
      }
    },
    computed:{
      tables:function(){
        const course =this.courseName;
        if(course)
        {
          return this.tableData.filter(dataNews=>{
            return Object.keys(dataNews).some(key=>{
              return String(dataNews[key]).toLowerCase().indexOf(course)>-1;
            })
            
          })
          
        }
        return this.tableData;
      }
    },
    mounted(){
      this.getToken();
      this.getTeableDate(this.webToken);
      },
    methods:{
      updateScore(insertForm){
      this.$refs[insertForm].validate((valid) => {
      if(valid){
      let courseStudent = {
        studentName:this.insertForm.studentName,
        courseName:this.insertForm.courseName,
        score:this.insertForm.score,
        finalEvaluation:this.insertForm.finalEvaluation
      };
      axios.put("http://localhost:8088/course/score",qs.stringify(courseStudent)).then(res=>{
        let result = res.data.data;
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '<strong>'+result.msg+'</strong>'
        });
      })
      }else{
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '<strong>插入失败！</strong>'
        });
      }
      });
        this.dialogFormVisible=false;
      },
      updateRow(index,row){
        this.dialogFormVisible=true;
        this.insertForm.studentName=row.studentName;
        this.insertForm.courseName=row.courseName;
        this.insertForm.score=row.score;
        this.insertForm.finalEvaluation=row.finalEvaluation;
      },
      ChangeScore(){
        let that =this;
        axios.get("http://localhost:8088/course/score").then(res=>{
          let result =res.data.data;
          that.courseStatus=2;
          that.tableData=result;
          
        })
      },
      flush(){
        this.$router.go(0);
      },
      ChangeCourse(){
        let that= this;
        axios.defaults.headers.common['token'] = this.webToken;
        axios.get("http://localhost:8088/course/teacher").then(res=>{
          let result = res.data.data;
          that.courseStatus=1;
          this.tableData=updateData(result);
					
        })
        
      },
      getToken:function(){
      let that =this;
      if(document.cookie.length>0)
      {
       let arrs = document.cookie.split(";");
        for(let i=0;i<arrs.length;i++)
       {
        let arr = arrs[i].split("=");
         if(arr[0].trim() == "token".trim())
          {
           that.webToken=arr[1];
          }else if(arr[0].trim()=="roleId".trim())
          {
            this.roleId=arr[1];
          }
        }
       }
       },
       getTeableDate(token)
       {
         let that =this;
         if(that.webToken!=='')
         { 
           axios.defaults.headers.common['token'] = token;
           axios.get("http://localhost:8088/course/courseList/"+this.roleId).then(res=>{
           let result =res.data.data;
           that.tableData=updateData(result);
           console.log(that.tableData);
           })
         }
       },
      showData(val){
        val=val+'';
        if(val.indexOf(this.courseName) !==-1 && this.courseName!=='')
        {
          return val.replace(this.courseName,'<font color="#409EFF">'+this.courseName+'</font>');
        }else{
          return val;
        }
        
      },
      PostRow(index,rows){
        axios.post("http://localhost:8088/course/teacher/"+rows.cid).then(res=>{
          let result =res.data.data;
           this.$message({
             dangerouslyUseHTMLString: true,
             message: '<strong>'+result.msg+'</strong>'
           });
        });
                    this.tableData.splice(index,1);
        },
      tableRowClassName({rowIndex}) {
        if (rowIndex === 1) {
          return 'warning-row';
        } else if (rowIndex === 3) {
          return 'success-row';
        }
        return '';
      }
    }
  }
</script>

<style>
</style>
