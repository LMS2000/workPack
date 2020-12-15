<template>
  <div>
    <div style="float: right;">
      <el-input v-model="courseName" placeholder="请输入课程名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary" icon="el-icon-search">搜索</el-button>
    </div><br>
    <div>
       <el-table
          @selection-change="getRowDate"
          :data="tables"
          style="width: 100%"
         >
          <el-table-column
            fixed
            prop="courseName"
            label="课程名"
            width="150">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseName)"></span></template>
          </el-table-column>
          <el-table-column
            prop="substituteTeacher"
            label="任教老师"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.substituteTeacher)"></span></template>
          </el-table-column>
          <el-table-column
            prop="learnTime"
            label="课时"
            width="120">
          </el-table-column>
          
          <el-table-column
            prop="courseType"
            label="课程类型"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseType)"></span></template>
          </el-table-column>
          <el-table-column
            prop="courseMaxCount"
            label="课程总人数"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseMaxCount)"></span></template>
          </el-table-column>
          <el-table-column
            prop="currentCount"
            label="课程当前选课人数"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.currentCount)"></span></template>
          </el-table-column>
          <el-table-column
            fixed="right"
            label="操作"
            width="120">
            <template slot-scope="scope" >
              <el-button
              @click="PostRow(scope.$index,scope.row)"
                type="text"
                size="small">
                选课
              </el-button>
            </template>
          </el-table-column>
        </el-table>
    </div>
    
    <div style="margin-top: 60px;" v-if="status==1">
      <el-table
         @selection-change="getRowDate"
         :data="tableData2"
         style="width: 100%"
         max-height="250">
         <el-table-column
           fixed
           prop="cid"
           label="课程id"
           width="150">
           <template slot-scope="scope"><span v-html="showData(scope.row.cid)"></span></template>
         </el-table-column>
         <el-table-column
           fixed
           prop="courseName"
           label="课程名"
           width="150">
           <template slot-scope="scope"><span v-html="showData(scope.row.courseName)"></span></template>
         </el-table-column>
         <el-table-column
           prop="substituteTeacher"
           label="任教老师"
           width="120">
           <template slot-scope="scope"><span v-html="showData(scope.row.substituteTeacher)"></span></template>
         </el-table-column>
         <el-table-column
           prop="learnTime"
           label="课时"
           width="120">
         </el-table-column>
         <el-table-column
           prop="courseType"
           label="课程类型"
           width="120">
           <template slot-scope="scope"><span v-html="showData(scope.row.courseType)"></span></template>
         </el-table-column>
         <el-table-column
           fixed="right"
           label="操作"
           width="120">
           <template slot-scope="scope" >
             <el-button
             @click="DeleteRow(scope.$index,scope.row,tableData2)"
               type="text"
               size="small">
               退选
             </el-button>
           </template>
         </el-table-column>
       </el-table>
    </div>
    
    
    
  </div>
</template>

<script>
  import {updateData} from '../../static/courseTypeHelper.js'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'courseChoosing',
    courseRow:[{}],
    data(){
      return{
        status:'',
        tableData2:[{}],
        roleId:'',
        webToken:'',
        courseName:'',
        tableData:[{}],
        courseNo:[{}]
      }
    },
    mounted(){
      this.getToken();
      this.getTeableDate(this.webToken);
      this.getTeableDate2(this.webToken);
    },
    computed:{
      tables:function(){
        const search =this.courseName;
        if(search)
        {
          return this.tableData.filter(dataNews=>{
            return Object.keys(dataNews).some(key=>{
              return String(dataNews[key]).toLowerCase().indexOf(search)>-1;
            })
            
          })
          
        }
        return this.tableData;
      }
    },
     methods: {
       DeleteRow(index,rows,table){
         axios.delete("http://localhost:8088/course/"+rows.cid).then(res=>{
           let result =res.data.data;
           this.$message({
             dangerouslyUseHTMLString: true,
             message: '<strong>'+result.msg+'</strong>'
           }); 
         });
         table.splice(index,1);
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
            that.roleId=arr[1];
          }else if(arr[0].trim()=="courseStatus".trim())
          {
            that.status=arr[1];
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
           axios.get("http://localhost:8088/course/courseList/"+that.roleId).then(res=>{
           let result =res.data.data;
           that.tableData=result;
            that.tableData=updateData(this.tableData);
           })
         }
       },
       getTeableDate2(token)
       {
         let that =this;
         if(that.webToken!=='')
         { 
           axios.defaults.headers.common['token'] = token;
           axios.get("http://localhost:8088/course/findCourseDelete").then(res=>{
           let result =res.data.data;
           that.tableData2=result;
            that.tableData2=updateData(this.tableData2);
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
           getRowDate(row){
             this.courseRow=row;
           },
           PostRow(index,rows){
             axios.post("http://localhost:8088/course/student/"+rows.cid).then(res=>{
               let result =res.data.data;
                this.$message({
                  dangerouslyUseHTMLString: true,
                  message: '<strong>'+result.msg+'</strong>'
                });
             });
              this.tableData.splice(index,1);
             }
        }
    
    
  }
</script>

<style>
</style>
