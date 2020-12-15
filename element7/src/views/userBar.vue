<template>
  <div id="userBar">
    <el-container>
      <el-header>
        <el-menu  :default-active="indexList" class="el-menu-demo" mode="horizontal" @select="handleSelect">
          <el-menu-item index="/home" >首页</el-menu-item>
          <el-submenu index="/home/courseOrder">
            <template slot="title" >课程管理</template>
              <el-menu-item index="/home/courseFinalScore" >学期课程成绩</el-menu-item>
              <el-menu-item index="/home/courseOrder" >个人课表</el-menu-item>
              <el-menu-item index="/home/courseOrder" >课程通过查询</el-menu-item>
          </el-submenu>
          <el-menu-item index="/home/courseChoosing" a>选课</el-menu-item>
          <el-menu-item style="float: right;" >
            <el-popover
            style="margin-right: 15px;"
            placement="left"
            trigger="click">
           <el-form ref="userData" :model="userData" :rules="studentRoles" label-width="80px">
             <el-form-item  label="学生姓名">
               <el-input type="text" auto-complete="off" disabled v-model="userData.studentName"></el-input>
             </el-form-item>
             <el-form-item label="性别">
               <el-radio-group     v-model="userData.gender">
                 <el-radio :label="0" >男</el-radio>
                 <el-radio :label="1" >女</el-radio>
               </el-radio-group>
             </el-form-item>
             <el-form-item  label="学号">
               <el-input type="text" auto-complete="off" disabled v-model="userData.studentNo"></el-input>
             </el-form-item>
             <el-form-item prop="email"  label="电子邮箱">
               <el-input type="text" auto-complete="off"  v-model="userData.email"></el-input>
             </el-form-item>
             <el-form-item prop="phone"  label="电话号码">
               <el-input type="text" auto-complete="off"  v-model="userData.phone"></el-input>
             </el-form-item>
             <el-form-item>
               <el-button type="primary" @click="insertCourse('userData')">修改信息</el-button>
               <el-button>取消</el-button>
             </el-form-item>
           </el-form>
            <el-button icon="el-icon-user-solid" circle="" slot="reference"></el-button>
          </el-popover>
          </el-menu-item>
          <el-menu-item style="float: right;">
            <el-button @click="outLogin">注销</el-button>
          </el-menu-item>
          <el-menu-item style="float: right;">
            <el-button @click="dialogFormVisible=true">修改密码</el-button>
          </el-menu-item>
        </el-menu> 
        <el-dialog title="修改密码"  :visible.sync="dialogFormVisible">
          <el-form ref="insertForm" :model="insertForm" :rules="passwordRoles">
            <el-form-item prop="oldPassword" label="旧密码" >
              <el-input type="password" v-model="insertForm.oldPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  prop="newPassword" label="新密码" >
              <el-input type="password" v-model="insertForm.newPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  prop="checkPassword" label="确认密码" >
              <el-input type="password" v-model="insertForm.checkPassword" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogFormVisible = false;updatePassword('insertForm')">确 定</el-button>
          </div>
        </el-dialog>
      </el-header>
      <el-main><router-view/></el-main>
    </el-container>
  </div>
</template>

