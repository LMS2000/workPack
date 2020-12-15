<template>
  <div id="teacherOrder">
    <div style="float: left;">
      <el-popover
        style="margin-right: 15px;"
        placement="right"
        trigger="click">
       <el-form ref="insertForm" :model="insertForm" :rules="teacherRoles" label-width="80px">
         <el-form-item prop="insertTeacherName" label="教师姓名">
           <el-input type="text" auto-complete="off" v-model="insertForm.insertTeacherName"></el-input>
         </el-form-item>
         <el-form-item prop="insertTeacherNo" label="教工号">
           <el-input type="text" auto-complete="off" v-model="insertForm.insertTeacherNo"></el-input>
         </el-form-item>
         <el-form-item prop="insertCollegeName" label="所属院系">
           <el-input type="text" auto-complete="off" v-model="insertForm.insertCollegeName"></el-input>
         </el-form-item>
         <el-form-item>
           <el-button type="primary" @click="insertTeacher('insertForm')">立即创建</el-button>
           <el-button>取消</el-button>
         </el-form-item>
       </el-form>
        <el-button  type="primary" icon="el-icon-edit" slot="reference"></el-button>
      </el-popover>
      <el-button type="primary"   @click="$router.go(0)"  >刷新</el-button>
    </div>
    <div style="float: right;">
      <el-input v-model="teacherName" placeholder="请输入教师名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary" icon="el-icon-search">搜索</el-button>
    </div><br>
     <div>
       <el-table
          :data="tables"
          style="width: 100%">
          <el-table-column
            prop="tid"
            label="教师ID"
            width="180">
            <template slot-scope="scope"><span v-html="showData(scope.row.tid)"></span></template>
          </el-table-column>
          <el-table-column
          prop="teacherNo"
          label="教师编号"
          width="180">
          <template slot-scope="scope"><span v-html="showData(scope.row.teacherNo)"></span></template>
          </el-table-column>
          <el-table-column
            prop="classNoStr"
            label="班级"
            width="180">
            <template slot-scope="scope"><span v-html="showData(scope.row.classNoStr)"></span></template>
          </el-table-column>
          <el-table-column
            prop="collegeName"
            label="学院所属">
            <template slot-scope="scope"><span v-html="showData(scope.row.collegeName)"></span></template>
          </el-table-column>
          <el-table-column
            prop="teacherName"
            label="教师姓名"
          >
          <template slot-scope="scope"><span v-html="showData(scope.row.teacherName)"></span></template>
          </el-table-column>
       </el-table>
     </div>
     
  </div>
</template>

<script>
  import qs from 'qs'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'teacherOrder',
    data(){
      var validateTeacherName = (rule, value, callback) => {
        const testStudentName =/^[\u0391-\uFFE5]+$/;
             if (value === '') {
               callback(new Error('请输入教师姓名'));
             } else {
               if (!testStudentName.test(value)) {
                 callback(new Error('请输入正确的格式！'));
               }else{
                 callback();
               } 
               
             }
           };
      var validateTeacherNo = (rule, value, callback) => {
        const testStudentNo = /^[1-9][0-9]{7}$/;
            if (value === '') {
              callback(new Error('请输入教工号'));
            } else {
              if (!testStudentNo.test(value)) {
                callback(new Error('请输入正确的教工号格式！'));
              }else 
              {
                callback();
              }
               
            }
          };
      return{
        dialogFormVisible:false,
        webToken:'',
        roleId:'',
        teacherName:'',
          tableData:[{}],
          insertForm:{
            insertTeacherName:'',
            insertTeacherNo:'',
            insertCollegeName:''
          },
          teacherRoles:{
            insertTeacherName:[{ validator: validateTeacherName, trigger: 'blur'}],
            insertTeacherNo:[{ validator: validateTeacherNo, trigger: 'blur'}],
            insertCollegeName:[{required: true, message: '请写入学院', trigger: 'blur'}]
          }
      }
    },
    computed:{
      tables:function(){
        const search =this.teacherName;
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
    mounted(){
      this.getToken();
      this.getTeableDate(this.webToken);
    },
    methods:{
    
      insertTeacher(insertForm){
        this.$refs[insertForm].validate((valid) => {
        if(valid){
        let teacher = {
          teacherName:this.insertForm.insertTeacherName,
          teacherNo:this.insertForm.insertTeacherNo,
          collegeName:this.insertForm.insertCollegeName
        };
        axios.post("http://localhost:8088/teacher",qs.stringify(teacher)).then(res=>{
          let result = res.data.data;
          console.log(result)
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
           axios.get("http://localhost:8088/teacher/getTeacherList").then(res=>{
           let result =res.data.data;
           that.tableData=result;
           })
         }
       },
      
      showData(val){
        val=val+'';
        if(val.indexOf(this.teacherName) !==-1 && this.teacherName!=='')
        {
          return val.replace(this.teacherName,'<font color="#409EFF">'+this.teacherName+'</font>');
        }else{
          return val;
        }
      }
    }
  }
</script>

<style>
</style>
