<template>
  <div id="courseAdmin">
    <div style="float: left;">
      <el-button type="primary" icon="el-icon-edit" @click="dialogFormVisible=true"></el-button>
      <el-button type="primary"  @click="$router.go(0)">刷新</el-button>
      <el-button type="primary" icon="el-icon-delete" @click="print">导出</el-button> 
    </div>
    <div style="float: right; margin-top: 70px;" >
      <template>开启或关闭退选课：</template>
      <el-switch
        v-model="flag"
        active-color="#13ce66"
        inactive-color="#ff4949">
      </el-switch>
      <el-button @click="postFlag">设置</el-button>
    </div>
    <div style="float: left;">
      <el-upload
             class="upload-demo"
             action=""
             accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
             :on-preview="handlePreview"
             :on-remove="handleRemove"
             :before-remove="beforeRemove"
             :http-request="uploadFile"
             multiple
             :limit="1"
             :on-exceed="handleExceed">
             <el-button size="primary" type="primary" @click="handlePreview">导入</el-button>
             <div slot="tip" class="el-upload__tip">只能上传Excel文件</div>
      </el-upload>
    </div>
    <div style="float: right;">
      <el-input v-model="courseName" placeholder="请输入课程名" style="height: 60px; width: 200px;"></el-input>
      <el-button type="primary" icon="el-icon-search">搜索</el-button>
    </div><br>
    <div style="margin-top: 100px;">
       <el-table
         :data="tables"
         style="width: 100%">
          <el-table-column
            fixed
            prop="courseName"
            label="课程名"
            width="150">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseName)"></span></template>
          </el-table-column>
          <el-table-column
            prop="courseNo"
            label="课程编号"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseNo)"></span></template>
          </el-table-column>
          <el-table-column
            prop="substituteTeacher"
            label="任教老师"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.substituteTeacher)"></span></template>
          </el-table-column>
          <el-table-column
            prop="learnTime"
            label="课时"
            width="120">
          </el-table-column>
          <el-table-column
            prop="courseType"
            label="课程类型"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseType)"></span></template>
          </el-table-column>
          <el-table-column
            prop="coursePlace"
            label="上课地点"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.coursePlace)"></span></template>
          </el-table-column>
          <el-table-column
            prop="courseMaxCount"
            label="课程总人数"
            width="120">
            <template slot-scope="scope"><span v-html="showData(scope.row.courseMaxCount)"></span></template>
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
        <el-dialog title="修改课程"  :visible.sync="dialogVisibleV2">
          <el-form  ref="updateForm" :model="updateForm" :rules="insertFormRoles">
            <el-form-item prop="courseNo" label="课程编号" >
              <el-input v-model="updateForm.courseNo" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  prop="courseName" label="课程名" >
              <el-input v-model="updateForm.courseName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="开课时间" prop="startTime">
               <el-select v-model="updateForm.startTime" placeholder="请选择开课时间">
                 <el-option label="一周" value="一周"></el-option>
                 <el-option label="二周" value="二周"></el-option>
                 <el-option label="三周" value="三周"></el-option>
                 <el-option label="四周" value="四周"></el-option>
                 <el-option label="五周" value="五周"></el-option>
                 <el-option label="六周" value="六周"></el-option>
                 <el-option label="七周" value="七周"></el-option>
                 <el-option label="八周" value="八周"></el-option>
               </el-select>
             </el-form-item>
             <el-form-item label="课程时间" prop="learnTime">
                <el-select v-model="updateForm.learnTime" placeholder="请选择课程时间">
                  <el-option label="4课时" value="4课时"></el-option>
                  <el-option label="6课时" value="6课时"></el-option>
                  <el-option label="8课时" value="8课时"></el-option>
                  <el-option label="10课时" value="10课时"></el-option>
                  <el-option label="12课时" value="12课时"></el-option>
                  <el-option label="14课时" value="14课时"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="结束时间" prop="endTime">
                 <el-select v-model="updateForm.endTime" placeholder="请选择课程结束时间">
                   <el-option label="十二周" value="十二周"></el-option>
                   <el-option label="十三周" value="十三周"></el-option>
                   <el-option label="十四周" value="十四周"></el-option>
                   <el-option label="十五周" value="十五周"></el-option>
                   <el-option label="十六周" value="十六周"></el-option>
                   <el-option label="十七周" value="17周"></el-option>
                 </el-select>
               </el-form-item>
              <el-form-item label="课程类型" prop="courseType">
                 <el-select v-model="updateForm.courseType" placeholder="请选择课程类型">
                   <el-option label="选修课" value="选修课"></el-option>
                   <el-option label="公共限定课" value="公共限定课"></el-option>
                   <el-option label="必修课" value="必修课"></el-option>
                   <el-option label="校际课" value="校际课"></el-option>
                 </el-select>
                </el-form-item>
                <el-form-item label="课程学期" prop="term">
                    <el-radio-group v-model="updateForm.term">
                      <el-radio :label="1" value="1">第一学期</el-radio>
                      <el-radio :label="2" value="2">第二学期</el-radio>
                    </el-radio-group>
                  </el-form-item>
                <el-form-item label="课程学年" prop="academicYear">
                   <el-select v-model="updateForm.academicYear" placeholder="请选择学年">
                     <el-option label="2019" value="2019"></el-option>
                     <el-option label="2020" value="2020"></el-option>
                     <el-option label="2021" value="2021"></el-option>
                   </el-select>
                  </el-form-item>
                  <el-form-item  prop="coursePlace" label="上课地点" >
                    <el-input v-model="updateForm.coursePlace" autocomplete="off"></el-input>
                  </el-form-item>
                <el-form-item  prop="courseDesc" label="课程描述" >
                  <el-input v-model="updateForm.courseDesc" autocomplete="off"></el-input>
                </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVisibleV2 = false">取 消</el-button>
            <el-button type="primary" @click="submitUpdateForm('updateForm')" >修改</el-button>
          </div>
        </el-dialog>
        <el-dialog title="插入课程"  :visible.sync="dialogFormVisible">
          <el-form  ref="insertForm" :model="insertForm" :rules="insertFormRoles">
            <el-form-item prop="courseNo" label="课程编号" >
              <el-input v-model="insertForm.courseNo" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item  prop="courseName" label="课程名" >
              <el-input v-model="insertForm.courseName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="开课时间" prop="startTime">
               <el-select v-model="insertForm.startTime" placeholder="请选择开课时间">
                 <el-option label="一周" value="一周"></el-option>
                 <el-option label="二周" value="二周"></el-option>
                 <el-option label="三周" value="三周"></el-option>
                 <el-option label="四周" value="四周"></el-option>
                 <el-option label="五周" value="五周"></el-option>
                 <el-option label="六周" value="六周"></el-option>
                 <el-option label="七周" value="七周"></el-option>
                 <el-option label="八周" value="八周"></el-option>
               </el-select>
             </el-form-item>
             <el-form-item label="课程时间" prop="learnTime">
                <el-select v-model="insertForm.learnTime" placeholder="请选择课程时间">
                  <el-option label="4课时" value="4课时"></el-option>
                  <el-option label="6课时" value="6课时"></el-option>
                  <el-option label="8课时" value="8课时"></el-option>
                  <el-option label="10课时" value="10课时"></el-option>
                  <el-option label="12课时" value="12课时"></el-option>
                  <el-option label="14课时" value="14课时"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="结束时间" prop="endTime">
                 <el-select v-model="insertForm.endTime" placeholder="请选择课程结束时间">
                   <el-option label="十二周" value="十二周"></el-option>
                   <el-option label="十三周" value="十三周"></el-option>
                   <el-option label="十四周" value="十四周"></el-option>
                   <el-option label="十五周" value="十五周"></el-option>
                   <el-option label="十六周" value="十六周"></el-option>
                   <el-option label="十七周" value="十七周"></el-option>
                 </el-select>
               </el-form-item>
              <el-form-item label="课程类型" prop="courseType">
                  <el-radio-group v-model="insertForm.courseType">
                    <el-radio label="0" value="0">选修课</el-radio>
                    <el-radio label="1" value="1">公共限定课</el-radio>
                    <el-radio label="2" value="2">必修课</el-radio>
                    <el-radio label="3" value="3">校际课</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="课程学期" prop="term">
                    <el-radio-group v-model="insertForm.term">
                      <el-radio label="1" value="1">第一学期</el-radio>
                      <el-radio label="2" value="2">第二学期</el-radio>
                    </el-radio-group>
                  </el-form-item>
                <el-form-item label="课程学年" prop="academicYear">
                    <el-radio-group v-model="insertForm.academicYear">
                      <el-radio label="2019" value="2019"></el-radio>
                      <el-radio label="2020" value="2020"></el-radio>
                      <el-radio label="2021" value="2021"></el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item  prop="coursePlace" label="上课地点" >
                    <el-input v-model="insertForm.coursePlace" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item  prop="courseMaxCount" label="课程总人数" >
                    <el-input v-model="insertForm.courseMaxCount" autocomplete="off"></el-input>
                  </el-form-item>
                <el-form-item  prop="courseDesc" label="课程描述" >
                  <el-input v-model="insertForm.courseDesc" autocomplete="off"></el-input>
                </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="insertCourse('insertForm')">提交</el-button>
          </div>
        </el-dialog>
    </div>    
  </div>