<script>
  import qs from 'qs'
  import axios from'../static/axios/dist/axios.min.js'
  export default{
    name:'userBar',
    data(){
      var validateStudentPhone = (rule, value, callback) => {
        const testStudentNo = /^[1-9][0-9]{10}$/;      
              if (!testStudentNo.test(value)) {
                callback(new Error('请输入正确的电话号码格式！'));
              }else 
              {
                callback();
              }
          };
          var validateStudentEmail = (rule, value, callback) => {
            const testStudentNo =/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                  if (!testStudentNo.test(value)) {
                    callback(new Error('请输入正确的电子邮箱格式！'));
                  }else 
                  {
                    callback();
                  }
  
              };
              var validateNewPassword=(rule,value,callback)=>{
                if(value=='')
                {
                  callback(new Error('请输入确认密码!'))
                }
                else if(value!==this.insertForm.newPassword)
                {
                  callback(new Error('新旧密码不一致！'))
                }
                callback();
              }
       return{
         insertForm:{
           oldPassword:'',
           newPassword:'',
           checkPassword:''
         },
         dialogFormVisible:false,
         indexList:'/home',
         webToken:'',
         userData:{},
         role:'',
         studentRoles:{
           email:[{validator:validateStudentEmail, trigger:'blur'}],
           phone:[{validator:validateStudentPhone, trigger:'blur'}]
         },
         passwordRoles:{
          oldPassword:[{ required: true, message: '请输入旧密码', trigger: 'blur' },],
          newPassword:[{ required: true, message: '请输入新密码', trigger: 'blur' },
            { min: 8, max: 12, message: '长度在 8 到 10 个字符', trigger: 'blur' }],
          checkPassword:[{ validator:validateNewPassword, trigger: 'blur' },
            { min: 8, max: 12, message: '长度在 8 到 10 个字符', trigger: 'blur' }],
         }
       }
     },
    mounted(){
       this.getCookie();
       this.getUserData();
      },
    methods:{
      updatePassword(insertForm){
        this.$refs[insertForm].validate((valid) => {
                 if (valid) {
                   axios.put("http://localhost:8088/user/update/"+this.insertForm.oldPassword+"/"+this.insertForm.newPassword).then(res=>{
                     let result =res.data.data;
                     this.$message({
                       dangerouslyUseHTMLString: true,
                       message: '<strong>'+result.msg+'</strong>'
                     }); 
                
                   })
                 } else {
                   this.$message({
                     dangerouslyUseHTMLString: true,
                     message: '<strong>提交失败,内容格式错误！</strong>'
                   }); 
                  
                 }
               });
      },
      outLogin(){
      this.setCookie('','',-1);
      this.$router.push("/");
      },
      setCookie(c_name,c_pass,days){
        let exdate  = new Date();
        exdate.setTime(exdate.getTime()+24*60*60*1000*days);
        window.document.cookie=
             "token"+"="+c_name+";path=/;expires="+exdate.toGMTString();
        window.document.cookie=
             "roleId"+"="+c_pass+";path=/;expires="+exdate.toGMTString();    
      },
      insertCourse(userData){
        let student = {
          sid:this.userData.sid,
          email:this.userData.email,
          phone:this.userData.phone
        };
         this.$refs[userData].validate((valid) => {
                  if (valid) {
                    axios.put("http://localhost:8088/student",qs.stringify(student)).then(res=>{
                      let result =res.data.data;
                      this.$message({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>'+result+'</strong>'
                      }); 
                    })
                  } else {
                    this.$message({
                      dangerouslyUseHTMLString: true,
                      message: '<strong>提交失败,内容格式错误！</strong>'
                    }); 
                    return false;
                  }
                });
        
        },
      getUserData(){
        axios.defaults.headers.common['token'] = this.webToken;
        axios.get("http://localhost:8088/student/getOne").then(res=>{
          let result =res.data.data;
          this.userData=result.student;
          
        })
      },
       handleSelect(key){
         this.indexList=key;
           this.$router.push(key);
       },    
       getCookie:function(){
          let that =this;
          if(document.cookie.length>0)
          {
           let arrs = document.cookie.split(";");
           for(let i=0;i<arrs.length;i++)
          {
            let arr = arrs[i].split("=");
             if(arr[0].trim() == "roleId".trim())
             {
               that.role=arr[1];
             }else if(arr[0].trim()=="token".trim())
             {
               that.webToken=arr[1];
             }
           }
         
          }
       }}
  }
 </script>

<style>
.el-header{
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
  }
    
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    height: 620px;
  }
  .el-main {    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    height: 600px;
  }
 
  body > .el-container {
    margin-bottom: 40px;
    height: 620px;
  }</style>