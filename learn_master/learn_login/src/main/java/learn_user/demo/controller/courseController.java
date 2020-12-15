package learn_user.demo.controller;

import learn_user.demo.domain.course;
import learn_user.demo.domain.course_student;
import learn_user.demo.service.courseService;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/course")
public class courseController {


    @Autowired
    private courseService courseService ;


    /*
     *param:none
     *说明：返回学生自己的课表信息
     * */
    @RequestMapping(value = "/student",method = RequestMethod.GET)
    @CrossOrigin
    public sysResult  getCourseListByStudent(HttpServletRequest request)
    {
        String webToken= request.getHeader("token");
        List<course> courseList = courseService.getCourseListByStudent(webToken);
       return sysResult.build(200,"ok",courseList);
    }
    /*
     *param:none
     *说明：返回教师自己的已经授课的课程信息
     * */
    @RequestMapping(value = "/teacher",method = RequestMethod.GET)
    @CrossOrigin
    public sysResult  getCourseListByTeacher(HttpServletRequest request)
    {
        String webToken =request.getHeader("token");
        List<course> courseList = courseService.getCourseListByTeacher(webToken);
        return sysResult.build(200,"ok",courseList);
    }
    /*
     *param:cid(课程id)
     *说明：写入学生的选课课程，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @RequestMapping(value = "/student/{cid}",method = RequestMethod.POST)
    @CrossOrigin
    public sysResult insertCourseByStudent(HttpServletRequest request,@PathVariable Integer cid){
        String token =(String)request.getHeader("token");
       Map<String,Object> map= courseService.insertCourseStudent(token ,cid);
       return  sysResult.build(200,"ok",map);
    }
    /*
     *param:cid(课程id)
     *说明：写入教师授课课程，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @RequestMapping(value = "/teacher/{cid}",method = RequestMethod.POST)
    @CrossOrigin
    public sysResult insertCourseByTeacher(HttpServletRequest request,@PathVariable Integer cid){
        String token =(String)request.getHeader("token");
        Map<String,Object> map= courseService.insertCourseTeacher(token, cid);
        return  sysResult.build(200,"ok",map);
    }
    /*
     *param: course(接收一个course pojo对象)
     *说明：写入一个新的课程，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin
    public sysResult insertCourse(course course)
    {
        Map<String,Object> map =   courseService.insertCourse(course );
        return sysResult.build(200,"ok",map);
    }
    /*
     *param:role(用户的role id)
     *说明：返回对应用户权限的课程信息，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @CrossOrigin
    @RequestMapping(value = "/courseList/{role}", method = RequestMethod.GET)
    public sysResult findByCourseList(HttpServletRequest request,@PathVariable Long role)
        {
            String webToken =request.getHeader("token");
            List<course> byCourseList =courseService.findCourseByUnChange(webToken,role );
            return sysResult.build(200,"ok",byCourseList);
    }
    /*
     *param：none
     *说明：返回教师授课的课表学生成绩信息，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @CrossOrigin
    @RequestMapping(value = "/score", method = RequestMethod.GET)
    public sysResult findScoreByStudent(HttpServletRequest request)
    {
        String webToken =request.getHeader("token");
        List<course_student> scoreByCourse = courseService.findScoreByCourse(webToken);
        return sysResult.build(200,"ok",scoreByCourse);
    }
    /*
     *param:none
     *说明：写入学生指定课程的成绩，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @CrossOrigin
    @RequestMapping(value = "/score", method = RequestMethod.PUT)
    public sysResult updateScore(course_student courseStudent )
    {
        Map<String, Object> stringObjectMap = courseService.updateScore(courseStudent );
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     *param:course(接收一个course pojo对象)
     *说明：修改指定课程的相关信息，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @CrossOrigin
    @RequestMapping( method = RequestMethod.PUT)
    public sysResult updateCourse(course course )
    {
        Map<String, Object> stringObjectMap = courseService.updateCourse(course );
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     *param:cid(课程id)
     *说明： 删除指定课程信息，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/{cid}", method = RequestMethod.DELETE)
    public sysResult deleteCourse(@PathVariable Long cid,HttpServletRequest request)
    {
        String token = request.getHeader("token");
        Map<String, Object> stringObjectMap = courseService.deleteCourseByStudentId(cid,token);
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     *param:cid(课程id)
     *说明： 获取学生自己的选修课课程信息，方便退选课程，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/findCourseDelete", method = RequestMethod.GET)
    public sysResult findCourseToDelete(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        List<course> courseListByStudentV2 = courseService.getCourseListByStudentV2(token);
        return sysResult.build(200,"ok",courseListByStudentV2);
    }
    /*
     *param:status(退选课程功能的是否启用状态)
     *说明：修改退选课程功能的状态，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/updateStatus/{status}", method = RequestMethod.POST)
    public sysResult updateStatus(@PathVariable Integer status)
    {
        Map<String, Object> stringObjectMap = courseService.updateStatus(status);
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     *param:none
     *说明： 查看退选课程功能的状态，返回一个结果集
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/findStatus", method = RequestMethod.GET)
    public sysResult findStatus()
    {
        return sysResult.build(200,"ok",courseService.findStatus());
    }
    /*
     *param:none
     *说明： 获取全部的必修课的课程名
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/findAllCid", method = RequestMethod.GET)
    public sysResult findAllCid()
    {
        return sysResult.build(200,"ok",courseService.findAllCid());
    }
    /*
     *param:cid(课程名),classNo(班级名)
     *说明： 写入一个班级指定的课程，如果成功返回一个成功的集合（一个状态码和一个msg信息）
     * */
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value ="/{classNo}/{cid}", method = RequestMethod.POST)
    public sysResult insertClassCourse(@PathVariable String classNo,@PathVariable String cid)
    {
        Map<String, Object> stringObjectMap = courseService.insertSomeCourse(classNo, cid);
        return sysResult.build(200,"ok",stringObjectMap);
    }
}
