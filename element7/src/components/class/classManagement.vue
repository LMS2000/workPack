<template>
  <div id="classManagement" >
    <div style="float: left; margin-bottom: 30px;">
      <el-popover
        style="margin-right: 15px;"
        placement="right"
        trigger="click">
       <el-form ref="addForm" :model="addForm" :rules="studentRoles" label-width="80px">
         <el-form-item prop="studentName" label="学生姓名">
           <el-input type="text" auto-complete="off" v-model="addForm.studentName"></el-input>
         </el-form-item>
         <el-form-item label="性别">
           <el-radio-group auto-complete="off"  v-model="addForm.gender">
             <el-radio label="0">男</el-radio>
             <el-radio label="1">女</el-radio>
           </el-radio-group>
         </el-form-item>
         <el-form-item prop="studentNo" label="学号">
           <el-input type="text" auto-complete="off" v-model="addForm.studentNo"></el-input>
         </el-form-item>
         <el-form-item>
           <el-button type="primary" @click="onSubmit('addForm')">立即创建</el-button>
           <el-button>取消</el-button>
         </el-form-item>
       </el-form>
        <el-button  type="primary" icon="el-icon-edit" slot="reference"></el-button>
      </el-popover>
      <el-button type="primary"  @click="$router.go(0)">刷新</el-button>
    </div>	
    <div style="float: right;">
      <el-input v-model="search" placeholder="请输入学生名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary"  icon="el-icon-search">搜索</el-button>
    </div>
    <div>
         <el-table :data="tables"  style="width: 100%" stripe>
             <el-table-column prop="sid" label="学生编号" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.sid)"></span></template>
             </el-table-column>
             <el-table-column prop="studentNo" label="学号" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.studentNo)" ></span></template>
             </el-table-column>
             <el-table-column prop="gender" label="性别" width="180">
               <template slot-scope="scope"><span v-html="showData(scope.row.gender)"></span></template>
             </el-table-column>
             <el-table-column prop="studentName" label="学生姓名">
               <template slot-scope="scope"><span v-html="showData(scope.row.studentName)"></span></template>
             </el-table-column>
             <el-table-column
               fixed="right"
               label="操作"
               width="120">
               <template slot-scope="scope" >
                 <el-button @click="dialogVisible=true; sopeIndex=scope.$index" type="text" size="small">删除</el-button>
                 <el-button type="text" @click="changeRows(scope.row)"  size="small">编辑</el-button>
               </template>
             </el-table-column>
         </el-table>
         <el-dialog
           title="提示"
           :visible.sync="dialogVisible"
           width="30%"
           :before-close="handleClose">
           <span>确定要删除吗</span>
           <span slot="footer" class="dialog-footer">
             <el-button @click="dialogVisible = false">取 消</el-button>
             <el-button type="primary" @click="dialogVisible = false; deleteRow(sopeIndex,tableData)">确 定</el-button>
           </span>
         </el-dialog>
         <el-dialog title="学生详细信息"  :visible.sync="dialogFormVisible">
           <el-form :model="insertForm" :rules="studentRoles">
             <el-form-item prop="addStudentName" label="学生姓名" >
               <el-input v-model="insertForm.addStudentName" autocomplete="off"></el-input>
             </el-form-item>
             <el-form-item  prop="addStudentNo" label="学号" >
               <el-input v-model="insertForm.addStudentNo" autocomplete="off"></el-input>
             </el-form-item>
            <el-form-item label="性别">
              <el-radio-group   v-model="insertForm.addGender">
                <el-radio :label="0" >男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
           </el-form>
           <div slot="footer" class="dialog-footer">
             <el-button @click="dialogFormVisible = false">取 消</el-button>
             <el-button type="primary" @click="dialogFormVisible = false;updateStudent()">确 定</el-button>
           </div>
         </el-dialog>
    </div>
  </div>
</template>

<script>
  import axios from '../../static/axios/dist/axios.min.js'
  import qs from 'qs'
  export default{
    name:'classManagement',
    data(){
      var validateStudentName = (rule, value, callback) => {
        const testStudentName =/^[\u0391-\uFFE5]+$/;
             if (value === '') {
               callback(new Error('请输入学生姓名'));
             } else {
               if (!testStudentName.test(value)) {
                 callback(new Error('请输入正确的格式！'));
               }else{
                 callback();
               } 
               
             }
           };
      var validateStudentNo = (rule, value, callback) => {
        const testStudentNo = /^[1-9][0-9]{7}$/;
            if (value === '') {
              callback(new Error('请输入学号'));
            } else {
              if (!testStudentNo.test(value)) {
                callback(new Error('请输入正确的学号格式！'));
              }else 
              {
                callback();
              }
               
            }
          };
      return{
              search:'',
              sopeIndex:'',
              gridData:[{}],
              dialogFormVisible: false,
              dialogVisible: false,
              insertForm:{
                addStudentName:'',
                addGender:'',
                addStudentNo:'',
                sid:''
              },
              addForm:{
                studentName:'',
                gender:'',
                studentNo:'',
                classNo:'602219'
              },
              studentRoles:{
                studentName:[{ validator: validateStudentName, trigger: 'blur'}],
                studentNo:[{validator: validateStudentNo, trigger: 'blur'}],
                addStudentName:[{ validator: validateStudentName, trigger: 'blur'}],
                addStudentNo:[{validator: validateStudentNo, trigger: 'blur'}]
               },
            tableData:[{
                    date: '2016-05-02',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1518 弄'
                  }, {
                    date: '2016-05-04',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1517 弄'
                  }, {
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                  }, {
                    date: '2016-05-03',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1516 弄'
            }]
       
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
      updateData(data){
        for(let i=0; i<data.length;i++)
        {
          if(data[i].gender=='0')
					{
						data[i].gender='男';
					}
					else{
						data[i].gender='女';
					}
        }
        
      },
      updateStudent(){
        let updateS={
          studentName:this.insertForm.addStudentName,
          studentNo:this.insertForm.addStudentNo,
          gender:this.insertForm.addGender,
          sid:this.insertForm.sid
        };
        axios.put("http://localhost:8088/student",qs.stringify(updateS)).then(res=>{
          let result = res.data.data;
          this.$message({
            dangerouslyUseHTMLString: true,
            message: '<strong>'+result+'</strong>'
          });
          
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
           axios.get("http://localhost:8088/teacher/getClassStudent").then(res=>{
           let result =res.data.data;
           that.tableData=result;
           this.updateData(this.tableData);
           })
         }
       },
      showData(val){
        val=val+'';
        if(val.indexOf(this.search) !==-1 && this.search!=='')
        {
          return val.replace(this.search,'<font color="#409EFF">'+this.search+'</font>');
        }else{
          return val;
        }
        
      },
      changeRows(row){
        let that =this;
        this.dialogFormVisible=true;
        that.insertForm.addStudentName=row.studentName;
        that.insertForm.addGender=row.Gender;
        that.insertForm.addStudentNo=row.studentNo
        that.insertForm.sid=row.sid;
      },
      deleteRow(index,rows){
        
        rows.splice(index,1);
      },
      handleClose(){
        
      },
      handleClick(){
        
      },
      onSubmit(addForm){
        this.$refs[addForm].validate((valid) => {
        if(valid){
            let student =this.addForm;
           axios.defaults.headers.common['token'] =this.webToken;
           axios.post("http://localhost:8088/student",qs.stringify(student)).then((res)=>{
            let result =res.data.data;
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
      }
    }    
   }
</script>

<style>
</style>
