package learn_user.demo.service.Impl;
import learn_user.demo.domain.Class;
import learn_user.demo.domain.student;
import learn_user.demo.domain.user;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.mapper.classMapper;
import learn_user.demo.mapper.studentMapper;
import learn_user.demo.mapper.userMapper;
import learn_user.demo.service.studentService;
import learn_user.demo.util.tokenWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class studentServiceImpl implements studentService {
    @Autowired
      private studentMapper studentMapper;
    @Autowired
      private userMapper  userMapper;
    @Autowired
    private classMapper classMapper ;
    @Override
    public void updateStudent(student student) {
        studentMapper.updateStudent(student );
    }
    public Boolean isDuplicate(student student){
        Map<String ,Object> maps = new HashMap<>();
        maps.put("studentNo",student.getStudentNo());
        student byStudent = studentMapper.findByStudent(maps);
        return byStudent!=null;
    }
    @Override
    public HashMap<String,Object> getStudent(String token){
        Map<String, Object> maps =tokenWebUtil.parserWebToken(token);
        Integer userId =(Integer)maps.get("userNo");
        HashMap<String,Object> map=new HashMap<>();
        map.put("userNo",userId);
        student byStudent = studentMapper.findByStudent(map);
        map.put("student",byStudent);
        map.put(statusEnum.MSG,"ok");
        map.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return map;
    }
    @Override
    public HashMap<String, Object> addStudent(student student) {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("classNo", student.getClassNo());
        Class byClass = classMapper.findByClass(maps);
        if (byClass == null) {
            maps.put(statusEnum.MSG, "班级不存在");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        if(isDuplicate(student))
        {
            maps.put(statusEnum.MSG,"学生信息已存在");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        Long userNo =new Long(student.getClassNo());
        Long studentNo =new Long(student.getStudentNo());
        Long Uid =userNo+studentNo;
        user user1 =new user();
        user1.setPassword("123456");
        user1.setUserName(student.getStudentNo());
        user1.setRole(1);
        user1.setCreateTime(new Date());
        user1.setUid(Uid);
        userMapper.insertUser(user1);
        student.setUserNo(Uid);
        studentMapper.insertStudent(student);
        maps.put(statusEnum.MSG,"学生信息已录入");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }


}
