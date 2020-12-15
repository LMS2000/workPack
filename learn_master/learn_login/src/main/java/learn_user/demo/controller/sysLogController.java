package learn_user.demo.controller;

import learn_user.demo.service.sysLogService;
import learn_user.demo.vo.sysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/log")
public class sysLogController {
    @Autowired
    private sysLogService sysLogService ;
    /*
     * 获取日志集合
     * */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public sysResult findSysLogList(){
        return sysResult.build(200,"ok",sysLogService.findBySysLogList());

    }

}
