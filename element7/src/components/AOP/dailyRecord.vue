<template>
  <div id="dailyRecord">
    <div style="float: right;">
      <el-input v-model="search" placeholder="请输入学生名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary"  icon="el-icon-search">搜索</el-button>
    </div>
    <div>
         <el-table :data="tables"  style="width: 100%" stripe>
             <el-table-column prop="url" label="访问url" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.url)"></span></template>
             </el-table-column>
             <el-table-column prop="logMethod" label="访问方法" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.logMethod)"></span></template>
             </el-table-column>
             <el-table-column prop="params" label="访问参数" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.params)"></span></template>
             </el-table-column>
             <el-table-column prop="logTime" label="访问时间">
               <template slot-scope="scope"><span v-html="showData(scope.row.logTime)"></span></template>
             </el-table-column>
             <el-table-column prop="clientIp" label="访问用户ID">
               <template slot-scope="scope"><span v-html="showData(scope.row.clientIp)"></span></template>
             </el-table-column>
         </el-table>
        </div>
    
    
    
  </div>
</template>

<script>
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'dailyRecord',
    data(){
      return{
        tableData:[{}],
        search:'',
        webToken:''
      }
    },
    computed:{
      tables:function(){
        const search =this.search;
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
      showData(val){
        val=val+'';
        if(val.indexOf(this.search) !==-1 && this.search!=='')
        {
          return val.replace(this.search,'<font color="#409EFF">'+this.search+'</font>');
        }else{
          return val;
        }
        
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
          axios.get("http://localhost:8088/log").then(res=>{
          let result =res.data.data;
          that.tableData=result;
          console.log(result);
          })
        }
      }
      
    }
  }
</script>

<style>
</style>
