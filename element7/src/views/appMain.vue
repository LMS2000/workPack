<template>
  <div id="appMain">
    <main-bar v-if="role==1"></main-bar>
    <teacher-bar v-if="role==2"></teacher-bar>
		<admin-bar v-if="role==3"></admin-bar>
  </div>
</template>

<script>
  import mainBar from './userBar.vue'
  import teacherBar from './teacherBar.vue'
	import adminBar from './adminBar.vue'
  export default{
    name:'appMain',
    components:{
      mainBar,teacherBar,adminBar
    },
    mounted(){
      this.getCookie()
      if(this.role ==''){
         this.$message({
                  dangerouslyUseHTMLString: true,
                  message: '<strong>请登陆后再访问此网页！</strong>'
                });
        this.$router.push("/login");
      }
    },
    data(){
      return{
        role:''
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
         }
       }
     
       }
      }			
    }
  
  }
</script>

<style>
</style>
