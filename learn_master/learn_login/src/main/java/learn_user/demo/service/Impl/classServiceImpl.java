package learn_user.demo.service.Impl;

import learn_user.demo.domain.Class;
import learn_user.demo.domain.teacher;
import learn_user.demo.enums.statusEnum;
import learn_user.demo.mapper.classMapper;
import learn_user.demo.mapper.teacherMapper;
import learn_user.demo.service.classService;
import learn_user.demo.vo.classDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class classServiceImpl  implements classService {
    @Autowired
      private classMapper classMapper ;
    @Autowired
      private teacherMapper teacherMapper ;
    @Override
    public List<Class> findClassList() {
        return classMapper.findByClassList();
    }

    @Override
    public Map<String, Object> insertClass(classDTO classDTO ) {
        Map<String,Object> maps= new HashMap<>();
        String className= classDTO.getClassName();
        String paramName="className";
        if(isDuplicate(paramName,className)){
            maps.put(statusEnum.MSG,"班级已存在！");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return  maps;
        }
        Map<String, Object> exist = isExist(classDTO);
         boolean isExit=(boolean)  exist.get("exit");
        if(!isExit)
        {
            maps.put(statusEnum.MSG,"教师不存在");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        Class aClass =new Class() ;
        aClass.setCreateTime(new Date());
        aClass.setClassName(className);
        aClass.setClassNo(classDTO.getClassNo());
        aClass.setClassTeacher((Long)exist.get("tid"));

        aClass.setClassCollegeName(classDTO.getClassCollegeName());
         classMapper.insertClass(aClass );
        maps.put(statusEnum.MSG,"录入成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    @Override
    public Map<String, Object> updateClass(classDTO classDTO) {
        Map<String,Object> maps= new HashMap<>();


        Map<String, Object> exist = isExist(classDTO);
        boolean isExit=(boolean)  exist.get("exit");
        if(!isExit)
        {
            maps.put(statusEnum.MSG,"教师不存在");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
       Long tid= (Long)exist.get("tid");
        boolean isTid = isClass(tid);
        if(isTid){
            maps.put(statusEnum.MSG,"该教师已经担任了班主任！");
            maps.put(statusEnum.RES_CODE,statusEnum.STATUS_406);
            return maps;
        }
        Class aClass =new Class() ;
        aClass.setUpdateTime(new Date());
        aClass.setClassName(classDTO.getClassName());
        aClass.setClassTeacher(tid);
        teacher t =new teacher();
        t.setTid(tid);
        t.setClassNo(classDTO.getClassNo());
        teacherMapper.updateTeacher(t);
        aClass.setClassNo(classDTO.getClassNo());
        aClass.setClassCollegeName(classDTO.getClassCollegeName());
        classMapper.updateClass(aClass );
        maps.put(statusEnum.MSG,"修改成功");
        maps.put(statusEnum.RES_CODE,statusEnum.STATUS_200);
        return maps;
    }

    @Override
    public String [] findAllClassNo() {
        return classMapper.findAllClassNo();
    }

    public boolean isClass(Long tid)
    {
        Map<String,Object> maps =new HashMap<>();
        maps.put("classTeacher",tid);
        Class byClass = classMapper.findByClass(maps);
        return  byClass!=null;
    }
    public  Map<String,Object> isExist(classDTO classDTO ){
        Map<String,Object> maps = new HashMap<>();
        teacher t =new teacher();
        t.setTeacherName(classDTO.getClassTeacher());
        teacher byTeacher = teacherMapper.findByTeacher(t);
        maps.put("exit",byTeacher!=null);
        if(byTeacher!=null)
        {
            maps.put("tid",byTeacher.getTid());
        }
     return maps;

    }
    public boolean isDuplicate(String ParamName,Object param){
        Map<String,Object> maps= new HashMap<>();
        maps.put(ParamName,param);
        Class aClass =classMapper.findByClass(maps);
        return  aClass!=null;
    }
}
