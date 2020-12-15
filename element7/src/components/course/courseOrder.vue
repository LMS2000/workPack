<template>
  <div class="courseOrder">

      <div>
          <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
              <el-table-column prop="courseName" label="课程名" width="180"></el-table-column>
              <el-table-column prop="learnTime" label="课时" width="180"></el-table-column>
              <el-table-column prop="courseType" label="课程类型"></el-table-column>
              <el-table-column prop="courseDesc" label="上课时间"></el-table-column>
							<el-table-column prop="coursePlace" label="地点"></el-table-column>
          </el-table>
      </div>
  </div>
</template>

<script>
	import {updateData} from '../../static/courseTypeHelper.js'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'courseOrder',
    mounted(){
      this.getToken();
      this.getTeableDate(this.webToken);
    },
   methods: {
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
          axios.get("http://localhost:8088/course/student").then(res=>{
          let result =res.data.data;
          that.tableData=result;
          that.tableData=updateData(that.tableData);
          })
        }
      },
        tableRowClassName({rowIndex}) {
          if (rowIndex === 1) {
            return 'warning-row';
          } else if (rowIndex === 3) {
            return 'success-row';
          }
          return '';
        }
      },
      data() {
        return {
          webToken:'',
          yearOptions:[{
                       value:'2020',
                       label:'2020'
                   },{value:'2019',label:'2019'}
                  ],
            termOptions: [{
                    value: '1',
                    label: '1'
                     }, {
                    value: '2',
                    label: '2'
                  }],
                  termValue: '',yearValue:'',
          tableData: [{
            date: '2016-05-02',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
          }, {
            date: '2016-05-04',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }, {
            date: '2016-05-01',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄',
          }, {
            date: '2016-05-03',
            name: '王小虎',
            address: '上海市普陀区金沙江路 1518 弄'
          }]
        }
      }
  };
</script>

<style>
</style>
