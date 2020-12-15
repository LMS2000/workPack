package learn_user.demo.service.Impl;
import learn_user.demo.domain.user;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.mapper.courseMapper;
import learn_user.demo.service.userService;
import learn_user.demo.util.tokenWebUtil;
import netscape.security.UserTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private learn_user.demo.mapper.userMapper userMapper;
 @Autowired
    private courseMapper courseMapper ;
    @Override
    public HashMap<String, Object> selectByUserName(String userName, String password) {
        HashMap<String,Object> maps=new HashMap<>();
        user user = userMapper.selectByUserName(userName, password);
        if(user==null)
        {
            maps.put(statusEnum.MSG,"账号或密码错误");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        maps.put("userName",user.getUserName());
        maps.put("userNo",user.getUid());
        String webToken = tokenWebUtil.createWebToken(maps);
        maps.clear();
        maps.put(statusEnum.MSG,"用户登陆成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        maps.put("roleId",user.getRole());
        maps.put("courseStatus",courseMapper.findStatus());
        maps.put("token",webToken);
       return maps;
    }
    public Boolean isDuplicate(String paramName ,String param){
        HashMap<String,Object> maps =new HashMap<>() ;
        maps.put(paramName,param);
        user user  =userMapper.findByUser(maps);
        return user!=null;
    }
@Override
    public HashMap<String,Object> addUser(user u){
        HashMap<String ,Object> maps =new HashMap<>() ;
     if(isDuplicate("userName",u.getUserName())) {
         maps.put(statusEnum.MSG, "用户名已存在");
         maps.put(statusEnum.RES_CODE, statusEnum.STATUS_406);
         return maps;
     }
//     else if(isDuplicate("phone",u.getPhone()))
//     {
//         maps.put("msg","联系电话已存在");
//         maps.put("resCode",0406);
//         return maps;
//     }
     maps.put(statusEnum.MSG,"用户已注册");
     maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
     u.setCreateTime(  new Date());
    userMapper.insertUser(u);
    return maps;
    }
    public boolean isPassword(String userName,String password)
    {
        Map<String,Object> maps=new HashMap<>();
        maps.put("userName",userName);
        maps.put("password",password);
        user byUser = userMapper.findByUser(maps);
        return byUser!=null;
    }
    @Override
    public  Map<String,Object> updateUser(String token,String oldPassword,String newPassword){
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        Map<String,Object> map =new HashMap<>() ;
        String userName =(String)stringObjectMap.get("userName");
        if(!isPassword(userName ,oldPassword))
        {
            map.put(statusEnum.MSG,"旧密码错误");
            map.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return map;
        }

        map.put("userName",userName);
        map.put("updateTime",new Date());
        map.put("password",newPassword);
        userMapper.updatePasswordByUserName(map);
        map.put(statusEnum.MSG,"修改成功");
        map.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return  map;
    }
}
