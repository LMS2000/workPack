<template>
  <div id="adminBar">
    <el-container>
      <el-header>
      </el-header>
    <el-container>
      <el-aside style="width: 200px;" > 		
           <el-col :span="18" style="width: 200px; border: #42B983 1px solid;">
              <h2>后台管理系统</h2>
              <el-menu
                :default-active="indexList"
                class="el-menu-vertical-demo"
                @select="handleSelect"
                mode="horizontal"
                 >
               <el-menu-item index="/adminHome/teacherOrder">
                 <i class="el-icon-user"></i>
                 <span>教师管理</span>
               </el-menu-item>
               <el-menu-item index="/adminHome/courseAdmin">
                  <i class="el-icon-notebook-2"></i>
                  <span>课程管理</span>
               </el-menu-item>
               <el-menu-item index="/adminHome/dailyRecord">
                  <i class="el-icon-s-comment"></i>
                  <span>日志管理</span>
               </el-menu-item>
               <el-menu-item index="/adminHome/classMaster">
                  <i class="el-icon-s-comment"></i>
                  <span>班级管理</span>
               </el-menu-item>
               <el-menu-item >
                  <i class="el-icon-s-comment"></i>
                  <span @click="outLogin">注销</span>
               </el-menu-item>
              </el-menu>
            </el-col>
      </el-aside>
      <el-main><router-view/></el-main>
    </el-container>
    </el-container>
  </div>
</template>

<script>
  export default{
    name:'adminBar',
    data(){
      return{
        indexList:'/adminHome',
        role:'',
        webToken:''
      }
    },
    mounted(){
        this.getCookie();
        if(this.role.trim()!='3')
        {
                 this.$message({
                   dangerouslyUseHTMLString: true,
                   message: '<strong>权限不足！</strong>'
                 }); 
                 this.$router.back(-1);
        }
       },
    methods:{
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
      handleSelect(key){
        this.indexList=key;
          this.$router.push(key);
      }
    }
    
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
