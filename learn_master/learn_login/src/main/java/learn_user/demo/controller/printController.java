package learn_user.demo.controller;

import com.alibaba.fastjson.JSON;
import learn_user.demo.domain.course;
import learn_user.demo.enums.printEnum;
import learn_user.demo.service.courseService;
import learn_user.demo.util.printUtil;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/print")
public class printController {
    @Autowired
    private courseService courseService ;
    /*
     * 输出课程以excel的格式
     * */
    @CrossOrigin
    @RequestMapping( method = RequestMethod.POST)
    public void printSome(@RequestParam("courseData") String  courseData, HttpServletResponse response) throws IOException {

       List<course> courseList=   JSON.parseArray(courseData,course.class);
        ServletOutputStream outputStream = response.getOutputStream();
        printUtil.exportExcel(outputStream,courseList, printEnum.getCurseTitle());
        outputStream.close();
    }
    /*
     * 获取excel文件流然后批量写入到数据库
     * */
    @CrossOrigin
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    public sysResult importExcel(@RequestParam("file") MultipartFile multipartFile ) throws Exception {
        String s = printUtil.batchImport(multipartFile, courseService);
        return sysResult.build(200,"ok",s);
    }
}
