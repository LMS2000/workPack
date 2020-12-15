package learn_user.demo.controller;

import learn_user.demo.domain.student;
import learn_user.demo.service.studentService;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class studentController {
    @Autowired
    private studentService studentService ;
    /*
     * 添加学生
     * */
    @RequestMapping(method = RequestMethod.POST)
    public sysResult addStudent( student student)
    {
        HashMap<String, Object> stringObjectHashMap = studentService.addStudent(student);
        return sysResult.build(200,"ok",stringObjectHashMap);
    }
    /*
     * 学生获取个人信息
     * */
    @CrossOrigin
    @RequestMapping(value = "/getOne",method = RequestMethod.GET)
    public sysResult getStudent(HttpServletRequest request){
        String token =request.getHeader("token");
        HashMap<String, Object> student = studentService.getStudent(token);
        return sysResult.build(200,"ok",student);
    }
    /*
     * 学生修改个人信息
     * */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    public sysResult updateStudent( student student)
    {
        studentService.updateStudent(student );
        return sysResult.build(200,"ok","修改成功");
    }

}
