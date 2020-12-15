package learn_user.demo.controller;

import learn_user.demo.domain.student;
import learn_user.demo.domain.teacher;
import learn_user.demo.service.teacherService;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class teacherController {
    @Autowired
    private teacherService teacherService ;
    /*
    * 插入教师
    *
    * */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public sysResult insertTeacher(teacher teacher)
    {
        HashMap<String,Object> map= teacherService.insertTeacher(teacher);
        return sysResult.build(200,"ok",map);
    }
    /*
     * 修改教师
     * */
    @CrossOrigin
    @RequestMapping( method = RequestMethod.PUT)
    public sysResult updateTeacher(teacher teacher){
        teacherService.updateTeacher(teacher);
        return sysResult.build(200,"ok","修改成功");
    }
    /*
     * 获取班主任老师的班级学生集合
     * */
    @CrossOrigin
    @RequestMapping( value = "/getClassStudent",method = RequestMethod.GET)
    public sysResult getClassStudent(HttpServletRequest request){
        String token = request.getHeader("token");
        List<student> studentByClass = teacherService.findStudentByClass(token);
        return sysResult.build(200,"ok",studentByClass);
    }
    /*
     * 获取教师的集合
     * */
    @CrossOrigin
    @RequestMapping( value = "/getTeacherList",method = RequestMethod.GET)
    public sysResult findByTeacherList(){
        List<teacher>  byTeacher=  teacherService.findByTeacherList();
        return sysResult.build(200,"ok",byTeacher);
    }


}
