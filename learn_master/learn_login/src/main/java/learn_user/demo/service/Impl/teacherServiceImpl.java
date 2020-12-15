package learn_user.demo.service.Impl;

import learn_user.demo.domain.student;
import learn_user.demo.domain.teacher;
import learn_user.demo.domain.user;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.mapper.studentMapper;
import learn_user.demo.mapper.teacherMapper;
import learn_user.demo.mapper.userMapper;
import learn_user.demo.service.teacherService;
import learn_user.demo.util.tokenWebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class teacherServiceImpl implements teacherService {
    @Autowired
    private teacherMapper teacherMapper ;
    @Autowired
    private studentMapper studentMapper ;
    @Autowired
      private userMapper userMapper ;
    @Override
    public HashMap<String,Object> insertTeacher(teacher teacher) {
        HashMap<String,Object> maps =new HashMap<>();
        String param =teacher.getTeacherNo();
        boolean isDuplicate = isDuplicate("teacherNo",param);
        if(!isDuplicate)
        {
            Long classNo=new Long(0);
            if(teacher.getClassNo()!=null)
            {
                 classNo =new Long(teacher.getClassNo());
            }
            Long teacherNo =new Long(teacher.getTeacherNo());
            Long Uid =classNo+teacherNo;
            user user1 =new user();
            user1.setPassword("123456");
            user1.setUserName(teacher.getTeacherNo());
            user1.setRole(2);
            user1.setCreateTime(new Date());
            user1.setUid(Uid);
            userMapper.insertUser(user1);
            teacher.setUserNo(Uid);
             teacherMapper.insertTeacher(teacher);
             maps.put(statusEnum.MSG,"录入成功");
             maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
             return maps;
        }
        maps.put(statusEnum.MSG,"录入失败");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
        return maps;
    }

    @Override
    public void  updateTeacher(teacher teacher)
    {
     teacher.setUpdateTime(new Date());
     teacherMapper.updateTeacher(teacher);
    }

    @Override
    public List<student> findStudentByClass(String token) {
        Map<String, Object> stringObjectMap = tokenWebUtil.parserWebToken(token);
        teacher t =new teacher();
        Integer userNo =(Integer)stringObjectMap.get("userNo");
        t.setUserNo(userNo.longValue());
         teacher t2=  teacherMapper.findByTeacher(t);
        List<student> studentByClass = studentMapper.findStudentByClass(t2.getTid());
        return studentByClass;
    }
    public Boolean isDuplicate(String paramName ,String param){
        HashMap<String,Object> maps =new HashMap<>() ;
        maps.put(paramName,param);
        teacher teacher1  =teacherMapper.findByTeacherV2(maps );
        return teacher1!=null;
    }
    @Override
    public List<teacher> findByTeacherList() {
    List<teacher> byTeacher = teacherMapper.findByTeacherList();
        return byTeacher;
    }
}
