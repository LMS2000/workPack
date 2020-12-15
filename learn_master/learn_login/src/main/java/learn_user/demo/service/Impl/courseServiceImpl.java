package learn_user.demo.service.Impl;

import learn_user.demo.domain.*;
import learn_user.demo.domain.Class;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.mapper.*;
import learn_user.demo.service.courseService;
import learn_user.demo.util.tokenWebUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class courseServiceImpl  implements courseService
{
    @Autowired
    private studentMapper studentMapper ;
    @Autowired
    private courseMapper courseMapper ;
    @Autowired
    private teacherMapper teacherMapper ;
    @Autowired
    private classMapper classMapper ;
    @Override
    public List<course> findByCourseList() {

        return courseMapper.findByCourseList();
    }

    @Override
    public Map<String, Object> insertSomeCourse( String classNo, String courseName) {
        Map<String,Object> maps =new HashMap<>();
        maps.put("className",classNo);
        Class byClass = classMapper.findByClass(maps);
        Long[] sidByClassNo = classMapper.findSidByClassNo(byClass.getClassNo());
        maps.put("courseName",courseName);
        course byCourse = courseMapper.findByCourse(maps);
        Map<String,Object> insert = new HashMap<>();
        for(Long sid: sidByClassNo)
        {
            insert.put("sid",sid);
            insert.put("cid",byCourse.getCid());
            course_student courseStudent = courseMapper.findCourseStudent(insert);
            if(courseStudent!=null){

              continue;
            }
            insert.put("createTime",new Date());
            courseMapper.insertCourseStudent(insert);
        }
        maps.put(statusEnum.MSG,"录入成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    public Boolean isDuplicate(String paramName,String param)
    {
        Map<String,Object>  map = new HashMap<>();
        map.put(paramName,param);
        course byCourse = courseMapper.findByCourse(map);
        return  byCourse!=null;
    }
    @Override
    public List<course> getCourseListByStudent(String token)
    {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        student s =  studentMapper.findByStudent(stringObjectMap );
        return courseMapper.findCourseByStudent(s.getSid());
    }
    @Override
    public List<course> getCourseListByTeacher(String token)
    {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
          teacher  t = new teacher();
          Integer userNo =(Integer) stringObjectMap.get("userNo");
          t.setUserNo(userNo.longValue());
        teacher byTeacher = teacherMapper.findByTeacher(t);
        long tid = byTeacher.getTid();
        return courseMapper.findCourseByTeacher(tid);
    }
    @Override
    public Map<String, Object> insertCourse(course course) {
        HashMap<String,Object> map =new HashMap<>();
      String paramName ="courseNo";
      String param = course.getCourseNo();
      boolean flag= isDuplicate(paramName , param);
      if(flag)
      {
          map.put(statusEnum.MSG,"课程已存在");
          map.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
          return map;
      }
      courseMapper.insertCourse(course );
      map.put(statusEnum.MSG,"录入成功");
      map.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
      return map;
    }

    @Override
    public Map<String,Object> updateCourse(course course) {
        Map<String,Object> maps =new HashMap<>();
            String paramName ="cid";
            Long param = course.getCid();
            if(!isDuplicate(paramName,param.toString())) {
                maps.put(statusEnum.MSG,"找不到对应课程");
                maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
            course.setUpdateTime(new Date());
        courseMapper.updateCourse(course );
        maps.put(statusEnum.MSG,"修改成功");
        maps.put(statusEnum.STATUS_200,statusEnum.STATUS_200);
        return  maps;
    }

    @Override
    public Map<String, Object> insertCourseStudent(String token ,Integer courseId) {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        student s = studentMapper.findByStudent(stringObjectMap);
        stringObjectMap.clear();
        stringObjectMap.put("sid",s.getSid());
        stringObjectMap.put("createTime",new Date());
        stringObjectMap.put("cid",courseId);
        courseMapper.insertCourseStudent(stringObjectMap);
        stringObjectMap.clear();
        stringObjectMap.put(statusEnum.MSG,"录入成功");
        stringObjectMap.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return stringObjectMap;
    }

    @Override
    public Map<String, Object> insertCourseTeacher(String token,Integer courseId)
    {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        Integer userId=  (Integer) stringObjectMap.get("userNo");
        teacher t =new teacher();
        t.setUserNo(userId.longValue());
        t =teacherMapper.findByTeacher(t);
        Map<String,Object> map= new HashMap<>();
        map.put("tid",t.getTid());
        map.put("updateTime",new Date());
        map.put("cid",courseId);
        courseMapper.updateCourseByTeacher(map);
        map.clear();
        map.put(statusEnum.MSG,"修改成功");
        map.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return map;
    }

    @Override
    public List<course> findCourseByUnChange(String token,long role) {
        List<course> courseByUnChange;
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        if(role==1){
            student s =  studentMapper.findByStudent(stringObjectMap );
            List<course> courseByStudent = courseMapper.findCourseByStudent(s.getSid());
            if(courseByStudent.isEmpty()){
                return courseMapper.findByCourseList();
            }else{
                return  courseMapper.findCourseByUnChange(s.getSid());
            }
        }else if(role ==2)
        {
            teacher t = new teacher();
            Integer userNo =(Integer) stringObjectMap.get("userNo");
            t.setUserNo(userNo.longValue());
            teacher byTeacher = teacherMapper.findByTeacher(t);
            List<course> courseByTeacher = courseMapper.findCourseByTeacher(byTeacher.getTid());
            if(courseByTeacher.isEmpty())
            {
                return courseMapper.findByCourseList();
            }
            else{
                return  courseMapper.findCourseByUnChangeTeacher(byTeacher.getTid());
            }

        }else if(role==3)
        {
           return   courseMapper.findByCourseList();
        }


     return null;
    }

    @Override
    public List<course_student> findScoreByCourse(String token) {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        Integer userNo = (Integer) stringObjectMap.get("userNo");
          teacher t = new teacher();
          t.setUserNo(userNo.longValue());
        teacher byTeacher = teacherMapper.findByTeacher(t);
        List<course_student> scoreByCourse = courseMapper.findScoreByCourse(byTeacher.getTid());
        return scoreByCourse;
    }

    @Override
    public Map<String, Object> updateScore(course_student course_student ) {
        Map<String,Object> maps =new HashMap<>();
        maps.put("studentName",course_student.getStudentName());
        maps.put("courseName",course_student.getCourseName());
        student byStudent = studentMapper.findByStudent(maps);
        course byCourse = courseMapper.findByCourse(maps);
        if(byStudent.getSid()==null || byCourse.getCid()==null)
        {
            maps.clear();
            maps.put(statusEnum.MSG,"修改失败");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return  maps;
        }
        course_student.setCourseFk(byCourse.getCid());
        course_student.setStudentFk(byStudent.getSid());
        courseMapper.updateScore(course_student);
        maps.clear();
        maps.put(statusEnum.MSG,"修改成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    @Override
    public Map<String, Object> deleteCourseByStudentId(Long cid,String token) {
        Map<String,Object> maps =tokenWebUtil.parserWebToken(token);
           if( courseMapper.findStatus().equals("0")) {
              maps.clear();
              maps.put(statusEnum.MSG,"删除未开放！");
              maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
              return  maps;
           }
        student byStudent = studentMapper.findByStudent(maps);
        maps.clear();
        if(byStudent.getSid()==null||cid==null)
        {
            maps.put(statusEnum.MSG,"删除失败！");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        maps.put("studentFk",byStudent.getSid());
        maps.put("courseFk",cid);
        courseMapper.deleteCourseByStudentId(maps );
        maps.clear();
        maps.put(statusEnum.MSG,"删除成功！");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    @Override
    public List<course> getCourseListByStudentV2(String token) {
        if(courseMapper.findStatus()==0){return null;}
        Map<String,Object> maps=tokenWebUtil.parserWebToken(token);
        student byStudent = studentMapper.findByStudent(maps);
        List<course> courseByStudentV2 = courseMapper.findCourseByStudentV2(byStudent.getSid());
        return courseByStudentV2;
    }

    @Override
    public Map<String, Object> updateStatus(Integer status) {
        Map<String,Object> maps= new HashMap<>();
        courseMapper.updateStatus(status.intValue());
        if(status==0)
        {
            maps.put(statusEnum.MSG,"关闭成功") ;
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
            return maps;
        }
         maps.put(statusEnum.MSG,"开启成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    @Override
    public Integer findStatus() {
        return courseMapper.findStatus();
    }

    @Override
    public String [] findAllCid() {
        return courseMapper.findAllCid();
    }


}
