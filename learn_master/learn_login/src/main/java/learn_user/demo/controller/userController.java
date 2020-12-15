package learn_user.demo.controller;
import learn_user.demo.domain.user;
import learn_user.demo.service.userService;
import learn_user.demo.vo.sysResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class userController {
    @Autowired
    private userService userService ;
    /*
    * param:用户的账号和密码
    * 返回的data是一个map，其中存放一个token，一个roleId,一个userName,一个msg信息，一个登陆状态resCode
    * */
    @RequestMapping(value = "/token",method = RequestMethod.POST)
    @CrossOrigin
     public sysResult selectByName(@Param("userName") String userName ,@Param("password") String password){
          HashMap<String,Object> maps = userService.selectByUserName(userName ,password );
        return  sysResult.build(200,"ok",maps ) ;
     }
    /*
     * param:一个user用户对象
     * 返回的data是一个map结果集
     * */
     @CrossOrigin
     @RequestMapping( method = RequestMethod.POST)
     public sysResult addUser( user user)
     {
         HashMap<String, Object> stringObjectHashMap = userService.addUser(user);
         return sysResult.build(200,"ok",stringObjectHashMap);
     }
    /*
     * param:用户输入的旧密码和新密码
     * 验证旧密码，修改用户的密码
     * */
     @CrossOrigin
     @RequestMapping(value = "/update/{oldPassword}/{newPassword}",method =RequestMethod.PUT)
     public sysResult updateUser(HttpServletRequest request,@PathVariable("oldPassword") String oldPassword,@PathVariable String newPassword)
     {
         String token = request.getHeader("token");
         Map<String, Object> stringObjectMap = userService.updateUser(token, oldPassword, newPassword);
         return sysResult.build(200,"ok",stringObjectMap);
     }
}
