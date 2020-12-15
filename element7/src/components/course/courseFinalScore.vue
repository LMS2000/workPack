
<template>
  <div id="courseFinalScore">
    <div style="float: left;">
      <el-button type="primary" icon="el-icon-edit" @click="$router.go(0)"></el-button>
      <el-button type="primary" icon="el-icon-share"></el-button>
    </div>
   <div>
     <template>学年：</template>
     <el-select v-model="yearValue" multiple placeholder="请选择学年">
         <el-option
           v-for="item in yearOptions"
           :key="item.value"
           :label="item.label"
           :value="item.value">
         </el-option>
       </el-select>&nbsp;
       <template>学期：</template>
       <el-select v-model="termValue" multiple placeholder="请选择学期">
           <el-option
             v-for="item in termOptions"
             :key="item.value"
             :label="item.label"
             :value="item.value">
           </el-option>
         </el-select>
   </div>
         
       <div>
             <el-table
                :data="tabels"
                style="width: 100%"
                :row-class-name="tableRowClassName">
                <el-table-column
                  prop="courseName"
                  label="课程名"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="learnTime"
                  label="课时"
                  width="180">
                </el-table-column>
                <el-table-column
                  prop="courseType"
                  label="课程类型">
                </el-table-column>
                <el-table-column
                  prop="score"
                  label="课程成绩"
                ></el-table-column>
                <el-table-column
                  prop="finalEvaluation"
                  label="课程最终评价"
                ></el-table-column>
             </el-table>
       </div>
  </div>
</template>

<script>
  import {updateData} from '../../static/courseTypeHelper.js'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'courseFinalScore',
    computed:{
      tabels:function(){
				let arr=[];
				if(this.termValue!==''&& this.yearValue!==''&& this.tableData!=='')
				{
					for(let i=0;i<this.tableData.length;i++)
					{
						if(this.tableData[i].term==this.termValue &&this.tableData[i].academicYear==this.yearValue)
						{
							arr.push(this.tableData[i]);
						}
					}
					
				}
				
       return arr;
        
      
      }
      
    },
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
            that.tableData= updateData(that.tableData);
           })
         }
       },
         tableRowClassName({ rowIndex}) {
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
           tableData:[{}],
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
           
         }
       }
    
    
    
  }
</script>

<style>
</style>
