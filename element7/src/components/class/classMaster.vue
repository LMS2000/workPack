<template>
  <div id="classMaster">
    <div style="float: left; margin-bottom: 30px;">
      <el-button type="primary"  @click="dialogVisible=true">创建</el-button>
      <el-button type="primary"  @click="$router.go(0)">刷新</el-button>
      <el-button type="primary"   @click="dialogFormVisibleV2=true"  >按照班级授课</el-button>
    </div>	
    <div style="float: right;">
      <el-input v-model="search" placeholder="请输入班级名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary"  icon="el-icon-search">搜索</el-button>
    </div>
    <el-table :data="tables"  style="width: 100%" stripe>
        <el-table-column prop="classNo" label="班级ID" width="180">
          <template slot-scope="scope"><span v-html="showData(scope.row.classNo)"></span></template>
        </el-table-column>
        <el-table-column prop="className" label="班级名" width="180">
          <template slot-scope="scope"><span v-html="showData(scope.row.className)" ></span></template>
        </el-table-column>
        <el-table-column prop="classTeacherStr" label="班主任" width="180">
          <template slot-scope="scope"><span v-html="showData(scope.row.classTeacherStr)"></span></template>
        </el-table-column>
        <el-table-column prop="classCollegeName" label="所属学院">
          <template slot-scope="scope"><span v-html="showData(scope.row.classCollegeName)"></span></template>
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="120">
          <template slot-scope="scope" >
            <el-button type="text" @click="changeRows(scope.row)"  size="small">编辑</el-button>
          </template>
        </el-table-column>
    </el-table>
    <el-dialog title="插入信息"  :visible.sync="dialogVisible">
      <el-form  ref="insertForm" :model="insertForm" :rules="insertFormRoles">
        <el-form-item prop="classNo" label="班级编号" >
          <el-input v-model="insertForm.classNo" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item  prop="className" label="班级名" >
          <el-input v-model="insertForm.className" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班主任" prop="classTeacher">
          <el-input v-model="insertForm.classTeacher" autocomplete="off"></el-input>
        </el-form-item>
       <el-form-item label="所属院系" prop="classCollegeName">
         <el-input v-model="insertForm.classCollegeName" autocomplete="off"></el-input>
       </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false;insertData('insertForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="修改信息"  :visible.sync="dialogFormVisible">
      <el-form  ref="updateForm" :model="updateForm" :rules="insertFormRoles">
        <el-form-item  prop="className" label="班级名" >
          <el-input v-model="updateForm.className" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="班主任" prop="classTeacher">
          <el-input v-model="updateForm.classTeacher" autocomplete="off"></el-input>
        </el-form-item>
       <el-form-item label="所属院系" prop="classCollegeName">
         <el-input v-model="updateForm.classCollegeName" autocomplete="off"></el-input>
       </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false;updateData('updateForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="班级基本课程插入"  :visible.sync="dialogFormVisibleV2">
      <el-form  ref="insertCourseForm" :model="insertCourseForm" >
        <el-form-item prop="insertClassNo" label="班级" >
         <el-select v-model="insertCourseForm.insertClassNo" placeholder="请选择班级名">
           <el-option v-for="item in allClass" :key="item.index" :label="item" :value="item"></el-option>
         </el-select>
        </el-form-item>
        <el-form-item  prop="insertCid" label="课程ID" >
          <el-select v-model="insertCourseForm.insertCid" placeholder="请选择课程名">
             <el-option  v-for="item in allCourse" :key="item.index" :label="item" :value="item"></el-option>
          </el-select>
          
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisibleV2 = false">取 消</el-button>
        <el-button type="primary" @click="insertAllCourse('insertCourseForm')">确 定</el-button>
      </div>
    </el-dialog>
    
  </div>
</template>

<script>
  import qs from 'qs'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
     name:'classMaster',
     data(){
       return{		 
         insertForm:{
           classNo:'',
           className:'',
           classCollegeName:'',
           classTeacher:''
         },
         insertCourseForm:{
           insertClassNo:'',
           insertCid:''
         },
         allCourse:[{}],
         allClass:[{}],
         updateForm:{
           classNo:'',
           className:'',
           classCollegeName:'',
           classTeacher:''
         },
         dialogFormVisibleV2:false,
         dialogVisible:false,
         dialogFormVisible:false,
         search:'',
         webToken:'',
         classList:[{}],
         insertCourseFormRoles:{
           insertClassNo:[{required: true, message: '请选择班级名', trigger: 'change'}],
           insertCid:[{required: true, message: '请选择课程', trigger: 'change'}]
         },
         insertFormRoles:{
           classNo:[{ required: true, message: '请输入班级编号', trigger: 'blur' },
             { min: 6, max: 6, message: '长度为6的字符', trigger: 'blur' }],
           className:[{ required: true, message: '请输入班级名', trigger: 'blur' },
             { min: 2, max: 12, message: '长度在 8 到 10 个字符', trigger: 'blur' }],
           classTeacher:[{required: true, message: '请输入班主任', trigger: 'blur'}],
           classCollegeName:[{required: true, message: '请选择院系', trigger: 'blur'}]
         }
       }
     },
     mounted(){
       this.getToken();
       this.getTeableDate(this.webToken);
       this.getAllCourseAndClass();
     },
     computed:{
       tables:function(){
         const search =this.search;
         if(search)
         {
           return this.classList.filter(dataNews=>{
             return Object.keys(dataNews).some(key=>{
               return String(dataNews[key]).toLowerCase().indexOf(search)>-1;
             })
             
           })
           
         }
         return this.classList;
       }
     },
     methods:{
       getAllCourseAndClass()
       {
         axios.get("http://localhost:8088/class/allClassNo").then(res=>{
           console.log(res)
           let result =res.data.data;
           this.allClass=result;
         });
         axios.get("http://localhost:8088/course/findAllCid").then(res=>{
           console.log(res)
           let result=res.data.data;
           this.allCourse=result;
         })
       },
       insertAllCourse(insertCourseForm){
         let classNo=this.insertCourseForm.insertClassNo;
         let cid=this.insertCourseForm.insertCid;
         this.$refs[insertCourseForm].validate((valid) => {
         if(valid){
         axios.post("http://localhost:8088/course/"+classNo+"/"+cid).then(res=>{
           let result = res.data.data;
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
         this.dialogFormVisibleV2=false;
       },
       updateData(updateForm){
         this.$refs[updateForm].validate((valid) => {
         if(valid){
             let aclass =this.updateForm;
            axios.defaults.headers.common['token'] =this.webToken;
            axios.put("http://localhost:8088/class",qs.stringify(aclass)).then((res)=>{
             let result =res.data.data;
               this.$message({
                 dangerouslyUseHTMLString: true,
                 message: '<strong>'+result.msg+'</strong>'
               });   
            })
            }else{
              this.$message({
                dangerouslyUseHTMLString: true,
                message: '<strong>修改失败！</strong>'
              });   
            }
            });
         
       },
       insertData(insertForm)
       {
         this.$refs[insertForm].validate((valid) => {
         if(valid){
             let aclass =this.insertForm;
            axios.defaults.headers.common['token'] =this.webToken;
            axios.post("http://localhost:8088/class",qs.stringify(aclass)).then((res)=>{
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
            axios.get("http://localhost:8088/class").then(res=>{
            let result =res.data.data;
            that.classList=result;
            })
          }
        },
       changeRows(row){
        this.dialogFormVisible=true;
        this.updateForm.className=row.className;
        this.updateForm.classTeacher=row.classTeacherStr;
        this.updateForm.classCollegeName=row.classCollegeName;
        this.updateForm.classNo=row.classNo;
       }
       
     }
    
  }
</script>

<style>
</style>