</template>

<script>
  import {updateData} from '../../static/courseTypeHelper.js'
  import {parseData} from '../../static/courseTypeHelper.js'
  import qs from 'qs'
  import axios from '../../static/axios/dist/axios.min.js'
  export default{
    name:'courseAdmin',
    data(){
      return{
        flag:true,
        dialogVisibleV2:false,
        contextData:'',
        dialogFormVisible:false,
        webToken:'',
        roleId:'',
        courseName:'',
        tableData:[{
          courseName:'2323',
          learnTime:'4',
          substituteTeacher:'233',
          courseType:'wee'
        }
        ],
        insertForm:{
          courseNo:'',
          courseName:'',
          startTime:'',
          learnTime:'',
          endTime:'',
          courseType:'',
          term:'',
          academicYear:'',
          courseDesc:'',
          coursePlace:'',
          courseMaxCount:''
        },
        insertFormRoles:{
          courseNo:[{ required: true, message: '请输入课程编号', trigger: 'blur' },
            { min: 8, max: 10, message: '长度在 8 到 10 个字符', trigger: 'blur' }],
          courseName:[{ required: true, message: '请输入课程名', trigger: 'blur' },
            { min: 2, max: 12, message: '长度在 8 到 10 个字符', trigger: 'blur' }],
          startTime:[{required: true, message: '请选择开课时间', trigger: 'change'}],
          learnTime:[{required: true, message: '请选择课程时间', trigger: 'change'}],
          endTime:[{required: true, message: '请选择课程结束时间', trigger: 'change'}],
          courseType:[{required: true, message: '请选择课程类型', trigger: 'change'}],
          term:[{required: true, message: '请指定课程学期', trigger: 'change'}],
          academicYear:[{required: true, message: '请指定课程学年', trigger: 'change'}],
          courseDesc:[{required: true, message: '请写入课程描述', trigger: 'blur'}],
          coursePlace:[{required: true, message: '请写入上课地点', trigger: 'blur'}],
          courseMaxCount:[{required: true, message: '请写入课程总人数', trigger: 'blur'}]
        },
        updateForm:{
          cid:'',
          courseNo:'',
          courseName:'',
          startTime:'',
          learnTime:'',
          endTime:'',
          courseType:'',
          term:'',
          academicYear:'',
          courseDesc:'',
          coursePlace:''
        }
      }
    },
    computed:{
      tables:function(){
        const search =this.courseName;
        if(search)
        {
          let datas= this.tableData.filter(dataNews=>{
            return Object.keys(dataNews).some(key=>{
              return String(dataNews[key]).toLowerCase().indexOf(search)>-1;
            })
            
          });
          this.changeData(datas);
          return datas;
         
          
        }
        return this.tableData;
      }
    },
    mounted(){
      this.getToken();
      this.getTeableDate(this.webToken);
      this.changeData(this.tableData);
      this.getFlag();
    },
    methods: {
      getFlag(){
        
        axios.get("http://localhost:8088/course/findStatus").then(res=>{
          
          let result=res.data.data;
          if(result=='0')
          {
            this.flag=false;
          }else{
             this.flag=true;
          }
         
        })
      },
        postFlag()
      {
        let flag2="";
        if(this.flag==true)
        {
          flag2=1;
        }else{
          flag2=0
        }
        axios.post("http://localhost:8088/course/updateStatus/"+flag2).then(res=>{
          let result = res.data.data;
          this.$message({
            type:'success',
            message:result.msg
          });
          
        })
      },
      submitUpdateForm(updateForm)
      {
        this.updateForm.courseType=parseData(this.updateForm.courseType);
        this.$refs[updateForm].validate((valid) => {
          if(valid){
        axios.put("http://localhost:8088/course",qs.stringify(this.updateForm)).then(res=>{	
          let result =res.data.data;
          this.$message({
            type:'success',
            message:'<strong>'+result.msg+'</strong>'
          });
        })
        
        }else{
          this.$message({
            type:'success',
            message:'<strong>修改失败，输入内容不符！</strong>'
          });
        }
        });
      },
      changeRows(row){
        this.updateForm.cid=row.cid;
        this.updateForm.academicYear=row.academicYear;
        this.updateForm.courseName=row.courseName;
        this.updateForm.courseNo=row.courseNo;
        this.updateForm.startTime=row.startTime;
        this.updateForm.endTime=row.endTime;
        this.updateForm.term=row.term;
        this.updateForm.courseDesc=row.courseDesc;
        this.updateForm.learnTime=row.learnTime;
        this.updateForm.courseType=row.courseType;
        this.updateForm.coursePlace=row.coursePlace;
        this.dialogVisibleV2=true;
      
      },
      uploadFile(item)
      {
        const form =new FormData();
        form.append("file",item.file);
        axios.post("http://localhost:8088/print/import",form).then(res=>{
          let result =res.data.data;
          this.$message({
            type:'success',
            message:result
          });
        }).catch(err=>{
          console.log(err);
        })
        
        
      },
      handleExceed(){
        
      },
      changeData(data)
      {
        this.contextData=data;
      },
      fileList(){
        
      },
      handlePreview(){
        
      },
      handleRemove(){
        
      },
      beforeRemove(){
        
      },
      print(){
        let param = new FormData();
        let copyTableData=this.tableData;
         for(let i =0 ; i<copyTableData.length;i++)
         {
           copyTableData[i].courseType=parseData(copyTableData[i].courseType);
         }
        let courseData = JSON.stringify(copyTableData);
        param.append("courseData",courseData);
        let config = {
            headers: {
                "Content-Type": "multipart/form-data"
            },
            responseType: 'blob',
        };
        axios.post("http://localhost:8088/print",param,config).then(res=>{
          const blob = new Blob([res.data],{type: 'application/vnd.ms-excel'});
                 let filename ='course.xls';
                 //创建一个超链接，将文件流赋进去，然后实现这个超链接的单击事件
                 const elink = document.createElement('a');
                 elink.download = filename;
                 elink.style.display = 'none';
                 elink.href = URL.createObjectURL(blob);
                 document.body.appendChild(elink);
                 elink.click();
                 URL.revokeObjectURL(elink.href); // 释放URL 对象
                 document.body.removeChild(elink);

               this.tableData=updateData(this.tableData);
        }).catch(err=>{
          console.log(err);
        });
      },
      insertCourse(insertForm){
         this.$refs[insertForm].validate((valid) => {
                  if (valid) {
                    axios.post("http://localhost:8088/course",qs.stringify(this.insertForm)).then(res=>{
                      let result =res.data.data;
                      this.$message({
                        dangerouslyUseHTMLString: true,
                        message: '<strong>'+result.msg+'</strong>'
                      }); 
                    })
                    this.dialogFormVisible=false;
                    this.insertForm={};
                  } else {
                    this.$message({
                      dangerouslyUseHTMLString: true,
                      message: '<strong>提交失败！</strong>'
                    }); 
                    return false;
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
          axios.get("http://localhost:8088/course/courseList/"+that.roleId).then(res=>{
          let result =res.data.data;
          that.tableData=updateData(result);
          })
        }
      },
      showData(val){
        val=val+'';
        if(val.indexOf(this.courseName) !==-1 && this.courseName!=='')
        {
          return val.replace(this.courseName,'<font color="#409EFF">'+this.courseName+'</font>');
        }else{
          return val;
        }
      },
          getRowDate(row){
            this.courseRow=row;
          }
    }
  }
</script>

<style>
</style>
