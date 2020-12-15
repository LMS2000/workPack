<template>
 <div class="login"  :style="bg">
   <el-form id="loginForm"  ref="loginForm" style=" margin-top:100px;"   :model="loginForm" :rules="loginRules" class="demo-ruleForm" label-position="left">
          <h3 class="title" style="text-align: center;">
                  Learning 后台管理系统
          </h3>		
     <el-form-item prop="username" label="账号" >
             <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
             </el-input>
      </el-form-item>
     <el-form-item prop="password" label="密码" >
                  <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码" >
                  </el-input>
        </el-form-item>
      <el-form-item style="width:100%;">
        <el-checkbox v-model="loginForm.checkRemember" >
                 记住我
        </el-checkbox>
               <el-button style="margin-left: 60px;width: 200px;" :loading="loading" size="medium" type="primary"  @click="handleLogin('loginForm'),rememberMe()">
                 <span v-if="!loading">登 录</span>
                 <span v-else>登 录 中...</span>
               </el-button>
      </el-form-item>		
   </el-form>
 </div>
</template>

<script>
  import axios from '../static/axios/dist/axios.min.js'
export default{
   name:"login",
   data() {
     var validateUserName = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入账号'));
            } else {
              callback();
            }
          };
          var validatePass = (rule, value, callback) => {
                 if (value === '') {
                   callback(new Error('请输入密码'));
                 } else {
                   callback();
                 }
               };
    return {
        bg: {
         backgroundImage: "url(" + require("../assets/images/background.jpg") + ")",
         backgroundRepeat: "no-repeat",
         backgroundSize: "100% 100%",
         width:'100%',
         height:'100%',
         border:'red 1px solid'
         },
    loginForm: {
       username: '24212412',
       password: '123456',
       checkRemember: false
    },
    loginRules: {
       username: [{  validator: validatePass ,required: true, trigger: 'blur', message: '用户名不能为空' }],
       password: [{ validator: validateUserName ,required: true, trigger: 'blur', message: '密码不能为空' }]
    }, 
      loading:false
        }   
   
   
   },
   mounted(){
     this.getCookie()
   },
  methods:{
    rememberMe() {
      let that =this;
      if(that.loginForm.checkRemember == true){
        that.setCookie(that.loginForm.username , that.loginForm.password ,1);
      }else{
        that.clearCookie();
      }
    },
    handleLogin(loginForm){
      let that =this;
      that.loading =true;
       this.$refs[loginForm].validate((valid) => {
      if(valid){
      axios.post('http://localhost:8088/user/token?'+'userName='+
      that.loginForm.username+'&'+'password='+that.loginForm.password).then(res=>{
        let data =res.data.data;

        if(data.resCode.trim()=="0406")
        {
          this.loading=false;
          this.$message({
                   dangerouslyUseHTMLString: true,
                   message: '<strong>'+data.msg+'</strong>'
                 });
        }else if(data.resCode.trim()=="0200")
        {
          let exdate = new Date();
          exdate.setTime(exdate.getTime()+24*60*60*1000);
          window.document.cookie="token"+"="+data.token+";path=/;expires="+exdate.toGMTString();
          window.document.cookie="roleId"+"="+data.roleId+";path=/;expires="+exdate.toGMTString();
          if(data.roleId==1)
          {
            that.$router.push("/home")
          }else if(data.roleId==2)
          {
            that.$router.push("/teacherHome")
          }
          else if(data.roleId==3)
          {
            that.$router.push("/adminHome")
          }
          else{
            this.$message({
              dangerouslyUseHTMLString: true,
              message: '<strong>账号或密码错误</strong>'
            });
            that.loading=false;
          }
        }
       
      }).catch(function(err){
         console.log(err);
      })
      }else{
        this.$message({
          dangerouslyUseHTMLString: true,
          message: '<strong>'+'账号或密码为空！'+'</strong>'
        });
        this.loading=false;
      }
      });
    },
    setCookie(c_name,c_pass,days){
      let exdate  = new Date();
      exdate.setTime(exdate.getTime()+24*60*60*1000*days);
      window.document.cookie=
           "userName"+"="+c_name+";path=/;expires="+exdate.toGMTString();
      window.document.cookie=
           "password"+"="+c_pass+";path=/;expires="+exdate.toGMTString();
            
         
    },
    getCookie:function(){
      let that =this;
      if(document.cookie.length>0)
      {
        let arr =document.cookie.split(";");
        for(let i; i<arr.length; i++)
        {
          let arr2 =arr[i].split("=");
          if(arr2[0] == "userName")
          {
            that.loginForm.userName=arr[1];
          }else if(arr2[0] == "password")
          {
            that.loginForm.password=arr2[1];
          }
        }
      }
      
    },
    clearCookie:function(){
      this.setCookie("","",-1);
    }
  
  }
  
}
  
        
      
</script>
<style >

  .demo-ruleForm{
    width: 500px;height: 500px;box-align: center;margin-left: 450px;
  }
  .login_container{
    height: 100%;
  }
  
  .el-header{
      background-color: #B3C0D1;
      color: #333;
      text-align: center;
      line-height: 60px;
    height: auto;
    
    }
    .el-main {
      background-color: #E9EEF3;
      color: #333;
      text-align: center;
      line-height: 160px;
    height: auto;
    }
</style>
