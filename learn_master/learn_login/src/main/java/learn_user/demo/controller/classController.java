package learn_user.demo.controller;

import learn_user.demo.service.classService;
import learn_user.demo.vo.classDTO;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/class")
public class classController {
    @Autowired
    private classService classService ;
    /*
     * 获取全部班级集合
     * */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public sysResult findClassList()
    {
        return sysResult.build(200,"ok",classService.findClassList());
    }
    /*
     * 写入班级
     * */
    @RequestMapping(method = RequestMethod.POST)
    @CrossOrigin
    public sysResult insertClass(classDTO  aClass ){
        Map<String, Object> stringObjectMap = classService.insertClass(aClass);
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     * 修改指定班级
     * */
    @RequestMapping(method = RequestMethod.PUT)
    @CrossOrigin
    public sysResult updateClass(classDTO aClass){
        Map<String, Object> stringObjectMap = classService.updateClass(aClass );
        return sysResult.build(200,"ok",stringObjectMap);
    }
    /*
     * 获取全部班级名
     * */
    @RequestMapping(value = "/allClassNo",method = RequestMethod.GET)
    @CrossOrigin
    public sysResult findAllClassNo(){
        return sysResult.build(200,"ok",classService.findAllClassNo());
    }

}
